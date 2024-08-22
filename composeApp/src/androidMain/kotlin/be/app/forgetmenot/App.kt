import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import be.app.forgetmenot.data.MongoDB
import be.app.forgetmenot.presentation.screen.context.ContextViewModel
//import be.app.forgetmenot.presentation.screen.item.ItemViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.context.startKoin
import org.koin.dsl.module
import be.app.forgetmenot.screen.home.HomeScreen
import be.app.forgetmenot.screen.home.HomeViewModel

val lightRedColor = Color(color = 0xFFF57D88)
val darkRedColor = Color(color = 0xFF77000B)

@Composable
@Preview
fun App() {
    initializeKoin()

    val lightColors = lightColorScheme(
        primary = lightRedColor,
        onPrimary = darkRedColor,
        primaryContainer = lightRedColor,
        onPrimaryContainer = darkRedColor
    )
    val darkColors = darkColorScheme(
        primary = lightRedColor,
        onPrimary = darkRedColor,
        primaryContainer = lightRedColor,
        onPrimaryContainer = darkRedColor
    )
    val colors by mutableStateOf(
        if (isSystemInDarkTheme()) darkColors else lightColors
    )

    MaterialTheme(colorScheme = colors) {
        Navigator(HomeScreen()) {
            SlideTransition(it)
        }
    }
}

val mongoModule = module {
    single { MongoDB() }
    factory { HomeViewModel(get()) }
    factory { ContextViewModel(get()) }
    //factory { ItemViewModel(get()) }
}

fun initializeKoin() {
    startKoin {
        modules(mongoModule)
    }
}