package com.example.movieapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class MovieAdapter(private val movies: List<Movie>) : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}


class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val titleTextView: TextView = view.findViewById(R.id.titleTextView)
    private val descriptionTextView: TextView = view.findViewById(R.id.overviewTextView)
    private val imageView: ImageView = view.findViewById(R.id.posterImageView) as ImageView

    fun bind(movie: Movie) {
        titleTextView.text = movie.title
        descriptionTextView.text = movie.overview
//        Glide.with(imageView.context)
//            .load(movie.imagePath)
//            .into(imageView)
    }
}

