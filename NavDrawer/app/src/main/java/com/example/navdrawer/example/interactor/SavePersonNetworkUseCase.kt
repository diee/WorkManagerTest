package com.example.navdrawer.example.interactor

import com.example.navdrawer.data.Person
import com.example.navdrawer.data.PersonDao

class SavePersonNetworkUseCase(private val personDao: PersonDao) {

    fun execute(person: Person): Long {
        person.apply { synced = true }
        return personDao.insertPerson(person)
    }
}