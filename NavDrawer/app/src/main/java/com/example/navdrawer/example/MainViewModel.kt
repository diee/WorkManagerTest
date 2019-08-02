package com.example.navdrawer.example

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.navdrawer.example.interactor.GetPersonNetworkUseCase
import com.example.navdrawer.example.interactor.GetPersonUseCase
import com.example.navdrawer.example.interactor.SavePersonNetworkUseCase
import com.example.navdrawer.example.interactor.SavePersonUseCase
import com.example.navdrawer.data.Person
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(
    private val savePersonUseCase: SavePersonUseCase,
    private val getPersonUseCase: GetPersonUseCase,
    private val savePersonNetworkUseCase: SavePersonNetworkUseCase,
    private val getPersonNetworkUseCase: GetPersonNetworkUseCase
) :
    ViewModel() {

    val localList = MutableLiveData<List<Person>>()
    val syncedList = MutableLiveData<List<Person>>()

    val newPersonAdded = MutableLiveData<Long>()
    val syncedNewData = MutableLiveData<Boolean>()

    fun createNewPerson(person: Person) {
        Observable.fromCallable { savePersonUseCase.execute(person) }
            .doOnNext { id -> newPersonAdded.postValue(id) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun uploadNewPerson(person: Person) {
        Observable.fromCallable { savePersonNetworkUseCase.execute(person.apply { synced = true }) }
            .doOnNext { syncedNewData.postValue(true) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun getUnsycList() {
        Observable.fromCallable { getPersonUseCase.execute() }
            .doOnNext { localList.postValue(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun getSyncList() {
        Observable.fromCallable { getPersonNetworkUseCase.execute() }
            .doOnNext { syncedList.postValue(it) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

}