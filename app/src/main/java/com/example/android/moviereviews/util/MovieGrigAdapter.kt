package com.example.android.moviereviews.screens.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.moviereviews.databinding.GridViewItemBinding
import com.example.android.moviereviews.domain.Movie

class MovieGridAdapter(val clickListener: MovieListener) : ListAdapter<Movie,
        MovieGridAdapter.MovieViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movieImg = getItem(position)
        holder.bind(movieImg, clickListener)
    }

    class MovieViewHolder(private val binding: GridViewItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(Movie: Movie, clickListener: MovieListener) {
            binding.movie = Movie
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.displayTitle == newItem.displayTitle
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.imgSrcUrl == newItem.imgSrcUrl
        }
    }
}

class MovieListener(val clickListener: (url: String) -> Unit) {
    fun onClick(movie: Movie) = clickListener(movie.url)
}