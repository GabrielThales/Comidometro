package br.thales.comidometro

import android.app.AlertDialog
import android.app.Application
import android.content.Context
import android.content.DialogInterface
import android.text.InputType
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.*
import br.thales.comidometro.Domain.Comida
import br.thales.comidometro.Service.ComidaService
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.time.Instant
import java.util.*

class MainAcitivityViewModel(applicationContext : Application) : AndroidViewModel(applicationContext) {

    lateinit var Service : ComidaService
    lateinit var ListaGastos : LiveData<List<Comida>>
    var CountGastoMes : Int = 0

    init {
        Service = ComidaService(applicationContext)
        ListaGastos = Service.ListarComidaLiveData().asLiveData()
    }


    fun gastosMensais() : Double{
       val lista =  ListaGastos.value?.toList()
        var gastoMensal = 0.0
        CountGastoMes = 0
        //DateFormat.getDateInstance().format(Date.from(Instant.now()))

        if (lista != null) {
            for(comida in lista){
                if (DateFormat.getDateInstance().parse(comida.Data).month == Date.from(Instant.now()).month){
                    gastoMensal += comida.Preco
                    CountGastoMes  += 1
                }
            }


        }

        return gastoMensal
    }

    fun inserirGastoComida(comida : Comida){
        Service.InserirComida(comida)
    }

    fun excluirGastoComida(comida: Comida){
        Service.ExcluirComida(comida)
    }

    fun obterPorId(id: Int) : Comida{
        return Service.ObterComida(id)
    }

    class MainActivityViewModelFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainAcitivityViewModel::class.java)) {
                return MainAcitivityViewModel(application) as T
            }
            throw IllegalArgumentException("View Model não é compatível com essa Factory")
        }
    }

}