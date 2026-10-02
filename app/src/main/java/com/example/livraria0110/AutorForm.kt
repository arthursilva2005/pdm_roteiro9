package com.example.livraria0110

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.UUID

@Composable
fun AutorForm(
    id: UUID?,
    autoresViewModel: AutoresViewModel,
    onBack: () -> Unit
) {

    val autor = autoresViewModel.findById(id)

    var nomeInput by rememberSaveable {
        mutableStateOf(autor?.nome ?: "")
    }

    var nacionalidadeInput by rememberSaveable {
        mutableStateOf(autor?.nacionalidade ?: "")
    }

    var ativoInput by rememberSaveable {
        mutableStateOf(autor?.ativo ?: true)
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .systemBarsPadding()
            .padding(16.dp)
    ) {

        Text(
            text = if (autor == null) {
                "Cadastro de Autor"
            } else {
                "Editar Autor"
            },
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = Color(23, 23, 23)
        )

        Text(
            text = "Preencha os dados do autor abaixo.",
            fontSize = 14.sp,
            color = Color(87, 83, 78)
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        TextField(
            value = nomeInput,
            onValueChange = {
                nomeInput = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Nome")
            },
            placeholder = {
                Text("Digite o nome do autor")
            }
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        TextField(
            value = nacionalidadeInput,
            onValueChange = {
                nacionalidadeInput = it
            },
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text("Nacionalidade")
            },
            placeholder = {
                Text("Digite a nacionalidade")
            }
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Checkbox(
                checked = ativoInput,
                onCheckedChange = {
                    ativoInput = it
                }
            )

            Text("Ativo")
        }

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row {

            Button(
                onClick = {

                    autoresViewModel.save(
                        id = autor?.id,
                        nome = nomeInput,
                        nacionalidade = nacionalidadeInput,
                        ativo = ativoInput
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