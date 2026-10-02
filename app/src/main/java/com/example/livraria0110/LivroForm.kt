package com.example.livraria0110

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LivroForm(
    id: UUID?,
    livrosViewModel: LivrosViewModel,
    autoresViewModel: AutoresViewModel,
    onBack: () -> Unit
) {

    val livro = livrosViewModel.findById(id)

    var tituloInput by rememberSaveable {
        mutableStateOf(livro?.titulo ?: "")
    }

    var editoraInput by rememberSaveable {
        mutableStateOf(livro?.editora ?: "")
    }

    var precoInput by rememberSaveable {
        mutableStateOf(livro?.preco ?: "")
    }

    var disponivelInput by rememberSaveable {
        mutableStateOf(livro?.disponivel ?: true)
    }

    var autorSelecionado by remember {
        mutableStateOf<Autor?>(livro?.autor)
    }

    var mostrarAutores by rememberSaveable {
        mutableStateOf(false)
    }

    var buscaAutor by rememberSaveable {
        mutableStateOf("")
    }

    val autoresList by autoresViewModel.autores.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding()
            .padding(16.dp)
    ) {

        Text(
            text = if (livro == null) {
                "Cadastro de Livro"
            } else {
                "Editar Livro"
            },
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(23, 23, 23)
        )

        Text(
            text = "Preencha os dados do livro abaixo.",
            fontSize = 14.sp,
            color = Color(87, 83, 78)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        TextField(
            value = tituloInput,
            onValueChange = {
                tituloInput = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Título")
            },
            placeholder = {
                Text("Digite o título do livro")
            }
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        TextField(
            value = editoraInput,
            onValueChange = {
                editoraInput = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Editora")
            },
            placeholder = {
                Text("Digite a editora")
            }
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        TextField(
            value = precoInput,
            onValueChange = {
                precoInput = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Preço")
            },
            placeholder = {
                Text("Exemplo: 49,90")
            }
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    mostrarAutores = true
                }
        ) {

            TextField(
                value = autorSelecionado?.nome ?: "",
                onValueChange = {},
                readOnly = true,
                enabled = false,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Autor")
                },
                placeholder = {
                    Text("Selecione um autor")
                }
            )
        }

        if (mostrarAutores) {

            ModalBottomSheet(
                onDismissRequest = {
                    mostrarAutores = false
                }
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    OutlinedTextField(
                        value = buscaAutor,
                        onValueChange = {
                            buscaAutor = it
                        },
                        label = {
                            Text("Buscar autor")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    val autoresFiltrados = autoresList.filter {
                        it.nome.contains(
                            buscaAutor,
                            ignoreCase = true
                        )
                    }

                    LazyColumn(
                        modifier = Modifier.padding(top = 16.dp)
                    ) {

                        items(autoresFiltrados) { autor ->

                            Text(
                                text = autor.nome,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {

                                        autorSelecionado = autor

                                        mostrarAutores = false
                                    }
                                    .padding(vertical = 12.dp)
                            )
                        }
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = disponivelInput,
                onCheckedChange = {
                    disponivelInput = it
                }
            )

            Text("Disponível")
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row {

            Button(
                onClick = {

                    livrosViewModel.save(
                        id = livro?.id,
                        titulo = tituloInput,
                        editora = editoraInput,
                        preco = precoInput,
                        autor = autorSelecionado,
                        disponivel = disponivelInput
                    )

                    onBack()
                }
            ) {

                Text("Salvar")
            }

            Spacer(
                modifier = Modifier.width(8.dp)
            )

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(229, 229, 229),
                    contentColor = Color(23, 23, 23)
                ),
                onClick = {
                    onBack()
                }
            ) {

                Text("Cancelar")
            }
        }
    }
}