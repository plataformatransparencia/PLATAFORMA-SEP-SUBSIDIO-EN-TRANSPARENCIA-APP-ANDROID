package sep.dgesui.subsidioentransparencia.engineadapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_compromisos_list.view.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.getColorCumplimiento
import sep.dgesui.subsidioentransparencia.tableroext.compromisos.Cumplimiento

class CommitActividadesCumplimientoAdapter(
    val compromisosext: ArrayList<Cumplimiento>
) : RecyclerView.Adapter<CommitActividadesCumplimientoAdapter.CompromisosCumplimientosExtHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CompromisosCumplimientosExtHolder {
        return CompromisosCumplimientosExtHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_compromisos_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CompromisosCumplimientosExtHolder, position: Int) {
        holder.bind(compromisosext[position])
    }

    override fun getItemCount(): Int = compromisosext.size

    inner class CompromisosCumplimientosExtHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(compromiso: Cumplimiento) {
            var fechaEstipulada = ""
            var fechaEjecucion = ""
            if (compromiso.fechaEstipulada.isNotEmpty() || compromiso.fechaEjecucion.isNotEmpty()) {
                fechaEstipulada = compromiso.fechaEstipulada
                fechaEjecucion = compromiso.fechaEjecucion
            }
            view.textFechaItem.text = fechaEstipulada
            view.fechaDetailCumplimientoItem.text = fechaEjecucion


            val color = getColorCumplimiento(compromiso.cumplimiento, view.context)

            view.cardViewDetailCumplimiento18.setCardBackgroundColor(color)

            ajustarObservaciones(compromiso.observacion)

        }

        private fun ajustarObservaciones(observacion: String) {
            if (observacion.startsWith("http")) {
                view.botonDocumentoObservacion.isVisible = true
                view.plainTextObservacionDetailCompromiso.isVisible = false

                if (!observacion.endsWith(".pdf"))
                    view.botonDocumentoObservacion.text = "Ir a la página"

                view.botonDocumentoObservacion.setOnClickListener {
                    startActivity(
                        view.context,
                        Intent.getIntentOld(Uri.parse(observacion).toString()),
                        null
                    )
                }

            } else {
                view.botonDocumentoObservacion.isVisible = false
                view.plainTextObservacionDetailCompromiso.isVisible = true

                view.plainTextObservacionDetailCompromiso.text = observacion
            }
        }
    }
}