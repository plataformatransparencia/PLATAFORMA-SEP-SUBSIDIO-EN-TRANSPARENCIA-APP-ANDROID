package sep.dgesui.subsidioentransparencia

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat
import sep.dgesui.subsidioentransparencia.fragments.Item
import sep.dgesui.subsidioentransparencia.modelfilter.FilterValuesCache
import java.text.NumberFormat
import java.util.*

fun getColorCumplimiento(cumplimiento: String, context: Context?): Int {
    if (context == null)
        return Color.TRANSPARENT

    return when (cumplimiento) {
        "Cumplió" -> ContextCompat.getColor(context, R.color.green)
        "No cumplió" -> ContextCompat.getColor(context, R.color.red)
        "En revisión" -> ContextCompat.getColor(context, R.color.yellow)
        "Incompleta" -> ContextCompat.getColor(context, R.color.gob_red_dark)
        "Complementada" -> ContextCompat.getColor(context, R.color.green_extra_dark)
        "Extemporánea" -> ContextCompat.getColor(context, R.color.gob_pink)
        else -> Color.TRANSPARENT
    }
}


fun externalLink(uri: String): (View) -> Unit =
    { view ->

        Intent.getIntentOld(Uri.parse(uri).toString()).also {
            view.context.startActivity(it)
        }
    }

//La regla indica que se deben extraer las subacciones siempre que la descripción de estas no inicie
//con la palabra etapa. Se aprovecha el punto de que si una de las subacciones inicia con etapa, todas
//lo hacen. Al menos hasta el momento.
fun flattenItems(items: List<Item>): List<Item> =
    items.flatMap {
        if (it.subacciones != null &&
            it.subacciones.isNotEmpty() &&
            !it.subacciones.first().descripcion.startsWith("etapa", ignoreCase = true)
        ) {
            val self = listOf(it.copy(subacciones = emptyList()))
            val subacciones = it.subacciones

            self + subacciones
        } else
            listOf(it)
    }


//fun currentYear(): String = Calendar.getInstance().get(Calendar.YEAR).toString()
fun currentYear(): String = String.format("%s", getLastAnio())
fun getLastAnio(): String {
    if(FilterValuesCache.getFilterValuesIni().value != null){
        return FilterValuesCache.getFilterValuesIni().value.toString()
    }else{
        return Calendar.getInstance().get(Calendar.YEAR).toString()
    }
}
fun extraordinario(year: String, context: Context) =
    String.format(context.getString(R.string.upe_deficit_financiero), year)

fun ordinario(year: String, context: Context) =
    String.format(context.getString(R.string.upe_ordinario), year)

fun profexe(year: String, context: Context) =
    String.format(context.getString(R.string.upe_profexe), year)

fun extrau079(year: String, context: Context) =
    String.format(context.getString(R.string.upe_u079), year)

const val porcentaje_template = "%1.2f%%"

val localeMX = Locale("es", "MX")

val currencyFormatter: NumberFormat = NumberFormat.getCurrencyInstance(localeMX)

val integerFormatter: NumberFormat = NumberFormat.getIntegerInstance(localeMX)

val months = listOf(
    "Enero",
    "Febrero",
    "Marzo",
    "Abril",
    "Mayo",
    "Junio",
    "Julio",
    "Agosto",
    "Septiembre",
    "Octubre",
    "Noviembre",
    "Diciembre"
)

val mesesTrimestre1 = months.subList(0, 3)
val mesesTrimestre2 = months.subList(3, 6)
val mesesTrimestre3 = months.subList(6, 9)
val mesesTrimestre4 = months.subList(9, 12)
