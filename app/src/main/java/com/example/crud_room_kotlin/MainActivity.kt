package com.example.crud_room_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.crud_room_kotlin.adapter.AdaptadorListener
import com.example.crud_room_kotlin.adapter.AdaptadorUsuarios
import com.example.crud_room_kotlin.databinding.ActivityMainBinding
import com.example.crud_room_kotlin.model.Usuario
import com.example.crud_room_kotlin.repository.DBprueba
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() , AdaptadorListener {

    lateinit var binding: ActivityMainBinding
    lateinit var adaptador: AdaptadorUsuarios
    lateinit var room: DBprueba
    lateinit var usuario: Usuario
    var listaUsuarios: MutableList<Usuario> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUsuarios.layoutManager = LinearLayoutManager(this)
        room = Room.databaseBuilder(this,DBprueba::class.java, "DBpruebas").build()
        obtenerUsuarios(room)

        binding.btnAddUpdate.setOnClickListener {
            if(binding.etUsuario.text.isNullOrEmpty() || binding.etPais.text.isNullOrEmpty()){
                Toast.makeText(this, "Por favor complete los campos.",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(binding.btnAddUpdate.text.equals("agregar")){
                usuario = Usuario(
                    binding.etUsuario.text.toString().trim(),
                    binding.etPais.text.toString().trim()
                )

                agregarUsuarios(room,usuario)
            }else if(binding.btnAddUpdate.text.equals("actualizar")){
                usuario.pais =  binding.etPais.text.toString().trim()

                actualizarUsuario(room,usuario)
            }
        }
    }

    fun obtenerUsuarios(room: DBprueba){
        lifecycleScope.launch {
            listaUsuarios = room.daoUsusario().obtenerUsuarios()
            adaptador = AdaptadorUsuarios(listaUsuarios,this@MainActivity)
            binding.rvUsuarios.adapter = adaptador
        }
    }

    fun agregarUsuarios(room: DBprueba, usuario: Usuario){
        lifecycleScope.launch{
            room.daoUsusario().agregarUsuario(usuario)
            obtenerUsuarios(room)
            limpiarCampos()
        }
    }

    fun actualizarUsuario(room: DBprueba, usuario: Usuario){
        lifecycleScope.launch{
            room.daoUsusario().actualizarUsuario(usuario.usuario, usuario.pais)
            obtenerUsuarios(room)
            limpiarCampos()
        }
    }

    fun limpiarCampos(){
        usuario.usuario = ""
        usuario.pais = ""
        binding.etUsuario.setText("")
        binding.etPais.setText("")

        if(binding.btnAddUpdate.text.equals("actualizar")){
            binding.btnAddUpdate.setText("agregar")
            binding.etUsuario.isEnabled = true
        }
    }

    override fun onEditItemClick(usuario: Usuario) {
        binding.btnAddUpdate.setText("actualizar")
        binding.etUsuario.isEnabled = false
        this.usuario = usuario
        binding.etUsuario.setText(this.usuario.usuario)
        binding.etPais.setText(this.usuario.pais)
    }

    override fun onDeleteItemClick(usuario: Usuario) {
        lifecycleScope.launch {
            room.daoUsusario().borrarUsuario(usuario.usuario)
            adaptador.notifyDataSetChanged()
            obtenerUsuarios(room)
        }
    }
}