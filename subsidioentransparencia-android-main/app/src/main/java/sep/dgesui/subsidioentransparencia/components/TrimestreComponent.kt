package sep.dgesui.subsidioentransparencia.components

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.databinding.ComponentTrimestreBinding
import sep.dgesui.subsidioentransparencia.fragments.loadFragment
import sep.dgesui.subsidioentransparencia.getColorCumplimiento
import sep.dgesui.subsidioentransparencia.mesesTrimestre1
import kotlin.math.absoluteValue

class TrimestreComponent @JvmOverloads constructor(
    context: Context, attributeSet: AttributeSet? = null, styleSelector: Int = 0
) : ConstraintLayout(context, attributeSet, styleSelector) {
    private var expanded = false
    private var showReportLinks = true

    private var binding: ComponentTrimestreBinding? = null
    init {
        binding = rootView.let {
            ComponentTrimestreBinding.inflate(LayoutInflater.from(context),
                it as ViewGroup
            )
        }

        binding?.trimestreTitulo?.setOnClickListener { toggle() }
    }

    var trimestre: Int = 0
        set(value) {
            field = value
            binding?.trimestreTitulo?.text = String.format(context.getString(R.string.trimestreN), value)
        }

    var montos: TrimestreInfo? = null
        set(value) {
            field = value
            if (value != null) {
               binding?.trimestreMes1?.setup(value.mes1.first, value.mes1.second)
                binding?.trimestreMes2?.setup(value.mes2.first, value.mes2.second)
                binding?.trimestreMes3?.setup(value.mes3.first, value.mes3.second)
            }
        }

    fun semaforo(posicion: Mes, cumplimiento: String) {
        when (posicion) {
            Mes.PRIMERO -> binding?.trimestreMes1?.color = getColorCumplimiento(cumplimiento, context)
            Mes.SEGUNDO -> binding?.trimestreMes2?.color = getColorCumplimiento(cumplimiento, context)
            Mes.TERCERO -> binding?.trimestreMes3?.color = getColorCumplimiento(cumplimiento, context)
        }
    }

    private fun toggle() {

        expanded = !expanded
        val visibility = if (expanded) View.VISIBLE else View.GONE
        binding?.trimestreMes1?.visibility = visibility
        binding?.trimestreMes2?.visibility = visibility
        binding?.trimestreMes3?.visibility = visibility
        if (showReportLinks) {
            binding?.linkReporteSubsidioOrdinario?.visibility = visibility
            binding?.linkReporteRendicionCuentas?.visibility = visibility


            if (visibility == View.GONE)
                binding?.linkReporteMatricula?.visibility = visibility
            else if (visibility == View.VISIBLE && binding?.linkReporteMatricula!!.hasTarget())
                binding?.linkReporteMatricula?.visibility = visibility
        } else {
            binding?.linkReporteSubsidioOrdinario?.visibility = View.GONE
            binding?.linkReporteRendicionCuentas?.visibility = View.GONE
            binding?.linkReporteMatricula?.visibility = View.GONE
        }
        if (montos?.year!! >= "2025"){
            binding?.linkReporteSubsidioOrdinario?.visibility = View.GONE
            binding?.linkReporteRendicionCuentas?.visibility = View.GONE
            binding?.linkReporteMatricula?.visibility = View.GONE
        }

    }

    fun setMesesTargets(
        mes1Target: OnClickListener,
        mes2Target: OnClickListener,
        mes3Target: OnClickListener,
    ) {
        binding?.trimestreMes1?.setTarget(mes1Target)
        binding?.trimestreMes2?.setTarget(mes2Target)
        binding?.trimestreMes3?.setTarget(mes3Target)
    }

    fun setTargets(
        targetSubsidioOrdinario: Fragment,
        targetRendicionCuentas: Fragment,
        targetReporteMatricula: Fragment? = null,
        activity: FragmentActivity
    ) {
        binding?.linkReporteSubsidioOrdinario?.setTarget(loadFragment(targetSubsidioOrdinario, activity))
        binding?.linkReporteRendicionCuentas?.setTarget(loadFragment(targetRendicionCuentas, activity))

        if (targetReporteMatricula != null)
            binding?.linkReporteMatricula?.setTarget(loadFragment(targetReporteMatricula, activity))

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
        val mes3: Pair<String, String> = "" to "",
        val year: String = ""
    )
}