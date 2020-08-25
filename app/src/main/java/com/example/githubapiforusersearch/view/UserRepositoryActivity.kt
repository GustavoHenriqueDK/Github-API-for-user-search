package com.example.githubapiforusersearch.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapiforusersearch.R
import com.example.githubapiforusersearch.model.Repository
import com.example.githubapiforusersearch.rest.EndPoint
import com.example.githubapiforusersearch.rest.RetrofitConfiguration
import com.example.githubapiforusersearch.view.adapter.RecyclerViewRepositoryAdapter
import kotlinx.android.synthetic.main.activity_user_repository.*
import kotlinx.android.synthetic.main.recycler_view_repository_layout.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class UserRepositoryActivity : AppCompatActivity() {

    private val repositoryList = ArrayList<Repository>()
    private val recyclerViewRepositoryAdapter = RecyclerViewRepositoryAdapter(repositoryList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_repository)
        supportActionBar?.hide()

        setUserRepositories()
        setRepositoryAdapter()
    }

    private fun setUserRepositories() {
        val extras: Bundle = intent.extras!!
        val extrasKey: String = extras.getString("key")!!

        val endPoint = RetrofitConfiguration.getClient().create(EndPoint::class.java)

        val callList = endPoint.getUserRepositories(extrasKey)
        callList.enqueue(object : Callback<List<Repository>> {
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                if (response.body()!!.isNotEmpty()) {
                    try {
                        responseRepositoryInformations(response)
                    } catch (e: Exception) {
                        Log.e("An error occurred ", e.toString())
                    }
                } else {
                    showEmptyImageInScreen()
                }
                recyclerViewRepositoryAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Log.e("An error occurred ", t.toString())
            }
        })
    }

    private fun showEmptyImageInScreen() {
        constraintLayoutEmptyImage.visibility = View.VISIBLE
        constraintLayoutRecyclerView.visibility = View.GONE
    }

    private fun responseRepositoryInformations(response: Response<List<Repository>>) {
        lateinit var repository: Repository
        for (i in response.body()?.indices!!) {
            if (hasLanguageInRepository(response, i) && hasDescriptionInRepository(response, i)) {
                repository = Repository(response.body()!![i].name, response.body()!![i].language, response.body()!![i].description)
                //textViewRepositoryDescription.text = "test"
            }
            if (!hasLanguageInRepository(response, i) && !hasDescriptionInRepository(response, i)) {
                repository = Repository(response.body()!![i].name, "Não especificado", "Não especificado")
            }
            if (!hasLanguageInRepository(response, i)) {
                repository = Repository(response.body()!![i].name, "Não especificado", response.body()!![i].description)
            }
            if (!hasDescriptionInRepository(response, i)) {
                repository = Repository(response.body()!![i].name, response.body()!![i].language, "Não especificado")
            }
            repositoryList.add(repository)
        }
    }

    private fun hasDescriptionInRepository(
        response: Response<List<Repository>>,
        index: Int
    ): Boolean {
        return response.body()?.get(index)?.description != null
    }

    private fun hasLanguageInRepository(response: Response<List<Repository>>, index: Int): Boolean {
        return response.body()?.get(index)?.language != null
    }

    private fun setRepositoryAdapter() {
        recyclerViewRepository.adapter = recyclerViewRepositoryAdapter
        recyclerViewRepository.layoutManager = LinearLayoutManager(this)
        recyclerViewRepositoryAdapter.notifyDataSetChanged()
    }
}