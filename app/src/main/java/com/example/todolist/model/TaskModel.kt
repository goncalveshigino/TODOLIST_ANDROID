package com.example.todolist.model

data class TaskModel(
    val idDoc: String = "",
    val userEmail: String = "",
    val title: String = "",
    val date: String = ""
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "idDoc" to this.idDoc,
            "userEmail" to this.userEmail,
            "title" to this.title,
            "date" to this.date
        )
    }
}
