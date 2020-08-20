package com.example.githubapiforusersearch.controller

import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.githubapiforusersearch.model.User
import com.example.githubapiforusersearch.rest.EndPoint
import com.example.githubapiforusersearch.rest.RetrofitConfiguration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityController {

    fun requestAPI(
        userNameRequest: EditText,
        imageViewUserPhotoProfile: ImageView,
        textViewNickname: TextView,
        textViewUsername: TextView,
        textViewFollowing: TextView,
        textViewFollowers: TextView,
        textViewEmail: TextView,
        textViewRepository: TextView,
        textViewCompany: TextView
    ) {
        val endPoint = RetrofitConfiguration.getClient().create(EndPoint::class.java)

        val call = endPoint.getUser(userNameRequest.text.toString())

        call.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {

                setUserPhotoProfile(imageViewUserPhotoProfile, response)

                setUserNickname(textViewNickname, response)

                setUserName(textViewUsername, response)

                setUserFollowing(textViewFollowing, response)

                setUserFollowers(textViewFollowers, response)

                setUserEmail(textViewEmail, response)

                setUserRepositorySize(textViewRepository, response)

                setUserCompany(textViewCompany, response)
            }

            override fun onFailure(
                call: Call<User>,
                t: Throwable
            ) {
                Log.e("Error executing API ", t.toString())
            }
        })
    }

    private fun setUserCompany(textViewCompany: TextView, response: Response<User>) {
        try {
            textViewCompany.text = response.body()!!.company
        } catch (e: Exception) {
            Log.e("Company error ", e.toString())
        }
    }

    private fun setUserRepositorySize(textViewRepository: TextView, response: Response<User>) {
        try {
            //TODO: textViewRepository.text = response.body()!!.repository
        } catch (e: Exception) {
            Log.e("Repository error ", e.toString())
        }
    }

    private fun setUserEmail(textViewEmail: TextView, response: Response<User>) {
        try {
            textViewEmail.text = response.body()?.email
        } catch (e: Exception) {
            Log.e("E-mail error ", e.toString())
        }
    }

    private fun setUserFollowers(textViewFollowers: TextView, response: Response<User>) {
        textViewFollowers.text = response.body()?.followers
    }

    private fun setUserFollowing(textViewFollowing: TextView, response: Response<User>) {
        textViewFollowing.text = response.body()?.following
    }

    private fun setUserName(textViewUsername: TextView, response: Response<User>) {
        try {
            textViewUsername.text = response.body()?.name
        } catch (e: Exception) {
            Log.e("Username error ", e.toString())
        }
    }

    private fun setUserPhotoProfile(
        imageViewUserPhotoProfile: ImageView,
        response: Response<User>
    ) {
        val thread = Thread(Runnable {
            try {
                imageViewUserPhotoProfile.setImageBitmap(
                    ConvertURL.getBitmapFromURL(
                        response.body()?.avatar
                    )
                )
            } catch (e: Exception) {
                Log.e("Error getting image ", e.toString())
            }
        })
        thread.start()
    }

    private fun setUserNickname(textViewNickname: TextView, response: Response<User>) {
        textViewNickname.text = response.body()?.nickname
    }
}