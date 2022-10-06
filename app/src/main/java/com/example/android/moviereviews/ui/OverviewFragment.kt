/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.moviereviews.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.android.moviereviews.R
import com.example.android.moviereviews.databinding.FragmentOverviewBinding
import com.example.android.moviereviews.util.MovieGridAdapter
import com.example.android.moviereviews.util.MovieListener
import com.example.android.moviereviews.viewmodels.OverviewViewModel

class OverviewFragment : Fragment() {
    lateinit var binding: FragmentOverviewBinding

    private val viewModel: OverviewViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "You can only access the viewModel after onActivityCreated()"
        }
        ViewModelProvider(
            this,
            OverviewViewModel.Factory(activity.application)
        )[OverviewViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_overview, container, false)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.photosGrid.adapter = MovieGridAdapter(MovieListener { url ->
            viewModel.onMovieClicked(url)
        })

        viewModel.navigateToDetail.observe(viewLifecycleOwner, Observer { url ->
            url?.let {
                this.findNavController().navigate(
                    OverviewFragmentDirections
                        .actionOverviewFragmentToDetailsFragment(
                            url,
                            context?.isInternetAvailable() ?: false
                        )
                )
                viewModel.onMovieDetailNavigated()
            }
        })
        return binding.root
    }

    private fun Context.isInternetAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                val cap = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
                return cap.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            }
        else -> return true
        }
    }
}
