package com.example.navdrawer.example.interactor

import com.example.navdrawer.data.Person
import com.example.navdrawer.data.PersonDao

class SavePersonNetworkUseCase(private val personDao: PersonDao) {

    fun execute(person: Person) {
        person.apply { synced = true }
        personDao.updatePerson(person)
    }
}