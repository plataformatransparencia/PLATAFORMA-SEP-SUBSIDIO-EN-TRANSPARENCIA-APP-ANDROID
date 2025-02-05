package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.databinding.FragmentItemListBinding

open class ListaCompromisosUniversidadSimplifiedFragment(
    protected val informacion: InformacionGeneralWrapper,
    protected val compromisos: List<Item>,
) : Fragment() {

    private val targetFactory =
        { item: Item -> DetalleCompromisoUniversidadFragment(informacion, item) }

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
    open fun loadData() {
        binding.listHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        binding.listTitle.text = requireContext().getString(R.string.compromisos)

        binding.listBackButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.listItemsRecycler.adapter =
            ItemListCardRecyclerAdapter(
                compromisos,
                requireActivity(),
                targetFactory
            )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadData()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}