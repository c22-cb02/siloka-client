package com.siloka.client.views.main

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.siloka.client.R
import com.siloka.client.adapter.MessageAdapter
import com.siloka.client.data.models.MessageModel
import com.siloka.client.databinding.ActivityMainBinding
import com.siloka.client.views.settings.SettingsActivity
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var rvChatroom: RecyclerView? = null
    private var ibSendMessage: RelativeLayout? = null
    private var etChatbox: EditText? = null

    private var mRequestQueue: RequestQueue? = null

    private lateinit var messagesList: ArrayList<MessageModel>
    private var messageAdapter: MessageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setHeader()
        setRequestQueue()
        setMessages()
        bindChatbox()
    }

    private fun setHeader() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FF39C4F3")))
    }

    private fun setRequestQueue() {
        mRequestQueue = Volley.newRequestQueue(this@MainActivity)
        mRequestQueue!!.cache.clear()
    }

    private fun setMessages() {
        etChatbox = binding.etChatbox
        ibSendMessage = binding.btnSendChat

        messagesList = ArrayList()
        etChatbox?.setText("")
    }

    private fun bindChatbox() {
        ibSendMessage?.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (etChatbox!!.text.toString().isEmpty()) {
                    // if the edit text is empty display a toast message.
                    Toast.makeText(
                        this@MainActivity,
                        "Please enter your message..",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                sendMessage(etChatbox!!.text.toString())
                etChatbox!!.setText("")
                bindRV()
            }
        })
    }

    private fun bindRV() {
        messageAdapter = MessageAdapter(messagesList, this@MainActivity)

        val linearLayoutManager =
            LinearLayoutManager(
                this@MainActivity,
                RecyclerView.VERTICAL,
                false
            )

        rvChatroom?.layoutManager = linearLayoutManager
        rvChatroom?.adapter = messageAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sendMessage(userMsg: String) {
        messagesList.add(MessageModel(1, userMsg, USER_KEY))
        messageAdapter?.notifyDataSetChanged()

        val url = "Enter you API URL here$userMsg"
        val queue = Volley.newRequestQueue(this@MainActivity)
        
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null, {
                try {
                    val botResponse = it.getString("cnt")
                    messagesList.add(MessageModel(0, botResponse, BOT_KEY))

                    messageAdapter?.notifyDataSetChanged()
                } catch (e: JSONException) {
                    e.printStackTrace()

                    messagesList.add(MessageModel(0,"No response", BOT_KEY))
                    messageAdapter?.notifyDataSetChanged()
                }
            }
        ) {
            messagesList.add(MessageModel(0,"Sorry no response found", BOT_KEY))
            Toast.makeText(
                this@MainActivity,
                "No response from the bot..",
                Toast.LENGTH_SHORT
            ).show()
        }

        queue.add(jsonObjectRequest)
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
            R.id.menu1 -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> true
        }
    }

    companion object {
        private const val USER_KEY = "user"
        private const val BOT_KEY = "siloka"
    }
}
