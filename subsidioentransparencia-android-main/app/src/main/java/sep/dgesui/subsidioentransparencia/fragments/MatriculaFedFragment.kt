package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_matricula_fed.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.getColorCumplimiento

class MatriculaFedFragment(
    val listMatricula: ArrayList<String>,
    val id: String,
    val uni: String,
    val year: String
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matricula_fed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        YearMatricula.text = year.substring(0, 4)
        titleUniMat.text = uni
        buttonBackMatricula.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val colorCumplimientoMatricula = getColorCumplimiento(listMatricula[1], context)
        cardViewcumplimientoMatricula.setCardBackgroundColor(colorCumplimientoMatricula)

        when (listMatricula[1]) {
            "Cumplió" -> textMatriculaTrimestralCumplio.text = "Cumplió"
            "No cumplió" -> textMatriculaTrimestralCumplio.text = "No Cumplió"
            "En revisión" -> textMatriculaTrimestralCumplio.text = "En Revisión"
            else -> {
                textMatriculaTrimestralCumplio.text = "No Cumplió"
            }
        }

        visibleObservacionesMatricula.isVisible = false
        if (!listMatricula[2].isEmpty()) {
            visibleObservacionesMatricula.isVisible = true
            textObservacionMatricula.text = listMatricula[2]
        }
    }


}