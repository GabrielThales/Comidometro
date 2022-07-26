package br.thales.comidometro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import br.thales.comidometro.Domain.Comida
import br.thales.comidometro.databinding.FragmentAdicionarGastoComidaBinding
import com.google.android.material.snackbar.Snackbar
import java.time.Instant
import java.util.*


class AdicionarGastoComidaFragment : DialogFragment() {

    lateinit var viewModel: AddComidaFragmentViewModel
    lateinit var binding : FragmentAdicionarGastoComidaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, AddComidaFragmentViewModel.AddComidaFragmentFactory(this.activity!!.application)).get(AddComidaFragmentViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_adicionar_gasto_comida, container, false)
        val txtNomeComida = view.findViewById<EditText>(R.id.txtNomeComida)
        val txtPrecoComida = view.findViewById<EditText>(R.id.txtPrecoComida)
        val btnAddGasto = view.findViewById<Button>(R.id.btnAdicionarGasto)

        btnAddGasto.setOnClickListener {
            if (txtNomeComida.text.isNullOrEmpty() || txtPrecoComida.text.isNullOrEmpty()){
                Snackbar.make(view, "Algum campo está faltando", 2000).show()
            } else {
                val comida = Comida(0, txtNomeComida.text.toString(), txtPrecoComida.text.toString().toDouble(), Date.from(
                    Instant.now()).toString() )

                viewModel.Service.InserirComida(comida)

                Snackbar.make(view, "${comida.Nome} Foi Adicionado", 2000).show()
                this.dismiss()
            }

        }


        return view


    }

}