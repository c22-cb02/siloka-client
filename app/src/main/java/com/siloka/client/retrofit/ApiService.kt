package com.siloka.client.retrofit

import retrofit2.Call
import retrofit2.http.*
import java.math.BigInteger

interface ApiService {
    @GET("generate-roomid")
    fun getRoomId(
        @Path("room_id") room_id: BigInteger
    )

    @POST("message")
    fun postMessage(
        @Field("content") content: String,
    ): Call<Mess>

    @GET("to-cs")
    fun toCS(
        @Query("q") q: String
    ): Call<SearchUserResponseTest>

    @GET("users/{username}/followers")
    fun  getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<ListUserResponseItem>>

    @GET("users/{username}/following")
    fun  getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<ListUserResponseItem>>
}