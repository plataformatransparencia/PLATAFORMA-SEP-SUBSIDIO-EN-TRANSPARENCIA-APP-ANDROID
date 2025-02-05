package sep.dgesui.subsidioentransparencia.engineadapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import sep.dgesui.subsidioentransparencia.databinding.ItemCompromisosListBinding
import sep.dgesui.subsidioentransparencia.getColorCumplimiento
import sep.dgesui.subsidioentransparencia.tableroext.compromisos.Cumplimiento

class CommitActividadesCumplimientoAdapter(
    val compromisosext: ArrayList<Cumplimiento>
) : RecyclerView.Adapter<CommitActividadesCumplimientoAdapter.CompromisosCumplimientosExtHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): CompromisosCumplimientosExtHolder {
        val binding = ItemCompromisosListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CompromisosCumplimientosExtHolder(binding)
    }

    override fun onBindViewHolder(holder: CompromisosCumplimientosExtHolder, position: Int) {
        holder.bind(compromisosext[position])
    }

    override fun getItemCount(): Int = compromisosext.size

    inner class CompromisosCumplimientosExtHolder(val binding: ItemCompromisosListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(compromiso: Cumplimiento) {
            var fechaEstipulada = ""
            var fechaEjecucion = ""
            if (compromiso.fechaEstipulada.isNotEmpty() || compromiso.fechaEjecucion.isNotEmpty()) {
                fechaEstipulada = compromiso.fechaEstipulada
                fechaEjecucion = compromiso.fechaEjecucion
            }

            binding.textFechaItem.text = fechaEstipulada
            binding.fechaDetailCumplimientoItem.text = fechaEjecucion


            val color = getColorCumplimiento(compromiso.cumplimiento, binding.root.context)

            binding.cardViewDetailCumplimiento18.setCardBackgroundColor(color)

            ajustarObservaciones(compromiso.observacion)

        }

        private fun ajustarObservaciones(observacion: String) {
            if (observacion.startsWith("http")) {
                binding.botonDocumentoObservacion.isVisible = true
                binding.plainTextObservacionDetailCompromiso.isVisible = false

                if (!observacion.endsWith(".pdf"))
                    binding.botonDocumentoObservacion.text = "Ir a la página"

                binding.botonDocumentoObservacion.setOnClickListener {
                    startActivity(
                        binding.root.context,
                        Intent.getIntentOld(Uri.parse(observacion).toString()),
                        null
                    )
                }

            } else {
                binding.botonDocumentoObservacion.isVisible = false
                binding.plainTextObservacionDetailCompromiso.isVisible = true

                binding.plainTextObservacionDetailCompromiso.text = observacion
            }
        }
    }
}