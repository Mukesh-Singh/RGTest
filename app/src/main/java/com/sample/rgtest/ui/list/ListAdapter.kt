package com.sample.rgtest.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sample.rgtest.R
import com.sample.rgtest.data.entity.FeedItem
import com.sample.rgtest.databinding.RowFeedItemBinding

/**
 * Recycler view Adapter to show the list of the FeedItem on the Feeds list screen
 */
class ListAdapter(): RecyclerView.Adapter<ListAdapter.ItemViewHolder>() {
    private var listData= mutableListOf<FeedItem>()
    var itemClickListener: ItemClickListener? = null


    /**
     * ItemClickListener to set the click listener on the list items
     */
    interface ItemClickListener{
        fun onItemClicked(binding: RowFeedItemBinding,item: FeedItem)
    }

    /**
     * Update the list with new list data
     */
    fun updateList(newList: List<FeedItem>){
        listData.let {
            it.clear()
            listData.addAll(newList)
            notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater= LayoutInflater.from(parent.context)
       val binding = RowFeedItemBinding.inflate(inflater,parent,false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindData(listData[position])
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    fun clearList() {
        listData.let {
            it.clear()
            notifyDataSetChanged()
        }
    }


    inner class ItemViewHolder(private val binding: RowFeedItemBinding): RecyclerView.ViewHolder(binding.root) {
        /**
         * @param itemData FeedItem
         * show the data on the list item
         */
        fun bindData(itemData: FeedItem){
            binding.title.text = itemData.title
            binding.imageView.transitionName= "Image-$adapterPosition"
            Glide.with(binding.imageView)
                .load(itemData.imageUrl)
                .placeholder(R.drawable.place_holder_image)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
                .into(binding.imageView)
            binding.itemLayout.setOnClickListener {
                itemClickListener.let {
                    it?.onItemClicked(binding,itemData)
                }
            }
        }
    }

}