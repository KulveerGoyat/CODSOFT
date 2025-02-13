package com.example.todo_list_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo_list_app.R
import com.example.todo_list_app_codesoft.model.OnBoardingItem

class OnBoardingAdapter(private val onboardingItems: List<OnBoardingItem>): RecyclerView.Adapter<OnBoardingAdapter.OnboardingViewHolder>(){

inner  class OnboardingViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
    val title: TextView = itemView.findViewById(R.id.onboardingTitle)
    val description: TextView = itemView.findViewById(R.id.onboardingDescription)
    val image: ImageView = itemView.findViewById(R.id.onboardingImage)
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OnboardingViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.onboarding_items,parent,false)
        return OnboardingViewHolder(view)
    }

    override fun getItemCount(): Int {
        return  onboardingItems.size
    }

    override fun onBindViewHolder(holder: OnboardingViewHolder, position: Int) {
        val currentItem = onboardingItems[position]
        holder.title.text = currentItem.title
        holder.description.text = currentItem.description
        holder.image.setImageResource(currentItem.image)
    }

}