package cat.itb.m78.exercices.mapsApp

import com.russhwolf.settings.Settings

val settings: Settings = Settings()
val lastPhoto: String? = settings.getStringOrNull("key")