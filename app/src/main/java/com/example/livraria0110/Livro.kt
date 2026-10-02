package com.example.livraria0110

import java.util.UUID

data class Livro(
    val id: UUID,
    val titulo: String,
    val editora: String,
    val preco: String,
    val autor: Autor?,
    val disponivel: Boolean
)