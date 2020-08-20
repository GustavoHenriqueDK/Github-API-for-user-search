package com.example.githubapiforusersearch.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.githubapiforusersearch.R
import com.example.githubapiforusersearch.controller.MainActivityController
import kotlinx.android.synthetic.main.activity_main.*

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
        val mainActivityController: MainActivityController = MainActivityController()

        mainActivityController.requestAPI(
            editTextNickname,
            imageViewUserPhotoProfile,
            textViewNickname,
            textViewUsername,
            textViewFollowing,
            textViewFollowers,
            textViewEmail,
            textViewUserrepository,
            textViewCompany
        )
    }
}