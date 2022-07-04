package io.github.narutopig.bors.util

import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.nio.charset.StandardCharsets


class Discord {
    companion object {

        val client = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build()

        private fun buildFormDataFromMap(data: HashMap<String, String>): HttpRequest.BodyPublisher {
            // thanks to https://mkyong.com/Java/how-to-send-http-request-getpost-in-Java/
            val builder = StringBuilder()
            for ((key, value) in data) {
                if (builder.isNotEmpty()) {
                    builder.append("&")
                }
                builder.append(URLEncoder.encode(key, StandardCharsets.UTF_8))
                builder.append("=")
                builder.append(URLEncoder.encode(value, StandardCharsets.UTF_8))
            }
            return HttpRequest.BodyPublishers.ofString(builder.toString())
        }

        fun sendMessage(webhook: String, message: String) {
            val body = HashMap<String, String>()
            body["content"] = message

            val request = HttpRequest.newBuilder()
                .uri(URI.create(webhook))
                .POST(buildFormDataFromMap(body))
                .build()
            val response = client.send(request, HttpResponse.BodyHandlers.ofString())
            if (response.statusCode() != 200) {
                throw Exception()
            }
        }
    }
}