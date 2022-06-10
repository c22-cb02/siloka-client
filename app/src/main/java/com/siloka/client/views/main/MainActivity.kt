package com.siloka.client.views.main

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

//    private var mRequestQueue: RequestQueue? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setHeader()
        bindRV()
        bindChatbox()
    }

    private fun setHeader() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FF39C4F3")))
    }

//    private fun setRequestQueue() {
//        mRequestQueue = Volley.newRequestQueue(this@MainActivity)
//        mRequestQueue!!.cache.clear()
//    }

    private fun bindRV() {
        messageAdapter = MessageAdapter()
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
        messageAdapter.insertMessage(
            MessageModel(2, null))
    }

    private fun sendMessage(userMsg: String) {
        messageAdapter.insertMessage(MessageModel(1, userMsg))

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
