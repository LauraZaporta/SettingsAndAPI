package cat.itb.m78.exercices.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString

actual fun createDriver(): SqlDriver {
    val userHome = System.getProperty("user.home")
    val file = Path(userHome, "myDatabase.db")
    val driver = JdbcSqliteDriver("jdbc:sqlite:${file.absolutePathString()}")
    migrateIfNeeded(driver, Database.Schema)
    return driver
}