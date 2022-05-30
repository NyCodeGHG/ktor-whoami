package dev.nycode.ktor.whoami

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import java.net.InetAddress

fun main() {
    embeddedServer(Netty, port = 6969) {
        install(DefaultHeaders) {
            header("X-Powered-By", "ktor")
        }
        install(CallLogging)
        routing {
            handle {
                val hostname = async {
                    withContext(Dispatchers.IO) {
                        InetAddress.getLocalHost()
                    }.hostName
                }
                val loopback = async(Dispatchers.IO) {
                    InetAddress.getLoopbackAddress().hostAddress
                }
                val address = withContext(Dispatchers.IO) {
                    InetAddress.getByName(call.request.origin.remoteHost)
                }.hostAddress
                val httpMethod = call.request.local.method
                val httpVersion = call.request.local.version
                call.respondText(
                    buildString {
                        append("Hostname: ").append(hostname.await()).appendLine()
                        append("IP: ").append(loopback.await()).appendLine()
                        if (loopback.await() != address) {
                            append("IP: ").append(address).appendLine()
                        }
                        append(httpMethod.value).append(' ').append(call.request.uri).append(' ')
                            .append(httpVersion).appendLine()
                        call.request.headers.toMap().asSequence().flatMap { (key, values) ->
                            values.map { key to it }
                        }.forEach { (key, value) ->
                            append(key).append(": ").append(value).appendLine()
                        }
                    }
                )
            }
        }
    }.start(wait = true)
}
