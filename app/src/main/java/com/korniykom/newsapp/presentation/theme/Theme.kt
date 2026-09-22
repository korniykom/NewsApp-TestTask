package com.korniykom.newsapp.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val Primary10 = Color(0xFF1A0063)
private val Primary20 = Color(0xFF2E009C)
private val Primary30 = Color(0xFF4420C3)
private val Primary40 = Color(0xFF5D41DB)
private val Primary80 = Color(0xFFC8BFFF)
private val Primary90 = Color(0xFFE5DEFF)

private val Secondary10 = Color(0xFF1C192B)
private val Secondary20 = Color(0xFF312E41)
private val Secondary30 = Color(0xFF484459)
private val Secondary40 = Color(0xFF5F5C71)
private val Secondary80 = Color(0xFFC9C3DC)
private val Secondary90 = Color(0xFFE5DFF9)

private val Tertiary10 = Color(0xFF301121)
private val Tertiary20 = Color(0xFF482536)
private val Tertiary30 = Color(0xFF613B4C)
private val Tertiary40 = Color(0xFF7C5264)
private val Tertiary80 = Color(0xFFECB8CD)
private val Tertiary90 = Color(0xFFFFD8E7)

private val Error10 = Color(0xFF410002)
private val Error20 = Color(0xFF690005)
private val Error30 = Color(0xFF93000A)
private val Error40 = Color(0xFFBA1A1A)
private val Error80 = Color(0xFFFFB4AB)
private val Error90 = Color(0xFFFFDAD6)

private val Neutral10 = Color(0xFF1C1B1F)
private val Neutral20 = Color(0xFF313033)
private val Neutral90 = Color(0xFFE5E1E6)
private val Neutral95 = Color(0xFFF4EFF4)
private val Neutral99 = Color(0xFFFFFBFF)
private val NeutralVariant30 = Color(0xFF48454E)
private val NeutralVariant50 = Color(0xFF79767F)
private val NeutralVariant60 = Color(0xFF938F99)
private val NeutralVariant80 = Color(0xFFC9C5D0)
private val NeutralVariant90 = Color(0xFFE5E0EC)

private val LightColorScheme = lightColorScheme(
    primary = Primary40,
    onPrimary = Color.White,
    primaryContainer = Primary90,
    onPrimaryContainer = Primary10,

    secondary = Secondary40,
    onSecondary = Color.White,
    secondaryContainer = Secondary90,
    onSecondaryContainer = Secondary10,

    tertiary = Tertiary40,
    onTertiary = Color.White,
    tertiaryContainer = Tertiary90,
    onTertiaryContainer = Tertiary10,

    error = Error40,
    onError = Color.White,
    errorContainer = Error90,
    onErrorContainer = Error10,

    background = Neutral99,
    onBackground = Neutral10,
    surface = Neutral99,
    onSurface = Neutral10,
    surfaceVariant = NeutralVariant90,
    onSurfaceVariant = NeutralVariant30,

    outline = NeutralVariant50,
    outlineVariant = NeutralVariant80,

    inverseSurface = Neutral20,
    inverseOnSurface = Neutral95,
    inversePrimary = Primary80,
)

private val DarkColorScheme = darkColorScheme(
    primary = Primary80,
    onPrimary = Primary20,
    primaryContainer = Primary30,
    onPrimaryContainer = Primary90,

    secondary = Secondary80,
    onSecondary = Secondary20,
    secondaryContainer = Secondary30,
    onSecondaryContainer = Secondary90,

    tertiary = Tertiary80,
    onTertiary = Tertiary20,
    tertiaryContainer = Tertiary30,
    onTertiaryContainer = Tertiary90,

    error = Error80,
    onError = Error20,
    errorContainer = Error30,
    onErrorContainer = Error90,

    background = Neutral10,
    onBackground = Neutral90,
    surface = Neutral10,
    onSurface = Neutral90,
    surfaceVariant = NeutralVariant30,
    onSurfaceVariant = NeutralVariant80,

    outline = NeutralVariant60,
    outlineVariant = NeutralVariant30,

    inverseSurface = Neutral90,
    inverseOnSurface = Neutral20,
    inversePrimary = Primary40,
)


@Composable
fun NewsAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}