package com.example.lista2.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.lista2.R
import com.example.lista2.databinding.FragmentNotificationsBinding
import com.example.lista2.databinding.GradeListItemBinding
import com.example.lista2.ui.data.ExerciseList
import com.example.lista2.ui.data.Results
import com.example.lista2.ui.data.Exercise
import com.example.lista2.createdData
import com.example.lista2.databinding.ListListItemBinding
import com.example.lista2.databinding.TaskListItemBinding
import com.example.lista2.ui.home.ListListViewHolder

class TaskListAdapter(private val taskList: List<Exercise>) : RecyclerView.Adapter<TaskListViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {
        return TaskListViewHolder(TaskListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return createdData.listOfLists[createdData.klikniety].exercises.size
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        val currentItem = taskList[position]
        holder.bind(currentItem, position)
    }

}
class TaskListViewHolder(private val binding: TaskListItemBinding) :
    RecyclerView.ViewHolder(binding.root){

    fun bind(item: Exercise, position: Int){
        binding.exerciseNumber.text = "Zadanie " + (position + 1).toString()
        binding.points.text = "pkt: " + item.points.toString()
        binding.exerciseContent.text = item.content
    }
}
class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationsBinding.inflate(inflater)
        binding.recyclerView.apply {
            adapter = TaskListAdapter(createdData.listOfLists[createdData.klikniety].exercises)
            layoutManager = LinearLayoutManager(requireContext())
        }
        binding.textTitle.text = createdData.listOfLists[createdData.klikniety].subject.subjectName +"\nLista "+ createdData.listOfLists[createdData.klikniety].nrLis.toString()
        return binding.root
    }
}