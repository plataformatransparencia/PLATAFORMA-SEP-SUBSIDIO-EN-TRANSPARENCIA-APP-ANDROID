package sep.dgesui.subsidioentransparencia.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.layout_compromiso_card.view.*
import kotlinx.android.synthetic.main.layout_cumplimiento_card.view.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.getColorCumplimiento

class CardComponent @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    schemaSelector: Int = 0
) : ConstraintLayout(context, attributes, schemaSelector) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.layout_cumplimiento_card, this)
    }

    fun setValues(cumplimiento: String, fecha: String, titleFecha: String = "") {
        circuloCumplimiento.setCardBackgroundColor(getColorCumplimiento(cumplimiento, context))
        fechaEjecucion.text = fecha

        if (titleFecha.isNotBlank())
            fechaEstipulada.text = titleFecha
    }

    fun showExtra() {
        cardKeysExtra.visibility = View.VISIBLE
    }


}