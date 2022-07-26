package br.thales.comidometro.RoomDatabase

import br.thales.comidometro.Domain.Comida

interface IComidaRepository {

     fun ObterComida(id : Int) : Comida

     fun ListarComidas() : List<Comida>

     fun InserirComida(comida : Comida)

     fun ExcluirComida(comida : Comida)
}