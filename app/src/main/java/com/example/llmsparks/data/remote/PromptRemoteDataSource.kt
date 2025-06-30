package com.example.llmsparks.data.remote

import com.example.llmsparks.data.model.Prompt
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class PromptRemoteDataSource {
    private val db = FirebaseFirestore.getInstance()

    fun getPromptsFlow() = callbackFlow<List<Prompt>> {
        val listener = db.collection("prompts")
            .addSnapshotListener { snap, err ->
                if (err != null) close(err)
                else {
                    val list = snap?.documents?.mapNotNull {
                        it.toObject(Prompt::class.java)?.copy(id = it.id)
                    } ?: emptyList()
                    trySend(list).isSuccess
                }
            }
        awaitClose { listener.remove() }
    }
}
