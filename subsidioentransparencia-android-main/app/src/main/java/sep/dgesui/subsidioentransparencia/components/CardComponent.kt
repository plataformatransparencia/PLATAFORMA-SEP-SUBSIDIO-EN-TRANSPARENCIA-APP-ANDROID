package sep.dgesui.subsidioentransparencia.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import sep.dgesui.subsidioentransparencia.databinding.LayoutCompromisoCardBinding
import sep.dgesui.subsidioentransparencia.databinding.LayoutCumplimientoCardBinding
import sep.dgesui.subsidioentransparencia.getColorCumplimiento

class CardComponent @JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    schemaSelector: Int = 0
) : ConstraintLayout(context, attributes, schemaSelector) {
    private var binding: LayoutCumplimientoCardBinding? = null
    init {
        binding = rootView.let {
            LayoutCumplimientoCardBinding.inflate(LayoutInflater.from(context), it as ViewGroup)
        }

    }

    fun setValues(cumplimiento: String, fecha: String, titleFecha: String = "") {
        binding?.circuloCumplimiento?.setCardBackgroundColor(getColorCumplimiento(cumplimiento, context))
        binding?.fechaEjecucion?.text = fecha
        if (titleFecha.isNotBlank()){
            val binding2 = LayoutCompromisoCardBinding.bind(rootView)
            binding2.fechaEstipulada.text = titleFecha
        }


    }

    fun showExtra() {
        binding?.cardKeysExtra?.visibility = View.VISIBLE
        binding?.cardKeysExtra2?.visibility = View.VISIBLE
    }


}