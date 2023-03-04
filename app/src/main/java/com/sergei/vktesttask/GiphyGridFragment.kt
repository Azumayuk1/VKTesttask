package com.sergei.vktesttask

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import com.sergei.vktesttask.databinding.FragmentGiphyGridBinding

@Suppress("DEPRECATION")
class GiphyGridFragment : Fragment() {

    private val viewModel: GiphyGridViewModel by activityViewModels()

    private lateinit var binding: FragmentGiphyGridBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGiphyGridBinding.inflate(inflater)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.gifsGrid.adapter = GiphyGridAdapter()

        Log.d("GridFragment", "Fragment created")
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        with(searchView) {
            isIconified = false
            queryHint = "Search GIFs!"
            setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.initGifsSearch(query ?: "")
                    binding.gifsGrid.adapter?.notifyDataSetChanged()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }

    }
}