package br.thales.comidometro

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.thales.comidometro.Domain.Comida
import br.thales.comidometro.databinding.ActivityMainBinding
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import java.text.DateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity(), RecyclerViewItemClickListener {
    lateinit var viewModel: MainAcitivityViewModel
    lateinit var binding : ActivityMainBinding
    lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this, MainAcitivityViewModel.MainActivityViewModelFactory(this.application)).get(
            MainAcitivityViewModel::class.java)

        binding.rcvGastosComida.layoutManager = LinearLayoutManager(this)
        val adapter = ComidaRecyclerViewAdapter()
        adapter.setRecycleViewItemListener(this)

        if(viewModel.ListaGastos.value != null){
            adapter.listaComida = viewModel.ListaGastos.value!!
            binding.lblGastoComida.text = viewModel.ListaGastos.value?.sumByDouble { it.Preco }.toString()
        }
        viewModel.ListaGastos.observe(this, {
            it.let {
                adapter.listaComida = it
                binding.lblGastoComida.text = "R$ " + viewModel.ListaGastos.value?.sumByDouble { it.Preco }.toString()
                binding.txtMes.text = "R$ " + viewModel.gastosMensais().toString()
                binding.txtCount.text = "Total: " + viewModel.ListaGastos.value?.size.toString() + " | Este Mês: " + viewModel.CountGastoMes
            }
        })

        MobileAds.initialize(this) {}

        mAdView = binding.mainBannerAd
        //mAdView.adUnitId = "ca-app-pub-1317370482832097/4724053342"
        //mAdView.adSize = AdSize.BANNER
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)


        binding.rcvGastosComida.adapter = adapter
        binding.txtMes.text = viewModel.gastosMensais().toString()
        binding.txtCount.text = viewModel.ListaGastos.value?.size.toString()
        binding.txtCount.text = "Total: " + viewModel.ListaGastos.value?.size.toString() + " | Este Mês: " + viewModel.CountGastoMes


        binding.btnAddGastoComida.setOnClickListener {


            //val ft : FragmentTransaction = fm.beginTransaction()//.add(AdicionarGastoComidaFragment(), "add_gasto_fragment_tag")
            //ft.add(AdicionarGastoComidaFragment(), "add_gasto_fragment_tag").commit()

            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Adicionar Novo Gasto")
            val txtNome = EditText(this)
            txtNome.hint = "Nome = Hamburguer"
            val txtPreco = EditText(this)
            txtPreco.hint = "Preço = 10"
            txtPreco.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL

            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.addView(txtNome)
            linearLayout.addView(txtPreco)

            alertDialog.setView(linearLayout)

            alertDialog.setPositiveButton("Adicionar", DialogInterface.OnClickListener { dialog, which ->
                if(txtNome.text.isNullOrEmpty() || txtPreco.text.isNullOrEmpty()){
                    Snackbar.make(linearLayout, "Algum Campo está Vazio", 1500).show()
                } else {
                    val date = Date.from(Instant.now())
                    viewModel.inserirGastoComida(Comida(0, txtNome.text.toString(), txtPreco.text.toString().toDouble(), DateFormat.getDateInstance().format(Date.from(Instant.now()))))

                }
            })

            alertDialog.show()

            //viewModel.inserirGastoComida(Comida(0, "FEZES ATOMICA", 11.0, "ONTEM"))
//            val frag = fm.findFragmentByTag("add_gasto_fragment_tag")
//            if(frag == null){
//
//                Toast.makeText(this, "NULL", Toast.LENGTH_SHORT).show()
//            } else {
//                val dialogfragment = AdicionarGastoComidaFragment()
//                dialogfragment.show(fm, "add_gasto_fragment_tag")
//            }

        }


    }

    override fun recycleViewItemClicked(view: View, id: Int) {

        val comida = viewModel.obterPorId(id)

        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Excluir Gasto")
        val txtNome = TextView(this)
        txtNome.text = comida.Nome
        val txtPreco = TextView(this)
        txtPreco.text = comida.Preco.toString()
        //txtPreco.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
        val txtData = TextView(this)
        txtData.text = comida.Data


        val linearLayout = LinearLayout(this)
        linearLayout.orientation = LinearLayout.VERTICAL
        linearLayout.gravity = Gravity.CENTER_VERTICAL
        linearLayout.addView(txtNome)
        linearLayout.addView(txtPreco)
        linearLayout.addView(txtData)

        alertDialog.setView(linearLayout)

        alertDialog.setPositiveButton("Excluir", DialogInterface.OnClickListener { dialog, which ->
            if(txtNome.text == null || txtPreco.text == null){
                Snackbar.make(linearLayout, "Algum Campo está Vazio", 1500).show()
            } else {
                viewModel.excluirGastoComida(viewModel.obterPorId(id))
            }
        })

        alertDialog.show()

    }
}