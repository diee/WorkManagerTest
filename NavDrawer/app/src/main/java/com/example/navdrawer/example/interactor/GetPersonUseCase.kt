package com.example.navdrawer.example.interactor

import com.example.navdrawer.data.Person
import com.example.navdrawer.data.PersonDao
import io.reactivex.Flowable
import io.reactivex.Single

class GetPersonUseCase(private val personDao: PersonDao){

    fun execute(): List<Person>{
        return personDao.getPersonsUnsynced()
    }
}