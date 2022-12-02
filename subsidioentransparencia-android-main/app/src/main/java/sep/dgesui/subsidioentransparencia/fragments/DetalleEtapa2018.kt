package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_detalle_entapa.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper

class DetalleEtapa2018(
    private val informacion: InformacionGeneralWrapper,
    private val item: Item,
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_detalle_entapa, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        etapaHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        etapaBackButton.setOnClickListener { requireActivity().onBackPressed() }

        etapaFechaEstipulada.text = item.fechaCompromiso

        etapaDescripcion.text = item.descripcion
        compromiso_card.setValues(item.cumplimiento, item.fechaEjecucion)

        etapaObservaciones.text = item.observacion

    }
}