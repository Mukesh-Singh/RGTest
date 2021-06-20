package com.sample.rgtest.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.sample.rgtest.util.Resource
import com.sample.rgtest.data.datasource.remote.FeedsRemoteDataSource
import com.sample.rgtest.data.datasource.local.FeedItemDao
import com.sample.rgtest.data.entity.FeedItem
import com.sample.rgtest.data.modal.RssFeed
import com.sample.rgtest.util.DataConverter
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

/**
 * @author Mukesh
 * Feeds repository of the application. The class is responsible to get the data to be shown for the feeds module
 */
class FeedsRepository @Inject constructor(
    private val remoteDataSource: FeedsRemoteDataSource,
    private val localDataSource: FeedItemDao
){
    fun getFeeds(cat:String): LiveData<Resource<List<FeedItem>>> =
        liveData(Dispatchers.IO) {
            Log.d("FeedsRepository", "Calling the api");
            emit(Resource.loading())
            val source = localDataSource.getAllFeeds().map { Resource.success(it) }
            emitSource(source)

            val response = remoteDataSource.getAllFeeds(cat)
            if (response.status == Resource.Status.SUCCESS){
                val  data: RssFeed = response.data!!;
                val list= DataConverter.getFeedItemsFromRssFeeds(rssFeed = data)
                localDataSource.insertAll(list)
            }else if (response.status == Resource.Status.ERROR) {
                emit(Resource.error(response.message!!))
                emitSource(source)

            }
        }
}