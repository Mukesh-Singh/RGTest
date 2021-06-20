package com.sample.rgtest.ui.list

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.graphics.createBitmap
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.sample.rgtest.R
import com.sample.rgtest.data.entity.FeedItem
import com.sample.rgtest.databinding.FragmentListBinding
import com.sample.rgtest.util.Resource
import dagger.hilt.android.AndroidEntryPoint


/**
 * @author Mukesh
 * Feeds list fragment. This fragment will show the list list of feed on the screen
 */
@AndroidEntryPoint
class ListFragment : Fragment() {
    /*Layout binding*/
    private lateinit var binding: FragmentListBinding
    /*ViewModel for this fragment*/
    private val feedListViewModel:FeedListViewModel by activityViewModels()
    /*Adapter to show the data into the recycler view*/
    private lateinit var adapter: ListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //feedListViewModel.callApi.value = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        adapter = ListAdapter();
        /*Get the device type i.e. phone of table. For phone it will be 1 else 0 for tablet*/
        val deviceType = resources.getInteger(R.integer.device_type)
        if (deviceType ==1) {
            binding.recyclerView.layoutManager = LinearLayoutManager(
                context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        }
        else{
            binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
        }

        /*Set click listener on recycler view item*/
        adapter.itemClickListener = object: ListAdapter.ItemClickListener{
            override fun onItemClicked(item: FeedItem) {
                moveToDetails(item)
            }

        }
        binding.recyclerView.adapter = adapter
        binding.firstItemLayout.setOnClickListener {

            moveToDetails(feedListViewModel.firstItem.value)
        }
        /*Observe the feeds value from the ViewModel*/
        feedListViewModel.feeds.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Resource.Status.LOADING -> {
                    Log.d("FeedStatus", "Loading");
                    binding.recyclerView.visibility = View.GONE
                    binding.firstArticleCard.visibility = View.GONE
                    binding.progressBar.visibility = View.VISIBLE

                }
                Resource.Status.SUCCESS -> {
                    Log.d("FeedStatus", "Data size is ${it.data?.size ?: "No data size"}")
                    it.data?.let { it1 -> onDataReceived(it1) }
                }
                Resource.Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    it.message?.let { it1 ->
                        Log.e("FeedStatus", it1)
                        Toast.makeText(context, it1, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        })


    }

    /**
     * @param item
     * Show the details fragment for the selected item
     */
    private fun moveToDetails(item: FeedItem?) {
        feedListViewModel.itemForDetails.value = item
        val extras = FragmentNavigatorExtras(
            binding.imageView to "image",
        )
        val directions = ListFragmentDirections.listToDetailsFragment(item!!.title)
        findNavController().navigate(directions, extras)

    }

    /**
     * Show the received Feeds list on UI
     */
    private fun onDataReceived(data: List<FeedItem>){
        if (data.isEmpty())
            return
        binding.recyclerView.visibility =View.VISIBLE
        binding.firstArticleCard.visibility =View.VISIBLE
        binding.progressBar.visibility =View.GONE
        binding.title.text = data[0].title
        binding.description.text = data[0].description

        Glide.with(this)
            .load(data[0].imageUrl)
            .placeholder(R.drawable.place_holder_image)
            .apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
            .into(binding.imageView)
        feedListViewModel.firstItem.value = data[0]
        adapter.updateList(data.subList(1, data.size))

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_refresh){
            adapter.clearList()
            feedListViewModel.callApi.value = true
        }
        return super.onOptionsItemSelected(item)
    }
}