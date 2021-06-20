package com.sample.rgtest.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton
import com.google.gson.GsonBuilder
import com.sample.rgtest.data.datasource.local.AppDatabase
import com.sample.rgtest.data.datasource.local.FeedItemDao
import com.sample.rgtest.data.datasource.remote.FeedsRemoteDataSource
import com.sample.rgtest.data.datasource.remote.FeedsService
import com.sample.rgtest.data.repository.FeedsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

/**
 * @author Mukesh
 * Dependency injection Application module
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    /**
     * @return HttpLoggingInterceptor
     */
    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor=HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor;
    }

    /**
     * @param interceptor HttpLoggingInterceptor
     * @return  OkHttpClient object
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(interceptor: HttpLoggingInterceptor)= OkHttpClient.Builder().addInterceptor(interceptor).build()


    /**
     * @param httpClient OkHttpClient
     * @return Retrofit object
     * SimpleXmlConverterFactory has been used as a converter. We know that this converter has been depricated but there is no alternate available till now.
     * Although SimpleXmlConverterFactory is depricated but it working as aspected
     * @see 'https://infinum.com/the-capsized-eight/to-parse-or-not-to-parse-xml-on-android-in-2019'
     * @see 'https://github.com/square/retrofit/issues/2733'
     */
    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl("https://blog.personalcapital.com/")
        .client(httpClient)
        .addConverterFactory(SimpleXmlConverterFactory.create())
        .build()

    /**
     * @return Gson object
     */
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    /**
     * @param retrofit Retrofit
     * @return FeedsService service object
     */
    @Provides
    fun provideFeedsService(retrofit: Retrofit): FeedsService = retrofit.create(
        FeedsService::class.java)

    /**
     * @param feedsService FeedsService
     * @return FeedsRemoteDataSource remote data source
     */
    @Singleton
    @Provides
    fun provideFeedsRemoteDataSource(feedsService: FeedsService) = FeedsRemoteDataSource(feedsService)

    /**
     * This method will create and return the Database object following the Singleton pattern
     * @param appContext Context
     * @return Database object
     */
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    /**
     * This method will create and return FeedItemDao object
     * @param db AppDatabase object
     * @return FeedItemDao
     */
    @Singleton
    @Provides
    fun provideFeedDao(db: AppDatabase) = db.feedItemDao()

    /**
     * Create and return the FeedsRepository object
     * @param remoteDataSource
     * @param localDataSource
     *
     */
    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: FeedsRemoteDataSource,
                          localDataSource: FeedItemDao
    ) =
        FeedsRepository(remoteDataSource, localDataSource)

}