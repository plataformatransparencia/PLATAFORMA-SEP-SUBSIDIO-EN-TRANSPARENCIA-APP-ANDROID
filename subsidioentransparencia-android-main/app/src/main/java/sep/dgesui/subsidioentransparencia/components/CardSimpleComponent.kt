package sep.dgesui.subsidioentransparencia.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import sep.dgesui.subsidioentransparencia.databinding.ComponentSemaforoSmpleBinding
import sep.dgesui.subsidioentransparencia.fragments.yearWithExtraKeys
import sep.dgesui.subsidioentransparencia.getColorCumplimiento

class CardSimpleComponent @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    styleSelector: Int = 0
) :
    ConstraintLayout(context, attributeSet, styleSelector) {
    private var binding: ComponentSemaforoSmpleBinding? = null
    init {
        binding = rootView.let {
            ComponentSemaforoSmpleBinding.inflate(LayoutInflater.from(context), it as ViewGroup)
        }
    }
    fun showData(cumplimiento: String, year: String) {
        if (year.toInt() >= yearWithExtraKeys) {
            binding?.cardKeysExtraSimple?.visibility = View.VISIBLE
            binding?.cardKeysExtraSimple2?.visibility = View.VISIBLE
        }
        binding?.circuloCumplimientoSimple?.setCardBackgroundColor(
            getColorCumplimiento(
                cumplimiento,
                context
            )
        )

    }
}