package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_detalle_compromiso_estado_profexce.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper

open class DetalleCompromisoEstadoProfexceFragment(
    private val informacion: InformacionGeneralWrapper,
    private val item: Item
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_detalle_compromiso_estado_profexce, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        detalleEstadoProfexceHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        detalleEstadoProfexceBackButton.setOnClickListener { requireActivity().onBackPressed() }

        detalleEstadoProfexceDescripcion.text = item.descripcion
        detalleEstadoProfexceFechaEntrega.text = item.fechaCompromiso
        detalleEstadoProfexceObservaciones.text = item.observacion
    }
}