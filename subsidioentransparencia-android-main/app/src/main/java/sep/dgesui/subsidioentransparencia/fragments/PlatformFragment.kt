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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.databinding.FragmentPlatformBinding
import sep.dgesui.subsidioentransparencia.engineadapter.Filter
import sep.dgesui.subsidioentransparencia.engineadapter.TransparenciaRetrofit
import sep.dgesui.subsidioentransparencia.modelplataforma.Plataforma
import sep.dgesui.subsidioentransparencia.services.PlataformaService


class PlatformFragment : Fragment() {

    var filter = Filter.filter

    init {
        launch()
    }

    private var _binding: FragmentPlatformBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlatformBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.buttonBackPlataforma.setOnClickListener {
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

                        binding.titlePlataforma.text = plataforma.Informacion_plataforma.titulo
                        binding.listaPlataforma0.text = plataforma.Informacion_plataforma.descripcion[0]
                        binding.listaPlataforma1.text =
                            plataforma.Informacion_plataforma.ley_universitaria.titulo
                        binding.numArt62.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo62.titulo
                        binding.textArt62.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo62.descripcion
                        binding.numArt67.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.titulo
                        binding.textArt67.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.descripcion
                        binding.numI.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[0].indice
                        binding.textI.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[0].descripcion
                        binding.numII.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[1].indice
                        binding.textII.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[1].descripcion
                        binding.numIII.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[2].indice
                        binding.textIII.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[2].descripcion
                        binding.numIV.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[3].indice
                        binding.textIV.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[3].descripcion
                        binding.numV.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[4].indice
                        binding.textV.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[4].descripcion
                        binding.numVI.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[5].indice
                        binding.textVI.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[5].descripcion
                        binding.numVII.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[6].indice
                        binding.textVII.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[6].descripcion
                        binding.numVIII.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[7].indice
                        binding.textVIII.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[7].descripcion
                        binding.numIX.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[8].indice
                        binding.textIX.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[8].descripcion
                        binding.numX.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[9].indice
                        binding.textX.text =
                            plataforma.Informacion_plataforma.ley_universitaria.articulo67.numerales[9].descripcion
                        binding.textComplemento.text = plataforma.Informacion_plataforma.complemento[0]
                        binding.textComplemento1.text = plataforma.Informacion_plataforma.complemento[1]
                        binding.textComplemento2.text = plataforma.Informacion_plataforma.complemento[2]
                        binding.titleUI.text = plataforma.Informacion_plataforma.UI.titulo
                        binding.textUI.text = plataforma.Informacion_plataforma.UI.descripcion
                        binding.titleUPE.text = plataforma.Informacion_plataforma.UPE.titulo
                        binding.textUPE.text = plataforma.Informacion_plataforma.UPE.descripcion
                        binding.titleUPEAS.text = plataforma.Informacion_plataforma.UPEAS.titulo
                        binding.textUPEAS.text = plataforma.Informacion_plataforma.UPEAS.descripcion
                        binding.videoPlatform.setVideoURI(Uri.parse(plataforma.Informacion_plataforma.video))
                        MediaController(context).setAnchorView(binding.videoPlatform.findViewById(R.id.layoutVideoPlatform))
                        binding.videoPlatform.setMediaController(MediaController(context))
                        binding.butonPlay.isVisible = true
                        binding.butonPlay.setOnClickListener {
                            binding.butonPlay.isVisible = false
                        }
                    }

                    override fun onFailure(call: Call<Plataforma>, t: Throwable) {
                        Log.e("ERROR", "No se completo la petición - " + t)
                    }


                })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}