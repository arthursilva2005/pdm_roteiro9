package com.example.livraria0110

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class LivrosViewModel : ViewModel() {

    private val _livros = MutableStateFlow<List<Livro>>(emptyList())

    val livros = _livros.asStateFlow()

    fun findById(id: UUID?): Livro? {
        if (id == null) {
            return null
        }

        return _livros.value.firstOrNull { livro ->
            livro.id == id
        }
    }

    fun save(
        id: UUID?,
        titulo: String,
        editora: String,
        preco: String,
        autor: Autor?,
        disponivel: Boolean
    ) {

        val listaAtual = _livros.value.toMutableList()

        // Editar livro
        if (id != null) {

            val index = listaAtual.indexOfFirst {
                it.id == id
            }

            if (index != -1) {

                listaAtual[index] = Livro(
                    id = id,
                    titulo = titulo,
                    editora = editora,
                    preco = preco,
                    autor = autor,
                    disponivel = disponivel
                )
            }

        } else {

            // Novo livro
            val novoLivro = Livro(
                id = UUID.randomUUID(),
                titulo = titulo,
                editora = editora,
                preco = preco,
                autor = autor,
                disponivel = disponivel
            )

            listaAtual.add(novoLivro)
        }

        _livros.value = listaAtual
    }

    fun remove(livro: Livro) {
        _livros.value -= livro
    }
}