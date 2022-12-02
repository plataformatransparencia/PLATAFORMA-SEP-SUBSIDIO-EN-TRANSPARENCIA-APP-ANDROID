package sep.dgesui.subsidioentransparencia.fragments

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.engineadapter.Filter
import sep.dgesui.subsidioentransparencia.extraordinario
import sep.dgesui.subsidioentransparencia.ordinario
import sep.dgesui.subsidioentransparencia.profexe


fun formatHeader(filter: Filter, area: View, text: TextView, context: Context) {

    when (filter.subsidio) {
        "subsidio_ordinario" -> text.text =
            ordinario(filter.year, context)

        "subsidio_extraordinario" -> {
            area.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.gob_wine
                )
            )
            text.text = extraordinario(filter.year, context)
        }
        "subsidio_profexce" -> {
            area.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.gob_wine
                )
            )
            text.text = profexe(filter.year, context)
        }
        else -> text.text = ordinario(filter.year, context)
    }
}


fun getTitle(context: Context, subsidio: String): String =
    when (subsidio) {
        "subsidio_extraordinario" -> context.getString(R.string.upe_deficit_financiero)
        "subsidio_profexce" -> context.getString(R.string.upe_profexe)
        else -> context.getString(R.string.upe_ordinario)

    }

fun paletaColores(context: Context, subsidio: String): PaletaColores =
    when (subsidio) {
        "subsidio_extraordinario", "subsidio_profexce" -> PaletaColores(
            ContextCompat.getColor(
                context,
                R.color.gob_wine
            ),
            ContextCompat.getColor(
                context,
                R.color.gob_pink
            )
        )

        else -> PaletaColores(

            ContextCompat.getColor(
                context,
                R.color.gob_green_dark
            ),
            ContextCompat.getColor(
                context,
                R.color.gob_green
            )
        )

    }

data class PaletaColores(val principal: Int, val secundario: Int) {
    fun asList(): List<Int> = listOf(principal, secundario)
}


const val yearWithExtraKeys = 2020