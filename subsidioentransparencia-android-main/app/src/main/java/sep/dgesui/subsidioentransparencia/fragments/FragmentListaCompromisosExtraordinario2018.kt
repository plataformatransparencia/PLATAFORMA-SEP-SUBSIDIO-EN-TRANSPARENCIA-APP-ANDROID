package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.databinding.CompromisosExtraordinariosFragmentBinding
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
    private var _binding: CompromisosExtraordinariosFragmentBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CompromisosExtraordinariosFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.listaCompromisosHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        binding.listaCompromisosBack.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }

        binding.listaCompromisosCompromisos.adapter =
            ItemListCardRecyclerAdapter(
                compromisos,
                requireActivity(),
                simpleItemTargetFactory
            )

        binding.listaCompromisosActividades.adapter =
            ComplexItemListRecyclerAdapter(
                actividades,
                requireActivity(),
                accionesTargeFactory
            )

        binding.listaCompromisosEntregas.adapter = ComplexItemListRecyclerAdapter(
            entregas,
            requireActivity(),
            entregasTargeFactory
        )


        binding.listaCompromisosReferencias.adapter = ReferenciasAdapter(referencias ?: emptyMap())

        binding.listaCompromisosNota.text = notaCompromiso
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

