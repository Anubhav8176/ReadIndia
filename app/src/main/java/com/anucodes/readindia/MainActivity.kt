package com.anucodes.readindia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.anucodes.readindia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Initializing the fragments object associated with the respective fragment class
        val homeFragment = HomeFragment()
        val businessFragment = BuisnessFragment()
        val entertainmentFragment = EntertainmentFragment()
        val scienceFragment = ScienceFragment()
        val sportsFragment = SportsFragment()

        //Making home tab selected by default.
        binding.bottomNavigationMenu.setSelectedItemId(R.id.home)

        //setting default fragment.
        setCurrentFragment(homeFragment)

        binding.bottomNavigationMenu.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home->setCurrentFragment(homeFragment)
                R.id.sports->setCurrentFragment(sportsFragment)
                R.id.buisness->setCurrentFragment(businessFragment)
                R.id.science->setCurrentFragment(scienceFragment)
                R.id.entertainment->setCurrentFragment(entertainmentFragment)
            }
            true
        }

    }

    //setting the fragment.
    fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            addToBackStack(null)
            commit()
        }
    }
}