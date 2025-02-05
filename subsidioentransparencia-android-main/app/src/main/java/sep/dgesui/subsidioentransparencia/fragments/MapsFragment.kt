package sep.dgesui.subsidioentransparencia.fragments

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.Job
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.databinding.FragmentMapsBinding
import sep.dgesui.subsidioentransparencia.engineadapter.Filter
import sep.dgesui.subsidioentransparencia.model.Universidade
import timber.log.Timber


class MapsFragment : Fragment(), GoogleMap.OnMarkerClickListener {

    var filter = Filter.filter
    val job = Job()
    var id = ""
    var nombre = ""
    init {
        filter.selectDetalle = true
        filter.btnFilter = true
    }

    fun googleMapsConfigurations(googleMap: GoogleMap) {
        GoogleMapOptions().useViewLifecycleInFragment(true)
        googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(binding.root.context, R.raw.style_json))
        googleMap.uiSettings.isMapToolbarEnabled = false
        googleMap.uiSettings.isScrollGesturesEnabled = true
        googleMap.uiSettings.isRotateGesturesEnabled = false
        googleMap.setOnMarkerClickListener(this)
        googleMap.isIndoorEnabled
    }


    private fun setMarkers(googleMap: GoogleMap, list: List<Universidade>, subsidio: String) {

        googleMap.clear()

        googleMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    filter.getCoordinates().lat,
                    filter.getCoordinates().long
                ), 5.1F
            )
        )
        for (mapsUni in list) {

            if (subsidio != "subsidio_ordinario") {
                googleMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(mapsUni.latitud, mapsUni.longitud))
                        .title(mapsUni.nombre)
                        .snippet(mapsUni.siglas)
                        .zIndex(mapsUni.id.toFloat())
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ext))
                )
            }
            if (subsidio == "subsidio_ordinario") {
                when (mapsUni.clasificacion) {

                    1 -> googleMap.addMarker(
                        MarkerOptions()
                            .position(LatLng(mapsUni.latitud, mapsUni.longitud))
                            .title(mapsUni.nombre)
                            .snippet(mapsUni.siglas)
                            .zIndex(mapsUni.id.toFloat())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.upe))
                    )
                    2 -> googleMap.addMarker(
                        MarkerOptions()
                            .position(LatLng(mapsUni.latitud, mapsUni.longitud))
                            .title(mapsUni.nombre)
                            .snippet(mapsUni.siglas)
                            .zIndex(mapsUni.id.toFloat())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.upeas))
                    )
                    3 -> googleMap.addMarker(
                        MarkerOptions()
                            .position(LatLng(mapsUni.latitud, mapsUni.longitud))
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ui))
                            .title(mapsUni.nombre)
                            .snippet(mapsUni.siglas)
                            .zIndex(mapsUni.id.toFloat())
                    )
                }
            }


        }

    }


    var callback = OnMapReadyCallback { googleMap ->
        if (!filter.content.hasActiveObservers()) {


            filter.content.observe(this) { listaRecibida ->
                googleMapsConfigurations(googleMap)
                Log.e("markers", listaRecibida.toString() )
                setMarkers(googleMap, listaRecibida, filter.subsidio)

                binding.borrarFiltroMaps.isVisible = filter.filtered

                formatHeader(filter, binding.cTitleYearMaps, binding.TitleYearMaps, requireContext())

                binding.borrarFiltroMaps.setOnClickListener {
                    Timber.d("BORRAR ${filter.filtered}")
                    filter.reset()
                    filter.filtrar()
                    binding.borrarFiltroMaps.isVisible = false
                    filter.selectDeficitFinanciero = false
                    binding.cTitleYearMaps.setBackgroundColor(Color.parseColor("#225C4F"))
                }


                binding.buttonFilterMaps.setOnClickListener {
                    filter.btnFilter = false
                    filter.selectState = false
                    FilterFragment(requireContext()) { MapsFragment() }
                        .show(childFragmentManager, "filterFragment")

                }

            }

        }
    }


    override fun onMarkerClick(marker:Marker): Boolean {
        id = marker!!.zIndex.toInt().toString()
        nombre = marker.title.toString()
        filter.selectDetalle = false
        ModalMapFragment(
            filter.year,
            marker.zIndex.toString(),
            marker.title.toString(),
            marker.snippet.toString(),
            filter.subsidio,
        "map").show(childFragmentManager, "modalMapFragment")
        return true
    }

    private var _binding: FragmentMapsBinding? = null
    val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(callback)

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    System.exit(0)
                }
            })

    }



}
