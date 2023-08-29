package sep.dgesui.subsidioentransparencia.fragments


import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_detalle_ministracion_extraordinaria.*
import kotlinx.android.synthetic.main.fragment_detalle_ministracion_extraordinaria_transfers.*
import kotlinx.android.synthetic.main.fragment_programs.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.components.InformacionGeneralWrapper
import sep.dgesui.subsidioentransparencia.currencyFormatter
import sep.dgesui.subsidioentransparencia.tableroext.minest.Ministracion


class DetalleMinistracionExtraordinariaEstatal(

    private val informacin: InformacionGeneralWrapper,
    private val ministracion: List<Ministracion>
) : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_detalle_ministracion_extraordinaria_transfers, container, false)

    @SuppressLint("ResourceAsColor")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detalleMinistracionExtraordinariaHeaderTransfer.setValues(
            informacin.nombreUniversidad,
            informacin.subsidio,
            informacin.year,
            requireContext()
        )
        detalleMinistracionExtraordinariaBackTransfer.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        detalleMinistracionExtraordinariaTitleTransfer.text = currencyFormatter.format(ministracion[0].monto)

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        ministracion.forEach {


            val buttontransfer = Button(context)
            buttontransfer.text = currencyFormatter.format(it.estado_universidad)
            buttontransfer.layoutParams = params
            buttontransfer.gravity = Gravity.LEFT
            buttontransfer.setTextColor(Color.parseColor("#9D2449"))
            buttontransfer.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_arrow_right,0)
            buttontransfer.setBackgroundColor(Color.TRANSPARENT)
            buttontransfer.setPadding(0,0,0,20)
            conenidoscrollTransfer.addView(buttontransfer)
            val min = it
            buttontransfer.setOnClickListener{
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, DetalleMinistracionExtraordinariaEstatalTransfer(informacin,min))
                    .addToBackStack(null).commit()

            }


        }



 /*       ministracion.apply {

            detalleMinistracionExtraordinariaTitleTransfer.text = currencyFormatter.format(monto)

            detalleMinistracionExtraordinariaEstadoUniversidad.text = "60"
                //currencyFormatter.format(estado_universidad)

            detalleMinistracionExtraordinariaEstadoUniversidadFecha.text = fechaEjecucionEstado

            detalleMinistracionExtraordinariaObservacion.text = observacion
        }*/


    }

}

