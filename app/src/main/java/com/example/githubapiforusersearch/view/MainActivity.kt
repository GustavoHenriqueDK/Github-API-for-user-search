package com.example.githubapiforusersearch.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.githubapiforusersearch.R
import com.example.githubapiforusersearch.controller.MainActivityController
import com.example.githubapiforusersearch.model.Repository
import com.example.githubapiforusersearch.rest.EndPoint
import com.example.githubapiforusersearch.rest.RetrofitConfiguration
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.hide()

        buttonSearchClick()
    }

    private fun buttonSearchClick() {
        buttonSearch.setOnClickListener {
            requestAPI()
        }
    }

    private fun requestAPI() {
        val mainActivityController: MainActivityController = MainActivityController(this)

        mainActivityController.requestAPI(
            editTextNickname,
            imageViewUserPhotoProfile,
            textViewNickname,
            textViewUsername,
            textViewFollowing,
            textViewFollowers,
            textViewEmail,
            textViewRepository,
            textViewCompany,
            constraintLayoutUserInformations,
            constraintLayoutUserNotFound,
            constraintLayoutWhiteFlag
        )
    }
}