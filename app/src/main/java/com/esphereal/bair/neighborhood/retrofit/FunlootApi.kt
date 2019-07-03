package com.esphereal.bair.neighborhood.retrofit


import com.esphereal.bair.neighborhood.NEIGHBORHOOD.NibbaProblemsBody
import com.esphereal.bair.neighborhood.NEIGHBORHOOD.NibbaPostBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import io.reactivex.Observable

interface  FunlootApi {

    @POST( "/api/score")
    fun postProblems (@Header("idToken") idToken: String,@Body body: NibbaPostBody): Observable<NibbaPostBody>

    //This take data from backend, without idToken
    //
    @GET("/api/problems")
    fun getProblems():Observable<List<NibbaProblemsBody>>

}
