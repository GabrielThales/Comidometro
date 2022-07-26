package br.thales.comidometro

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.thales.comidometro.Domain.Comida
import br.thales.comidometro.Service.ComidaService
import kotlinx.android.synthetic.main.comida_recycler_layout.view.*
import kotlinx.android.synthetic.main.fragment_item.view.*

class ComidaRecyclerViewAdapter() : RecyclerView.Adapter<ComidaRecyclerViewAdapter.ViewHolder>() {

    var listaComida = listOf<Comida>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

     lateinit var itemListener : RecyclerViewItemClickListener

    fun setRecycleViewItemListener(listener: RecyclerViewItemClickListener){
        itemListener = listener
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.comida_recycler_layout, parent, false)

        return ComidaRecyclerViewAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bindItem(listaComida[position], itemListener, position)
    }

    override fun getItemCount(): Int {
        return listaComida.size
    }


    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        fun bindItem(comida : Comida,itemListener : RecyclerViewItemClickListener, position: Int){

            val lblId = itemView.findViewById<TextView>(R.id.lblId)
            val lblPreco = itemView.findViewById<TextView>(R.id.lblPreco)
            val lblNome = itemView.findViewById<TextView>(R.id.lblNome)
            val dataCompra = itemView.findViewById<TextView>(R.id.lblData)


            lblId.text = comida.Id.toString()
            lblPreco.text = "R$ " + comida.Preco.toString()
            lblNome.text = comida.Nome
            dataCompra.text = comida.Data

            itemView.imgExcluir.setOnClickListener {
//                val alertDialog = AlertDialog.Builder(this.itemView.context)
//                alertDialog.setTitle(comida.Nome)
//                alertDialog.setMessage("Tem certeza que deseja exlcuir esse gasto ?")
//                alertDialog.setPositiveButton("Sim", DialogInterface.OnClickListener { dialog, which ->
                    itemListener.recycleViewItemClicked(it, comida.Id)
//                    Toast.makeText(itemView.context, "Excluido", Toast.LENGTH_SHORT).show()
//                })
//                alertDialog.setNegativeButton("Não", null)
//                alertDialog.show()


            }
        }

    }

}

