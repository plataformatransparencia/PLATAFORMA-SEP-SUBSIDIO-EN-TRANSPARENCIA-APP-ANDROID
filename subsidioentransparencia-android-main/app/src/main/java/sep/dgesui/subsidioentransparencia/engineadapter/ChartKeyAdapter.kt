package sep.dgesui.subsidioentransparencia.engineadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.layout_chart_key.view.*
import sep.dgesui.subsidioentransparencia.R
import timber.log.Timber
import java.text.NumberFormat
import java.util.*

val numberFormatCurrency: NumberFormat =
    NumberFormat.getCurrencyInstance(Locale("es", "MX"))

class ChartKeyAdapter(private val keys: List<ChartKey>) :
    RecyclerView.Adapter<ChartKeyAdapter.ChartKeyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartKeyViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_chart_key, parent, false)
            .let { ChartKeyViewHolder(it) }


    override fun onBindViewHolder(holder: ChartKeyViewHolder, position: Int) {
        holder.bind(keys[position])
    }

    override fun getItemCount(): Int = keys.size

    inner class ChartKeyViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(key: ChartKey) {
            Timber.d("Binding: ${key.etiqueta} (${key.monto})")

            view.colorKey.setCardBackgroundColor(key.color)
            view.valueText.text = numberFormatCurrency.format(key.monto)
            view.labelText.text = key.etiqueta
        }
    }


}

data class ChartKey(val color: Int, val monto: Double, val etiqueta: String)
