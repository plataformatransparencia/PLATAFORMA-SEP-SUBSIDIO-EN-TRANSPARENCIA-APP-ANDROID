package sep.dgesui.subsidioentransparencia.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_detalle_reporte.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper

class DetalleReporteFragment(
    private val informacion: InformacionGeneralWrapper,
    private val titulo:String,
    private val item: Item
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_detalle_reporte, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detalleReporteTitulo.text = titulo

        detalleReporteBack.setOnClickListener { requireActivity().onBackPressed() }

        detalleReporteSemaforo.showData(item.cumplimiento, informacion.year)


        if (item.observacion.isNotEmpty()) {
            detalleReporteObservaciones.text = item.observacion
        } else {
            detalleReporteObservaciones.visibility = View.GONE
            detalleReporteTituloObservaciones.visibility = View.GONE
        }

    }
}