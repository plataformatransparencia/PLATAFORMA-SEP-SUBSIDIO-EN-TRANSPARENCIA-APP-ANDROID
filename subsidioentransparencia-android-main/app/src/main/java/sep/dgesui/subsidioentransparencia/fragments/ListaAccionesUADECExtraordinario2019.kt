package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.databinding.FragmentItemListBinding

class ListaAccionesUADECExtraordinario2019(
    private val tituloLista: String,
    private val informacion: InformacionGeneralWrapper,
    private val acciones: List<Item>
) : Fragment() {
    private var _binding: FragmentItemListBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    private val targetFactory = { item: Item ->
        DetalleAccionUniversidadFragment(informacion, item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.listHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        binding.listTitle.text = tituloLista

        binding.listBackButton.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }

        binding.listItemsRecycler.adapter =
            ItemListCardRecyclerAdapter(acciones, requireActivity(), targetFactory)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}