package sep.dgesui.subsidioentransparencia.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.layout_transparencia_header.view.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.fragments.PaletaColores
import sep.dgesui.subsidioentransparencia.fragments.getTitle
import sep.dgesui.subsidioentransparencia.fragments.paletaColores

class TransparenciaHeader
@JvmOverloads constructor(ctx: Context, attributes: AttributeSet? = null, styleSelect: Int = 0) :
    ConstraintLayout(ctx, attributes, styleSelect) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.layout_transparencia_header, this)
    }

    fun setValues(nombreUniversidad: String, subsidio: String, year: String, context: Context) {
        val title = String.format(getTitle(context, subsidio), year)
        val paleta = paletaColores(context, subsidio)

        setValues(title, nombreUniversidad, paleta)
    }

    private fun setValues(title: String, subtitle: String, paletaColores: PaletaColores) {

        this.title.apply {
            text = title
            setBackgroundColor(paletaColores.principal)
        }

        this.subtitle.apply {
            text = subtitle
            setBackgroundColor(paletaColores.secundario)
        }

    }


}