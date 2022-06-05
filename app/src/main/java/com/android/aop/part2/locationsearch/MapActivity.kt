package com.android.aop.part2.locationsearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.aop.part2.locationsearch.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMapBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
