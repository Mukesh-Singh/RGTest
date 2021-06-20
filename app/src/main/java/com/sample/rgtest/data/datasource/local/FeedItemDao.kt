package com.sample.rgtest.data.datasource.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sample.rgtest.data.entity.FeedItem

/**
 * @author Mukesh
 * Dao class for FeedItem Entity
 */
@Dao
interface FeedItemDao {

    @Query("SELECT * FROM feed_items")
    fun getAllFeeds() : LiveData<List<FeedItem>>

    @Query("SELECT * FROM feed_items WHERE title = :title")
    fun getFeed(title: String) : LiveData<FeedItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<FeedItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(character: FeedItem)

}