package com.example.githubapiforusersearch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubapiforusersearch.R
import com.example.githubapiforusersearch.model.Repository
import com.example.githubapiforusersearch.view.adapter.RecyclerViewRepositoryAdapter
import kotlinx.android.synthetic.main.activity_user_repository.*

class UserRepositoryActivity : AppCompatActivity() {

    private val repositoryList = ArrayList<Repository>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_repository)
        supportActionBar?.hide()

        //Testing
        val repository = Repository("Github search", "Kotlin")
        val repository2 = Repository("My project name", "Java")
        val repository3 = Repository("My JS project", "JavaScript")

        repositoryList.add(repository)
        repositoryList.add(repository2)
        repositoryList.add(repository3)

        setRepositoryAdapter()
    }

    private fun setRepositoryAdapter() {
        val recyclerViewRepositoryAdapter = RecyclerViewRepositoryAdapter(repositoryList)
        recyclerViewRepository.adapter = recyclerViewRepositoryAdapter
        recyclerViewRepository.layoutManager = LinearLayoutManager(this)
        recyclerViewRepositoryAdapter.notifyDataSetChanged()
    }
}