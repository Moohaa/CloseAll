package org.close_all.project.data

data class Message(
    val message: String,
    val type: MessageType
)


enum class MessageType {
    ERROR,
    SUCCESS
}