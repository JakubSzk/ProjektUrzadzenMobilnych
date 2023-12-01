package com.example.lista2.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.lista2.databinding.FragmentDashboardBinding
import com.example.lista2.databinding.GradeListItemBinding
import com.example.lista2.ui.data.ExerciseList
import com.example.lista2.ui.data.Results
import com.example.lista2.createdData

class GradeListAdapter(private val gradeList: List<Results>) : RecyclerView.Adapter<GradeListViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GradeListViewHolder {
        return GradeListViewHolder(GradeListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return createdData.listOfResults.size
    }

    override fun onBindViewHolder(holder: GradeListViewHolder, position: Int) {
        val currentItem = gradeList[position]
        holder.bind(currentItem)
    }

}
class GradeListViewHolder(private val binding: GradeListItemBinding) :
    RecyclerView.ViewHolder(binding.root){

        fun bind(item: Results){
            binding.subjectName.text = item.subject.subjectName
            binding.grade.text = "Średnia: " + String.format("%.2f", item.avg)
            binding.listAmount.text = "Liczba list: " + item.amount.toString()
        }
    }
class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater)
        binding.recyclerView.apply {
            adapter = GradeListAdapter(createdData.listOfResults)
            layoutManager = LinearLayoutManager(requireContext())
        }
        return binding.root


    }

}