package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_fed_cumplimiento.*
import kotlinx.android.synthetic.main.fragment_fed_cumplimiento.view.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.getColorCumplimiento


class FedCumplimientoFragment(
    val listCompromisos: ArrayList<String>,
    val id: String,
    val year: String,
    val nombre: String
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fed_cumplimiento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        YearMinFedCumpl.text = year.substring(0, 4)
        titleUni.text = nombre
        titleMesFed.text = listCompromisos[6]
        buttonBackTablero.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val colorSepFed = getColorCumplimiento(listCompromisos[0], view.context)
        view.cardViewcumplimientoSEPFed.setCardBackgroundColor(colorSepFed)



        fechaCumplimientoSEPFed.text = listCompromisos[1]
        visibleObservacionesSEPFed.isVisible = false
        if (!listCompromisos[2].isEmpty()) {
            visibleObservacionesSEPFed.isVisible = true
            textObservacionSEPFed.text = listCompromisos[2]
        }

        val colorCumplimientoEstFed = getColorCumplimiento(listCompromisos[3], view.context)
        view.cardViewcumplimientoEstFed.setCardBackgroundColor(colorCumplimientoEstFed)

        fechaCumplimientoEstFed.text = listCompromisos[4]

        visibleObservacionesEstFed.isVisible = false
        if (!listCompromisos[5].isEmpty()) {
            visibleObservacionesEstFed.isVisible = true
            textObservacionEstFed.text = listCompromisos[5]
        }
    }


}