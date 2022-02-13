package com.example.photo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.photo.data.Photo
import com.example.photo.databinding.ItemPhotoBinding

class PhotoAdapter(

    private val longClickListener: () -> Unit,
    private val clickOpen: (Photo) -> Unit,
    private val clickDelete: OnAdapter

) : ListAdapter<Photo, PhotoAdapter.PhotoViewHolder>(DiffUtilItemCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoAdapter.PhotoViewHolder =
        PhotoViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: PhotoAdapter.PhotoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PhotoViewHolder(
        private val bindingView: ItemPhotoBinding

    ) : RecyclerView.ViewHolder(bindingView.root) {

        fun bind(item: Photo) {
            bindingView.imageSrc.isInvisible = item.circle
            bindingView.imageSrcC.isInvisible = item.baptized


            Glide
                .with(itemView.context)
                .load(item.url)
                .into(bindingView.imageView)

            bindingView.root.setOnLongClickListener {
                longClickListener()
                true
            }

            bindingView.imageSrc.setOnClickListener {
                if (bindingView.imageSrcC.isInvisible) {
                    bindingView.imageSrcC.visibility = View.VISIBLE

                } else {
                    bindingView.imageSrcC.visibility = View.INVISIBLE
                }
                clickDelete.onClik(item)
            }
                            itemView.setOnClickListener {
                    clickOpen(item)
                }
        }
    }

    interface OnAdapter {
        fun onClik(photo: Photo)
    }
}

class DiffUtilItemCallback : DiffUtil.ItemCallback<Photo>() {
    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.url == newItem.url
    }
}