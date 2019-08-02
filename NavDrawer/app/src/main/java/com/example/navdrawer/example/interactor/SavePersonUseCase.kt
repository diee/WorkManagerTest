package com.example.navdrawer.example.interactor

import androidx.work.*
import com.example.navdrawer.data.Person
import com.example.navdrawer.data.PersonDao
import com.example.navdrawer.example.MainActivity
import com.example.navdrawer.utils.SerializerHelper
import com.example.navdrawer.workers.PERSON_IN
import com.example.navdrawer.workers.SavePersonNetworkWorker

class SavePersonUseCase(private val personDao: PersonDao) {

    fun execute(person: Person): Long {
        val workId = personDao.insertPerson(person)
        doWorkSavePerson(person)
        return workId
    }

    private fun doWorkSavePerson(person: Person) {
        val saveNetworkPerson = OneTimeWorkRequest.Builder(SavePersonNetworkWorker::class.java)
            .setConstraints(Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build())
            .setInputData(Data.Builder().putString(PERSON_IN, SerializerHelper.serializeToJson(person)).build())
            .addTag(MainActivity.WORK_TAG)
            .build()


        val workManager = WorkManager.getInstance()

        workManager.beginUniqueWork("WorkPerson", ExistingWorkPolicy.APPEND, saveNetworkPerson).enqueue()
    }
}