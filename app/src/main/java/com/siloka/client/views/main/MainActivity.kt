package com.siloka.client.views.main

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.siloka.client.R
import com.siloka.client.adapter.MessageAdapter
import com.siloka.client.data.models.MessageModel
import com.siloka.client.data.preferences.UserPreferences
import com.siloka.client.databinding.ActivityMainBinding
import com.siloka.client.utilities.showToast
import com.siloka.client.views.ViewModelFactory
import com.siloka.client.views.settings.SettingsActivity
import org.json.JSONException
import org.json.JSONObject

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var viewModel: MainViewModel

    private var rvChatroom: RecyclerView? = null
    private var ibSendMessage: RelativeLayout? = null
    private var etChatbox: EditText? = null

    private lateinit var popularTopicsMsgObj: MessageModel
    private lateinit var responseFeedbackMsgObj: MessageModel
    private lateinit var directToCsMsgObj: MessageModel
    private lateinit var loadingMsgObj: MessageModel

    private lateinit var roomId: String
    private lateinit var mRequestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setHeader()
        setViewModel()
        setFixedMessages()
        setRequestQueue()
        getRoomId()

        bindRV()
        bindChatbox()
    }

    private fun setHeader() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FF39C4F3")))
    }

    private fun setViewModel () {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreferences.getInstance(dataStore))
        )[MainViewModel::class.java]
    }

    private fun setFixedMessages() {
        popularTopicsMsgObj = MessageModel(POPULAR_TOPICS, null)
        responseFeedbackMsgObj = MessageModel(RESPONSE_FEEDBACK_PROMPT, null)
        directToCsMsgObj = MessageModel(DIRECT_TO_CS_PROMPT, null)
        loadingMsgObj = MessageModel(LOADING_MESSAGE, null)
    }

    private fun setRequestQueue() {
        mRequestQueue = Volley.newRequestQueue(this@MainActivity)
        mRequestQueue.cache.clear()
    }

    private fun getRoomId() {
        val getRoomIdRequest = JsonObjectRequest(
            Request.Method.GET,
            "${BASE_API_URL}${GET_ROOMID_PATH}",
            null,
            {
                try {
                    roomId = it.getString("room_id")
                    Log.i("SILOKA", "Successfully acquired room_id: $roomId")

                    // Only render greetings and poptop
                    // once roomId is set
                    setInitialMessages()
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.e("SILOKA", "Error parsing room_id data", e)
                    showToast(this, getString(R.string.err_client))
                }
            }
        ) {
            it.printStackTrace()
            Log.e("SILOKA", "Error on acquiring response from server", it)
            showToast(this, getString(R.string.err_server))
        }

        mRequestQueue.add(getRoomIdRequest)
    }

    private fun bindRV() {
        messageAdapter = MessageAdapter(this@MainActivity)
        rvChatroom = binding.rvChatroom
        rvChatroom?.adapter = messageAdapter
        rvChatroom?.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun bindChatbox() {
        etChatbox = binding.etChatbox
        ibSendMessage = binding.btnSendChat

        ibSendMessage?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (etChatbox?.text.toString().isEmpty()) {
                    Toast.makeText(
                        this@MainActivity,
                        "Please enter your message..",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                sendMessage(etChatbox?.text.toString())
                etChatbox?.setText("")
            }
        })
    }

    private fun setInitialMessages() {
        viewModel.getUser().observe(this, {
            messageAdapter.insertMessage(
                MessageModel(BOT_MESSAGE, "Hi, ${it.name} I'm Siloka, nice to meet you!"))
            messageAdapter.insertMessage(
                MessageModel(BOT_MESSAGE, "Let me help you find something you need."))
            messageAdapter.insertMessage(
                MessageModel(BOT_MESSAGE, "Choose any of the options below, or type your problem on the chatbox!"))
            messageAdapter.insertMessage(popularTopicsMsgObj)
        })
    }

    fun sendMessage(userMsg: String) {
        messageAdapter.insertMessage(MessageModel(USER_MESSAGE, userMsg))
        scrollToLatestMessage()

        if (!this::roomId.isInitialized) {
            showToast(this, "Error on connecting with our server")
            return
        }

        val postUserMessageRequest = JsonObjectRequest(
            Request.Method.POST,
            "${BASE_API_URL}${POST_MESSAGE_PATH}",
            JSONObject(
                mutableMapOf(
                    "room_id" to roomId,
                    "query" to userMsg
                ) as Map<String, Any?>
            ),
            {
                try {
                    showBotResponse(
                        MessageModel(
                            it.getInt("viewType") or 0,
                            it.getString("message"),
                        )
                    )
                    Log.i("SILOKA", "Successfully posted message & get bot response")
                } catch (e: JSONException) {
                    e.printStackTrace()
                    Log.e("SILOKA", "Error on parsing JSON", e)
                    showToast(this, getString(R.string.err_client))
                }
            },
        ) {
            it.printStackTrace()
            Log.e("SILOKA", "Error on acquiring response from server", it)
            showToast(this, getString(R.string.err_server))
        }

        mRequestQueue.add(postUserMessageRequest)
    }

    private fun showBotResponse(messageObj: MessageModel) {
        setLoading(true)
        messageAdapter.insertMessage(
            MessageModel(
                BOT_MESSAGE,
                messageObj.message
            )
        )
        scrollToLatestMessage()
        setLoading(false)

        Handler(Looper.getMainLooper())
            .postDelayed({
                setLoading(true)
                messageAdapter.insertMessage(responseFeedbackMsgObj)
                scrollToLatestMessage()
                setLoading(false)
            }, 1000)
    }

    private fun showDirectToCSPrompt() {
        setLoading(true)
        Handler(Looper.getMainLooper())
            .postDelayed({
                messageAdapter.insertMessage(directToCsMsgObj)
                scrollToLatestMessage()
            }, 1000)
        setLoading(false)
    }

    fun sendFeedback(isResponseOk: Boolean) {
        when(isResponseOk) {
            true -> {
                messageAdapter.insertMessage(MessageModel(USER_MESSAGE, "Yes"))
            }
            false -> {
                messageAdapter.insertMessage(MessageModel(USER_MESSAGE, "No"))
                Handler(Looper.getMainLooper())
                    .postDelayed({
                        showDirectToCSPrompt()
                    }, 1000)
            }
        }
        scrollToLatestMessage()
    }

    fun sendToCs(isSendToCs: Boolean) {
        when(isSendToCs) {
            true -> {
                messageAdapter.insertMessage(MessageModel(USER_MESSAGE, "Yes"))
            }
            false -> {
                messageAdapter.insertMessage(MessageModel(USER_MESSAGE, "No"))
            }
        }
        scrollToLatestMessage()
    }

    private fun setLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> messageAdapter.insertMessage(loadingMsgObj)
            false -> messageAdapter.removeMessage(loadingMsgObj)
        }
        scrollToLatestMessage()
    }

    private fun scrollToLatestMessage() {
        binding.rvChatroom.scrollToPosition(messageAdapter.itemCount - 1)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.option_setting -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> true
        }
    }

    companion object {
        private const val BOT_MESSAGE = 0
        private const val USER_MESSAGE = 1
        private const val POPULAR_TOPICS = 2
        private const val RESPONSE_FEEDBACK_PROMPT = 3
        private const val DIRECT_TO_CS_PROMPT = 4
        private const val LOADING_MESSAGE = 5

        private const val BASE_API_URL = "http://35.240.219.237"
        private const val GET_ROOMID_PATH = "/generate-roomid"
        private const val POST_MESSAGE_PATH = "/message"
    }
}
