package com.example.coinapp.data.service

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.nio.charset.Charset

/**
 * Base WebSocket Service
 */
abstract class BaseWebSocketService<T>(
    protected val okHttpClient: OkHttpClient
) {
    protected var webSocket: WebSocket? = null

    protected abstract val url: String
    protected abstract val json: Json

    /**
     * 구독 메시지 생성 (각 거래소별로 구현)
     */
    protected abstract fun createSubscribeMessage(params: Any): String

    /**
     * 메시지 파싱 (각 거래소별로 구현)
     */
    protected abstract fun parseMessage(text: String): T


    fun connect(params: Any): Flow<WebSocketEvent<T>> = callbackFlow {
        val request = Request.Builder()
            .url(url)
            .build()

        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                try {
                    val subscribeMessage = createSubscribeMessage(params)
                    val success = webSocket.send(subscribeMessage)

                    if (success) {
                        trySend(WebSocketEvent.Connected)
                    }
                } catch (e: Exception) {
                    trySend(WebSocketEvent.Error(e))
                }
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                try {
                    val text = bytes.string(Charset.forName("UTF-8"))
                    val data = parseMessage(text)
                    trySend(WebSocketEvent.MessageReceived(data))
                } catch (e: Exception) {
                    trySend(WebSocketEvent.Error(e))
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                trySend(WebSocketEvent.Error(t))
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, null)
                trySend(WebSocketEvent.Closing(code, reason))
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                trySend(WebSocketEvent.Closed(code, reason))
            }
        }

        webSocket = okHttpClient.newWebSocket(request, listener)

        awaitClose {
            disconnect()
        }
    }

    fun disconnect() {
        webSocket?.close(1000, "Client closed connection")
        webSocket = null
    }

    /**
     * WebSocket 이벤트
     */
    sealed class WebSocketEvent<out T> {
        data object Connected : WebSocketEvent<Nothing>()
        data class MessageReceived<T>(val data: T) : WebSocketEvent<T>()
        data class Error(val throwable: Throwable) : WebSocketEvent<Nothing>()
        data class Closing(val code: Int, val reason: String) : WebSocketEvent<Nothing>()
        data class Closed(val code: Int, val reason: String) : WebSocketEvent<Nothing>()
    }
}