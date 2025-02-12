package sep.dgesui.subsidioentransparencia.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.databinding.ComponentTrimestreProfexceBinding

class ComponenteTrimestreProfexce @JvmOverloads
constructor(context: Context, attributeSet: AttributeSet? = null, styleSelector: Int = 0) :
    ConstraintLayout(context, attributeSet, styleSelector) {

    private var expanded = false
    private var binding: ComponentTrimestreProfexceBinding? = null
    init {
        binding = rootView.let {
            ComponentTrimestreProfexceBinding.inflate(
                LayoutInflater.from(context),
                it as ViewGroup
            )
        }
        setOnClickListener { toggle() }
    }


    var trimestre: Int = 0
        set(value) {
            field = value
            binding?.trimestreProfexceTitulo?.text =
                String.format(context.getString(R.string.trimestreN), value)
        }

    var titulos: Titulos? = null
        set(value) {
            field = value

            if (value != null) {
                binding?.trimestreProfexceEntregaAcademico?.setLabel(value.boton1.first)
                binding?.trimestreProfexceEntregaAcademico?.setTarget(value.boton1.second)

                binding?.trimestreProfexceEntregaFinanciero?.setLabel(value.boton2.first)
                binding?.trimestreProfexceEntregaFinanciero?.setTarget(value.boton2.second)

                binding?.trimestreProfexcePublicacionInforme?.setLabel(value.boton3.first)
                binding?.trimestreProfexcePublicacionInforme?.setTarget(value.boton3.second)

            }
        }


    private fun toggle() {
        expanded = !expanded

        val visibility = if (expanded) View.VISIBLE else View.GONE

        binding?.trimestreProfexceEntregaAcademico?.visibility = visibility
        binding?.trimestreProfexceEntregaFinanciero?.visibility = visibility
        binding?.trimestreProfexcePublicacionInforme?.visibility = visibility
    }


    data class Titulos(
        val boton1: Pair<String, (View) -> Unit>,
        val boton2: Pair<String, (View) -> Unit>,
        val boton3: Pair<String, (View) -> Unit>,
    )
}