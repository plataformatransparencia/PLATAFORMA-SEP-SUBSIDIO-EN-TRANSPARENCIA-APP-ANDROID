package sep.dgesui.subsidioentransparencia.engineadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.databinding.LayoutCompromisoCardBinding
import sep.dgesui.subsidioentransparencia.externalLink
import sep.dgesui.subsidioentransparencia.fragments.CumplimientoInfo

class EntregaCompromisoAdapter(private val cumplimientos: List<CumplimientoInfo>) :
    RecyclerView.Adapter<EntregaCompromisoAdapter.CumplimientoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CumplimientoHolder{
        val binding = LayoutCompromisoCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CumplimientoHolder(binding)
    }

    override fun onBindViewHolder(holder: CumplimientoHolder, position: Int) {
        holder.bind(cumplimientos[position])
    }

    override fun getItemCount(): Int = cumplimientos.size

    inner class CumplimientoHolder(private val binding: LayoutCompromisoCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cumplimiento: CumplimientoInfo) {
            binding.fechaEstipulada.text = cumplimiento.fechaEstipulada

            binding.semaforo.setValues(cumplimiento.cumplimiento, cumplimiento.fechaEjecucion,)

            if (cumplimiento.observacion.startsWith("http", ignoreCase = true)) {
                activarBoton(cumplimiento)

                if (cumplimiento.observacion.endsWith("pdf", ignoreCase = true))
                    cambiarADocumento()

            } else {
                binding.entregaCompromisoObservaciones.text = cumplimiento.observacion
            }

        }

        private fun activarBoton(cumplimiento: CumplimientoInfo) {
            binding.entregaCompromisoObservaciones.visibility = View.GONE

            binding.entregaCompromisoObservacionesButton.visibility = View.VISIBLE
            binding.entregaCompromisoObservacionesButton.setOnClickListener(
                externalLink(cumplimiento.observacion)
            )
        }

        private fun cambiarADocumento() {
            binding.entregaCompromisoObservacionesButton.text =
                binding.root.context!!.getString(R.string.ver_documento)
            switchIcon()
        }

        private fun switchIcon() {
            val newIcon =
                ContextCompat.getDrawable(binding.root.context, R.drawable.ic_dowload_file)


            binding.entregaCompromisoObservacionesButton.setCompoundDrawablesWithIntrinsicBounds(
                newIcon,
                null,
                null,
                null
            )
        }
    }
}
