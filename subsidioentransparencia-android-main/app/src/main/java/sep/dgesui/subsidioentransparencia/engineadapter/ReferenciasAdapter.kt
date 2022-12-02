package sep.dgesui.subsidioentransparencia.engineadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_referencia.view.*
import sep.dgesui.subsidioentransparencia.R

class ReferenciasAdapter(referencias: Map<String, String>) :
    RecyclerView.Adapter<ReferenciasAdapter.ReferenciasViewHolder>() {

    private val listaReferencias = referencias.entries.map { ReferenciaWrapper(it.key, it.value) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReferenciasViewHolder =

        LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_referencia, parent, false)
            .let { ReferenciasViewHolder(it) }

    override fun onBindViewHolder(holder: ReferenciasViewHolder, position: Int) {
        holder.bind(listaReferencias[position])
    }

    override fun getItemCount(): Int = listaReferencias.size

    inner class ReferenciasViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(referencia: ReferenciaWrapper) {
            view.clave.text = referencia.clave
            view.contenido.text = referencia.contenido
        }
    }

}

data class ReferenciaWrapper(val clave: String, val contenido: String)

