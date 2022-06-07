package com.android.aop.part2.locationsearch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import com.android.aop.part2.locationsearch.databinding.ActivityMainBinding
import com.android.aop.part2.locationsearch.model.entity.LocationLatLngEntity
import com.android.aop.part2.locationsearch.model.entity.SearchResultEntity

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: SearchRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initAdapter()
        initViews()
        initData()
        setData()
    }

    private fun initViews() = with(binding) {
        emptyResultTextView.isVisible = false
        recyclerView.adapter = adapter
    }

    private fun initAdapter() {
        adapter = SearchRecyclerViewAdapter()
    }

    private fun initData() {
        adapter.notifyDataSetChanged()
    }

    private fun setData() {
        val dataList = (0..10).map {
            SearchResultEntity(
                name = "빌딩 $it",
                fullAdress = "주소 $it",
                locationLatLng = LocationLatLngEntity(it.toFloat(), it.toFloat())
            )
        }

        adapter = setSearchResultList(dataList) {

            Toast.makeText(this, "빌딩이름: ${it.name} 주소: ${it.fullAdress}", Toast.LENGTH_LONG).show()

        }
    }
}
