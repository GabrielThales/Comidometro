package br.thales.comidometro.Service

import android.app.Application
import androidx.lifecycle.LiveData
import br.thales.comidometro.Domain.Comida
import br.thales.comidometro.RoomDatabase.ComidaRepository
import br.thales.comidometro.RoomDatabase.IComidaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking

class ComidaService(applicationContext : Application) : IComidaRepository {

    private lateinit var Repository : ComidaRepository

    init {
        Repository = ComidaRepository(applicationContext)
    }

    override  fun ObterComida(id: Int): Comida {
        return runBlocking { Repository.ObterComida(id) }
    }

    fun ListarComidaLiveData() : Flow<List<Comida>>{
        return runBlocking {
            return@runBlocking Repository.ListarComidasLiveData()
        }
    }

    override  fun ListarComidas(): List<Comida> {
        return runBlocking { Repository.ListarComidas() }
    }

    override  fun InserirComida(comida: Comida) {
        runBlocking { Repository.InserirComida(comida) }
    }

    override  fun ExcluirComida(comida: Comida) {
        runBlocking { Repository.ExcluirComida(comida) }
    }


}