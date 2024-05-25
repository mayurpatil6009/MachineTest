package com.example.machinetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.example.machinetest.databinding.ActivityMainBinding

class HomepageActivity : AppCompatActivity() {
    private val binding:  ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
   

        replaceWithFragment(HomeFragment())
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.item1 -> replaceWithFragment(HomeFragment())
                else -> {

                }
            }
            true

        }

         binding.BtnNavigationDrawer.setOnClickListener {
             binding.drawerLayout.openDrawer(GravityCompat.START)
         }
//       binding.navigationDrawer.setNavigationItemSelectedListener {
//            when(it.itemId){
//                R.id.item1 -> replaceWithFragment(profilefragment())
//                R.id.item2 -> replaceWithFragment(amountfragment())
//                R.id.item3 -> replaceWithFragment(mycashfragment())
//                R.id.item4 -> replaceWithFragment(settingfragment())
//                else ->{
//                    drawerlayout.closeDrawer(GravityCompat.START)
//                }
//            }
//            true
//        }

    }



    private fun  replaceWithFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.androidFrame, fragment)
        fragmentTransaction.commit()

    }
    fun onBackButtonPress(){
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
           binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressedDispatcher.onBackPressed()
        }
    }
    }