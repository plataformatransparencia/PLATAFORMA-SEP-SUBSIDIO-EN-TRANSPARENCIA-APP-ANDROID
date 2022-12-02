package sep.dgesui.subsidioentransparencia.engineadapter

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import kotlinx.android.synthetic.main.layout_chart.view.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.fragments.paletaColores

class ChartSubsidios
@JvmOverloads
constructor(
    private val ctx: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(ctx, attributeSet, defStyleAttr) {

    init {
        LayoutInflater.from(ctx)
            .inflate(R.layout.layout_chart, this)
    }

    fun plot(montos: Map<String, Double>, tipoSubsidio: String) {

        if (montos.size > 1) {
            val total = montos.values.reduce { acc, value -> acc + value }
            monto_total.text = numberFormatCurrency.format(total)
        } else {
            monto_total.isVisible = false
            txtMontoTotal.isVisible = false
        }
        val dataset = montos.values.map { PieEntry(it.toFloat()) }
            .let { PieDataSet(it, "") }
        dataset.setDrawValues(true)
        val paleta = paletaColores(ctx, tipoSubsidio)
        dataset.colors = paleta.asList()
        val pieData = PieData(dataset)
        pieData.setValueTextSize(17F)
        pieData.setValueTextColor(ContextCompat.getColor(context, R.color.white))
        pieData.setValueFormatter(PercentFormatter(chart))

        chart.data = pieData

        chart.isRotationEnabled = false
        chart.setDrawEntryLabels(false)
        chart.setHoleColor(Color.WHITE)
        chart.centerTextRadiusPercent = 1.0f
        chart.isUsePercentValuesEnabled
        chart.setUsePercentValues(true)
        chart.legend.isEnabled = false
        chart.description.isEnabled = false
        chart.defaultValueFormatter
        chart.animation
        chart.animateXY(2000, 2000)
        chart_keys.adapter = montos.entries
            .mapIndexed { idx, entry ->
                ChartKey(paleta.asList()[idx], entry.value, entry.key)
            }
            .let { keys -> ChartKeyAdapter(keys) }
    }


}