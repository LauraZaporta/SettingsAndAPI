package cat.itb.m78.exercices.db

import app.cash.sqldelight.db.SqlDriver

expect fun createDriver(): SqlDriver
fun createDatabase(): Database {
    val driver = createDriver()
    return Database(driver)
}
val database by lazy { createDatabase() }