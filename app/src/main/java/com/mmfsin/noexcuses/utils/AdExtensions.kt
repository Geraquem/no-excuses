package com.mmfsin.noexcuses.utils

import android.content.Context
import androidx.core.view.isVisible
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.mmfsin.noexcuses.R
import com.mmfsin.noexcuses.databinding.NativeAdBinding

fun Context.loadNativeAds(nativeAdView: NativeAdView, container: NativeAdBinding) {
    val adLoader = AdLoader.Builder(this, this.getString(R.string.native_ad))
        .forNativeAd { nativeAd ->
            populateNativeAdView(nativeAd, nativeAdView, container)
        }.withAdListener(object : AdListener() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                nativeAdView.isVisible = false
            }
        }).build()
    adLoader.loadAd(AdRequest.Builder().build())
}

private fun populateNativeAdView(
    nativeAd: NativeAd,
    nativeAdView: NativeAdView,
    container: NativeAdBinding
) {
    container.apply {
        tvTitle.text = nativeAd.headline
        tvDescription.text = nativeAd.body
        val icon = nativeAd.icon
        icon?.let {
            image.setImageDrawable(icon.drawable)
            image.isVisible = true
        } ?: run { image.isVisible = false }

        item.isVisible = !nativeAd.callToAction.isNullOrBlank()
        nativeAdView.callToActionView = item

        nativeAdView.isVisible = true
        nativeAdView.setNativeAd(nativeAd)
    }
}