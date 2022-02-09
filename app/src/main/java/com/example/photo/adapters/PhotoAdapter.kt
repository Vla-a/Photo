package com.example.photo.adapters

import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.example.photo.data.Photo
import com.example.photo.databinding.ItemPhotoBinding
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.text.SimpleDateFormat
import java.util.*

class PhotoAdapter(
    private val characterList: MutableList<Photo> = mutableListOf(),
    private val clickListener: (Photo) -> Unit,
    private val longClickListener: () -> Unit
) : RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder>() {


    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder =
        PhotoViewHolder(
            ItemPhotoBinding.inflate(LayoutInflater.from(parent.context)), clickListener,
            longClickListener
        )


    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(characterList[position])
    }

    fun update(newCharacterList: List<Photo>) {
        characterList.clear()
        characterList.addAll(newCharacterList)
        notifyDataSetChanged()

    }

    class PhotoViewHolder(
        private val bindingView: ItemPhotoBinding,
        private val clickListener: (Photo) -> Unit,
        private val longClickListener: () -> Unit

    ) : RecyclerView.ViewHolder(bindingView.root) {

        fun bind(item: Photo) {
            bindingView.imageSrc.isInvisible = item.circle
//            bindingView.imageSrcC.visibility = View.GONE

            Glide
                .with(itemView.context)
                .load(item.url)
                .into(bindingView.imageView)

            itemView.setOnClickListener {
                clickListener(item)

            }

            itemView.setOnLongClickListener {
                longClickListener()
                true
            }

            bindingView.imageSrc.setOnClickListener {
                if(bindingView.imageSrcC.isInvisible){
                    bindingView.imageSrcC.isInvisible = false
                    item.baptized = true
                }else{
                    bindingView.imageSrcC.isInvisible = true
                    item.baptized = false
                }

            }
        }
    }
}