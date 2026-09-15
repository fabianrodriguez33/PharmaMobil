package pe.edu.upeu.pharmamobil.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val md_theme_light_primary = Color(0xFF00A693)
private val md_theme_light_onPrimary = Color(0xFFFFFFFF)
private val md_theme_light_primaryContainer = Color(0xFF99F5E5)
private val md_theme_light_onPrimaryContainer = Color(0xFF003730)
private val md_theme_light_secondary = Color(0xFF4A625C)
private val md_theme_light_onSecondary = Color(0xFFFFFFFF)
private val md_theme_light_background = Color(0xFFFAFDFB)
private val md_theme_light_surface = Color(0xFFFFFFFF)
private val md_theme_light_surfaceVariant = Color(0xFFF0F4F3)
private val md_theme_light_outline = Color(0xFFBFC9C6)

private val md_theme_dark_primary = Color(0xFF66E6D0)
private val md_theme_dark_onPrimary = Color(0xFF003730)
private val md_theme_dark_primaryContainer = Color(0xFF00A693)
private val md_theme_dark_onPrimaryContainer = Color(0xFF99F5E5)
private val md_theme_dark_secondary = Color(0xFFB3C9C4)
private val md_theme_dark_onSecondary = Color(0xFF1F3330)
private val md_theme_dark_background = Color(0xFF0F1413)
private val md_theme_dark_surface = Color(0xFF1A1F1E)
private val md_theme_dark_surfaceVariant = Color(0xFF2A3130)
private val md_theme_dark_outline = Color(0xFF5A6361)

val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    background = md_theme_light_background,
    surface = md_theme_light_surface,
    surfaceVariant = md_theme_light_surfaceVariant,
    outline = md_theme_light_outline
)

val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    background = md_theme_dark_background,
    surface = md_theme_dark_surface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    outline = md_theme_dark_outline
)

@Composable
fun PharmaMobilTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

private val Typography = Typography()