package com.example.navdrawer.utils

import com.example.navdrawer.data.Person
import com.google.gson.Gson

object SerializerHelper {

    fun serializeToJson(person: Person): String = Gson().toJson(person)

    fun deserializeJson(personJson: String): Person = Gson().fromJson(personJson, Person::class.java)
}