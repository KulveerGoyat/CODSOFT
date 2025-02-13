package com.example.todo_list_app.model

import java.util.*

object TaskData {

    var listdata = mutableListOf<CardData>()

    fun setdata(title: String, priority: String, status: String, dueDate: Date?) {
        listdata.add(CardData(title, priority, status, dueDate))
    }

    fun updateData(position: Int, title: String, priority: String, status: String, dueDate: Date) {
        listdata[position].title = title
        listdata[position].priority = priority
        listdata[position].status = status
        listdata[position].dueDate = dueDate
    }

    fun getalldata(): List<CardData> {
        return listdata
    }

    fun deletealldata() {
        listdata.clear()
    }

    fun getdata(position: Int): CardData {
        return listdata[position]
    }

    fun deletedata(position: Int) {
        listdata.removeAt(position)
    }
}
