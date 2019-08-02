package com.example.navdrawer.data

import androidx.room.*
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: Person): Long

    @Update
    fun updatePerson(person: Person)

    @Query("SELECT * FROM persons WHERE id= :id")
    fun getPersonById(id: Long): Person

    @Query("SELECT * FROM persons WHERE synced=0")
    fun getPersonsUnsynced(): List<Person>

    @Query("SELECT * FROM persons WHERE synced=1")
    fun getPersonsSynced(): List<Person>
}