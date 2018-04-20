package com.deneb.david.astroapp.presentation.view.activities

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.ashokvarma.bottomnavigation.BottomNavigationItem
import com.deneb.david.astroapp.BuildConfig
import com.deneb.david.astroapp.R
import com.deneb.david.astroapp.presentation.view.fragments.HomeFragment
import org.jetbrains.anko.find
import org.jetbrains.anko.toast


class NavigatorActivity : AppCompatActivity(), HomeFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val apiKey = BuildConfig.ASTROBIN_KEY
        val apiSecret = BuildConfig.ASTROBIN_SECRET
        val nasaKey = BuildConfig.NASA_KEY

        val bottombar = find<BottomNavigationBar>(R.id.bottomBar)

        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
        bottombar
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setActiveColor(R.color.black)
                .addItem(BottomNavigationItem(R.drawable.ic_home,"Home"))
                .addItem(BottomNavigationItem(R.drawable.ic_search,"Buscar"))
                .addItem(BottomNavigationItem(R.drawable.ic_favorite, "Favoritos"))
                .addItem(BottomNavigationItem(R.drawable.ic_person_pin, "Perfil"))
                .initialise()

        bottombar.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabSelected(position: Int) {
                toast("$position")
                when(position){
                    0 -> {
                        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
                    }
                    1 -> {

                    }
                    2 -> {

                    }
                    3 -> {

                    }
                    else -> {}
                }
            }
            override fun onTabUnselected(position: Int) {

            }
            override fun onTabReselected(position: Int) {
                //Vuelve a tocar en el mismo tab
                toast("reselected $position")
            }
        })
    }

    override fun onFragmentInteraction(uri: Uri) {
    }
}
