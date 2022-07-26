package br.thales.comidometro.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import br.thales.comidometro.Domain.Comida
import kotlinx.coroutines.flow.Flow

@Dao
interface IComidaDAO {

    @Query(value = "SELECT * FROM Comidas WHERE id = :id")
    suspend fun obter(id : Int) : Comida

    @Query("SELECT * FROM Comidas")
    suspend fun listar() : List<Comida>

    @Query(value = "SELECT * FROM Comidas")
    fun listarLiveData() : Flow<List<Comida>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun  inserir(comida : Comida)

    @Delete()
    suspend fun excluir(comida : Comida)

}