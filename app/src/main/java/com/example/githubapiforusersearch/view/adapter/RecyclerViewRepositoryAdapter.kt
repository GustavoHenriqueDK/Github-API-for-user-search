package com.example.githubapiforusersearch.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.githubapiforusersearch.R
import com.example.githubapiforusersearch.model.Repository
import kotlinx.android.synthetic.main.recycler_view_repository_layout.view.*
import retrofit2.Response

class RecyclerViewRepositoryAdapter(
    private val repositoryList: List<Repository>
) :
    RecyclerView.Adapter<RecyclerViewRepositoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_repository_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(repositoryList[position])
    }

    override fun getItemCount(): Int {
        return repositoryList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(repository: Repository) {
            itemView.textViewRepositoryName.text = repository.name
            itemView.textViewRepositoryLanguage.text = repository.language
            itemView.textViewRepositoryDescription.text = repository.description
        }
    }
}