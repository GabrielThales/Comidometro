package br.thales.comidometro.Domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Comidas")
data class Comida(
    @PrimaryKey(autoGenerate = true)
    var Id : Int,
    @ColumnInfo(name = "Nome")
    var Nome : String,
    @ColumnInfo(name = "Preco")
    var Preco : Double,
    @ColumnInfo(name = "Data")
    var Data : String
) {

}