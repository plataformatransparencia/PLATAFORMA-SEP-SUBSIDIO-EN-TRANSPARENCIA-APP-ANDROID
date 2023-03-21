package sep.dgesui.subsidioentransparencia.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.component_semaforo_smple.view.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.fragments.yearWithExtraKeys
import sep.dgesui.subsidioentransparencia.getColorCumplimiento

class CardSimpleComponent @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    styleSelector: Int = 0
) :
    ConstraintLayout(context, attributeSet, styleSelector) {

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.component_semaforo_smple, this)
    }


    fun showData(cumplimiento: String, year: String) {

        if (year.toInt() >= yearWithExtraKeys) {
            cardKeysExtraSimple.visibility = View.VISIBLE
            cardKeysExtraSimple2.visibility = View.VISIBLE
        }
        circuloCumplimientoSimple.setCardBackgroundColor(
            getColorCumplimiento(
                cumplimiento,
                context
            )
        )

    }
}