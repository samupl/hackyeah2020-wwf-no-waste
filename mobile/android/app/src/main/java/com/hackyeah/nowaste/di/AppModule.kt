package com.hackyeah.nowaste.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hackyeah.nowaste.data.remote.CouponRemoteDataSource
import com.hackyeah.nowaste.data.remote.CouponService
import com.hackyeah.nowaste.data.remote.ProductRemoteDataSource
import com.hackyeah.nowaste.data.remote.ProductService
import com.hackyeah.nowaste.data.repository.CouponRepository
import com.hackyeah.nowaste.data.repository.ProductRepository
import com.hackyeah.nowaste.utils.Session
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Session.productsBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideProductService(retrofit: Retrofit): ProductService =
        retrofit.create(ProductService::class.java)

    @Provides
    fun provideCouponService(retrofit: Retrofit): CouponService {
        val retrofit2 = Retrofit.Builder()
            .baseUrl(Session.couponBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
        return retrofit2.create(CouponService::class.java)
    }

    @Singleton
    @Provides
    fun provideProductRemoteDataSource(productService: ProductService) =
        ProductRemoteDataSource(productService)

    @Singleton
    @Provides
    fun provideProductRepository(remoteDataSource: ProductRemoteDataSource) =
        ProductRepository(remoteDataSource)

    @Singleton
    @Provides
    fun provideCouponRemoteDataSource(couponService: CouponService) =
        CouponRemoteDataSource(couponService)

    @Singleton
    @Provides
    fun provideCouponRepository(remoteDataSource: CouponRemoteDataSource) =
        CouponRepository(remoteDataSource)
}