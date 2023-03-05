package com.sergei.vktesttask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.sergei.vktesttask.databinding.FragmentGifDetailBinding

class GifDetailFragment : Fragment() {

    private lateinit var binding: FragmentGifDetailBinding

    val args: GifDetailFragmentArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentGifDetailBinding.inflate(inflater)
        //return inflater.inflate(R.layout.fragment_gif_detail, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.gifSrcUrl = args.gifImgSrcUrl
        binding.gifIdArg = args.gifId
        binding.gifTitleArg = args.gifTitle
    }

}