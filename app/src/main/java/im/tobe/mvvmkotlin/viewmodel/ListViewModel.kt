package im.tobe.mvvmkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import im.tobe.mvvmkotlin.model.Animal
import im.tobe.mvvmkotlin.model.AnimalApiService
import im.tobe.mvvmkotlin.model.ApiKey
import im.tobe.mvvmkotlin.util.SharedPreferencesHelper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

// google does not recommend passing context into viewmodel
class ListViewModel(application: Application): AndroidViewModel(application) {
    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val isloading by lazy { MutableLiveData<Boolean>() }

    private val disposable = CompositeDisposable()
    private val apiService = AnimalApiService()

    private val prefs = SharedPreferencesHelper(getApplication())
    private var invalidApiKey = false

    fun refresh() {
        isloading.value = true
        invalidApiKey = false
        val key = prefs.getApiKey()
        if (key.isNullOrEmpty()) {
            getKey()
        } else {
            getAnimals(key)
        }
    }

    private fun getKey() {
        disposable.add(
            apiService.getApiKey()
                .subscribeOn(Schedulers.newThread()) // put to background thread
                .observeOn(AndroidSchedulers.mainThread()) // when the api returns, return the result to main thread
                .subscribeWith(object: DisposableSingleObserver<ApiKey>(){ // get the information
                    override fun onSuccess(key: ApiKey) {
                        if (key.key.isNullOrEmpty()) {
                            loadError.value = true
                            isloading.value = false
                        } else {
                            prefs.saveApiKey(key.key)
                            getAnimals(key.key)
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (!invalidApiKey) {
                            invalidApiKey = true
                            getKey()
                        } else {
                            e.printStackTrace()
                            isloading.value = false
                            loadError.value = true
                        }
                    }

                })
        )
    }

    private fun getAnimals(key : String) {

        disposable.add(
            apiService.getAnimals(key)
                .subscribeOn(Schedulers.newThread()) // put to background thread
                .observeOn(AndroidSchedulers.mainThread()) // when the api returns, return the result to main thread
                .subscribeWith(object: DisposableSingleObserver<List<Animal>>(){ // get the information
                    override fun onSuccess(list: List<Animal>) {
                        loadError.value = false
                        isloading.value = false
                        animals.value = list
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()

                        loadError.value = true
                        isloading.value = false
                        animals.value = null
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()

        // before the viewmodel is destroyed, there still rxjava link (Single observable) which may lead to data leak.
        // therefore need to dispose it.
        disposable.clear()
    }
}