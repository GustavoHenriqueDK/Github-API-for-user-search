package com.example.githubapiforusersearch.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.githubapiforusersearch.R
import com.example.githubapiforusersearch.controller.MainActivityController
import com.example.githubapiforusersearch.model.User
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
            //requestAPI()
            testFunction()
        }
    }

    private fun testFunction() {
        val endPoint = RetrofitConfiguration.getClient().create(EndPoint::class.java)
        val callbackList = endPoint.getUserRepositories(editTextNickname.text.toString().trim())
        callbackList.enqueue(object : Callback<List<Integer>> {
            override fun onResponse(call: Call<List<Integer>>, response: Response<List<Integer>>) {
                TODO("Not yet implemented")
            }
            override fun onFailure(call: Call<List<Integer>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
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