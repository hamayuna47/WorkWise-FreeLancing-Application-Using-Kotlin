package com.sherazsadiq.workwise

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SellerHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_seller_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val Homebtn= findViewById<FrameLayout>(R.id.Home)
        Homebtn.setOnClickListener {
            val intent = Intent(this, SellerHomeActivity::class.java)
            startActivity(intent)
        }

        val Chatbtn = findViewById<FrameLayout>(R.id.Chat)
        Chatbtn.setOnClickListener {
            val intent = Intent(this, SellerInboxActivity::class.java)
            startActivity(intent)
        }

        val Orders = findViewById<FrameLayout>(R.id.Orders)
        Orders.setOnClickListener {
            val intent = Intent(this, ActiveOrdersActivity::class.java)
            startActivity(intent)
        }
        val Profile = findViewById<FrameLayout>(R.id.Profile)
        Profile.setOnClickListener {
            val intent = Intent(this, SellerProfileActivity::class.java)
            startActivity(intent)
        }
    }
}