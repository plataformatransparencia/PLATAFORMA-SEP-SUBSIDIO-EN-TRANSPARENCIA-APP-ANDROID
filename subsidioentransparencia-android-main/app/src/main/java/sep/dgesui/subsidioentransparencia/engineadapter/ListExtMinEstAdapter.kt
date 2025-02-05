package sep.dgesui.subsidioentransparencia.engineadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sep.dgesui.subsidioentransparencia.databinding.ItemMontosMinestextBinding
import sep.dgesui.subsidioentransparencia.interfaces.DataListMinEstExt
import sep.dgesui.subsidioentransparencia.tableroext.minest.Ministracion
import java.text.NumberFormat
import java.util.*

class ListExtMinEstAdapter(val ministracion: ArrayList<Ministracion>,val itemClickListener:DataListMinEstExt): RecyclerView.Adapter<ListExtMinEstAdapter.ListExtMinHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListExtMinHolder {
        val binding = ItemMontosMinestextBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListExtMinHolder(binding)
    }

    override fun onBindViewHolder(holder: ListExtMinHolder, position: Int) {
        holder.bind(ministracion[position], position)
    }

    override fun getItemCount(): Int = ministracion.size

    inner class ListExtMinHolder(val binding: ItemMontosMinestextBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(ministracion: Ministracion, position: Int) {
            val numberFormat = NumberFormat.getNumberInstance(Locale("es", "MX"))
            numberFormat.maximumFractionDigits = 2
            numberFormat.minimumFractionDigits = 2

            val numberFormatCurrency =
                NumberFormat.getCurrencyInstance(Locale("es", "MX"))
            numberFormat.maximumFractionDigits = 2
            numberFormat.minimumFractionDigits = 2

            binding.numMontoExtListMinEstatal.text = numberFormatCurrency.format(ministracion.estado_universidad)
            binding.botonDetalleMontoDetalle.setOnClickListener {
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