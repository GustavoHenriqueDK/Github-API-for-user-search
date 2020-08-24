package com.example.githubapiforusersearch.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapiforusersearch.R
import com.example.githubapiforusersearch.model.Repository
import com.example.githubapiforusersearch.rest.EndPoint
import com.example.githubapiforusersearch.rest.RetrofitConfiguration
import com.example.githubapiforusersearch.view.adapter.RecyclerViewRepositoryAdapter
import kotlinx.android.synthetic.main.activity_user_repository.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                responseRepositoryInformations(response)
                recyclerViewRepositoryAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Log.e("An error occurred ", t.toString())
            }
        })
    }

    private fun responseRepositoryInformations(response: Response<List<Repository>>) {
        lateinit var repository: Repository
        for (i in response.body()?.indices!!) {
            repository = if (hasLanguageInRepository(response, i)) {
                Repository(response.body()!![i].name, language = response.body()!![i].language)
            } else {
                Repository(response.body()!![i].name, language = "Não especificado")
            }
            repositoryList.add(repository)
        }
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