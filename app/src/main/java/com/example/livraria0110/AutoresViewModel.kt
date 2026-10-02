package com.example.livraria0110

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class AutoresViewModel : ViewModel() {

    private val _autores = MutableStateFlow<List<Autor>>(emptyList())

    val autores = _autores.asStateFlow()

    fun findById(id: UUID?): Autor? {
        if (id == null) {
            return null
        }

        return _autores.value.firstOrNull { autor ->
            autor.id == id
        }
    }

    fun save(
        id: UUID?,
        nome: String,
        nacionalidade: String,
        ativo: Boolean
    ) {

        val listaAtual = _autores.value.toMutableList()

        // Se existe ID, estamos editando
        if (id != null) {

            val index = listaAtual.indexOfFirst {
                it.id == id
            }

            if (index != -1) {
                listaAtual[index] = Autor(
                    id = id,
                    nome = nome,
                    nacionalidade = nacionalidade,
                    ativo = ativo
                )
            }

        } else {

            // Se não existe ID, cria um novo autor
            val novoAutor = Autor(
                id = UUID.randomUUID(),
                nome = nome,
                nacionalidade = nacionalidade,
                ativo = ativo
            )

            listaAtual.add(novoAutor)
        }

        _autores.value = listaAtual
    }

    fun remove(autor: Autor) {
        _autores.value -= autor
    }
}