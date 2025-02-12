package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import sep.dgesui.subsidioentransparencia.ListaAccionesEtapas2018
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.databinding.FragmentDetalleCompromisoUniversidadBinding
import sep.dgesui.subsidioentransparencia.databinding.LayoutCompromisoCardBinding
import sep.dgesui.subsidioentransparencia.databinding.LayoutCumplimientoCardBinding

open class DetalleCompromisoUniversidadFragment(
    private val informacion: InformacionGeneralWrapper,
    private val item: Item,
    private val titulo: String = ""
) : Fragment() {

    private var _binding: FragmentDetalleCompromisoUniversidadBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetalleCompromisoUniversidadBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.detalleTitle.text = titulo
        if (titulo == "Compromisos"){
            binding.subtituloDetalle.text = titulo
        }else{
            binding.subtituloDetalle.text = "Informe"
            binding.compromisoCard.showExtra()
        }

        binding.detalleHeader.setValues(
            informacion.nombreUniversidad,
            informacion.subsidio,
            informacion.year,
            requireContext()
        )

        binding.detalleBackButton.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        binding.detalleDescripcion.text = HtmlCompat.fromHtml(item.descripcion, HtmlCompat.FROM_HTML_MODE_COMPACT)
        binding.compromisoCard.setValues(item.cumplimiento, item.fechaCompromiso)
        binding.detalleObservaciones.text = item.observacion
        /*Se agrega condicion para cambiar el "Cumplió por Cumple" y "No cumplió por No cumple"
        para los compromisos del subsidio ordinario unicamente, para cambiar todos de todos los subsidios
        cambiar nombres en archivo "layout_cumplimiento_car.xml*/
        if(informacion.subsidio == "subsidio_ordinario"){
            val bnc = LayoutCumplimientoCardBinding.bind(view)
            bnc.cplio.text = getString(R.string.label_cumple)
            bnc.ncplio.text = getString(R.string.label_no_cumple)
        }


        if (item.imagen.isNotBlank()) {

            runBlocking {
                binding.imagen.visibility = View.VISIBLE
                launch {
                    Picasso
                        .get().load("https://dgesui.ses.sep.gob.mx${item.imagen}")
                        .into(binding.imagen)
                }
            }

        }

        if (!item.subacciones.isNullOrEmpty()) {
            binding.buttonEtapas.visibility = View.VISIBLE
            binding.compromisoCard.visibility = View.GONE

            binding.buttonEtapas.setTarget(
                loadFragment(
                    ListaAccionesEtapas2018(informacion, item.subacciones),
                    requireActivity()
                )
            )
        }

        if(item.porcentajeIncremento != null){
            binding.compromisoCard.visibility = View.GONE
            binding.detalleObservaciones.visibility = View.GONE
            binding.detalleSubtitleObservaviones.visibility = View.GONE
            binding.porcentajeIncrementoDetalle.visibility = View.VISIBLE
            val porcentaje = item.porcentajeIncremento
            binding.porcentajeIncrementoDetalle.text = "$porcentaje % Incremento"
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


class DetalleAccionUniversidadFragment(informacion: InformacionGeneralWrapper, item: Item) :
    DetalleCompromisoUniversidadFragment(informacion, item) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val lblAccion = requireContext().getString(R.string.accion)

        binding.detalleTitle.text = lblAccion
        binding.subtituloDetalle.text = lblAccion

    }
}
