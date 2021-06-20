package com.sample.rgtest.ui.details

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.sample.rgtest.data.entity.FeedItem
import com.sample.rgtest.databinding.FragmentDetailsBinding
import com.sample.rgtest.ui.list.FeedListViewModel
import com.sample.rgtest.util.formatTo
import com.sample.rgtest.util.toDate
import dagger.hilt.android.AndroidEntryPoint

/**
 * Item details [Fragment] to show thedatils of the item on the screen.
 */
@AndroidEntryPoint
class DetailsFragment : Fragment() {
    /*layout binding class*/
    private lateinit var binding: FragmentDetailsBinding
    /*ViewModel to the business logic of this fragment*/
    private val feedListViewModel: FeedListViewModel by activityViewModels()
    /*Chrome custom builder*/
    private var builder = CustomTabsIntent.Builder()
    private lateinit var data: FeedItem;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*Observe the itemForDetails data of the view model*/
        feedListViewModel.itemForDetails.observe(viewLifecycleOwner, Observer {
            Log.d("ForDetails", it.title)
            data = it
            showData(it)
        })
        /*Click listener of read full story button*/
        binding.readFullStory.setOnClickListener {
            if (this::data.isInitialized) {
                var customTabsIntent: CustomTabsIntent = builder.build();
                customTabsIntent.launchUrl(requireContext(), Uri.parse(data.link))
            }
        }
    }

    /**
     * This method will set the data to the UI controls
     */
    private fun showData(data: FeedItem){
        binding.description.text = data.description
        val date = (data.pubDate).toDate().formatTo("dd MMM yyyy")
        binding.time.text = date
        Glide.with(this)
            .load(data.imageUrl)
            .placeholder(com.sample.rgtest.R.drawable.place_holder_image)
            //.apply(RequestOptions.bitmapTransform(RoundedCorners(16)))
            .into(binding.imageView)
    }

}