package com.example.mapsapp.data.remote.client

import android.util.Log
import com.example.mapsapp.data.converter.PointRemoteConverter
import com.example.mapsapp.data.remote.model.PointRemoteModel
import com.example.mapsapp.domain.entity.MapPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.Socket
import javax.inject.Inject

class PointReceiverClient @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val converter: PointRemoteConverter
) {
    companion object {
        private const val SERVER_ADDRESS = "192.168.0.157"
        private const val SERVER_PORT = 64781
        private const val SOCKET_TIMEOUT = 1000
        private const val TAG = "CLIENT_SOCKET"
    }

    private var socket: Socket? = null

    fun start(onPointReceived: (MapPoint) -> Unit) {

        coroutineScope.launch(Dispatchers.IO) {
            try {
                socket = Socket(SERVER_ADDRESS, SERVER_PORT)
                socket.use { socket ->
                    socket?.soTimeout = SOCKET_TIMEOUT
                    Log.d(TAG, "Connected to: $SERVER_ADDRESS, $SERVER_PORT")

                    socket?.getInputStream().use { inputStream ->
                        val reader = BufferedReader(InputStreamReader(inputStream))
                        if (socket != null) {
                            while (!socket.isClosed) {
                                val receivedMessage = processInput(reader)
                                try {
                                    if (receivedMessage.startsWith("{")){
                                        val mapPoint = Json.decodeFromString<PointRemoteModel>(receivedMessage)
                                        Log.d(TAG, "Received json: $receivedMessage")
                                        onPointReceived.invoke(converter.convert(mapPoint))
                                    }
                                } catch (e: SerializationException) {
                                    Log.e(TAG, "Received invalid JSON: $receivedMessage", e)
                                    break
                                }
                            }
                        }
                    }
                }
            } catch (e: IOException) {
                Log.d(TAG, "Couldn't connect to server: $e")
                e.printStackTrace()
            } finally {
                socket?.close()
            }
        }
    }


    private fun processInput(reader: BufferedReader): String {
        val json = StringBuilder()
        try {
            while (true) {
                val line = reader.readLine() ?: break
                json.append(line)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return json.toString()
    }

    fun stop() {
        coroutineScope.cancel()
        socket?.close()
    }
}

