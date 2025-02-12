package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.currencyFormatter
import sep.dgesui.subsidioentransparencia.databinding.FragmentListaCompromisosUniversidadProfexceBinding
import sep.dgesui.subsidioentransparencia.tableroext.profexce.compromisos.TablaMontoUniversidad

class ListaCompromisosUniversidadProfexceFragment(
    private val informacion: InformacionGeneralWrapper,
    private val compromisos: List<Item>,
    private val tablaMonto: TablaMontoUniversidad,
) :
    Fragment() {

    private val targetFactory =
        { item: Item -> DetalleCompromisoEstadoProfexceFragment(informacion, item) }
    private var _binding: FragmentListaCompromisosUniversidadProfexceBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaCompromisosUniversidadProfexceBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.detalleUniversidadProfexceHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        binding.detalleUniversidadProfexceBackButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }


        binding.compromisosUniversidadRecycler.adapter =
            ItemListCardRecyclerAdapter(
                compromisos,
                requireActivity(),
                targetFactory
            )

        val fragmentObservaciones = binding.observaciones
        tablaMonto.apply {

            binding.montoAsignado.text = currencyFormatter.format(monto)
            binding.ministracionEstado.text = fechaDepEst
            binding.ministracionUniversidad.text = fechaDepUni

            fragmentObservaciones.text = observaciones


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}