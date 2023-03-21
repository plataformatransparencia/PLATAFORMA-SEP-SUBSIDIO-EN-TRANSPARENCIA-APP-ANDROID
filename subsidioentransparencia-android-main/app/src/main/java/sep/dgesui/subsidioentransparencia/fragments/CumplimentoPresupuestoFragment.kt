package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_item_list.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.tableroext.presupuesto.tablero.*
import kotlin.math.log

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //loadData()
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
        buttonInformes.visibility = View.VISIBLE
        buttonInformes.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.main_fragment_container,
                ListaCumplimientoPresupuestoFragment(informacion,itemsInformes,R.string.seguimiento_entrega)
            ).addToBackStack(null).commit()
        }
        val itemsSeguimiento = tablero.Seguimiento_de_entrega.map {
            it.toItem()
        }
        buttonSeguimientoEntregas.visibility = View.VISIBLE
        buttonSeguimientoEntregas.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.main_fragment_container,
                ListaCumplimientoPresupuestoFragment(informacion,itemsSeguimiento,R.string.seguimiento_entrega)
            ).addToBackStack(null).commit()
        }
        val itemsTESOFE = tablero.Reintegro_TESOFE.map {
            it.toItem()
        }
        buttonTESOFE.visibility = View.VISIBLE
        buttonTESOFE.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.main_fragment_container,
                ListaCumplimientoPresupuestoFragment(informacion,itemsTESOFE,R.string.seguimiento_entrega)
            ).addToBackStack(null).commit()
        }
        val itemsCierreCuenta = ArrayList<ItemPresupuesto>()
        itemsCierreCuenta.add(tablero.Cierre_productiva.toItem())
        buttonCierreCuenta.visibility = View.VISIBLE
        buttonCierreCuenta.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.main_fragment_container,
                ListaCumplimientoPresupuestoFragment(informacion,itemsCierreCuenta,R.string.seguimiento_entrega)
            ).addToBackStack(null).commit()
        }
    }

    private fun Entrega.toItem(): ItemPresupuesto = ItemPresupuesto(
        descripcion = mes,
        fechaCompromiso = fechaLimite,
        fechaEjecucion = fechaEntrega,
        cumplimiento = cumplimiento,
        observacion = observacion,
        tipo = "Fecha de entrega"
    )

    private fun DetalleCumplimientoPresupuesto.toItem(): ItemPresupuesto = ItemPresupuesto(
        descripcion = descripcion,
        fechaCompromiso = fechaLimite,
        fechaEjecucion = fechaEntrega,
        cumplimiento = cumplimiento,
        observacion = observacion,
        tipo = "Fecha de entrega"
    )

    private fun Cierre.toItem(): ItemPresupuesto = ItemPresupuesto(
        descripcion = accion,
        fechaCompromiso = fechaLimite,
        fechaEjecucion = fechaComprobacion,
        cumplimiento = cumplimiento,
        observacion = observacion,
        tipo = "Fecha de comprobación"
    )

    private fun TESOFE.toItem(): ItemPresupuesto = ItemPresupuesto(
        descripcion = descripcion,
        fechaCompromiso = fechaLimite,
        fechaEjecucion = fechaReintegro,
        cumplimiento = cumplimiento,
        observacion = observacion,
        tipo =  "Fecha de reintegro"
    )


}