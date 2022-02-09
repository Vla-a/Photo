package com.example.photo.photo


import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.photo.R
import com.example.photo.adapters.PhotoAdapter
import com.example.photo.data.Photo
import com.example.photo.databinding.FragmentMainBinding
import com.example.photo.databinding.ItemPhotoBinding
import com.example.photo.utilites.Utility
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.*

class FragmentMain: Fragment(){

    private var list: MutableList<Photo> = mutableListOf()
    private var binding: FragmentMainBinding? = null
    val imageViewModel: ImageViewModel by viewModel()
    private var saveList: List<Photo> = mutableListOf()
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

        val photoAdapter = PhotoAdapter(
            characterList = list,
            clickListener = ::clickListener,
            longClickListener =::longlickListener
        )

        binding!!.largeLabel.setOnClickListener {
//            Toast.makeText(view?.context, "long click", Toast.LENGTH_SHORT).show()
          binding!!.tvTex.text = binding!!.editText.text.toString()
            binding!!.editText.setText(R.string.Street)
        }



        with(binding?.rvPhoto) {
            this?.layoutManager = context?.let {Utility.calculateNoOfColumns(it, 120f) }?.let {
                GridLayoutManager(
                    context,
                    it
                )
            }
            binding?.rvPhoto?.adapter = photoAdapter
        }

        imageViewModel.photoListLiveData.observe(this.viewLifecycleOwner, { it ->
            it.forEach { it.url?.let { it1 -> Log.e("RER", it1) } }
            photoAdapter.update(it)
             saveList = it;
        })

        binding!!.btnAdd.setOnClickListener {
            openGalleryForImages()
        }
        binding!!.btnDelete.setOnClickListener {
            binding!!.btnDelete.visibility = View.INVISIBLE
            saveList.forEach { item ->
                imageViewModel.addPhotoToDatabase(Photo(item.id,item.url, item.date, true,true))
            }
        }
    }

    private fun clickListener(photo: Photo) {

        this.findNavController().navigate(FragmentMainDirections.toBigPhotoFragment())
        setFragmentResult(TEST, Bundle().apply {
            putString(KEY1, photo.url)
        })
    }
    private fun longlickListener() {
        binding!!.btnDelete.visibility = View.VISIBLE
        saveList.forEach { item ->
            if(item.baptized){
            imageViewModel.deletePhoto(item)
//                imageViewModel.deleteMessage(item)

            }
            imageViewModel.addPhotoToDatabase(Photo(item.id,item.url, item.date, false,item.baptized))
        }
       binding!!.btnDelete.visibility = View.VISIBLE
        }



    private fun openGalleryForImages() {

        if (Build.VERSION.SDK_INT < 19) {
            var intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Choose Pictures")
                , REQUEST_CODE
            )
        }
        else { // For latest versions API LEVEL 19+
            var intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "image/*"
            startActivityForResult(intent, REQUEST_CODE);
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE){

            // if multiple images are selected
            if (data?.clipData != null) {
                var count = data.clipData?.itemCount

                for (i in 0 until count!!) {
                    var imageUri: Uri = data.clipData?.getItemAt(i)!!.uri

                    imageViewModel.addPhotoToDatabase(Photo(i.toLong(), imageUri.toString(), 100000, true,true))
                    
                    //     iv_image.setImageURI(imageUri) Here you can assign your Image URI to the ImageViews
                }

            } else if (data?.data != null) {
                // if single image is selected

                var imageUri: Uri? = data.data
                //   iv_image.setImageURI(imageUri) Here you can assign the picked image uri to your imageview
                imageViewModel.addPhotoToDatabase(Photo(111, imageUri.toString(), 100000, true,true))
            }
        }
    }

    companion object {
        val REQUEST_CODE = 200
        const val TEST = "TEST"
        const val KEY1 = "KEY1"
    }
}

