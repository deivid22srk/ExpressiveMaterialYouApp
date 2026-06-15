package com.example.expressiveapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// Esquema de cores claro (fallback estático)
private val LightColorScheme = lightColorScheme(
    primary = FallbackPrimary,
    onPrimary = FallbackOnPrimary,
    primaryContainer = FallbackPrimaryContainer,
    onPrimaryContainer = FallbackOnPrimaryContainer,
    secondary = FallbackSecondary,
    onSecondary = FallbackOnSecondary,
    secondaryContainer = FallbackSecondaryContainer,
    onSecondaryContainer = FallbackOnSecondaryContainer,
    tertiary = FallbackTertiary,
    onTertiary = FallbackOnTertiary,
    tertiaryContainer = FallbackTertiaryContainer,
    onTertiaryContainer = FallbackOnTertiaryContainer,
    error = FallbackError,
    onError = FallbackOnError,
    errorContainer = FallbackErrorContainer,
    onErrorContainer = FallbackOnErrorContainer,
    background = FallbackBackground,
    onBackground = FallbackOnBackground,
    surface = FallbackSurface,
    onSurface = FallbackOnSurface,
    surfaceVariant = FallbackSurfaceVariant,
    onSurfaceVariant = FallbackOnSurfaceVariant,
    outline = FallbackOutline,
    outlineVariant = FallbackOutlineVariant,
    inverseSurface = FallbackInverseSurface,
    inverseOnSurface = FallbackInverseOnSurface,
    inversePrimary = FallbackInversePrimary
)

// Esquema de cores escuro (fallback estático)
private val DarkColorScheme = darkColorScheme(
    primary = FallbackInversePrimary,
    onPrimary = FallbackOnPrimaryContainer,
    primaryContainer = FallbackPrimaryContainer,
    onPrimaryContainer = FallbackOnPrimary,
    secondary = FallbackSecondaryContainer,
    onSecondary = FallbackOnSecondaryContainer,
    secondaryContainer = FallbackSecondary,
    onSecondaryContainer = FallbackSecondary,
    tertiary = FallbackTertiaryContainer,
    onTertiary = FallbackOnTertiaryContainer,
    tertiaryContainer = FallbackTertiary,
    onTertiaryContainer = FallbackOnTertiary,
    error = FallbackErrorContainer,
    onError = FallbackOnErrorContainer,
    errorContainer = FallbackError,
    onErrorContainer = FallbackOnError,
    background = FallbackOnBackground,
    onBackground = FallbackBackground,
    surface = FallbackOnBackground,
    onSurface = FallbackBackground,
    surfaceVariant = FallbackOnSurfaceVariant,
    onSurfaceVariant = FallbackSurfaceVariant,
    outline = FallbackOutlineVariant,
    outlineVariant = FallbackOutline,
    inverseSurface = FallbackBackground,
    inverseOnSurface = FallbackOnBackground,
    inversePrimary = FallbackPrimary
)

@Composable
fun ExpressiveMaterialYouTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true, // Ativa o Material You dinâmico
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        // Android 12+ com suporte a cor dinâmica
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = androidx.compose.ui.platform.LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        // Fallback estático
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    // Configura a barra de status e navegação para edge-to-edge
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            window.navigationBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
