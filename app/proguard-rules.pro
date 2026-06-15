# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.

# Keep Material You / Compose related classes
-keep class androidx.compose.** { *; }
-keep class * extends androidx.compose.runtime.Composable { *; }
