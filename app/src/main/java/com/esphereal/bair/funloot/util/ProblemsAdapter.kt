package com.esphereal.bair.funloot.util

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView


import com.esphereal.bair.funloot.R
import com.esphereal.bair.funloot.dummyAdding.IventProblems

class ProblemsAdapter(private val listOfIventProblem: List<IventProblems>) : RecyclerView.Adapter<ProblemsAdapter.ProblemsViewHolder>() {


    init {
        viewHolderCount = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProblemsViewHolder {
        val contex = parent.context
        val layoutIdForListItem = R.layout.fragment_problems_list_item

        val inflater = LayoutInflater.from(contex) //Класс для создания новго представления

        val view = inflater.inflate(layoutIdForListItem, parent, false)

        val viewHolder = ProblemsViewHolder(view)
        viewHolder.viewHolderIndex.text = "ViewHolder index: $viewHolderCount"

        viewHolderCount++

        return viewHolder
    }

    override fun onBindViewHolder(holder: ProblemsViewHolder, position: Int) {
        holder.bind(listOfIventProblem[position])



    }

    override fun getItemCount(): Int {
        return listOfIventProblem.size
    }

    inner class ProblemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var listItemProblemView: TextView
        var viewHolderIndex: TextView

        init {

            listItemProblemView = itemView.findViewById(R.id.problem_item)
            viewHolderIndex = itemView.findViewById(R.id.problem_holder_number)
        }

        fun bind(iventProblems: IventProblems) {
            listItemProblemView.text = iventProblems.problems_body
        }
    }

    companion object {

        private var viewHolderCount: Int = 0
    }
}
