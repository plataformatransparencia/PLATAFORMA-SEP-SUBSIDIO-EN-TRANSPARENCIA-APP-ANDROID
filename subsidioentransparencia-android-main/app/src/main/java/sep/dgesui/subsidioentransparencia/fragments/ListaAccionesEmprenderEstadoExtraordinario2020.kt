package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.databinding.FragmentItemListBinding
import sep.dgesui.subsidioentransparencia.engineadapter.ReferenciasAdapter
import timber.log.Timber

class ListaAccionesEmprenderEstadoExtraordinario2020(
    private val informacion: InformacionGeneralWrapper,
    private val acciones: List<Item>?,
    private val referencias: Map<String, String>?,
) : Fragment() {

    private val targetFactory = { item: Item ->
        DetalleAccionUniversidadFragment(informacion, item)
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        binding.listHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        binding.listTitle.text = requireContext().getString(R.string.acciones_por_emprender_estado)

        binding.listBackButton.setOnClickListener { requireActivity().onBackPressedDispatcher.onBackPressed() }

        binding.listItemsRecycler.adapter =
            ItemListCardRecyclerAdapter(acciones ?: emptyList(), requireActivity(), targetFactory)

        if (referencias != null) {
            Timber.d("Adjusting padding")
            binding.listItemsRecycler.setPaddingRelative(0, 0, 0, 0)

            binding.titleReferencias.visibility = View.VISIBLE
            binding.listReferenciasRecycler.visibility = View.VISIBLE
            binding.listReferenciasRecycler.adapter = ReferenciasAdapter(referencias)

        }
    }

}
