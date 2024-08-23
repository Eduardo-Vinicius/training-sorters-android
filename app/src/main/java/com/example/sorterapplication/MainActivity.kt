package com.example.sorterapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sorterapplication.ui.theme.SorterApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SorterApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TrainingSorterScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TrainingSorterScreen(modifier: Modifier = Modifier) {
    // Estado para armazenar a entrada do usuário
    var trainingDay by remember { mutableStateOf(TextFieldValue("")) }
    var repetitions by remember { mutableStateOf(TextFieldValue("")) }
    var sortedTrainings by remember { mutableStateOf<List<String>>(emptyList()) }

    val trainings = mapOf(
        "biceps" to listOf("Rosca direta com barra", "Rosca alternada com halteres", "Rosca concentrada", "Rosca martelo", "Rosca inversa com barra"),
        "triceps" to listOf("Tríceps pulley", "Tríceps testa", "Tríceps banco", "Tríceps francês", "Mergulho entre bancos"),
        "posterior" to listOf("Cadeira flexora", "Mesa flexora", "Stiff", "Good morning", "Levantamento terra romeno"),
        "quadriceps" to listOf("Agachamento livre", "Leg press", "Cadeira extensora", "Afundo com halteres", "Agachamento no Smith"),
        "costas" to listOf("Remada curvada", "Puxada alta", "Remada cavalinho", "Levantamento terra", "Pull-up (barra fixa)"),
        "peito" to listOf("Supino reto", "Supino inclinado", "Crucifixo com halteres", "Supino declinado", "Cross over")
    )

    fun trainingSorter(trainingDay: String, rep: Int) {
        val listTrainings = trainings[trainingDay]
        if (listTrainings == null || rep <= 0) {
            sortedTrainings = listOf("Treino não configurado no sistema ou número de séries inválido.")
        } else {
            sortedTrainings = listTrainings.shuffled().take(rep)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Spacer(modifier = Modifier.height(16.dp))
        Text("O que vamos treinar hoje?")
        Spacer(modifier = Modifier.height(16.dp))
        Text("Biceps/Triceps/Posterior/Quadriceps/Costas/Peito")
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = trainingDay,
            onValueChange = { trainingDay = it },
            label = { Text("Tipo de Treino") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = repetitions,
            onValueChange = { repetitions = it },
            label = { Text("Número de Exercícios") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val rep = repetitions.text.toIntOrNull() ?: 0
            trainingSorter(trainingDay.text.trim().lowercase(), rep)
        }) {
            Text("Sortear Treinos")
        }
        Spacer(modifier = Modifier.height(16.dp))
        sortedTrainings.forEach { training ->
            Text(training)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TrainingSorterScreenPreview() {
    SorterApplicationTheme {
        TrainingSorterScreen()
    }
}
