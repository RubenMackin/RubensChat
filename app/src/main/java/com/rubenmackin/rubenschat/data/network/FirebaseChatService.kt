package com.rubenmackin.rubenschat.data.network

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.snapshots
import com.rubenmackin.rubenschat.data.network.dto.MessageDto
import com.rubenmackin.rubenschat.data.network.response.MessageResponse
import com.rubenmackin.rubenschat.domain.model.MessageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FirebaseChatService @Inject constructor(private val reference: DatabaseReference) {

    companion object {
        private const val PATH = "messages"
    }

    fun sendMessageToFirebase(messageDto: MessageDto) {
        val newMessage = reference.child(PATH).push()
        newMessage.setValue(messageDto)
    }

    fun getMessage(): Flow<List<MessageModel>> {
        return reference.child(PATH).snapshots.map { messages ->
            //RECORRER LOS HIJOS QUE NO SON NULOS
            messages.children.mapNotNull { messagesNotNulls ->
                //SE CONVIERTE A ESA CLASE MESSAGERESPONSE
                messagesNotNulls.getValue(MessageResponse::class.java)?.toDomain()
            }
        }
    }
}