package sep.dgesui.subsidioentransparencia.fragments

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_list.*
import sep.dgesui.subsidioentransparencia.R
import sep.dgesui.subsidioentransparencia.engineadapter.Filter
import sep.dgesui.subsidioentransparencia.engineadapter.UniversityAdapter
import sep.dgesui.subsidioentransparencia.interfaces.DataList
import sep.dgesui.subsidioentransparencia.model.Universidade
import timber.log.Timber


class ListFragment : Fragment(), DataList {
    val back_to_page = "list"
    val filter = Filter.filter
    var listUni: List<Universidade> = emptyList()

    init {
        filter.content.observe(this) { listaRecibida: List<Universidade> ->
            setRecyclerList(listaRecibida)
        }
        filter.btnFilter = true

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        filter.content.observe(this) { listaRecibida: List<Universidade> ->
            aplicarFiltro(listaRecibida)
        }

    }

    private fun aplicarFiltro(listaRecibida: List<Universidade>) {
        listUni = listaRecibida
        val query = searchView_university?.query?.toString() ?: ""
        setRecyclerList(listUni.find(query))

        formatHeader(filter, cTitleYearList, TitleYearList, requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        aplicarFiltro(filter.content.value ?: emptyList())

        borrarFiltroList.setOnClickListener {
            Timber.d("BORRAR ${filter.filtered}")
            filter.reset()
            filter.filtrar()
            borrarFiltroList.isVisible = false
            filter.selectDeficitFinanciero = false
            cTitleYearList.setBackgroundColor(Color.parseColor("#225C4F"))
        }

        buttonFilter.setOnClickListener {
            filter.btnFilter = false
            filter.selectState = false
            FilterFragment(requireContext()) { ListFragment() }
                .show(
                    childFragmentManager,
                    "filterFragment"
                )
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.main_fragment_container, MapsFragment()).commit()
                }
            })

        searchView_university.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                setRecyclerList(listUni.find(query))
                view.let { activity?.hideKeyboard(it) }
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                setRecyclerList(listUni.find(newText))
                return true
            }
        })
    }


    fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }


    private fun setRecyclerList(list: List<Universidade>) {
        view?.findViewById<RecyclerView>(R.id.universitiesRecycler)?.adapter =
            UniversityAdapter(list, this)
    }


    override fun onItemClickDetalle(id: String, nombre: String, subsidio: String) {
//        back_to_page = "list"
        filter.selectDetalle = false
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(
                R.id.main_fragment_container,
                DetalleFragment(id, filter.year, nombre, subsidio,back_to_page)
            )
            .addToBackStack(null)
            .commit()
    }


    override fun onStart() {
        super.onStart()

        borrarFiltroList.isVisible = filter.filtered


    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        filter.content.observe(viewLifecycleOwner) { listaRecibida: List<Universidade> ->
            listUni = listaRecibida
        }

    }


}


private fun List<Universidade>.find(query: String = ""): List<Universidade> =
    if (query.isBlank())
        this
    else {
        query.trim().let { trimmedQuery ->
            this
                .filter {
                    it.siglas.contains(
                        trimmedQuery,
                        ignoreCase = true
                    ) || it.nombre.contains(trimmedQuery, ignoreCase = true)
                }
        }
    }
