package sep.dgesui.subsidioentransparencia.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.databinding.ComponentLinkMinistracionBinding

class LinkMinistracionComponent @JvmOverloads constructor(
    context: Context,
    attributesSet: AttributeSet? = null,
    styleSelector: Int = 0
) : ConstraintLayout(context, attributesSet, styleSelector) {
    private var binding: ComponentLinkMinistracionBinding? = null
    init {
        binding = rootView.let {
            ComponentLinkMinistracionBinding.inflate(LayoutInflater.from(context), it as ViewGroup, true)
        }
    }
    var color: Int = 0
        set(value) {
            field = value
            binding?.linkMinistracionSemaforo?.setCardBackgroundColor(value)
        }



    override fun onFinishInflate() {
        super.onFinishInflate()

        color = ContextCompat.getColor(context, R.color.trans)
    }

    fun setup(mes: String, monto: String, target: OnClickListener? = null) {
        binding?.linkMinistracionMes?.text = mes
        binding?.linkMinistracionMonto?.text = monto

        if (target != null)
            binding?.linkMinistracionBoton?.setOnClickListener(target)
    }

    fun setTarget(target: OnClickListener) {
        binding?.linkMinistracionBoton?.setOnClickListener(target)
    }
}
