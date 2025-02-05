package sep.dgesui.subsidioentransparencia.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import sep.dgesui.subsidioentransparencia.databinding.LayoutTransparenciaHeaderBinding
import sep.dgesui.subsidioentransparencia.fragments.PaletaColores
import sep.dgesui.subsidioentransparencia.fragments.getTitle
import sep.dgesui.subsidioentransparencia.fragments.paletaColores

class TransparenciaHeader
@JvmOverloads constructor(ctx: Context, attributes: AttributeSet? = null, styleSelect: Int = 0) :
    ConstraintLayout(ctx, attributes, styleSelect) {
    private var binding: LayoutTransparenciaHeaderBinding? = null
    init {
        binding = rootView.let {
            LayoutTransparenciaHeaderBinding.inflate(
                LayoutInflater.from(ctx),
                it as ViewGroup
            )
        }

    }

    fun setValues(nombreUniversidad: String, subsidio: String, year: String, context: Context) {
        val title = String.format(getTitle(context, subsidio), year)
        val paleta = paletaColores(context, subsidio)
        setValues(title, nombreUniversidad, paleta)
    }

    private fun setValues(title: String, subtitle: String, paletaColores: PaletaColores) {
        binding?.title?.apply {
            text = title
            setBackgroundColor(paletaColores.principal)
        }
        binding?.subtitle?.apply {
            text = subtitle
            setBackgroundColor(paletaColores.secundario)
        }

    }

}