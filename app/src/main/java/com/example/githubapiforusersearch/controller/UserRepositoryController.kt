package com.example.githubapiforusersearch.controller

import android.util.Log
import com.example.githubapiforusersearch.model.Repository
import retrofit2.Response

class UserRepositoryController {

    private fun hasDescriptionInRepository(
        response: Response<List<Repository>>,
        index: Int
    ): Boolean {
        return response.body()?.get(index)?.description != null
    }

    private fun hasLanguageInRepository(response: Response<List<Repository>>, index: Int): Boolean {
        return response.body()?.get(index)?.language != null
    }

    fun addAllRepositoriesInList(response: Response<List<Repository>>, repositoryList: ArrayList<Repository>) {
        lateinit var repository: Repository
        for (i in response.body()?.indices!!) {
            try {
                if (hasLanguageInRepository(
                        response,
                        i
                    ) && hasDescriptionInRepository(
                        response,
                        i
                    )
                ) {
                    repository = Repository(
                        response.body()!![i].name,
                        response.body()!![i].language,
                        response.body()!![i].description
                    )
                }
            } catch (e: Exception) {
                Log.e("Error ", e.toString())
            }

            try {
                if (!hasLanguageInRepository(
                        response,
                        i
                    ) && !hasDescriptionInRepository(
                        response,
                        i
                    )
                ) {
                    repository =
                        Repository(
                            response.body()!![i].name,
                            "Não especificado",
                            "Não especificado"
                        )
                }
            } catch (e: Exception) {
                Log.e("Error ", e.toString())
            }

            try {
                if (!hasLanguageInRepository(response, i)) {
                    repository = Repository(
                        response.body()!![i].name,
                        "Não especificado",
                        response.body()!![i].description
                    )
                }
            } catch (e: Exception) {
                Log.e("Error ", e.toString())
            }

            try {
                if (!hasDescriptionInRepository(response, i)) {
                    repository = Repository(
                        response.body()!![i].name,
                        response.body()!![i].language,
                        "Não especificado"
                    )
                }
            } catch (e: Exception) {
                Log.e("Error ", e.toString())
            }

            repositoryList.add(repository)
        }
    }
}