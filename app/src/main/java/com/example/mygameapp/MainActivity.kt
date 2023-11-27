package com.example.mygameapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.rememberImagePainter
import com.example.mygameapp.ui.theme.MyGameAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyGameAppTheme {
/*                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")

                }*/
                MyApp {
                    MyScreenContent()
                }
            }
        }
    }
}
/*

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    MyGameAppTheme {
        Greeting("Android")
    }
}
*/

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


@Composable
fun HeaderSection(title: String) {
    // Componente para el encabezado de cada sección
    Text(text = title)
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

@Composable
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
}

@Composable
fun Chip(label: String) {
    // Puedes diseñar este componente para que se vea como un chip de género de juego
    Text(text = label)
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
