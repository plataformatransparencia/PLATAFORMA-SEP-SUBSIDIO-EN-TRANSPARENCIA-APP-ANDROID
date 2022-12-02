package sep.dgesui.subsidioentransparencia.engineadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_compromiso_card.view.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.externalLink
import sep.dgesui.subsidioentransparencia.fragments.CumplimientoInfo

class EntregaCompromisoAdapter(private val cumplimientos: List<CumplimientoInfo>) :
    RecyclerView.Adapter<EntregaCompromisoAdapter.CumplimientoHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CumplimientoHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_compromiso_card, parent, false)
            .let { CumplimientoHolder(it) }

    override fun onBindViewHolder(holder: CumplimientoHolder, position: Int) {
        holder.bind(cumplimientos[position])
    }

    override fun getItemCount(): Int = cumplimientos.size

    inner class CumplimientoHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(cumplimiento: CumplimientoInfo) {

            view.fechaEstipulada.text = cumplimiento.fechaEstipulada

            view.semaforo.setValues(cumplimiento.cumplimiento, cumplimiento.fechaEjecucion)

            if (cumplimiento.observacion.startsWith("http", ignoreCase = true)) {
                activarBoton(cumplimiento)

                if (cumplimiento.observacion.endsWith("pdf", ignoreCase = true))
                    cambiarADocumento()

            } else {
                view.entregaCompromisoObservaciones.text = cumplimiento.observacion
            }

        }

        private fun activarBoton(cumplimiento: CumplimientoInfo) {
            view.entregaCompromisoObservaciones.visibility = View.GONE

            view.entregaCompromisoObservacionesButton.visibility = View.VISIBLE
            view.entregaCompromisoObservacionesButton.setOnClickListener(
                externalLink(cumplimiento.observacion)
            )
        }

        private fun cambiarADocumento() {
            view.entregaCompromisoObservacionesButton.text =
                view.context!!.getString(R.string.ver_documento)

            switchIcon()
        }

        private fun switchIcon() {
            val newIcon =
                ContextCompat.getDrawable(view.context, R.drawable.ic_dowload_file)


            view.entregaCompromisoObservacionesButton.setCompoundDrawablesWithIntrinsicBounds(
                newIcon,
                null,
                null,
                null
            )
        }
    }
}
