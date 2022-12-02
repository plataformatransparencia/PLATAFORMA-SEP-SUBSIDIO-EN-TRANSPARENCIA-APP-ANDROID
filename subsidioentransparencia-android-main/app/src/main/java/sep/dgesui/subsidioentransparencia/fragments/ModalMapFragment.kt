package sep.dgesui.subsidioentransparencia.fragments

//noinspection SuspiciousImport

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.fragment_maps.*
import kotlinx.android.synthetic.main.fragment_maps.view.*
import kotlinx.android.synthetic.main.fragment_modal_map.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.engineadapter.Filter
import sep.dgesui.subsidioentransparencia.engineadapter.TransparenciaRetrofit
import sep.dgesui.subsidioentransparencia.model.Detalle
import sep.dgesui.subsidioentransparencia.services.DetalleService


class ModalMapFragment(
    val year: String,
    val id: String,
    val nombre: String,
    val siglas: String,
    val subsidio: String,
    private var back_to_page: String
) : DialogFragment() {

    var filter = Filter.filter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? =
        inflater.inflate(R.layout.fragment_modal_map, container, false)



    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        framentModal.background.alpha = 0.1.toInt()
        buttonModalCancel.setOnClickListener {
            dismiss()
        }
        buttonModal.setOnClickListener {
            back_to_page = "map"
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(
                R.id.main_fragment_container,
                DetalleFragment(id, year, nombre, subsidio,back_to_page)

            )
                .addToBackStack(null)
                .commit()
            }
//            DetalleFragment(id, year, nombre, subsidio).show(
//                parentFragmentManager,
//                "detalle"
//            )
        }
    }

    override fun onStart() {
        super.onStart()
        if (filter.selectDetalle) {
            dismiss()
        } else {
            CoroutineScope(Job() + Dispatchers.Default).launch {
                textModalAcrn.text = siglas
                textModalUni.text = nombre
                TransparenciaRetrofit.serviceFactory(DetalleService::class.java)
                    .getDetalle(year, id, subsidio)
                    .enqueue(object : Callback<Detalle> {
                        override fun onResponse(call: Call<Detalle>, response: Response<Detalle>) {
                            val image = response.body()!!.escudo
                            Picasso.get().load(image).into(imageModaUni)
                        }

                        override fun onFailure(call: Call<Detalle>, t: Throwable) =
                            errorHandler(call, t)

                    })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (filter.selectDetalle) {
            dismiss()
        }
    }


}