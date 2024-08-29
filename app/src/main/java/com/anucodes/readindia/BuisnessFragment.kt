package com.anucodes.readindia

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest

class BuisnessFragment : Fragment(R.layout.fragment_buisness), NewsAdapter.onItemClicked {

    private lateinit var mbusinessAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_buisness, container, false)
        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.buisnessRecyclerView)


        recyclerView.layoutManager  = LinearLayoutManager(context)
        getData()
        mbusinessAdapter = NewsAdapter( this)
        recyclerView.adapter = mbusinessAdapter

        return view
    }

    private fun getData() {

        val apiUrl = "https://newsdata.io/api/1/latest?apikey=pub_201840ff62d175f70208810c2460569221e50&q=business"
        Log.i("The api is: ", apiUrl)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, apiUrl,
            null,
            { response ->
                val newsItemArray = response.getJSONArray("results")
                val newsItem = ArrayList<News>()
                for (i in 0 until newsItemArray.length()) {
                    val newsjsonObject = newsItemArray.getJSONObject(i)
                    val news = News(
                        newsjsonObject.getString("title"),
                        newsjsonObject.getString("link"),
                        newsjsonObject.getString("image_url"),
                        newsjsonObject.getString("source_name")
                    )
                    newsItem.add(news)
                }
                mbusinessAdapter.updateNews(newsItem)
            },
            { error ->
                Log.i("There is an error", "The error is: $error")
                val newsItem = ArrayList<News>()
                for (i in 0 until 100){
                    val errorNews = News(
                        "The data is broken",
                        "https://www.google.com/",
                        "https://images.wondershare.com/repairit/aticle/2021/07/resolve-images-not-showing-problem-1.jpg",
                        "Not found!!"
                    )
                    newsItem.add(errorNews)
                }
                mbusinessAdapter.updateNews(newsItem)
            }
        )
        context?.let { MySingleton.getInstance(it).addToRequestQueue(jsonObjectRequest) }

    }

    override fun itemClicked(item: News) {
        TODO("Not yet implemented")
    }
}