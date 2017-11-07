package com.wj.kotlintest.net

import android.util.Log
import com.wj.kotlintest.BuildConfig
import com.wj.kotlintest.expanding.jsonFormat
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.internal.http.HttpHeaders
import okio.Buffer
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 * 日志打印拦截器
 */
class LogInterceptor : Interceptor {

    private val charsetUTF8 = Charset.forName("UTF-8")

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request() // 获取请求信息

        if (!BuildConfig.DEBUG) { // 正是环境直接返回，不打印日志
            return chain.proceed(request)
        }

        // 日志拦截
        Log.d("NET_INTERCEPTOR", "---------->> Intercept to log <<----------")

        val requestBody = request.body() // 获取请求体

        val connection = chain.connection()
        val protocol = if (connection != null) connection.protocol() else Protocol.HTTP_1_1

        val logStr = StringBuilder() // 初始化 Log 输出文本
        logStr.append("--> ${request.method()} ${request.url()} $protocol")

        if (null != requestBody) {
            logStr.append(" (${requestBody.contentLength()}-byte body")
        }

        logStr.append("\n")

        if (null != requestBody) {
            if (null != requestBody.contentType()) {
                logStr.append("Content-Type: ${requestBody.contentType()}\n")
            }
            logStr.append("Content-Length: ${if (requestBody.contentLength() != -1L) "${requestBody.contentLength()}" else "unknown"}\n")
        }

        val headers = request.headers() // 获取请求头
        for (i in 0 until headers.size()) {
            val name = headers.name(i)
            if (!"Content-Type".equals(name, true) && !"Content_Length".equals(name, true)) {
                logStr.append("$name: ${headers.value(i)}\n")
            }
        }

        fun bodyEncode(headers: Headers): Boolean {
            val contentEncoding = headers.get("Content-Encoding")
            return null != contentEncoding && "identity" != contentEncoding
        }

        when {
            null == requestBody -> logStr.append("--> END ${request.method()}\n")
            bodyEncode(headers) -> logStr.append("--> END ${request.method()} (encoded body omitted)\n")
            else -> {
                val buffer = Buffer()
                requestBody.writeTo(buffer)

                var charset = charsetUTF8
                val contentType = requestBody.contentType()
                if (null != contentType) {
                    charset = contentType.charset(charsetUTF8)
                }

                logStr.append("\n")

                if (null != charset) {
                    logStr.append("${buffer.readString(charset)}\n")
                }

                logStr.append("--> END ${request.method()} (${requestBody.contentLength()}-byte body\n")
            }
        }

        // 记录请求开始时间
        val startNs = System.nanoTime()

        // 发送请求，获取返回数据，会造成多次请求
        val response = chain.proceed(request)
        // 统计请求耗时
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)

        // 获取返回体
        val responseBody = response.body()

        if (null != responseBody) {
            val contentLength = responseBody.contentLength()
            val bodySize = if (contentLength != -1L) "$contentLength-byte" else "unknown-length"

            logStr.append("<-- ${response.code()} ${response.message()} ${response.request().url()}" +
                    " (${tookMs}ms, $bodySize body)\n")

            val headers1 = response.headers()
            for (i in 0 until headers1.size()) {
                logStr.append("${headers1.name(i)}: ${headers1.value(i)}\n")
            }

            if (!HttpHeaders.hasBody(response)) {
                logStr.append("<-- END HTTP\n")
            } else if (bodyEncode(headers1)) {
                logStr.append("<-- END HTTP (encoded body omitted)\n")
            } else {
                val source = responseBody.source()
                source.request(Long.MAX_VALUE)
                val buffer = source.buffer()

                var charset = charsetUTF8
                val contentType = responseBody.contentType()
                if (null != contentType) {
                    charset = contentType.charset(charsetUTF8)
                }

                if (-1L != contentLength) {
                    logStr.append("\n")
                    val json = buffer.clone().readString(charset)
                    val jsonFormat = json.jsonFormat()
                    logStr.append("$json\n${
                    if (jsonFormat.length > 200)
                        "${jsonFormat.substring(0, 100)}\n\n The Json String was too long...\n\n ${
                        jsonFormat.substring(jsonFormat.length - 100)}\n"
                    else
                        "$jsonFormat\n"}")
                }

                logStr.append("<-- END HTTP (${buffer.size()}-byte body\n")
            }
        }

        Log.d("NET_INTERCEPTOR", logStr.toString())
        return response
    }
}