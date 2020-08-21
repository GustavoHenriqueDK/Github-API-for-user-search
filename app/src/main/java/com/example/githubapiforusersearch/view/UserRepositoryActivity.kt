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

        requestAPI()
        setRepositoryAdapter()
    }

    private fun requestAPI() {
        val extras: Bundle = intent.extras!!
        var myString: String = ""
        if (extras != null) {
            myString = extras.getString("key")!!
        }

        val endPoint = RetrofitConfiguration.getClient().create(EndPoint::class.java)

        val callList = endPoint.getUserRepositories(myString)
        callList.enqueue(object : Callback<List<Repository>> {
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                for (i in response.body()?.indices!!) {
                    if (response.body()!![i].language != null) {
                        val repository = Repository(response.body()!![i].name, response.body()!![i].language)
                        repositoryList.add(repository)
                    } else {
                        val repository = Repository(response.body()!![i].name, "Não especificado")
                        repositoryList.add(repository)
                    }
                }
                recyclerViewRepositoryAdapter.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Log.e("An error occurred ", t.toString())
            }
        })
    }

    private fun setRepositoryAdapter() {
        recyclerViewRepository.adapter = recyclerViewRepositoryAdapter
        recyclerViewRepository.layoutManager = LinearLayoutManager(this)
        recyclerViewRepositoryAdapter.notifyDataSetChanged()
    }
}