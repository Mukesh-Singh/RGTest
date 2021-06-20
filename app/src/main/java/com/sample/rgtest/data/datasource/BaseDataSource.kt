package com.sample.rgtest.data.datasource

import android.util.Log
import com.sample.rgtest.util.Resource
import retrofit2.Response

/**
 * @author Mukesh
 * An Base source class which will make essy to call the api and check the intermediate response if its success or not
 */
abstract class BaseDataSource {

    /**
     * Execute the @param call
     * @return Resources object with result type
     */
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Log.e("BaseDataSource", message)
        return Resource.error("Network call failed, reason: $message")
    }


}