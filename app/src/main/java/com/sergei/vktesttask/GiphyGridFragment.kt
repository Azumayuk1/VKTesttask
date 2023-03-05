package com.sergei.vktesttask

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
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

        Log.d("GridFragment", "Fragment created")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.gifsGrid.adapter = GiphyGridAdapter()
        Log.d("Giphy Grid", "Adapter set")

        // Pagination
        // Пагинация: подгружает новые гифки (по GIFS_ON_PAN_LOAD штук)
        // когда пользователь прокручивает до конца
        binding.gifsGrid.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (!binding.gifsGrid.canScrollVertically(1) && dy != 0) {
                    viewModel.loadMoreGifs()

                    binding.gifsGrid.post {
                        binding.gifsGrid.adapter?.notifyItemRangeInserted(
                            viewModel.gifs.value!!.size,
                            GIFS_ON_PAN_LOAD
                        )
                    }
                }
            }
        }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val searchView = menu.findItem(R.id.app_bar_search).actionView as SearchView
        with(searchView) {
            isFocusable = false
            isIconifiedByDefault = false
            queryHint = "Search GIFs!"

            // Обработка ввода пользователем запроса в SearchView
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    viewModel.initGifsSearch(query ?: "")
                    binding.gifsGrid.post {
                        binding.gifsGrid.adapter?.notifyDataSetChanged()
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            })
        }

    }
}