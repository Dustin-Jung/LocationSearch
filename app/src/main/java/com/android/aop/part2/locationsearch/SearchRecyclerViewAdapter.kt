package com.android.aop.part2.locationsearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.aop.part2.locationsearch.databinding.ViewholderSearchResultItemBinding

class SearchRecyclerViewAdapter :
    RecyclerView.Adapter<SearchViewHolder>() {

    private var searchResultList: List<Any> = listOf()
    lateinit var searchResultClickListener: (Any) -> Unit


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = ViewholderSearchResultItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SearchViewHolder(view, searchResultClickListener)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bindData(searchResultList[position])

    }

    override fun getItemCount(): Int {
        return 10
    }

}

class SearchViewHolder(
    private val binding: ViewholderSearchResultItemBinding,
    val searchResultClickListener: (Any) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(data: Any) = with(binding) {
        textTextView.text = "제목"
        subtextTextView.text = "부제목"
    }
}



