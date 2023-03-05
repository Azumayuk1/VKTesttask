package com.sergei.vktesttask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        setContentView(R.layout.activity_main)
        //Toast.makeText(this, R.string.try_search, Toast.LENGTH_LONG).show()
    }
}