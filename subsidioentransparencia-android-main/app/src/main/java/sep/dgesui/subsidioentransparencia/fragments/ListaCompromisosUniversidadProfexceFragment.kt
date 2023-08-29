package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_lista_compromisos_universidad_profexce.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.currencyFormatter
import sep.dgesui.subsidioentransparencia.tableroext.profexce.compromisos.TablaMontoUniversidad

class ListaCompromisosUniversidadProfexceFragment(
    private val informacion: InformacionGeneralWrapper,
    private val compromisos: List<Item>,
    private val tablaMonto: TablaMontoUniversidad,
) :
    Fragment() {

    private val targetFactory =
        { item: Item -> DetalleCompromisoEstadoProfexceFragment(informacion, item) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_lista_compromisos_universidad_profexce, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detalleUniversidadProfexceHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        detalleUniversidadProfexceBackButton.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }


        compromisosUniversidadRecycler.adapter =
            ItemListCardRecyclerAdapter(
                compromisos,
                requireActivity(),
                targetFactory
            )

        val fragmentObservaciones = observaciones
        tablaMonto.apply {

            montoAsignado.text = currencyFormatter.format(monto)
            ministracionEstado.text = fechaDepEst
            ministracionUniversidad.text = fechaDepUni

            fragmentObservaciones.text = observaciones


        }
    }
}