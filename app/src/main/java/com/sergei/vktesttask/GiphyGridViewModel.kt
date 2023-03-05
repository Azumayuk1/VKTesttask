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

const val GIFS_PER_LOAD = 25
const val GIFS_ON_PAN_LOAD = 10

enum class APIStatus { WAITING, LOADING, NO_RESULTS, ERROR, FINISHED }

class GiphyGridViewModel : ViewModel() {

    private val _status = MutableLiveData(APIStatus.WAITING)
    val status: LiveData<APIStatus> = _status

    private val _gifs = MutableLiveData<MutableList<GiphyGifData>>(mutableListOf<GiphyGifData>())
    val gifs: LiveData<MutableList<GiphyGifData>> = _gifs

    private val _currentSearchQuery = MutableLiveData<String>("")
    //val currentSearchQuery: LiveData<String> = _currentSearchQuery

    /**
     * Called on search in SearchView.
     * Вызывается при запросе.
     */
    fun initGifsSearch(searchQuery: String) {
        // Clear any GIFs from last search
        clearGifsList()
        _currentSearchQuery.value = searchQuery

        viewModelScope.launch {
            _status.value = APIStatus.LOADING
            try {
                val response = GiphyAPI.retrofitAPIService
                        .getGiphySearchResponse(
                            _currentSearchQuery.value!!,
                            GIFS_PER_LOAD,
                            _gifs.value?.size ?: 0,
                            "g",
                            Locale.getDefault().language
                        )

                _gifs.value?.addAll(response.data)

                if (gifs.value?.size != 0) {
                    _status.value = APIStatus.FINISHED
                } else {
                    _status.value = APIStatus.NO_RESULTS
                }

                Log.d(
                    "viewModel API coroutine",
                    "API response: loaded, size: ${_gifs.value?.size ?: 0}"
                )
            } catch (e: Exception) {
                Log.e("viewModel API coroutine", "${e.message}")
                _status.value = APIStatus.ERROR
            }
        }
    }

    /**
     * Used for pagination.
     * Loads next gifs
     * with given current offset.
     * Загружает больше гиф по
     * тому жу запросу.
     */
    fun loadMoreGifs() {
        viewModelScope.launch {
            _status.value = APIStatus.LOADING

            val response = GiphyAPI.retrofitAPIService
                .getGiphySearchResponse(
                    _currentSearchQuery.value!!,
                    GIFS_ON_PAN_LOAD,
                    _gifs.value?.size ?: 0,
                    "g",
                    Locale.getDefault().language
                )

            _gifs.value!!.addAll(response.data)
        }
    }

    private fun clearGifsList() {
        _gifs.value?.clear()
    }

    init {
        initGifsSearch("")
        Log.d("viewModel", "initialized")
    }
}