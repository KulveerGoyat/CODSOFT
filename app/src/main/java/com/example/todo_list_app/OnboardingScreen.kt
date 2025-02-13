package com.example.todo_list_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.viewpager2.widget.ViewPager2
import com.example.todo_list_app.adapter.OnBoardingAdapter
import com.example.todo_list_app.database.TodoDatabase
import com.example.todo_list_app.model.CardData
import com.example.todo_list_app.model.TaskData
import com.example.todo_list_app_codesoft.model.OnBoardingItem
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class OnboardingScreen : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2
    private lateinit var  database: TodoDatabase

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_screen)

        database = Room.databaseBuilder(
            applicationContext, TodoDatabase::class.java,"T0_DO_LIST"
        ).build()

        GlobalScope.launch {
            TaskData.listdata = database.dao().getTaskList() as MutableList<CardData>
        }

        viewPager = findViewById(R.id.viewPager)

        val onboarditems = listOf(
            OnBoardingItem(
                getString(R.string.OnboardTitle1),
                getString(R.string.OnboardDescription1),
                R.drawable.onboard1
            ),
            OnBoardingItem(
                getString(R.string.OnboardTitle2),
                getString(R.string.OnboardDescription2),
                R.drawable.onboard3
            ),
            OnBoardingItem(
                getString(R.string.OnboardTitle3),
                getString(R.string.OnboardDescription3),
                R.drawable.onboard2
            ),
        )

        viewPager.adapter = OnBoardingAdapter(onboarditems)

        var currentScreen = viewPager.currentItem

        val onboardingbtn = findViewById<Button>(R.id.OnBoardButton)

        onboardingbtn.setOnClickListener {

            if (currentScreen == 2) {
               startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                viewPager.setCurrentItem( ++currentScreen, true)

            }
        }

    }
}