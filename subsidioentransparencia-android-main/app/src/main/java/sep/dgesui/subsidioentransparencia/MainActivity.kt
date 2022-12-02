package sep.dgesui.subsidioentransparencia

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_programs.*
import sep.dgesui.subsidioentransparencia.engineadapter.Filter
import sep.dgesui.subsidioentransparencia.fragments.ContactFragment
import sep.dgesui.subsidioentransparencia.fragments.ListFragment
import sep.dgesui.subsidioentransparencia.fragments.MapsFragment
import sep.dgesui.subsidioentransparencia.fragments.MenuFragment
import timber.log.Timber


class MainActivity : AppCompatActivity() {

    val mapsFragment = MapsFragment()
    val listFragment = ListFragment()
    val contacFragment = ContactFragment()
    val menuFragment = MenuFragment()
    var filter = Filter.filter

    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener { item: MenuItem ->
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



