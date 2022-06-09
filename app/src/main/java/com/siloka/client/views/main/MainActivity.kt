package com.siloka.client.views.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.siloka.client.R
import com.siloka.client.adapter.MessageAdapter
import com.siloka.client.data.models.MessageModel
import com.siloka.client.databinding.ActivityMainBinding
import com.siloka.client.views.adapter.ShortcutAdapter
import com.siloka.client.views.entity.Topic
import com.siloka.client.views.settings.SettingsActivity
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val USER_KEY = "user"
    private val BOT_KEY = "bot"
    private val listTopics = ArrayList<Topic>()

    private var mRequestQueue: RequestQueue? = null

    // creating a variable for array list and adapter class.
    private lateinit var messageModelArrayList: ArrayList<MessageModel>
    private var messageAdapter: MessageAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val view = binding.root
        setContentView(view)

        mRequestQueue = Volley.newRequestQueue(this@MainActivity)
        mRequestQueue!!.cache.clear()

        // creating a new array list
        messageModelArrayList = ArrayList()
        binding.edChat.setText("")
        // adding on click listener for send message button.

        //harus dibikin flag dulu
        showShortcut()


        binding.sendButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                if (binding.edChat.text.toString().isEmpty()) {
                    // if the edit text is empty display a toast message.
                    Toast.makeText(
                        this@MainActivity,
                        "Please enter your message..",
                        Toast.LENGTH_SHORT
                    ).show()
                    return
                }

                sendMessage(binding.edChat.text.toString())

                binding.edChat.setText("")

                messageAdapter = MessageAdapter(messageModelArrayList, this@MainActivity)

                val linearLayoutManager =
                    LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)

                binding.rvChat.layoutManager = linearLayoutManager

                binding.rvChat.adapter = messageAdapter
            }
        })
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun sendMessage(userMsg: String) {
        messageModelArrayList.add(MessageModel(userMsg, USER_KEY))
        messageAdapter?.notifyDataSetChanged()

      //Implement Wiring yang di Dicoding aja
    }

    private fun showShortcut() {
        val linearLayoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        binding.rvChat.layoutManager = linearLayoutManager
        val listHeroAdapter = ShortcutAdapter(listTopics)
        binding.rvChat.adapter = listHeroAdapter
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
                val i = Intent(this, SettingsActivity::class.java)
                startActivity(i)
                true
            }
            else -> true
        }
    }

    companion object {
        private const val USER_KEY = "user"
        private const val BOT_KEY = "bot"
    }
}
