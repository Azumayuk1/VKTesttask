package com.sergei.vktesttask

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergei.vktesttask.network.GiphyAPI
import com.sergei.vktesttask.network.GiphyGifData
import kotlinx.coroutines.launch
import java.util.*

private const val GIFS_PER_LOAD = 25

enum class APIStatus { WAITING, LOADING, ERROR, FINISHED }

class GiphyGridViewModel : ViewModel() {

    private val _status = MutableLiveData(APIStatus.WAITING)
    val status: LiveData<APIStatus> = _status

    private val _gifs = MutableLiveData<MutableList<GiphyGifData>>(mutableListOf<GiphyGifData>())
    val gifs: LiveData<MutableList<GiphyGifData>> = _gifs

    /*private val _currentGifsOffset = MutableLiveData<Int>(0)
    val currentGifsOffset: LiveData<Int> = _currentGifsOffset*/

    private val _currentSearchQuery = MutableLiveData<String>()
    val currentSearchQuery: LiveData<String> = _currentSearchQuery

    fun initGifsSearch(searchQuery: String) {

        // Clear any GIFs from last search
        clearGifsList()

        viewModelScope.launch {
            _status.value = APIStatus.LOADING
            try {
                val response = GiphyAPI.retrofitAPIService
                        .getGiphySearchResponse(
                            searchQuery,
                            GIFS_PER_LOAD,
                            _gifs.value?.size ?: 0,
                            "g",
                            Locale.getDefault().language
                        )

                _gifs.value?.addAll(response.data)

                _status.value = APIStatus.FINISHED
                Log.d(
                    "viewModel API coroutine",
                    "API response: loaded, size: ${gifs.value?.size}"
                )
            } catch (e: Exception) {
                Log.e("viewModel API coroutine", "${e.message}")
                _status.value = APIStatus.ERROR
            }
        }
    }

    private fun clearGifsList() {
        _gifs.value?.clear()
    }

    init {
        initGifsSearch("cheese")
        Log.d("viewModel", "initialized")
    }
}