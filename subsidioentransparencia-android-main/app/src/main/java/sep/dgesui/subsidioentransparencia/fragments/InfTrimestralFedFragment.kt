package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_inf_trimestral_fed.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.getColorCumplimiento


class InfTrimestralFedFragment(
    val listSO: ArrayList<String>,
    val id: String,
    val year: String,
    val uni: String
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inf_trimestral_fed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        YearInfTrimestral.text = year.substring(0, 4)
        titleUni.text = uni
        buttonBackTableroTO.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        val colorTrimestreO = getColorCumplimiento(listSO[1], context)
        cardViewcumplimientoTrimestreO.setCardBackgroundColor(colorTrimestreO)


        when (listSO[1]) {
            "Cumplió" -> textInfTrimestralCumplio.text = "Cumplió"
            "No cumplió" -> textInfTrimestralCumplio.text = "No Cumplió"
            "En revisión" -> textInfTrimestralCumplio.text = "En Revisión"
            else -> {
                textInfTrimestralCumplio.text = "No Cumplió"
            }
        }

        visibleObservacionesTrimestreO.isVisible = false
        if (listSO[2] != "") {
            visibleObservacionesTrimestreO.isVisible = true
            textObservacionTrimestreO.text = listSO[2]
        }
    }
}