package im.tobe.mvvmkotlin.model

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AnimalApi {

    @GET("getKey")
    fun getApiKey() : Single<ApiKey> // return Single value (observable) and finish it

    @FormUrlEncoded
    @POST("getAnimals")
    fun getAnimals(@Field("key") key : String): Single<List<Animal>>
}