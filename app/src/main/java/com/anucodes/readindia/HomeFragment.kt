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

class HomeFragment : Fragment(), NewsAdapter.onItemClicked {

    private lateinit var mhomeAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Initializing the recyclerView and connect it to the newsAdapter
        val recyclerView = view.findViewById<RecyclerView>(R.id.homeRecyclerView)


        recyclerView.layoutManager  = LinearLayoutManager(context)
        mhomeAdapter = NewsAdapter( this)
        getData()
        recyclerView.adapter = mhomeAdapter

        return view
    }

    private fun getData() {
        val apiUrl = "https://newsapi.org/v2/top-headlines?country=in&apiKey=a1b141b9a36440b2bfc75549e288539e"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, apiUrl,
            null,
            { response ->
                val newsItemArray = response.getJSONArray("articles")
                val newsItem = ArrayList<News>()
                Log.i("Is it okay?", "Yes the app is getting response.")
                for (i in 0 until newsItemArray.length()) {
                    val newsjsonObject = newsItemArray.getJSONObject(i)
                    val news = News(
                        newsjsonObject.getString("title"),
                        newsjsonObject.getString("url"),
                        newsjsonObject.getString("urlToImage")
                    )
                    newsItem.add(news)
                }
                mhomeAdapter.updateNews(newsItem)
            },
            { error ->
                Log.i("There is an error", "The error is: $error")
                val newsItem = ArrayList<News>()
                for (i in 0 until 100){
                    val errorNews = News(
                        "The data is broken",
                        "https://www.google.com/",
                        "https://images.wondershare.com/repairit/aticle/2021/07/resolve-images-not-showing-problem-1.jpg"
                    )
                    newsItem.add(errorNews)
                }
                mhomeAdapter.updateNews(newsItem)
            }
        )
        context?.let { MySingleton.getInstance(it).addToRequestQueue(jsonObjectRequest) }

    }

    override fun itemClicked(item: News) {

    }


}