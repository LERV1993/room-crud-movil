package com.example.crud_room_kotlin.repository

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.crud_room_kotlin.model.Usuario

@Database(
    entities = [Usuario::class],
    version = 1
)
abstract class DBprueba: RoomDatabase() {

    abstract fun daoUsusario() : DaoUsuario
}