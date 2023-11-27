package com.example.mygameapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.mygameapp.ui.theme.MyGameAppTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyGameAppTheme {
                MyApp {
                    MyScreenContent()
                }
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    MaterialTheme {
        Surface {
            Box {
                content()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApp {
        MyScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyScreenContent() {
    // Estado que mantiene la ruta seleccionada
    val (selectedRoute, setSelectedRoute) = remember { mutableStateOf("Home") }

    // Puedes usar Scaffold para un diseño con barra de navegación inferior y contenido principal
    Scaffold(
        // Pasar el estado y la función a MyBottomNavigationBar
        bottomBar = {
            MyBottomNavigationBar(
                selectedRoute = selectedRoute,
                onItemSelect = { route ->
                    setSelectedRoute(route)
                }
            )
        }
    ) { innerPadding ->
        // Aquí iría tu contenido, por ejemplo, una columna con listas desplazables
        Column(modifier = Modifier.padding(innerPadding)) {
            // Aquí puedes decidir qué contenido mostrar basado en la ruta seleccionada
            when (selectedRoute) {
                "Home" -> {
                    HeaderSection("Hot Games")
                    GameList(hotGames) // 'hotGames' sería una lista de datos de juegos
                }
                "Search" -> {
                    // Contenido para la búsqueda
                }
                "Profile" -> {
                    // Contenido para el perfil
                }
            }

            // Suponiendo que quieres mostrar siempre las secciones de juegos independientemente de la ruta:
            HeaderSection("Popular Games")
            GameList(popularGames) // 'popularGames' sería otra lista de datos de juegos
        }
    }
}


/*
@Composable
fun HeaderSection(title: String) {
    // Componente para el encabezado de cada sección
    Text(text = title)
}
*/

@Composable
fun HeaderSection(title: String) {
    Surface(
        color = MaterialTheme.colorScheme.primary, // Usa el color primario del tema para el fondo
        modifier = Modifier.fillMaxWidth() // El encabezado debe ocupar todo el ancho
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium, // Usa un estilo más grande para el encabezado
            color = MaterialTheme.colorScheme.onPrimary, // El color del texto debe contrastar con el fondo
            modifier = Modifier.padding(8.dp) // Agrega espaciado alrededor del texto del encabezado
        )
    }
}


@Composable
fun GameList(games: List<Game>) {
    // Lista desplazable de juegos
    LazyColumn {
        items(games) { game ->
            GameItem(game)
        }
    }
}

/*@Composable
fun GameItem(game: Game) {
    Card {
        Column {
            // Imaginemos que usamos la librería Coil para cargar la imagen del juego
            Image(
                painter = rememberImagePainter(game.imageUrl),
                contentDescription = "Game Image"
            )
            Text(text = game.title)
            Text(text = "Rating: ${game.rating}")
            Text(text = "Release Date: ${game.releaseDate}")
            // Para mostrar los géneros
            Row {
                game.genre.forEach { genre ->
                    Chip(label = genre)
                }
            }
        }
    }
}*/

@Composable
fun GameItem(game: Game) {
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Usa CardDefaults para Material3
    ) {
        Row(modifier = Modifier.fillMaxWidth()) { // Usa Row para imagen a la izquierda y texto a la derecha
            Image(
                painter = rememberImagePainter(game.imageUrl),
                contentDescription = "Game Image",
                modifier = Modifier.size(100.dp) // Establece un tamaño fijo para las imágenes
            )
            Column(modifier = Modifier.padding(8.dp)) { // Agrega espacio dentro de la columna
                Text(
                    text = game.title,
                    style = MaterialTheme.typography.headlineMedium, // Usa un estilo más prominente para el título
                    fontWeight = FontWeight.Bold // Hace el título en negrita
                )
                RatingBar(rating = game.rating) // Crea una barra de calificación con estrellas
                Text(text = "Release Date: ${game.releaseDate}")
                // Muestra los géneros en chips
                Row {
                    game.genre.forEach { genre ->
                        Chip(label = genre)
                    }
                }
            }
        }
    }
}
@Composable
fun RatingBar(rating: Float) {
    Row {
        repeat((rating.roundToInt())) { // Muestra estrellas llenas basadas en la calificación
            Icon(
                Icons.Filled.Star,
                contentDescription = "Star",
                tint = Color.Yellow // Usa el color amarillo para las estrellas
            )
        }
    }
}

/*
@Composable
fun Chip(label: String) {
    // Puedes diseñar este componente para que se vea como un chip de género de juego
    Text(text = label)
}*/

@Composable
fun Chip(label: String) {
    Surface(
        shape = MaterialTheme.shapes.small, // Usa una forma definida en tu tema
        color = MaterialTheme.colorScheme.secondary, // Usa el color secundario del tema
        modifier = Modifier.padding(4.dp) // Agrega espaciado alrededor del chip
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), // Espaciado interno para el texto del chip
            color = MaterialTheme.colorScheme.onSecondary // El color del texto debe contrastar con el fondo del chip
        )
    }
}


// Definir una lista de juegos para "Hot Games"
val hotGames = listOf(
    Game(
        title = "Alan Wake 2",
        rating = 4.8f,
        imageUrl = "url_to_alan_wake_2_image",
        releaseDate = "15 March 2023",
        genre = listOf("Action", "Adventure")
    ),
    Game(
        title = "Lethal Company",
        rating = 4.5f,
        imageUrl = "url_to_lethal_company_image",
        releaseDate = "29 August 2023",
        genre = listOf("Strategy", "RPG")
    ),
    // ...agregar más juegos según sea necesario
)

// Definir una lista de juegos para "Popular Games"
val popularGames = listOf(
    Game(
        title = "Red Dead Redemption 2",
        rating = 4.9f,
        imageUrl = "url_to_red_dead_redemption_2_image",
        releaseDate = "26 October 2018",
        genre = listOf("Action", "Adventure")
    ),
    Game(
        title = "Destiny 2",
        rating = 4.0f,
        imageUrl = "url_to_destiny_2_image",
        releaseDate = "06 September 2017",
        genre = listOf("Action", "Shooter")
    ),
    // ...agregar más juegos según sea necesario
)

// Modelo de datos para un juego
data class Game(
    val title: String,
    val rating: Float,
    val imageUrl: String,
    val releaseDate: String,
    val genre: List<String>
)

@Composable
fun MyBottomNavigationBar(selectedRoute: String, onItemSelect: (String) -> Unit) {
    BottomNavigation {
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Home, contentDescription = "Home") },
            label = { Text("Home") },
            selected = selectedRoute == "Home",
            onClick = { onItemSelect("Home") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
            label = { Text("Search") },
            selected = selectedRoute == "Search",
            onClick = { onItemSelect("Search") }
        )
        BottomNavigationItem(
            icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Profile") },
            label = { Text("Profile") },
            selected = selectedRoute == "Profile",
            onClick = { onItemSelect("Profile") }
        )
    }
}
