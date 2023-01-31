package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_item_list.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.tableroext.presupuesto.tablero.*

class CumplimentoPresupuestoFragment(
    protected val informacion: InformacionGeneralWrapper,
    protected val tablero: TableroCumplimientoPresupuesto
    ) : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_item_list, container, false)
    }

    open fun loadData() {

        listHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        listTitle.text = requireContext().getString(R.string.tablero_cumplimiento)

        listBackButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val itemsInformes = tablero.Informes.map {
            it.toItem()
        }

        buttonInformes.apply {
            visibility = View.VISIBLE
            setTarget(
                loadFragment( ListaCumplimientoPresupuestoFragment(informacion,itemsInformes,R.string.seguimiento_entrega), requireActivity()
                )
            )

        }

        val itemsSeguimiento = tablero.Seguimiento_de_entrega.map {
            it.toItem()
        }

        buttonSeguimientoEntregas.apply {
            visibility = View.VISIBLE

            setTarget(
                loadFragment( ListaCumplimientoPresupuestoFragment(informacion,itemsSeguimiento,R.string.seguimiento_entrega), requireActivity()
                )
            )
        }

        val itemsTESOFE = tablero.Reintegro_TESOFE.map {
            it.toItem()
        }


        buttonTESOFE.apply {
            visibility = View.VISIBLE
            setTarget(
                loadFragment( ListaCumplimientoPresupuestoFragment(informacion,itemsTESOFE,R.string.seguimiento_entrega), requireActivity()
                )
            )

        }

        val itemsCierreCuenta = ArrayList<ItemPresupuesto>()
        itemsCierreCuenta.add(tablero.Cierre_productiva.toItem())

        buttonCierreCuenta.apply {
            visibility = View.VISIBLE
            setTarget(
                loadFragment( ListaCumplimientoPresupuestoFragment(informacion,itemsCierreCuenta,R.string.seguimiento_entrega), requireActivity()
                )
            )

        }

    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        loadData()
    }

    private fun Entrega.toItem(): ItemPresupuesto = ItemPresupuesto(
        descripcion = mes,
        fechaCompromiso = fechaLimite,
        fechaEjecucion = fechaEntrega,
        cumplimiento = cumplimiento,
        observacion = observacion
    )

    private fun DetalleCumplimientoPresupuesto.toItem(): ItemPresupuesto = ItemPresupuesto(
        descripcion = descripcion,
        fechaCompromiso = fechaLimite,
        fechaEjecucion = fechaEntrega,
        cumplimiento = cumplimiento,
        observacion = observacion
    )

    private fun Cierre.toItem(): ItemPresupuesto = ItemPresupuesto(
        descripcion = accion,
        fechaCompromiso = fechaLimite,
        fechaEjecucion = fechaComprobacion,
        cumplimiento = cumplimiento,
        observacion = observacion
    )

    private fun TESOFE.toItem(): ItemPresupuesto = ItemPresupuesto(
        descripcion = descripcion,
        fechaCompromiso = fechaLimite,
        fechaEjecucion = fechaReintegro,
        cumplimiento = cumplimiento,
        observacion = observacion
    )


}