package sep.dgesui.subsidioentransparencia.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.databinding.ComponentLinkBinding

class TransparenciaLinkComponent
@JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    styleSelector: Int = 0
) : ConstraintLayout(context, attributes, styleSelector) {
    private var binding: ComponentLinkBinding? = null
    private var target: ((View) -> Unit)? = null

    init {
        binding = rootView.let {
            ComponentLinkBinding.inflate(LayoutInflater.from(context), it as ViewGroup)
        }
        context.obtainStyledAttributes(attributes, R.styleable.TransparenciaLinkComponent).apply {
            setLabel(getString(R.styleable.TransparenciaLinkComponent_dgesui_linkLabel))
            recycle()
        }
    }

    fun setLabel(label: String?) {

        binding?.linkTitle?.text = label
    }

    fun setTarget(targetAction: (View) -> Unit) {
        target = targetAction
        binding?.linkButton?.setOnClickListener(targetAction)
    }

    fun hasTarget(): Boolean = target != null
}