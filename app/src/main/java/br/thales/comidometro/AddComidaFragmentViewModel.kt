package br.thales.comidometro

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.thales.comidometro.Service.ComidaService

class AddComidaFragmentViewModel(applicationContext : Application) : AndroidViewModel(applicationContext) {

    lateinit var Service : ComidaService

    init {
        Service = ComidaService(applicationContext)
    }

    class AddComidaFragmentFactory(private val application: Application) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddComidaFragmentViewModel::class.java)) {
                return AddComidaFragmentViewModel(application) as T
            }
            throw IllegalArgumentException("View Model não é compatível com essa Factory")
        }
    }
}