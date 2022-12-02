package sep.dgesui.subsidioentransparencia.engineadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_montos_minestext.view.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.interfaces.DataListMinEstExt
import sep.dgesui.subsidioentransparencia.tableroext.minest.Ministracion
import java.text.NumberFormat
import java.util.*

class ListExtMinEstAdapter(val ministracion: ArrayList<Ministracion>,val itemClickListener:DataListMinEstExt): RecyclerView.Adapter<ListExtMinEstAdapter.ListExtMinHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListExtMinHolder {
        return ListExtMinHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_montos_minestext, parent, false))
    }

    override fun onBindViewHolder(holder: ListExtMinHolder, position: Int) {
        holder.bind(ministracion[position], position)
    }

    override fun getItemCount(): Int = ministracion.size

    inner class ListExtMinHolder(val view: View):RecyclerView.ViewHolder(view) {
        fun bind(ministracion: Ministracion, position: Int) {
            val numberFormat = NumberFormat.getNumberInstance(Locale("es", "MX"))
            numberFormat.maximumFractionDigits = 2
            numberFormat.minimumFractionDigits = 2

            val numberFormatCurrency =
                NumberFormat.getCurrencyInstance(Locale("es", "MX"))
            numberFormat.maximumFractionDigits = 2
            numberFormat.minimumFractionDigits = 2
            view.numMontoExtListMinEstatal.text = numberFormatCurrency.format(ministracion.estado_universidad)

            view.botonDetalleMontoDetalle.setOnClickListener {
                itemClickListener.onClickListenerCompromiso(
                    position,
                    ministracion.estado_universidad,
                    ministracion.fechaEjecucionEstado,
                    ministracion.observacion
                )
            }

        }
    }

}