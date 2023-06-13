package com.mmfsin.noexcuses.presentation.webview

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWebViewBinding

    private var dataURL: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setWebView()
    }

    private fun setWebView() {
        dataURL = intent.getStringExtra("dataURL")
        binding.apply {
//            toolbar.title.text = getString(R.string.how_to_do_it)
            toolbar.ivBack.setOnClickListener { finish() }
            dataURL?.let { url -> webview.loadUrl(url) }
        }
    }

}