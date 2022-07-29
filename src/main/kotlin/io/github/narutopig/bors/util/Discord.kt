package io.github.narutopig.bors.util

import com.google.gson.JsonObject
import org.apache.http.client.methods.HttpPost
import org.apache.http.entity.ContentType
import org.apache.http.entity.StringEntity
import org.apache.http.impl.client.HttpClientBuilder


object Discord {
    private val client = HttpClientBuilder.create().build()

    fun sendMessage(webhook: String, message: String) {
        val body = HashMap<String, String>()
        body["content"] = message
        val jsonBody = JsonObject()
        jsonBody.addProperty("content", message)

        val entity = StringEntity(jsonBody.toString(), ContentType.APPLICATION_JSON)
        val request = HttpPost(webhook)
        request.entity = entity

        val response = client.execute(request)
        if (response.statusLine.statusCode < 200 || response.statusLine.statusCode > 299) {
            throw Exception(response.toString())
        }
    }
}