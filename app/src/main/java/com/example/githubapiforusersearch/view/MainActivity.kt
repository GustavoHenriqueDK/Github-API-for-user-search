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
            //requestAPI()
            testFunction()
        }
    }

    private fun testFunction() {
        val endPoint = RetrofitConfiguration.getClient().create(EndPoint::class.java)
        val callbackList = endPoint.getUserRepositories(editTextNickname.text.toString().trim())

        callbackList.enqueue(object : Callback<List<Repository>> {
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                for (i in response.body()?.indices!!) {
                    Log.i("let's go ", response.body()?.get(i)?.name)
                }
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Log.e("catch an error ", t.toString())
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