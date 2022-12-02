package sep.dgesui.subsidioentransparencia.components

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.component_trimestre.view.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.fragments.loadFragment
import sep.dgesui.subsidioentransparencia.getColorCumplimiento
import timber.log.Timber

class TrimestreComponent @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, styleSelector: Int = 0
) : ConstraintLayout(context, attributeSet, styleSelector) {
    private var expanded = false
    private var showReportLinks = true

    init {
        LayoutInflater.from(context).inflate(R.layout.component_trimestre, this)

        trimestreTitulo.setOnClickListener { toggle() }
    }


    var trimestre: Int = 0
        set(value) {
            field = value
            trimestreTitulo.text = String.format(context.getString(R.string.trimestreN), value)
        }

    var montos: TrimestreInfo? = null
        set(value) {
            field = value


            if (value != null) {
                trimestreMes1.setup(value.mes1.first, value.mes1.second)
                trimestreMes2.setup(value.mes2.first, value.mes2.second)
                trimestreMes3.setup(value.mes3.first, value.mes3.second)
            }
        }

    fun semaforo(posicion: Mes, cumplimiento: String) {
        when (posicion) {
            Mes.PRIMERO -> trimestreMes1.color = getColorCumplimiento(cumplimiento, context)
            Mes.SEGUNDO -> trimestreMes2.color = getColorCumplimiento(cumplimiento, context)
            Mes.TERCERO -> trimestreMes3.color = getColorCumplimiento(cumplimiento, context)
        }
    }

    private fun toggle() {
        expanded = !expanded

        val visibility = if (expanded) View.VISIBLE else View.GONE

        trimestreMes1.visibility = visibility
        trimestreMes2.visibility = visibility
        trimestreMes3.visibility = visibility

        if (showReportLinks) {
            linkReporteSubsidioOrdinario.visibility = visibility
            linkReporteRendicionCuentas.visibility = visibility


            if (visibility == View.GONE)
                linkReporteMatricula.visibility = visibility
            else if (visibility == View.VISIBLE && linkReporteMatricula.hasTarget())
                linkReporteMatricula.visibility = visibility
        } else {
            linkReporteSubsidioOrdinario.visibility = View.GONE
            linkReporteRendicionCuentas.visibility = View.GONE
            linkReporteMatricula.visibility = View.GONE
        }

    }

    fun setMesesTargets(
        mes1Target: OnClickListener,
        mes2Target: OnClickListener,
        mes3Target: OnClickListener,
    ) {
        trimestreMes1.setTarget(mes1Target)
        trimestreMes2.setTarget(mes2Target)
        trimestreMes3.setTarget(mes3Target)
    }

    fun setTargets(
        targetSubsidioOrdinario: Fragment,
        targetRendicionCuentas: Fragment,
        targetReporteMatricula: Fragment? = null,
        activity: FragmentActivity
    ) {

        linkReporteSubsidioOrdinario.setTarget(loadFragment(targetSubsidioOrdinario, activity))
        linkReporteRendicionCuentas.setTarget(loadFragment(targetRendicionCuentas, activity))

        Timber.d("Setting reporte matricula to $targetReporteMatricula")

        if (targetReporteMatricula != null)
            linkReporteMatricula.setTarget(loadFragment(targetReporteMatricula, activity))

    }

    fun hideLinks() {
        showReportLinks = false
    }

    enum class Mes {
        PRIMERO, SEGUNDO, TERCERO
    }

    data class TrimestreInfo(
        val mes1: Pair<String, String> = "" to "",
        val mes2: Pair<String, String> = "" to "",
        val mes3: Pair<String, String> = "" to ""
    )
}