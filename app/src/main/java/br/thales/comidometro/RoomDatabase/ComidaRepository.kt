package br.thales.comidometro.RoomDatabase

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Room
import br.thales.comidometro.Domain.Comida
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class ComidaRepository(applicationContext : Application) : IComidaRepository {

    private lateinit var dao : IComidaDAO

    init {
        val db = Room.databaseBuilder(applicationContext, RoomDb::class.java, "db_comida").build()
        dao = db.getComidaDao()
    }

    override  fun ObterComida(id : Int): Comida {
        return runBlocking { dao.obter(id) }
    }

    override  fun ListarComidas(): List<Comida> {
       return  runBlocking { dao.listar() }
    }

    fun ListarComidasLiveData() : Flow<List<Comida>> {
        return runBlocking { return@runBlocking dao.listarLiveData() }
    }

    override  fun InserirComida(comida: Comida){
        runBlocking { dao.inserir(comida) }
    }

    override  fun ExcluirComida(comida: Comida) {
        runBlocking { dao.excluir(comida) }
    }
}