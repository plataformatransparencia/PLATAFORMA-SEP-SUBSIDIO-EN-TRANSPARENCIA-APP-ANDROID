package sep.dgesui.subsidioentransparencia.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.component_trimestre_profexce.view.*
import sep.dgesui.subsidioentransparencia.R

class ComponenteTrimestreProfexce @JvmOverloads
constructor(context: Context, attributeSet: AttributeSet? = null, styleSelector: Int = 0) :
    ConstraintLayout(context, attributeSet, styleSelector) {

    private var expanded = false

    init {
        LayoutInflater.from(context)
            .inflate(R.layout.component_trimestre_profexce, this)

        setOnClickListener { toggle() }
    }

    var trimestre: Int = 0
        set(value) {
            field = value

            trimestreProfexceTitulo.text =
                String.format(context.getString(R.string.trimestreN), value)
        }

    var titulos: Titulos? = null
        set(value) {
            field = value

            if (value != null) {
                trimestreProfexceEntregaAcademico.setLabel(value.boton1.first)
                trimestreProfexceEntregaAcademico.setTarget(value.boton1.second)

                trimestreProfexceEntregaFinanciero.setLabel(value.boton2.first)
                trimestreProfexceEntregaFinanciero.setTarget(value.boton2.second)

                trimestreProfexcePublicacionInforme.setLabel(value.boton3.first)
                trimestreProfexcePublicacionInforme.setTarget(value.boton3.second)

            }
        }


    private fun toggle() {
        expanded = !expanded

        val visibility = if (expanded) View.VISIBLE else View.GONE

        trimestreProfexceEntregaAcademico.visibility = visibility
        trimestreProfexceEntregaFinanciero.visibility = visibility
        trimestreProfexcePublicacionInforme.visibility = visibility
    }


    data class Titulos(
        val boton1: Pair<String, (View) -> Unit>,
        val boton2: Pair<String, (View) -> Unit>,
        val boton3: Pair<String, (View) -> Unit>,
    )
}