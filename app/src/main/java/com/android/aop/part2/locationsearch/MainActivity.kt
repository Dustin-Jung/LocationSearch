package com.android.aop.part2.locationsearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.android.aop.part2.locationsearch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)}

        private lateinit var adapter: SearchRecyclerViewAdapter

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(binding.root)

            initAdapter()
            initViews()
        }

    private fun initViews() = with(binding) {
        emptyResultTextView.isVisible = false
        recyclerView.adapter = adapter
    }

    private fun initAdapter() {
        adapter = SearchRecyclerViewAdapter()
    }


    }

