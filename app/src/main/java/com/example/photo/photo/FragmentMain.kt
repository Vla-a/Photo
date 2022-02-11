package com.example.photo.photo


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photo.R
import com.example.photo.adapters.PhotoAdapter
import com.example.photo.data.Photo
import com.example.photo.databinding.FragmentMainBinding
import com.example.photo.utilites.Utility
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class FragmentMain : Fragment(), PhotoAdapter.OnAdapter {

    private val mutList: MutableList<Photo> = mutableListOf()
    private var binding: FragmentMainBinding? = null
    val imageViewModel: ImageViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnDelete?.bringToFront()

        val photoAdapter = PhotoAdapter(
            longClickListener = ::longlickListener,
            clickOpen = ::KlickOpen,
            this
        )

        binding!!.largeLabel.setOnClickListener {
//            Toast.makeText(view?.context, "long click", Toast.LENGTH_SHORT).show()
            binding!!.tvTex.text = binding!!.editText.text.toString()
            binding!!.editText.setText(R.string.Street)
        }



        with(binding?.rvPhoto) {
            this?.layoutManager = context?.let { Utility.calculateNoOfColumns(it, 120f) }?.let {
                GridLayoutManager(
                    context,
                    it
                )
            }
            binding?.rvPhoto?.adapter = photoAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            imageViewModel.list.collect {
                photoAdapter.submitList(it)
            }
        }


        binding!!.btnAdd.setOnClickListener {
            openGalleryForImages()
        }

        binding!!.btnDelete.setOnClickListener {
            binding!!.btnDelete.visibility = View.INVISIBLE

            mutList.forEach { photo ->
                imageViewModel.deletePhoto(photo)
            }
            mutList.clear()
        }
    }

    private fun longlickListener() {
        imageViewModel.chengeList()
        binding!!.btnDelete.isInvisible = !binding!!.btnDelete.isInvisible

    }

    private fun openGalleryForImages() {

        if (Build.VERSION.SDK_INT < 19) {
            var intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Choose Pictures"), REQUEST_CODE
            )
        } else { // For latest versions API LEVEL 19+
            var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE);
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {

            // if multiple images are selected
            if (data?.clipData != null) {
                var count = data.clipData?.itemCount

                for (i in 0 until count!!) {
                    var imageUri: Uri = data.clipData?.getItemAt(i)!!.uri

                    imageViewModel.addPhotoToDatabase(
                        Photo(
                            imageUri.toString(),
                            100000,
                            true,
                            true
                        )
                    )
                }
            } else if (data?.data != null) {

                var imageUri: Uri? = data.data
                imageViewModel.addPhotoToDatabase(Photo(imageUri.toString(), 100000, true, true))
            }
        }
    }

    fun KlickOpen(photo: Photo) {

        this.findNavController().navigate(FragmentMainDirections.toBigPhotoFragment())
        setFragmentResult(TEST, Bundle().apply {
            putString(KEY1, photo.url)
        })
    }

    override fun onClik(photo: Photo) {

        if (!mutList.contains(photo)) {
            mutList.add(photo)
        } else {
            mutList.remove(photo)
        }
    }

    companion object {
        val REQUEST_CODE = 200
        const val TEST = "TEST"
        const val KEY1 = "KEY1"
    }
}

