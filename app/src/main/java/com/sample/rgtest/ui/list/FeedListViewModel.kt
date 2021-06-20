package com.sample.rgtest.ui.list
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sample.rgtest.data.entity.FeedItem
import com.sample.rgtest.data.repository.FeedsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel of the Feeds data
 */
@HiltViewModel
class FeedListViewModel @Inject constructor(
    private val repository: FeedsRepository
) : ViewModel() {
    private val categoryId: String = "3,891,890,68,284";
    /*get the feed data from the repository*/
    private fun getFeedData() = repository.getFeeds(categoryId)
    /*A Live data to be used for accept the boolean value to get latest data from the database and api*/
    val callApi: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val feeds= Transformations.switchMap(callApi) { _ -> getFeedData() }
    /*A live data which will hold the first item of the list of feed*/
    val firstItem= MutableLiveData<FeedItem>()
    /*A live data which will be responsible to hold the FeedItem data to be shown on the details page*/
    val itemForDetails= MutableLiveData<FeedItem>()
    init {
        callApi.value = true
    }

}