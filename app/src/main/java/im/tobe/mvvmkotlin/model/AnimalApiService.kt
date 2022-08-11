package im.tobe.mvvmkotlin.model

import io.reactivex.rxjava3.core.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class AnimalApiService {
    private val BASE_URL = "https://us-central1-apis-4674e.cloudfunctions.net"
    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()) // translate JSON API response to a list of Animal model
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create()) // converts into Single observable
        .build()
        .create(AnimalApi::class.java)

    fun getApiKey() : Single<ApiKey> {
        return api.getApiKey()
    }

    fun getAnimals(key: String) : Single<List<Animal>> {
        return api.getAnimals(key)
    }
}