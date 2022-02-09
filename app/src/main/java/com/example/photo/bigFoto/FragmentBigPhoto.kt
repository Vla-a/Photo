package com.example.photo.bigFoto

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.photo.databinding.FragmentBigphotoBinding
import com.example.photo.databinding.FragmentMainBinding
import com.example.photo.photo.FragmentMain.Companion.KEY1
import com.example.photo.photo.FragmentMain.Companion.TEST

class FragmentBigPhoto : Fragment() {

    private var binding: FragmentBigphotoBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentBigphotoBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding!!.btnReturn.setOnClickListener {
            it.findNavController().popBackStack()
        }

        setFragmentResultListener(TEST) { key, bundle ->
            val uri: String = bundle.getString(KEY1) as String
            context?.let {
                binding?.let { it1 ->
                    Glide
                        .with(it)
                        .load(uri)
//                        .override(20,20)
                        .into(it1.imagePhoto)
                }
            }
        }
    }
}