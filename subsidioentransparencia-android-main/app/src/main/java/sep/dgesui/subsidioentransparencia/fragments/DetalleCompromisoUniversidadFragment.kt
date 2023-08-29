package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detalle_compromiso_universidad.*
import kotlinx.android.synthetic.main.fragment_detalle_compromiso_universidad.view.*
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.android.synthetic.main.layout_compromiso_card.view.*
import kotlinx.android.synthetic.main.layout_cumplimiento_card.view.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sep.dgesui.subsidioentransparencia.ListaAccionesEtapas2018
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper

open class DetalleCompromisoUniversidadFragment(
    private val informacion: InformacionGeneralWrapper,
    private val item: Item,
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_detalle_compromiso_universidad, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        detalleHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        detalleBackButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        detalleDescripcion.text = item.descripcion
        compromiso_card.setValues(item.cumplimiento, item.fechaCompromiso)
        detalleObservaciones.text = item.observacion
        /*Se agrega condicion para cambiar el "Cumplió por Cumple" y "No cumplió por No cumple"
        para los compromisos del subsidio ordinario unicamente, para cambiar todos de todos los subsidios
        cambiar nombres en archivo "layout_cumplimiento_car.xml*/
        if(informacion.subsidio == "subsidio_ordinario"){
            compromiso_card.cardKeys.cplio.setText(getString(R.string.label_cumple))
            compromiso_card.cardKeys.ncplio.setText(getString(R.string.label_no_cumple))
        }


        if (item.imagen.isNotBlank()) {

            runBlocking {
                view.imagen.visibility = View.VISIBLE
                launch {
                    Picasso
                        .get().load("https://dgesui.ses.sep.gob.mx${item.imagen}")
                        .into(view.imagen)
                }
            }

        }

        if (item.subacciones != null && item.subacciones.isNotEmpty()) {
            buttonEtapas.visibility = View.VISIBLE
            compromiso_card.visibility = View.GONE

            buttonEtapas.setTarget(
                loadFragment(
                    ListaAccionesEtapas2018(informacion, item.subacciones),
                    requireActivity()
                )
            )
        }

        if(item.porcentajeIncremento != null){
            compromiso_card.visibility = View.GONE
            detalleObservaciones.visibility = View.GONE
            detalleSubtitleObservaviones.visibility = View.GONE
            porcentajeIncrementoDetalle.visibility = View.VISIBLE
            val porcentaje = item.porcentajeIncremento
            porcentajeIncrementoDetalle.text = "$porcentaje % Incremento"
        }
    }
}


class DetalleAccionUniversidadFragment(informacion: InformacionGeneralWrapper, item: Item) :
    DetalleCompromisoUniversidadFragment(informacion, item) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lblAccion = requireContext().getString(R.string.accion)

        detalleTitle.text = lblAccion
        subtituloDetalle.text = lblAccion

    }
}
