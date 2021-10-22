package com.example.anvisdigitalassignment.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.anvisdigitalassignment.R
import com.example.anvisdigitalassignment.data.model.Search
import com.example.anvisdigitalassignment.utils.show

class CustomAdapterMovies(
    private var moviesList : ArrayList<Search?>,
    val itemClickListener: OnItemClickListener
    ) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return if (viewType == VIEW_TYPE_ITEM) {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_movie_list, parent, false)
            MovieViewHolder(view)
        } else {
            val view: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_lazy_loading, parent, false)
            LoadingViewHolder(view)
        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (holder is MovieViewHolder) {
            if (moviesList[position] != null) {
                holder.bindItems(moviesList[position]!!, itemClickListener)
            }
        } else if (holder is LoadingViewHolder) {
            holder.showLoadingView()
        }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (moviesList[position] == null) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newMoviesList: ArrayList<Search?>?) {
        if (newMoviesList != null) {
            if (moviesList.isNotEmpty())
                moviesList.removeAt(moviesList.size - 1)
            moviesList.clear()
            moviesList.addAll(newMoviesList)
        } else {
            moviesList.add(newMoviesList)
        }
        notifyDataSetChanged()
    }

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imagePoster: ImageView = itemView.findViewById(R.id.img_poster)
        private val textTitle: TextView = itemView.findViewById(R.id.txt_movie_name)
        private val textYear: TextView = itemView.findViewById(R.id.txt_year)

        @SuppressLint("SetTextI18n")
        fun bindItems(movie: Search, clickListener: OnItemClickListener) {
            textTitle.text = movie.Title
            textYear.text = movie.Year
            Glide.with(imagePoster.context).load(movie.Poster)
                .centerCrop()
                .thumbnail(0.5f)
                .placeholder(R.drawable.ic_launcher_background)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imagePoster)

            itemView.setOnClickListener {
                clickListener.onItemClicked(movie)
            }
        }
    }

    class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val progressBar: ProgressBar = itemView.findViewById(R.id.progress_bar)

        fun showLoadingView() {
            progressBar.show()
        }
    }

    interface OnItemClickListener{
        fun onItemClicked(movie: Search)
    }

}