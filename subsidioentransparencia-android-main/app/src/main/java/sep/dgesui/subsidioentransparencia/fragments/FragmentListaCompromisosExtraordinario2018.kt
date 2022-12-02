package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.compromisos_extraordinarios_fragment.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.engineadapter.ReferenciasAdapter

class FragmentListaCompromisosExtraordinario2018(
    private val informacion: InformacionGeneralWrapper,
    private val compromisos: List<Item>,
    private val actividades: List<ComplexItem>,
    private val entregas: List<ComplexItem>,
    private val referencias: Map<String, String>?,
    private val notaCompromiso: String?,
) : Fragment() {
    private val simpleItemTargetFactory =
        { item: Item -> DetalleCompromisoUniversidadFragment(informacion, item) }

    private val accionesTargeFactory =
        { complexItem: ComplexItem ->
            DetalleComplexItemFragment(
                informacion,
                "Acción",
                complexItem
            )
        }

    private val entregasTargeFactory =
        { complexItem: ComplexItem ->
            DetalleComplexItemFragment(
                informacion,
                "Entrega",
                complexItem
            )
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.compromisos_extraordinarios_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lista_compromisos_header.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        listaCompromisosBack.setOnClickListener { requireActivity().onBackPressed() }

        listaCompromisosCompromisos.adapter =
            ItemListCardRecyclerAdapter(
                compromisos,
                requireActivity(),
                simpleItemTargetFactory
            )

        listaCompromisosActividades.adapter =
            ComplexItemListRecyclerAdapter(
                actividades,
                requireActivity(),
                accionesTargeFactory
            )

        listaCompromisosEntregas.adapter = ComplexItemListRecyclerAdapter(
            entregas,
            requireActivity(),
            entregasTargeFactory
        )


        listaCompromisosReferencias.adapter = ReferenciasAdapter(referencias ?: emptyMap())

        listaCompromisosNota.text = notaCompromiso
    }
}

