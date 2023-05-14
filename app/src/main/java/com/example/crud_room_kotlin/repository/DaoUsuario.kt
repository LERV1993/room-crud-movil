package com.example.crud_room_kotlin.repository

import androidx.room.*
import com.example.crud_room_kotlin.model.Usuario

@Dao
interface DaoUsuario {

    @Query("SELECT * FROM usuarios")
    suspend fun obtenerUsuarios(): MutableList<Usuario>

    @Insert
    suspend fun agregarUsuario(usuario:Usuario)

    @Query("UPDATE usuarios SET pais=:pais WHERE usuario=:usuario ")
    suspend fun actualizarUsuario(usuario: String, pais: String)

    @Query("DELETE FROM usuarios WHERE usuario=:usuario")
    suspend fun borrarUsuario(usuario: String)
}