package com.android.aop.part2.locationsearch

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.aop.part2.locationsearch.databinding.ViewholderSearchResultItemBinding
import com.android.aop.part2.locationsearch.model.entity.SearchResultEntity

class SearchRecyclerViewAdapter :
    RecyclerView.Adapter<SearchViewHolder>() {

    private var searchResultList: List<SearchResultEntity> = listOf()
    lateinit var searchResultClickListener: (SearchResultEntity) -> Unit


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
        holder.bindViews(searchResultList[position])

    }

    override fun getItemCount(): Int {
        return searchResultList.size
    }

    fun setSearchResultList(
        searchResultList: List<SearchResultEntity>,
        searchResultClickListener: (SearchResultEntity) -> Unit
    ) {
        this.searchResultList = searchResultList
        this.searchResultClickListener = searchResultClickListener
        notifyDataSetChanged()
    }

}

class SearchViewHolder(
    private val binding: ViewholderSearchResultItemBinding,
    val searchResultClickListener: (SearchResultEntity) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bindData(data: SearchResultEntity) = with(binding) {
        textTextView.text = data.fullAdress
        subtextTextView.text = data.name
    }

    fun bindViews(data: SearchResultEntity) {
        binding.root.setOnClickListener {
            searchResultClickListener(data)
        }
    }
}



