package com.sherazsadiq.workwise

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PlaceOrderActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_place_order)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val buttonGotoBack = findViewById<ImageView>(R.id.button_gotoBack)
        buttonGotoBack.setOnClickListener {
            finish()
        }

        val buttonContinue = findViewById<RelativeLayout>(R.id.button_continueOrder)
        buttonContinue.setOnClickListener {
            val intent = Intent(this, UpgradeOrderActivity::class.java)
            startActivity(intent)
        }

        val buttonHome = findViewById<FrameLayout>(R.id.button_clienthome)
        buttonHome.setOnClickListener {
            val intent = Intent(this, ClientHomeActivity::class.java)
            startActivity(intent)
        }


        val buttonChat = findViewById<FrameLayout>(R.id.button_clientchat)
        buttonChat.setOnClickListener {
            val intent = Intent(this, ClientChatInboxActivity::class.java)
            startActivity(intent)
        }


        val buttonSearch = findViewById<FrameLayout>(R.id.button_clientsearch)
        buttonSearch.setOnClickListener {
            val intent = Intent(this, SearchScreenActivity::class.java)
            startActivity(intent)
        }

        val buttonProfile = findViewById<FrameLayout>(R.id.button_clientprofile)
        buttonProfile.setOnClickListener {
            val intent = Intent(this, ClientProfileActivity::class.java)
            startActivity(intent)
        }
    }
}