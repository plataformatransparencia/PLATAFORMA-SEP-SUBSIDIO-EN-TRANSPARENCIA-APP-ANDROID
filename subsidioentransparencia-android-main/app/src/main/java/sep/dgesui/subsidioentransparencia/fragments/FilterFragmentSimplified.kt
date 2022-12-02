package sep.dgesui.subsidioentransparencia.fragments

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_filter.*
import kotlinx.coroutines.runBlocking
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.engineadapter.Filter
import sep.dgesui.subsidioentransparencia.modelfilter.FilterValuesCache

class FilterFragment(
    private val contexto: Context,
    private val source: () -> Fragment,
) : BottomSheetDialogFragment() {

    private val filterValues = FilterValuesCache.getFilterValues(contexto)

    private val selectedYear = MutableLiveData("")
    private val selectedTipoSubsidio = MutableLiveData("")
    private val selectedSubsidio = MutableLiveData("")
    private val selectedClasificacion = MutableLiveData("")
    private val selectedEstado = MutableLiveData("")


    override fun onResume() {
        super.onResume()

        filterValues.observe(this) {
            setupItemSelectedListeners()
            setupObservers()
            setupButtonListener()
            loadYears()
        }
    }

    private fun setupButtonListener() {
        closeFilter.setOnClickListener {
            onDestroyView()
        }


        butonFilter.setOnClickListener {

            runBlocking {

                val year = selectedYear.value!!

                val subsidio =
                    filterValues.value?.subsidios?.llaveDeValor(year, selectedSubsidio.value!!)
                        ?: ""


                val categoria = selectedClasificacion.value!!


                val estado =
                    filterValues.value?.categorias?.llaveDeValor(
                        selectedEstado.value!!,
                        subsidio,
                        year,
                        categoria
                    ) ?: ""


                val categoriaValor =
                    filterValues.value?.clasificacion?.llaveDeValor(subsidio, categoria)!!

                Filter.filter.filtrar(year, categoriaValor, estado, subsidio)

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.main_fragment_container, source.invoke())
                    .commit()

            }
        }
    }

    private fun setupObservers() {

        selectedYear.observe(this) {
            if (it.isNotBlank())
                loadTiposSubsidiosForYear()
        }


        selectedTipoSubsidio.observe(this) {
            if (it.isNotBlank())
                loadSubsidiosForYear()
        }

        selectedSubsidio.observe(this) {
            if (it.isNotBlank())
                loadClasificaciones()
        }

        selectedClasificacion.observe(this) {
            if (it.isNotBlank())
                loadEstados()
        }
    }

    private fun setupItemSelectedListeners() {
        spAñoFiltro.onItemSelectedListener = listenerFactory(selectedYear)
        spTipoSubsidio.onItemSelectedListener = listenerFactory(selectedTipoSubsidio)
        spSubsidio.onItemSelectedListener = listenerFactory(selectedSubsidio)
        spCategoriaFiltro.onItemSelectedListener = listenerFactory(selectedClasificacion)
        spEstadosFiltro.onItemSelectedListener = listenerFactory(selectedEstado)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_filter, container, false)

    private fun listenerFactory(target: MutableLiveData<*>): AdapterView.OnItemSelectedListener =
        object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (parent?.getItemAtPosition(position) != "Todos")
                    target.value = (parent?.getItemAtPosition(position) as String)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                parent?.setSelection(0)
            }
        }

    private fun loadYears() {
        val years = filterValues.value?.subsidios?.years ?: emptyList()

        spAñoFiltro.adapter = arrayAdapterFactory(years)
        spAñoFiltro.setSelection(0)
    }

    private fun loadTiposSubsidiosForYear() {
        val year = selectedYear.value!!
        val tiposSubsidio = filterValues.value?.subsidios?.tiposSubsidioForYear(year) ?: emptyList()

        spTipoSubsidio.adapter = arrayAdapterFactory(tiposSubsidio)
        spTipoSubsidio.setSelection(0)

    }

    private fun loadSubsidiosForYear() {

        val year = selectedYear.value!!
        val tipoSubsidio = selectedTipoSubsidio.value!!

        val subsidios =
            filterValues.value?.subsidios?.subsidiosForYear(year, tipoSubsidio) ?: emptyList()

        spSubsidio.adapter = arrayAdapterFactory(subsidios)
        spSubsidio.setSelection(0)
    }

    private fun loadClasificaciones() {
        val year = selectedYear.value!!
        val subsidio = selectedSubsidio.value!!
        val claveSubsidio = filterValues.value?.subsidios?.llaveDeValor(year, subsidio) ?: ""

        val clasificaciones =
            filterValues.value?.clasificacion?.clasificacionesForSubsidio(claveSubsidio)
                ?: emptyList()

        spCategoriaFiltro.adapter = arrayAdapterFactory(clasificaciones)
        spCategoriaFiltro.setSelection(0)
    }

    private fun loadEstados() {
        val year = selectedYear.value!!
        val subsidio = selectedSubsidio.value!!
        val clasificacion = selectedClasificacion.value!!

        val claveSubsidio = filterValues.value?.subsidios?.llaveDeValor(year, subsidio) ?: ""

        val estados =
            filterValues.value?.categorias?.forSubsidioAndYearAndClasificacion(
                claveSubsidio,
                year,
                clasificacion
            ) ?: emptyList()

        spEstadosFiltro.adapter = arrayAdapterFactory(estados)
        spEstadosFiltro.setSelection(0)

    }

    private fun arrayAdapterFactory(content: Collection<*>) = ArrayAdapter(
        contexto,
        android.R.layout.select_dialog_item,
        content.toTypedArray()
    )


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val d = dialog as BottomSheetDialog

        d.behavior.peekHeight = run {
            val displayMetrics = DisplayMetrics()
            (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.heightPixels
        }

        d.behavior.skipCollapsed = true
        d.behavior.isDraggable = true
        d.dismissWithAnimation = true

        return dialog


    }
}
