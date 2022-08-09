package im.tobe.mvvmkotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import im.tobe.mvvmkotlin.model.Animal

// google does not recommend passing context into viewmodel
class ListViewModel(application: Application): AndroidViewModel(application) {
    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val isloading by lazy { MutableLiveData<Boolean>() }

    fun refresh() {
        // mock data now
        getAnimals()
    }

    private fun getAnimals() {
        val a1 = Animal("tiger")
        val a2 = Animal("dog")
        val a3 = Animal("cat")
        val a4 = Animal("panda")
        val a5 = Animal("bear")

        val animalList : ArrayList<Animal> = arrayListOf(a1, a2, a3, a4, a5)
        animals.value = animalList
        loadError.value = false
        isloading.value = false
    }
}