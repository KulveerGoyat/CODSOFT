package com.example.todo_list_app.model

import java.util.Date

data class CardData(
    var title : String,
    var priority : String,
    var status: String,
    var dueDate: Date? = null
)

