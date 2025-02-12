package sep.dgesui.subsidioentransparencia

import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.fragments.DetalleEtapa2018
import sep.dgesui.subsidioentransparencia.fragments.Item
import sep.dgesui.subsidioentransparencia.fragments.ItemListCardRecyclerAdapter
import sep.dgesui.subsidioentransparencia.fragments.ListaCompromisosUniversidadSimplifiedFragment

class ListaAccionesEtapas2018(
    informacion: InformacionGeneralWrapper,
    acciones: List<Item>,
) : ListaCompromisosUniversidadSimplifiedFragment(informacion, acciones) {


    private val targetFactory = { item: Item ->
        DetalleEtapa2018(informacion, item)
    }

    override fun loadData() {
        binding.listHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        binding.listTitle.text = requireContext().getString(R.string.acciones_por_emprender)

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
}