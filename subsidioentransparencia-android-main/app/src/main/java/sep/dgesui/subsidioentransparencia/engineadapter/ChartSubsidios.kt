package sep.dgesui.subsidioentransparencia.engineadapter

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.databinding.LayoutChartBinding
import sep.dgesui.subsidioentransparencia.fragments.paletaColores
import java.text.DecimalFormat

class ChartSubsidios
@JvmOverloads
constructor(
    private val ctx: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(ctx, attributeSet, defStyleAttr) {
    private var binding: LayoutChartBinding? = null
    init {
        binding = rootView.let {
            LayoutChartBinding.inflate(LayoutInflater.from(context), it as ViewGroup)
        }
    }

    fun plot(montos: Map<String, Double>, tipoSubsidio: String) {

        if (montos.size > 1) {
            val total = montos.values.reduce { acc, value -> acc + value }
            binding?.montoTotal?.text = numberFormatCurrency.format(total)
        } else {
            binding?.montoTotal?.isVisible = false
            binding?.txtMontoTotal?.isVisible = false
        }
        val dataset = montos.values.map { PieEntry(it.toFloat()) }
            .let { PieDataSet(it, "") }
        dataset.setDrawValues(true)
        val paleta = paletaColores(ctx, tipoSubsidio)
        dataset.colors = paleta.asList()
        val pieData = PieData(dataset)
        pieData.setValueTextSize(17F)

        val formater = PercentFormatter(binding?.chart)
        formater.mFormat = DecimalFormat("###,###,##0.00")
        pieData.setValueTextColor(ContextCompat.getColor(context, R.color.white))
        pieData.setValueFormatter(formater)

        binding?.chart?.data = pieData

        binding?.chart?.isRotationEnabled = false
        binding?.chart?.setDrawEntryLabels(false)
        binding?.chart?.setHoleColor(Color.WHITE)
        binding?.chart?.centerTextRadiusPercent = 1.0f
        binding?.chart?.isUsePercentValuesEnabled
        binding?.chart?.setUsePercentValues(true)
        binding?.chart?.legend?.isEnabled = false
        binding?.chart?.description?.isEnabled = false
        binding?.chart?.defaultValueFormatter
        binding?.chart?.animation
        binding?.chart?.animateXY(2000, 2000)
        binding?.chartKeys?.adapter = montos.entries
            .mapIndexed { idx, entry ->
                ChartKey(paleta.asList()[idx], entry.value, entry.key)
            }
            .let { keys -> ChartKeyAdapter(keys) }
    }


}