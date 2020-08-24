package com.example.githubapiforusersearch.controller

import android.app.ProgressDialog
import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.example.githubapiforusersearch.R
import com.example.githubapiforusersearch.model.Repository
import com.example.githubapiforusersearch.model.User
import com.example.githubapiforusersearch.rest.EndPoint
import com.example.githubapiforusersearch.rest.RetrofitConfiguration
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivityController(private val context: Context) {

    companion object {
        const val HAVE_SOME_REPOSITORY: String = "have_some_repository"
    }

    fun requestAPI(
        userNameRequest: EditText,
        imageViewUserPhotoProfile: ImageView,
        textViewNickname: TextView,
        textViewUsername: TextView,
        textViewFollowing: TextView,
        textViewFollowers: TextView,
        textViewEmail: TextView,
        textViewRepository: TextView,
        textViewCompany: TextView,
        constraintLayoutUserInformations: ConstraintLayout,
        constraintLayoutUserNotFound: ConstraintLayout,
        constraintLayoutWhiteFlag: ConstraintLayout
    ) {
        val endPoint = RetrofitConfiguration.getClient().create(EndPoint::class.java)

        val progressDialog: ProgressDialog = ProgressDialog(context, R.style.myAlertDialogStyle)
        progressDialog.setMessage("Aguarde...")
        progressDialog.show()

        val callUser = endPoint.getUser(userNameRequest.text.toString().trim())
        callUser.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {

                hideWhiteFlagConstraint(constraintLayoutWhiteFlag)

                if (isValidUserNickname(response)) {
                    showUserInformationsConstraint(
                        constraintLayoutUserInformations,
                        constraintLayoutUserNotFound
                    )
                    setUserPhotoProfile(imageViewUserPhotoProfile, response)

                    setUserNickname(textViewNickname, response)

                    setUserName(textViewUsername, response)

                    setUserFollowing(textViewFollowing, response)

                    setUserFollowers(textViewFollowers, response)

                    setUserEmail(textViewEmail, response)

                    setUserCompany(textViewCompany, response)
                } else {
                    showEmptyImageConstraint(
                        constraintLayoutUserInformations,
                        constraintLayoutUserNotFound
                    )
                }
                if (progressDialog.isShowing) progressDialog.dismiss()
            }

            override fun onFailure(
                call: Call<User>,
                t: Throwable
            ) {
                Log.e("Error executing API ", t.toString())
                if (progressDialog.isShowing) progressDialog.dismiss()
            }
        })

        val callList = endPoint.getUserRepositories(userNameRequest.text.toString().trim())
        callList.enqueue(object : Callback<List<Repository>> {
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                setUserRepositorySize(textViewRepository, response)
                if (progressDialog.isShowing) progressDialog.dismiss()
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                Log.e("An error occurred", t.toString())
                if (progressDialog.isShowing) progressDialog.dismiss()
            }
        })

    }

    private fun userHaveSomeRepository(response: Response<List<Repository>>): Boolean {
        return response.body()?.size!! > 0
    }

    private fun hideWhiteFlagConstraint(constraintLayoutWhiteFlag: ConstraintLayout) {
        constraintLayoutWhiteFlag.visibility = View.GONE
    }

    private fun showEmptyImageConstraint(
        constraintLayoutUserInformations: ConstraintLayout,
        constraintLayoutUserNotFound: ConstraintLayout
    ) {
        constraintLayoutUserInformations.visibility = View.GONE
        constraintLayoutUserNotFound.visibility = View.VISIBLE
    }

    private fun showUserInformationsConstraint(
        constraintLayoutUserInformations: ConstraintLayout,
        constraintLayoutUserNotFound: ConstraintLayout
    ) {
        constraintLayoutUserInformations.visibility = View.VISIBLE
        constraintLayoutUserNotFound.visibility = View.GONE
    }

    private fun setUserCompany(textViewCompany: TextView, response: Response<User>) {
        try {
            if (response.body()?.company == null) {
                textViewCompany.setText(R.string.not_specified_user_erro)
                textViewCompany.setTextColor(ContextCompat.getColor(context, R.color.colorRed))
            } else {
                textViewCompany.text = response.body()?.company
                textViewCompany.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
            }
        } catch (e: Exception) {
            Log.e("Company error ", e.toString())
        }
    }

    private fun setUserRepositorySize(
        textViewRepository: TextView,
        response: Response<List<Repository>>
    ) {
        textViewRepository.text = response.body()?.size.toString()
        try {
            if (userHaveSomeRepository(response))
                textViewRepository.paintFlags = Paint.UNDERLINE_TEXT_FLAG
            else
                textViewRepository.typeface = Typeface.DEFAULT
        } catch (e: Exception) {
            Log.e("error ", e.toString())
        }

    }

    private fun setUserEmail(textViewEmail: TextView, response: Response<User>) {
        try {
            if (response.body()?.email == null) {
                textViewEmail.setText(R.string.not_specified_user_erro)
                textViewEmail.setTextColor(ContextCompat.getColor(context, R.color.colorRed))
            } else {
                textViewEmail.text = response.body()?.email
                textViewEmail.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
            }
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
            if (response.body()?.name == null) {
                textViewUsername.setText(R.string.not_specified_user_erro)
                textViewUsername.setTextColor(ContextCompat.getColor(context, R.color.colorRed))
            } else {
                textViewUsername.text = response.body()?.name
                textViewUsername.setTextColor(ContextCompat.getColor(context, R.color.colorWhite))
            }
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

    private fun isValidUserNickname(response: Response<User>): Boolean {
        return response.body()?.nickname != null
    }

}