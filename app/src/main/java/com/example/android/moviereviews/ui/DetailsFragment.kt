package com.example.android.moviereviews.ui

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import android.webkit.*
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.android.moviereviews.R
import com.example.android.moviereviews.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {
    lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)

        startLoaderAnimate()
        binding.webView.setWebViewSettings()
        binding.webView.loadUrl(args.url)
        return binding.root
    }

    private fun WebView.setWebViewSettings() {
        binding.webView.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK

        webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                endLoaderAnimate()
            }
        }
    }

    private fun endLoaderAnimate() {
        binding.loaderImage.clearAnimation()
        binding.loaderImage.visibility = View.GONE
    }

    private fun startLoaderAnimate() {
        val objectAnimator = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                val startHeight = 170
                val newHeight = (startHeight + (startHeight + 40) * interpolatedTime).toInt()
                binding.loaderImage.layoutParams.height = newHeight
                binding.loaderImage.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }
        objectAnimator.repeatCount = -1
        objectAnimator.repeatMode = ValueAnimator.REVERSE
        objectAnimator.duration = 1000
        binding.loaderImage.startAnimation(objectAnimator)
    }

}