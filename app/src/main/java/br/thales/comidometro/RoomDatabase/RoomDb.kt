package br.thales.comidometro.RoomDatabase

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import br.thales.comidometro.Domain.Comida


@Database(entities = [Comida::class], version = 1, exportSchema = false)
abstract class RoomDb : RoomDatabase() {

    abstract fun getComidaDao() : IComidaDAO

}