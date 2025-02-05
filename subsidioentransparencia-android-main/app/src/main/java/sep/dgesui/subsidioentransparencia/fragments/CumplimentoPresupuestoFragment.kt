package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.databinding.FragmentItemListBinding
import sep.dgesui.subsidioentransparencia.tableroext.presupuesto.tablero.*

class CumplimentoPresupuestoFragment(
    protected val informacion: InformacionGeneralWrapper,
    protected val tablero: TableroCumplimientoPresupuesto
    ) : Fragment() {

    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!
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
        //loadData()
        binding.listHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        binding.listTitle.text = requireContext().getString(R.string.tablero_cumplimiento)

        binding.listBackButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        val itemsInformes = tablero.Informes.map {
            it.toItem()
        }
        binding.buttonInformes.visibility = View.VISIBLE
        binding.buttonInformes.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.main_fragment_container,
                ListaCumplimientoPresupuestoFragment(informacion,itemsInformes,R.string.seguimiento_entrega)
            ).addToBackStack(null).commit()
        }
        val itemsSeguimiento = tablero.Seguimiento_de_entrega.map {
            it.toItem()
        }
        binding.buttonSeguimientoEntregas.visibility = View.VISIBLE
        binding.buttonSeguimientoEntregas.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.main_fragment_container,
                ListaCumplimientoPresupuestoFragment(informacion,itemsSeguimiento,R.string.seguimiento_entrega)
            ).addToBackStack(null).commit()
        }
        val itemsTESOFE = tablero.Reintegro_TESOFE.map {
            it.toItem()
        }
        binding.buttonTESOFE.visibility = View.VISIBLE
        binding.buttonTESOFE.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.main_fragment_container,
                ListaCumplimientoPresupuestoFragment(informacion,itemsTESOFE,R.string.seguimiento_entrega)
            ).addToBackStack(null).commit()
        }
        val itemsCierreCuenta = ArrayList<ItemPresupuesto>()
        itemsCierreCuenta.add(tablero.Cierre_productiva.toItem())
        binding.buttonCierreCuenta.visibility = View.VISIBLE
        binding.buttonCierreCuenta.setOnClickListener {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}