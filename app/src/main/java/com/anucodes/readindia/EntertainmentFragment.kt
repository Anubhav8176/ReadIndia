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

class EntertainmentFragment : Fragment(), NewsAdapter.onItemClicked {

    private lateinit var mEntertainmentAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_entertainment, container, false)

        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.entertainmentRecyclerView)


        recyclerView.layoutManager  = LinearLayoutManager(context)
        getData()
        mEntertainmentAdapter = NewsAdapter(this)
        recyclerView.adapter = mEntertainmentAdapter

        return view
    }

    private fun getData() {
        val apiUrl = "https://newsdata.io/api/1/latest?apikey=pub_201840ff62d175f70208810c2460569221e50&q=entertainment"
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
                mEntertainmentAdapter.updateNews(newsItem)
            },
            { error ->
                Log.i("There is an error", "The error is: "+error.message)
                val newsItem = ArrayList<News>()
                for (i in 0 until 100){
                    val errorNews = News(
                        "The data is broken",
                        "https://www.google.com/",
                        "https://images.wondershare.com/repairit/aticle/2021/07/resolve-images-not-showing-problem-1.jpg",
                        "Not found"
                    )
                    newsItem.add(errorNews)
                }
                mEntertainmentAdapter.updateNews(newsItem)
            }
        )
        context?.let { MySingleton.getInstance(it).addToRequestQueue(jsonObjectRequest) }
    }

    override fun itemClicked(item: News) {
        TODO("Not yet implemented")
    }
}