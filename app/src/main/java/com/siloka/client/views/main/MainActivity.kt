package com.siloka.client.views.main

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.siloka.client.R
import com.siloka.client.adapter.MessageAdapter
import com.siloka.client.data.models.MessageModel
import com.siloka.client.databinding.ActivityMainBinding
import com.siloka.client.views.settings.SettingsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var messageAdapter: MessageAdapter

    private var rvChatroom: RecyclerView? = null
    private var ibSendMessage: RelativeLayout? = null
    private var etChatbox: EditText? = null

    private lateinit var popularTopicsMsgObj: MessageModel
    private lateinit var responseFeedbackMsgObj: MessageModel
    private lateinit var directToCsMsgObj: MessageModel
    private lateinit var loadingMsgObj: MessageModel

//    private var mRequestQueue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setHeader()
        setFixedMessages()
        bindRV()
        bindChatbox()
    }

    private fun setHeader() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FF39C4F3")))
    }

    private fun setFixedMessages() {
        popularTopicsMsgObj = MessageModel(2, null)
        responseFeedbackMsgObj = MessageModel(3, null)
        directToCsMsgObj = MessageModel(4, null)
        loadingMsgObj = MessageModel(5, null)
    }

//    private fun setRequestQueue() {
//        mRequestQueue = Volley.newRequestQueue(this@MainActivity)
//        mRequestQueue!!.cache.clear()
//    }

    private fun bindRV() {
        messageAdapter = MessageAdapter(this@MainActivity)
        rvChatroom = binding.rvChatroom
        rvChatroom?.adapter = messageAdapter
        rvChatroom?.layoutManager = LinearLayoutManager(applicationContext)

        setInitialMessages()
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
        messageAdapter.insertMessage(
            MessageModel(0, "Hi, Brodie! I'm Siloka, nice to meet you!"))
        messageAdapter.insertMessage(
            MessageModel(0, "Let me help you find something you need."))
        messageAdapter.insertMessage(
            MessageModel(0, "Choose any of the options below, or type your problem on the chatbox!"))
        messageAdapter.insertMessage(popularTopicsMsgObj)
    }

    fun sendMessage(userMsg: String) {
        messageAdapter.insertMessage(MessageModel(1, userMsg))
        scrollToLatestMessage()
        showResponse()

//        val url = "Enter you API URL here$userMsg"
//        val queue = Volley.newRequestQueue(this@MainActivity)
//
//        val jsonObjectRequest = JsonObjectRequest(
//            Request.Method.GET,
//            url,
//            null, {
//                try {
//                    val botResponse = it.getString("cnt")
//                    messagesList.add(MessageModel(0, botResponse))
//                    messageAdapter?.notifyItemInserted(messagesList.size - 1)
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                    messagesList.add(MessageModel(0,"No response", BOT_KEY))
//                    messageAdapter?.notifyItemInserted(messagesList.size - 1)
//                }
//            }
//        ) {
//            messagesList.add(MessageModel(0,"Sorry no response found", BOT_KEY))
//            Toast.makeText(
//                this@MainActivity,
//                "No response from the bot..",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//
//        queue.add(jsonObjectRequest)
    }

    private fun showResponse() {
        setLoading(true)
        messageAdapter.insertMessage(
            MessageModel(
                0,
                """To pay for your booking via Indomaret or Alfamart, follow these steps: \n \n \

                1. After you’ve completed your booking details, choose Indomaret or Alfamart on the payment page. \n \
                2. Tap Pay at Indomaret/Alfamart. \n \
                3. Go to the nearest Indomaret/Alfamart and show your payment code to the cashier. Then, make the payment. Make sure to ask the cashier for the receipt. \n \
                4. Once your payment at Indomaret or Alfamart is completed, your Traveloka e-ticket/voucher and receipt will be sent to the email address you used for booking within 60 minutes. \n \n \

                Things to note: \n \n \

                1. For each transaction, Alfamart charges a fee of Rp2.500 while Indomaret charges Rp5.000. \n \
                2. Payment via Indomaret or Alfamart is only available for transactions amounting up to Rp5.000.000. \n \
                For further information : https://www.traveloka.com/en-id/help/general-info/general-information-payment/paying-in-idr/how-to-pay-for-my-booking-via-indomaret-or-alfamart
                """
            ))
        scrollToLatestMessage()
        setLoading(false)

        setLoading(true)
        Handler(Looper.getMainLooper())
            .postDelayed({
                messageAdapter.insertMessage(responseFeedbackMsgObj)
                scrollToLatestMessage()
            }, 1000)
        setLoading(false)
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
                messageAdapter.insertMessage(MessageModel(1, "Yes"))
            }
            false -> {
                messageAdapter.insertMessage(MessageModel(1, "No"))
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
                messageAdapter.insertMessage(MessageModel(1, "Yes"))
            }
            false -> {
                messageAdapter.insertMessage(MessageModel(1, "No"))
            }
        }
        scrollToLatestMessage()
    }

    private fun setLoading(isLoading: Boolean) {
        when (isLoading) {
            true -> {
                messageAdapter.insertMessage(loadingMsgObj)
                scrollToLatestMessage()
            }
            false -> {
                Handler(Looper.getMainLooper())
                    .postDelayed({
                        messageAdapter.removeMessage(loadingMsgObj)
                        scrollToLatestMessage()
                    }, 1000)
            }
        }
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
}
