package com.example.githubapiforusersearch.view

import android.content.Intent
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
        textViewRepositoryClick()
    }

    private fun buttonSearchClick() {
        buttonSearch.setOnClickListener {
            requestAPI()
        }
    }

    private fun textViewRepositoryClick() {
        textViewRepository.setOnClickListener {
            val intent = Intent(this, UserRepositoryActivity::class.java)
            intent.putExtra("key", editTextNickname.text.toString())
            startActivity(intent)
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