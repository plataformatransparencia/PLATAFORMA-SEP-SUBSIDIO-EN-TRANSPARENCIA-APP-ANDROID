package sep.dgesui.subsidioentransparencia.engineadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sep.dgesui.subsidioentransparencia.databinding.LayoutReferenciaBinding

class ReferenciasAdapter(referencias: Map<String, String>) :
    RecyclerView.Adapter<ReferenciasAdapter.ReferenciasViewHolder>() {

    private val listaReferencias = referencias.entries.map { ReferenciaWrapper(it.key, it.value) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReferenciasViewHolder{
        val binding = LayoutReferenciaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReferenciasViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReferenciasViewHolder, position: Int) {
        holder.bind(listaReferencias[position])
    }

    override fun getItemCount(): Int = listaReferencias.size

    inner class ReferenciasViewHolder(private val binding: LayoutReferenciaBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(referencia: ReferenciaWrapper) {
            binding.clave.text = referencia.clave
            binding.contenido.text = referencia.contenido
        }
    }

}

data class ReferenciaWrapper(val clave: String, val contenido: String)

