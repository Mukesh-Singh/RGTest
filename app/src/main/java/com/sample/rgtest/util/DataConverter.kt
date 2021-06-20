package com.sample.rgtest.util

import com.sample.rgtest.data.entity.FeedItem
import com.sample.rgtest.data.modal.RssFeed
import com.sample.rgtest.data.modal.RssItem

/**
 * @author Mukesh
 * Data converter Utility for the applicatoin
 */
object DataConverter {
    /**
     * @param rssFeed
     * This method will convert the RssFeed to FeedItem list. The converted list can be used to store the data into the data base or can be used to the data on the screen
     * @return ArrayList<FeedItem>
     */
    fun getFeedItemsFromRssFeeds(rssFeed: RssFeed?): ArrayList<FeedItem>{
        val list= ArrayList<FeedItem>();
        rssFeed.let {
            it?.channel?.item?.forEach { rssItem ->
                list.add(FeedItem(rssItem.title,rssItem.link,rssItem.pubDate,rssItem.description,rssItem.imageUrl))
            }
        }
        return list;
    }
}