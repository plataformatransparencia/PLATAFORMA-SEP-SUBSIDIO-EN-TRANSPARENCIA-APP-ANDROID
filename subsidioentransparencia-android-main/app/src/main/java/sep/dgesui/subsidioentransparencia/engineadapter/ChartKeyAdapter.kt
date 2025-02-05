package sep.dgesui.subsidioentransparencia.engineadapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sep.dgesui.subsidioentransparencia.databinding.LayoutChartKeyBinding
import java.text.NumberFormat
import java.util.*

val numberFormatCurrency: NumberFormat =
    NumberFormat.getCurrencyInstance(Locale("es", "MX"))

class ChartKeyAdapter(private val keys: List<ChartKey>) :
    RecyclerView.Adapter<ChartKeyAdapter.ChartKeyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChartKeyViewHolder {
        val binding = LayoutChartKeyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChartKeyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChartKeyViewHolder, position: Int) {
        holder.bind(keys[position])
    }

    override fun getItemCount(): Int = keys.size

    inner class ChartKeyViewHolder(private val binding: LayoutChartKeyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(key: ChartKey) {
            binding.colorKey.setCardBackgroundColor(key.color)
            binding.valueText.text = numberFormatCurrency.format(key.monto)
            binding.labelText.text = key.etiqueta
        }
    }


}

data class ChartKey(val color: Int, val monto: Double, val etiqueta: String)
