package com.mmfsin.noexcuses

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.realm.Realm

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Realm.init(this)
    }
}