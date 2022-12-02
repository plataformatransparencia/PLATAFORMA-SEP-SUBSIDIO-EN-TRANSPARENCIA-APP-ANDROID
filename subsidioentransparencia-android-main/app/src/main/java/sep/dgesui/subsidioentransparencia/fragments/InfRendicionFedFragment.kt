package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_inf_rendicion_fed.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.getColorCumplimiento


class InfRendicionFedFragment(
    val listRendicion: ArrayList<String>,
    val id: String,
    val year: String,
    val uni: String
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inf_rendicion_fed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        YearRend.text = year.substring(0, 4)
        titleUni.text = uni
        buttonBackTableroTR.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val colorCumplimientoTrimestreR = getColorCumplimiento(listRendicion[1], context)
        cardViewcumplimientoTrimestreR.setCardBackgroundColor(colorCumplimientoTrimestreR)

        when (listRendicion[1]) {
            "Cumplió" -> textInfRendicionTrimestralCumplio.text = "Cumplió"
            "No cumplió" -> textInfRendicionTrimestralCumplio.text = "No Cumplió"
            "En revisión" -> textInfRendicionTrimestralCumplio.text = "En Revisión"
            else -> {
                textInfRendicionTrimestralCumplio.text = "No Cumplió"
            }
        }

        visibleObservacionesTrimestreR.isVisible = false
        if (listRendicion[2] != "") {
            visibleObservacionesTrimestreR.isVisible = true
            textObservacionTrimestreR.text = listRendicion[2]
        }
    }
}