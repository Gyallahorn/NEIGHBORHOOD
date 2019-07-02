package com.esphereal.bair.funloot.retrofit

import com.esphereal.bair.funloot.dummyAdding.IventNews
import com.esphereal.bair.funloot.dummyAdding.IventProblems
import com.esphereal.bair.funloot.dummyAdding.ScoreBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import io.reactivex.Observable

interface  FunlootApi {
    /*    @GET("/posts/{id}")
    public Call<FunlootUser> getPostWithID(@Path("id") int id);

    @GET("/posts")
    public Call<List<FunlootUser>> getAllPosts();

    @GET("/posts")
    public Call<List<FunlootUser>> getPostOfUser(@Query("userId") int id);*/

    @GET("/api/login")
    fun getLogin(@Header("idtoken") idtoken: String): Call<Void>

    @GET("/api/user")
    fun getUser(@Header("idtoken") idtoken: String): Call<FunlootUser>

    @GET("api/news")
    fun getNews(@Header("idtoken") idToken: String):Observable<IventNews>

    @GET("/api/score")
    fun getScore (@Header ("idtoken") idToken: String):Call<ScoreBody>

    @POST("/api/user")
    fun postUser(@Header("idToken") idToken: String, @Body body: FunlootUser): Call<FunlootUser>

    @POST("/api/train")
    fun postTrain(@Header("idToken") idToken: String, @Body body: TrainBody): Observable<TrainBody>

    @POST( "/api/score")
    fun postScore (@Header("idToken") idToken: String,@Body body: ScoreBody ): Observable<ScoreBody>

    //This take data from backend, without idToken
    //
    @GET("/api/problems")
    fun getProblems():Observable<List<IventProblems>>

}
