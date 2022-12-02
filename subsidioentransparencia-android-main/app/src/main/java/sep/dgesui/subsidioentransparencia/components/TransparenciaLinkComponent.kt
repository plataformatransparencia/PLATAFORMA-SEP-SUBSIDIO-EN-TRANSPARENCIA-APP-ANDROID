package sep.dgesui.subsidioentransparencia.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.component_link.view.*
import sep.dgesui.subsidioentransparencia.R

class TransparenciaLinkComponent
@JvmOverloads constructor(
    context: Context,
    attributes: AttributeSet? = null,
    styleSelector: Int = 0
) : ConstraintLayout(context, attributes, styleSelector) {

    private var target: ((View) -> Unit)? = null

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.component_link, this)

        context.obtainStyledAttributes(attributes, R.styleable.TransparenciaLinkComponent).apply {

            setLabel(getString(R.styleable.TransparenciaLinkComponent_dgesui_linkLabel))

            recycle()
        }


    }

    fun setLabel(label: String?) {
        linkTitle.text = label
    }

    fun setTarget(targetAction: (View) -> Unit) {
        target = targetAction
        linkButton.setOnClickListener(targetAction)
    }

    fun hasTarget(): Boolean = target != null
}