package com.example.android.moviereviews.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
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


        if (args.internetAvailable) {
            binding.webView.settings.cacheMode = WebSettings.LOAD_DEFAULT // Использовать стратегию кэширования по умолчанию, когда сеть нормальная
        } else {
            binding.webView.settings.cacheMode = WebSettings.LOAD_CACHE_ONLY // Использовать кеш, только когда сеть недоступна
        }

            binding.webView.loadUrl(args.url)


        return binding.root
    }



}