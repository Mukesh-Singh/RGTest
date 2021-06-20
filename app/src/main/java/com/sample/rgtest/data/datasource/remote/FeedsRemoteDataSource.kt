package com.sample.rgtest.data.datasource.remote

import com.sample.rgtest.data.datasource.BaseDataSource
import javax.inject.Inject

/**
 * @author Mukesh
 * Remote data source for the Feed service. This class is responsible to call the Feeds API using Feed service
 */
class FeedsRemoteDataSource @Inject constructor(
    private val feedsService: FeedsService
): BaseDataSource() {
    /**
     * @param cat categories seprated by "," i.e. 3,891,890,68,284
     *
     */
    suspend fun getAllFeeds(cat: String) = getResult { feedsService.getAllFeeds(cat) }

}