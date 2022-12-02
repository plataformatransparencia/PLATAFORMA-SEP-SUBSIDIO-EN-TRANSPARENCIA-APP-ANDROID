package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_item_list.*
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.engineadapter.ReferenciasAdapter

class FragmentAccionesEmprendidasExtraordinario2018(
    private val informacion: InformacionGeneralWrapper,
    private val acciones: List<Item>?,
    private val referencias: Map<String, String>?,
) : Fragment() {
    private val targetFactory =
        { item: Item -> DetalleAccionUniversidadFragment(informacion, item) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_item_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        runBlocking {

            listHeader.setValues(
                informacion.nombreUniversidad,
                informacion.subsidio,
                informacion.year,
                requireContext()
            )

            listBackButton.setOnClickListener { requireActivity().onBackPressed() }

            listTitle.text = requireContext().getString(R.string.accion)

            coroutineScope {
                listItemsRecycler.adapter =
                    ItemListCardRecyclerAdapter(
                        acciones ?: emptyList(),
                        requireActivity(),
                        targetFactory
                    )

                if (referencias != null) {
                    title_referencias.visibility = View.VISIBLE

                    listReferenciasRecycler.visibility = View.VISIBLE
                    listReferenciasRecycler.adapter = ReferenciasAdapter(referencias)
                }
            }

        }
    }
}