package com.sample.rgtest.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author Mukesh
 * Entity class which format will be used to same data in the data and further used as a pojo class in the code
 */
@Entity(tableName = "feed_items")
data class FeedItem(
    @PrimaryKey
    val title: String,
    val link: String,
    val pubDate: String,
    val description: String,
    val imageUrl: String
)
