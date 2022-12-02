package sep.dgesui.subsidioentransparencia.fragments

//noinspection SuspiciousImport
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.android.synthetic.main.fragment_programs.*
import kotlinx.android.synthetic.main.fragment_programs.contenido
import kotlinx.android.synthetic.main.layout_referencia.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.engineadapter.Filter
import sep.dgesui.subsidioentransparencia.engineadapter.TransparenciaRetrofit
import sep.dgesui.subsidioentransparencia.model.Programas
import sep.dgesui.subsidioentransparencia.services.ProgramsService
import kotlin.math.log


class ProgramsFragment : Fragment() {

    var filter = Filter.filter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_programs, container, false)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonBackPrograms.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onStart() {
        super.onStart()

        CoroutineScope(Job() + Dispatchers.Default).launch {
            TransparenciaRetrofit.serviceFactory(ProgramsService::class.java).getProgramas()
                .enqueue(object : Callback<Programas> {
                    override fun onResponse(call: Call<Programas>, response: Response<Programas>) {

                        response.body()!!.programa.forEach {
                            val titulo = TextView(context)
                            titulo.text = it.titulo
                            val params = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            )

                            titulo.layoutParams = params
                            titulo.gravity = Gravity.CENTER
                            titulo.textSize = 20F
                            titulo.setTextColor(Color.BLACK)
                            titulo.setPadding(10,40,10,20)
                            conenidoscroll.addView(titulo)
                            it.descripcion.forEach {
                                val descripcion = TextView(context)
                                descripcion.layoutParams = params
                                descripcion.gravity = Gravity.LEFT
                                descripcion.textSize = 16F
                                descripcion.setTextColor(Color.BLACK)
                                descripcion.setPadding(40,20,40,50)
                                descripcion.text = it
                                conenidoscroll.addView(descripcion)
                            }



                        }


                    }

                    override fun onFailure(call: Call<Programas>, t: Throwable) {
                        Log.e("Error", "No se completo la petición")
                    }

                })
        }
    }




}

private fun LinearLayout.addView(view1: String) {

}
