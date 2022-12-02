package sep.dgesui.subsidioentransparencia.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.component_link_ministracion.view.*
import sep.dgesui.subsidioentransparencia.R

class LinkMinistracionComponent @JvmOverloads constructor(
    context: Context,
    attributesSet: AttributeSet? = null,
    styleSelector: Int = 0
) : ConstraintLayout(context, attributesSet, styleSelector) {

    var color: Int = 0
        set(value) {
            field = value
            linkMinistracionSemaforo.setCardBackgroundColor(value)
        }

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.component_link_ministracion, this)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        color = ContextCompat.getColor(context, R.color.trans)
    }

    fun setup(mes: String, monto: String, target: OnClickListener? = null) {
        linkMinistracionMes.text = mes
        linkMinistracionMonto.text = monto

        if (target != null)
            linkMinistracionBoton.setOnClickListener(target)
    }

    fun setTarget(target: OnClickListener) {
        linkMinistracionBoton.setOnClickListener(target)
    }
}
