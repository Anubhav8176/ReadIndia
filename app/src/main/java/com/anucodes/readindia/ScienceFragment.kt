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

class ScienceFragment : Fragment(), NewsAdapter.onItemClicked {

    private lateinit var mScienceAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_science, container, false)

        //Adding the url
        val url = "https://newsapi.org/v2/top-headlines?country=in&category=science&apiKey=e3f69fc7d62142a3ae77afd3a0d070c5"


        val recyclerView: RecyclerView = view.findViewById<RecyclerView>(R.id.scienceRecyclerView)


        recyclerView.layoutManager  = LinearLayoutManager(context)
        getData(url)

        mScienceAdapter = NewsAdapter(this)
        recyclerView.adapter = mScienceAdapter

        return view
    }

    private fun getData(apiUrl: String) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, apiUrl,
            null,
            { response ->
                val newsItemArray = response.getJSONArray("articles")
                val newsItem = ArrayList<News>()
                for (i in 0 until newsItemArray.length()) {
                    val newsjsonObject = newsItemArray.getJSONObject(i)
                    val news = News(
                        newsjsonObject.getString("title"),
                        newsjsonObject.getString("url"),
                        newsjsonObject.getString("urlToImage")
                    )
                    newsItem.add(news)
                }
                mScienceAdapter.updateNews(newsItem)
            },
            { error ->
                Log.i("There is an error", "The error is: "+error.toString())
                val newsItem = ArrayList<News>()
                for (i in 0 until 100){
                    val errorNews = News(
                        "The data is broken",
                        "https://www.google.com/",
                        "https://images.wondershare.com/repairit/aticle/2021/07/resolve-images-not-showing-problem-1.jpg"
                    )
                    newsItem.add(errorNews)
                }
                mScienceAdapter.updateNews(newsItem)
            }
        )
        context?.let { MySingleton.getInstance(it).addToRequestQueue(jsonObjectRequest) }
    }

    override fun itemClicked(item: News) {
        TODO("Not yet implemented")
    }

}