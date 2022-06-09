package com.siloka.client.views.main

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
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

    private var rvChatroom: RecyclerView? = null
    private var ibSendMessage: RelativeLayout? = null
    private var etChatbox: EditText? = null

//    private var mRequestQueue: RequestQueue? = null

    private var messageAdapter: MessageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Log.d("INFO", "akakak")

        setHeader()
        bindRV()
        bindChatbox()
    }

    private fun setHeader() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FF39C4F3")))
    }

//    private fun setRequestQueue() {
//        mRequestQueue = Volley.newRequestQueue(this@MainActivity)
//        mRequestQueue!!.cache.clear()
//    }

    private fun bindRV() {
        messageAdapter = MessageAdapter()
        rvChatroom?.adapter = messageAdapter
        rvChatroom?.layoutManager = LinearLayoutManager(this)

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
        messageAdapter?.insertMessage(
            MessageModel(0, "ayo dong bantai kami", BOT_KEY))
        messageAdapter?.insertMessage(
            MessageModel(1, "kalo elo punya nyali", USER_KEY))
        messageAdapter?.insertMessage(
            MessageModel(0, "tongkronang kami bukan tongrkongan pecundang", BOT_KEY))
        messageAdapter?.insertMessage(
            MessageModel(1, "pecundang", BOT_KEY))
        messageAdapter?.insertMessage(
            MessageModel(1, "pecundang", BOT_KEY))
    }

    private fun sendMessage(userMsg: String) {
        messageAdapter?.insertMessage(MessageModel(1, userMsg, USER_KEY))

//        val url = "Enter you API URL here$userMsg"
//        val queue = Volley.newRequestQueue(this@MainActivity)
//
//        val jsonObjectRequest = JsonObjectRequest(
//            Request.Method.GET,
//            url,
//            null, {
//                try {
//                    val botResponse = it.getString("cnt")
//                    messagesList.add(MessageModel(0, botResponse, BOT_KEY))
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
