package com.diayan.kaal.api

import android.content.Context
import com.diayan.kaal.BuildConfig
import okhttp3.*
import java.io.IOException
import java.io.InputStream

class MockInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url().uri().toString()
            val res = when {
                uri.endsWith("kaal/events") ->readJsonFromSampleData(context, uri)
                uri.endsWith("kaal/places") ->readJsonFromSampleData(context, uri)
                uri.endsWith("kaal/stores") ->readJsonFromSampleData(context, uri)

                else -> ""
            }
            return buildResponse(chain, res)
        }else{ 
            throw IllegalAccessError("MockInterceptor is only meant for Testing Purposes and " + "bound to be used only with DEBUG mode")
        }
    }
    private fun buildResponse(chain: Interceptor.Chain, response: String): Response{
        return Response.Builder()
            .code(200)
            .message(response)
            .request(chain.request())
            .protocol(Protocol.HTTP_2)
            .body(ResponseBody.create(MediaType.parse("application/json"), response.toByteArray()))
            .addHeader("content-type", "application/json")
            .build()
    }

    private fun readJsonFromSampleData(context: Context, path: String): String {
        var json = ""
        try {
            val inputStream: InputStream = context.assets.open(path)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer)
        } catch (ex: IOException) {
            ex.printStackTrace()
        }
        return json
    }

}