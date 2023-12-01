package com.example.lista2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lista2.databinding.FragmentDashboardBinding
import com.example.lista2.databinding.ListListItemBinding
import com.example.lista2.ui.data.ExerciseList
import com.example.lista2.createdData
import com.example.lista2.ui.data.Results

class ListListAdapter(private val listList: List<ExerciseList>) : RecyclerView.Adapter<ListListViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListListViewHolder {
        return ListListViewHolder(ListListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int {
        return createdData.listOfLists.size
    }

    override fun onBindViewHolder(holder: ListListViewHolder, position: Int) {
        val currentItem = listList[position]
        holder.bind(currentItem)
    }

}
class ListListViewHolder(private val binding: ListListItemBinding) :
    RecyclerView.ViewHolder(binding.root){

    fun bind(item: ExerciseList){
        binding.subjectName.text = item.subject.subjectName
        binding.listNumber.text = "Lista: " + item.nrLis.toString()
        binding.questionAmount.text = "Liczba zadań: " + item.exercises.size.toString()
        binding.score.text = "Ocena: " + String.format("%.2f", item.grade)
    }
}
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater)
        binding.recyclerView.apply {
            adapter = ListListAdapter(createdData.listOfLists)
            layoutManager = LinearLayoutManager(requireContext())
        }
        return binding.root
    }


}