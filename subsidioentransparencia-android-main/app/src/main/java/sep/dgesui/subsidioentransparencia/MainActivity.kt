package sep.dgesui.subsidioentransparencia

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import sep.dgesui.subsidioentransparencia.databinding.ActivityMainBinding
import sep.dgesui.subsidioentransparencia.engineadapter.Filter
import sep.dgesui.subsidioentransparencia.fragments.ContactFragment
import sep.dgesui.subsidioentransparencia.fragments.ListFragment
import sep.dgesui.subsidioentransparencia.fragments.MapsFragment
import sep.dgesui.subsidioentransparencia.fragments.MenuFragment
import sep.dgesui.subsidioentransparencia.modelfilter.FilterValuesCache
import timber.log.Timber


class MainActivity : AppCompatActivity() {

   private val mapsFragment = MapsFragment()
   private val listFragment = ListFragment()
   private val contacFragment = ContactFragment()
   private val menuFragment = MenuFragment()
    var filter = Filter.filter
    private lateinit var binding: ActivityMainBinding
    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        val lastYearInfo = FilterValuesCache.getFilterValuesIni()
        lastYearInfo.observe(this) {
            if (lastYearInfo.value != null) {
                if (filter.year != lastYearInfo.value ){
                    filter.filtrar()
                }
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener  { item: MenuItem ->
            item.isCheckable = false
            when (item.itemId) {
                R.id.mapFragment -> {
                    item.isCheckable = true
                    filter.response.cancel()
                    if (filter.contact) {
                        filter.responseContacto.cancel()
                    }
                    loadFragment(mapsFragment)

                }
                R.id.listFragment -> {
                    filter.response.cancel()
                    if (filter.contact) {
                        filter.responseContacto.cancel()
                    }
                    loadFragment(listFragment)
                }
                R.id.contacFragment -> {
                    loadFragment(contacFragment)
                }
                R.id.menuFragment -> {
                    if (filter.contact) {
                        filter.responseContacto.cancel()
                    }
                    loadFragment(menuFragment)
                }

            }
            true

        }


        loadFragment(mapsFragment)


    }


    fun loadFragment(fragment: Fragment) {
        filter.selectDetalle = true
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_fragment_container, fragment)
            .commit()
    }

}



