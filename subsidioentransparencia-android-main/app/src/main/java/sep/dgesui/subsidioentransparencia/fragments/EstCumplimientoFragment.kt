package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_est_cumplimiento.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.getColorCumplimiento


class EstCumplimientoFragment(
    val listEstCumplimiento: ArrayList<String>,
    val id: String,
    val year: String,
    val nombre: String
) : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_est_cumplimiento, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        YearMinEstCumpl.text = year.substring(0, 4)
        titleUni.text = nombre
        titleMesEst.text = listEstCumplimiento[0]

        buttonBackMesEst.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        val colorCumplimientoMesEst = getColorCumplimiento(listEstCumplimiento[1], context)
        cardViewcumplimientoMesEst.setCardBackgroundColor(colorCumplimientoMesEst)

        fechaCumplimientoMesEst.text = listEstCumplimiento[2]

        if (!listEstCumplimiento[3].isEmpty()) {
            visibleObservacionesMesEst.isVisible = true
            textObservacionMesEst.text = listEstCumplimiento[3]
        } else {
            visibleObservacionesMesEst.isVisible = false
            visibleObservacionesMesEst.isVisible = false
        }

    }


}