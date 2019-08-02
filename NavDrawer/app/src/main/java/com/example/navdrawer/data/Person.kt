package com.example.navdrawer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "persons"
)
data class Person(
    @PrimaryKey val id: Long? = null,
    var name: String? = null, var age: Int? = null, var synced: Boolean? = false
) {

    override fun toString(): String {
        return "$name $synced"
    }
}