package sep.dgesui.subsidioentransparencia.fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_platform.*
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
import sep.dgesui.subsidioentransparencia.modelplataforma.Plataforma
import sep.dgesui.subsidioentransparencia.services.PlataformaService


class PlatformFragment : Fragment() {

    var filter = Filter.filter

    init {
        launch()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_platform, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        buttonBackPlataforma.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }

    fun launch() {
        CoroutineScope(Job() + Dispatchers.Default).launch {
            TransparenciaRetrofit.serviceFactory(PlataformaService::class.java).getPlataforma()
                .enqueue(object : Callback<Plataforma> {
                    override fun onResponse(
                        call: Call<Plataforma>,
                        response: Response<Plataforma>
                    ) {
                        val plataforma = response.body()!!

                        titlePlataforma.text = plataforma.Informacion_plataforma.titulo
                        listaPlataforma0.text = plataforma.Informacion_plataforma.descripcion[0]
                        listaPlataforma1.text =
                            plataforma.Informacion_plataforma.ley_universitaria.titulo
                        numArt62.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo62.titulo
                        textArt62.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo62.descripcion
                        numArt67.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.titulo
                        textArt67.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.descripcion
                        numI.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[0].indice
                        textI.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[0].descripcion
                        numII.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[1].indice
                        textII.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[1].descripcion
                        numIII.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[2].indice
                        textIII.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[2].descripcion
                        numIV.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[3].indice
                        textIV.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[3].descripcion
                        numV.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[4].indice
                        textV.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[4].descripcion
                        numVI.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[5].indice
                        textVI.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[5].descripcion
                        numVII.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[6].indice
                        textVII.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[6].descripcion
                        numVIII.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[7].indice
                        textVIII.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[7].descripcion
                        numIX.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[8].indice
                        textIX.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[8].descripcion
                        numX.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[9].indice
                        textX.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[9].descripcion
                        textComplemento.text = plataforma.Informacion_plataforma.complemento[0]
                        textComplemento1.text = plataforma.Informacion_plataforma.complemento[1]
                        textComplemento2.text = plataforma.Informacion_plataforma.complemento[2]
                        titleUI.text = plataforma.Informacion_plataforma.UI.titulo
                        textUI.text = plataforma.Informacion_plataforma.UI.descripcion
                        titleUPE.text = plataforma.Informacion_plataforma.UPE.titulo
                        textUPE.text = plataforma.Informacion_plataforma.UPE.descripcion
                        titleUPEAS.text = plataforma.Informacion_plataforma.UPEAS.titulo
                        textUPEAS.text = plataforma.Informacion_plataforma.UPEAS.descripcion
                        videoPlatform.setVideoURI(Uri.parse(plataforma.Informacion_plataforma.video))
                        MediaController(context).setAnchorView(videoPlatform.findViewById(R.id.layoutVideoPlatform))
                        videoPlatform.setMediaController(MediaController(context))
                        butonPlay.isVisible = true
                        butonPlay.setOnClickListener {
                            butonPlay.isVisible = false
                        }
                    }

                    override fun onFailure(call: Call<Plataforma>, t: Throwable) {
                        Log.e("ERROR", "No se completo la petición - " + t)
                    }


                })
        }
    }

}