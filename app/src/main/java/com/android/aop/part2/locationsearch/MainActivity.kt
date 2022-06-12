package com.android.aop.part2.locationsearch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.android.aop.part2.locationsearch.api.response.search.Poi
import com.android.aop.part2.locationsearch.api.response.search.Pois
import com.android.aop.part2.locationsearch.api.util.RetrofitUtil
import com.android.aop.part2.locationsearch.databinding.ActivityMainBinding
import com.android.aop.part2.locationsearch.model.entity.LocationLatLngEntity
import com.android.aop.part2.locationsearch.model.entity.SearchResultEntity
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    companion object{
        val SEARCH_RESULT_EXTRA_KEY =  "SEARCH_RESULT_EXTRA_KEY"
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: SearchRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        job = Job()

        initAdapter()
        initViews()
        bindViews()
        initData()
    }

    private fun initViews() = with(binding) {
        emptyResultTextView.isVisible = false
        recyclerView.adapter = adapter
    }

    private fun bindViews() = with(binding) {
        searchButton.setOnClickListener {
            searchKeyword(searchBarInputView.text.toString())
        }
    }

    private fun initAdapter() {
        adapter = SearchRecyclerViewAdapter()
    }

    private fun initData() {
        adapter.notifyDataSetChanged()
    }

    private fun setData(pois: Pois) {
        val dataList = pois.poi.map {
            SearchResultEntity(
                name = it.name ?: "",
                fullAdress = makeMainAddress(it),
                locationLatLng = LocationLatLngEntity(it.noorLat, it.noorLon)
            )
        }

        adapter.setSearchResultList(dataList) {

            startActivity(
                Intent(this, MapActivity::class.java).apply {
                    putExtra(SEARCH_RESULT_EXTRA_KEY, it)
                }
            )

            Toast.makeText(
                this,
                "빌딩이름: ${it.name} 주소: ${it.fullAdress} 위도/경도: ${it.locationLatLng}",
                Toast.LENGTH_LONG
            ).show()

        }
    }

    private fun searchKeyword(keywordString: String) {
        launch(coroutineContext) {
            try {
                withContext(Dispatchers.IO) {
                    val response = RetrofitUtil.apiService.getSearchLocation(
                        keyword = keywordString
                    )
                    if (response.isSuccessful) {
                        val body = response.body()
                        withContext(Dispatchers.Main) {
                            Log.e("response", body.toString())
                            body?.let { searchResponse ->
                                setData(searchResponse.searchPoiInfo.pois)
                            }

                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(
                    this@MainActivity,
                    "검색하는 과정에서 에러가 발생했습니다. : $(e.message)",
                    Toast.LENGTH_LONG
                ).show()

            }
        }

    }

    private fun makeMainAddress(poi: Poi): String =
        if (poi.secondNo?.trim().isNullOrEmpty()) {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.lowerAddrName?.trim() ?: "") + " " +
                    (poi.detailAddrName?.trim() ?: "") + " " +
                    poi.firstNo?.trim()
        } else {
            (poi.upperAddrName?.trim() ?: "") + " " +
                    (poi.middleAddrName?.trim() ?: "") + " " +
                    (poi.lowerAddrName?.trim() ?: "") + " " +
                    (poi.detailAddrName?.trim() ?: "") + " " +
                    (poi.firstNo?.trim() ?: "") + " " +
                    poi.secondNo?.trim()
        }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}

