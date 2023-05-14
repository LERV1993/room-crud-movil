package com.example.crud_room_kotlin.model

import androidx.room.*


@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey var usuario: String,
    @ColumnInfo(name = "pais") var pais: String
)
