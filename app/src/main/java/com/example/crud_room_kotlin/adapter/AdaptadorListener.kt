package com.example.crud_room_kotlin.adapter

import com.example.crud_room_kotlin.model.Usuario

interface AdaptadorListener {

    fun onEditItemClick(usuario: Usuario)
    fun onDeleteItemClick(usuario: Usuario)
}