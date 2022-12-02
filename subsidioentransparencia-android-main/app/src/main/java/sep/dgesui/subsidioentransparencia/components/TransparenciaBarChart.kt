package sep.dgesui.subsidioentransparencia.components

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.LargeValueFormatter
import kotlinx.android.synthetic.main.component_bar_chart.view.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.months

class TransparenciaBarChart @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    styleSelector: Int = 0
) : ConstraintLayout(context, attributeSet, styleSelector) {
    private var darkWhite: Int = 0
    private var green: Int = 0
    private var darkGreen: Int = 0

    private val barEntryMaper =
        { idx: Int, valor: Double -> BarEntry(idx.toFloat(), valor.toFloat()) }

    init {
        LayoutInflater.from(context).inflate(R.layout.component_bar_chart, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        barChartChart.apply {
            setNoDataText("Cargando...")
            setBackgroundColor(Color.TRANSPARENT)
            setDrawValueAboveBar(true)
            axisRight.isEnabled = false
            zoom(3.8F, 0.9F, 0F, 0F)
            setScaleEnabled(true)
            extraBottomOffset = 10F
        }




        darkWhite = ContextCompat.getColor(context, R.color.gob_dark_withe)
        green = ContextCompat.getColor(context, R.color.gob_green)
        darkGreen = ContextCompat.getColor(context, R.color.gob_green_dark)

    }


    private fun formatXAxis(xAxis: XAxis) {
        xAxis.valueFormatter = IndexAxisValueFormatter(months)
        xAxis.textSize = 15f
        xAxis.textColor = darkWhite
        xAxis.setDrawGridLines(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setCenterAxisLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = 00.0f
        xAxis.yOffset = 5f
    }

    private fun formatLeftAxis(yAxis: YAxis) {
        yAxis.textColor = darkWhite
        yAxis.textSize = 15f
        yAxis.valueFormatter = LargeValueFormatter()
        yAxis.axisMinimum = 0f
        yAxis.spaceTop = 0.35f
        yAxis.spaceBottom = 0.5f
        yAxis.setDrawGridLines(false)
    }

    private fun formatLegend(legend: Legend) {
        legend.isEnabled = true
        legend.textSize = 15f
        legend.formSize = 15f
        legend.formToTextSpace = 10f
        legend.textColor = darkWhite
        legend.setDrawInside(false)
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.yEntrySpace = 10.0f
        legend.xOffset = 0.0f
        legend.yOffset = 10.0f
    }

    fun setData(montos: List<Double>, montosRecibidos: List<Double>) {

        val montosBarEntry = montos.mapIndexed(barEntryMaper)

        val montosRecibidosBarEntry = montosRecibidos.mapIndexed(barEntryMaper)


        val calendarizadoDataSet =
            BarDataSet(montosBarEntry, context.getString(R.string.monto_calendarizado))
        val recibidoDataSet =
            BarDataSet(montosRecibidosBarEntry, context.getString(R.string.monto_recibido))

        configureDataSet(calendarizadoDataSet, green)
        configureDataSet(recibidoDataSet, darkGreen)

        val chartData = BarData(listOf(calendarizadoDataSet, recibidoDataSet))

        chartData.setValueTextSize(12f)
        chartData.setValueFormatter(LargeValueFormatter())
        chartData.barWidth = 0.400f
        chartData.groupBars(0.0f, 0.2f, 0.0f)

        barChartChart.data = chartData
        barChartChart.description.isEnabled = false
        barChartChart.notifyDataSetChanged()
        barChartChart.xAxis.axisMinimum = 0f
        barChartChart.xAxis.axisMaximum = 12f

        formatXAxis(barChartChart.xAxis)
        formatLeftAxis(barChartChart.axisLeft)
        formatLegend(barChartChart.legend)
    }

    private fun configureDataSet(dataSet: BarDataSet, color: Int) {
        dataSet.isHighlightEnabled = false
        dataSet.barBorderColor = color
        dataSet.setDrawValues(true)
        dataSet.color = color
        dataSet.valueTextColor = darkWhite

    }
}