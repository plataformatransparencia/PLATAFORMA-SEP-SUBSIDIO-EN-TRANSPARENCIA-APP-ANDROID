package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_item_list.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.engineadapter.CumplimientoRepository
import sep.dgesui.subsidioentransparencia.tableroext.presupuesto.tablero.DetalleCumplimientoPresupuesto
import sep.dgesui.subsidioentransparencia.tableroext.presupuesto.tablero.Entrega
import sep.dgesui.subsidioentransparencia.tableroext.profexce.tablero.DetalleCumplimiento
import sep.dgesui.subsidioentransparencia.tableroext.profexce.tablero.TableroCumplimiento

open class ListaCumplimientoPresupuestoFragment(
    protected val informacion: InformacionGeneralWrapper,
   protected val tablero: List<ItemPresupuesto>,
    protected val titulo: Int
) : Fragment() {

    private val targetFactory =
        { item: ItemPresupuesto -> DetalleCumplimientoPresupuestoFragment(informacion, item) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_item_list, container, false)

    open fun loadData() {

        listHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        listTitle.text = requireContext().getString(titulo)

        listBackButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }


        listItemsRecycler.adapter =
            ItemListCumplimientoPresupuestoRecyclerAdapter(
                tablero,
                requireActivity(),
                targetFactory
            )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadData()
    }

}