package sep.dgesui.subsidioentransparencia.fragments

//noinspection SuspiciousImport

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.databinding.FragmentModalMapBinding
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

    private var _binding: FragmentModalMapBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        _binding = FragmentModalMapBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }



    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.framentModal.background.alpha = 0.1.toInt()
        binding.buttonModalCancel.setOnClickListener {
            dismiss()
        }
        binding.buttonModal.setOnClickListener {
            back_to_page = "map"
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace(
                R.id.main_fragment_container,
                DetalleFragment(id, year, nombre, subsidio,back_to_page)

            )
                .addToBackStack(null)
                .commit()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if (filter.selectDetalle) {
            dismiss()
        } else {
            CoroutineScope(Job() + Dispatchers.Default).launch {
                binding.textModalAcrn.text = siglas
                binding.textModalUni.text = nombre
                TransparenciaRetrofit.serviceFactory(DetalleService::class.java)
                    .getDetalle(year, id, subsidio)
                    .enqueue(object : Callback<Detalle> {
                        override fun onResponse(call: Call<Detalle>, response: Response<Detalle>) {
                            val image = response.body()!!.escudo
                            Picasso.get().load(image).into(binding.imageModaUni)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}