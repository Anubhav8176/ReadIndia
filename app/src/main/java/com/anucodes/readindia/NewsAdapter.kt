package com.anucodes.readindia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsAdapter(private val listener: onItemClicked) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private var news: ArrayList<News> = ArrayList()

    interface onItemClicked{
        fun itemClicked(item: News)
    }

    inner class NewsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.title)
        val image: ImageView = itemView.findViewById(R.id.image)
        val source: TextView = itemView.findViewById(R.id.publisher)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        //Inflate the .xml file to the view that can be used further.
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.news_layout, parent, false)

        val viewHolder = NewsViewHolder(view)

        view.setOnClickListener {
            listener.itemClicked(news[viewHolder.adapterPosition])
        }

        return viewHolder

    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.title.text = news[position].title
        holder.source.text = "Source: "+news[position].author
        Glide.with(holder.itemView.context).load(news[position].imgUrl).into(holder.image)
    }

    fun updateNews(updatedNews: ArrayList<News>){
        news.clear()
        news.addAll(updatedNews)

        notifyDataSetChanged()
    }
}
