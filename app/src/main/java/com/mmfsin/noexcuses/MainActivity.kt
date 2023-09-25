package com.mmfsin.noexcuses

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mmfsin.noexcuses.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Realm.init(this)
    }
}