package com.jp_funda.todomind.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.jp_funda.todomind.R
import com.jp_funda.todomind.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set up Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up Navigation
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_top,
                R.id.navigation_task,
                R.id.navigation_mind_map,
                R.id.navigation_record,
                R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener(
            NavController.OnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.navigation_top -> findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.VISIBLE
                    R.id.navigation_task -> findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.VISIBLE
                    R.id.navigation_mind_map -> findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.VISIBLE
                    R.id.navigation_record -> findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.VISIBLE
                    R.id.navigation_settings -> findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.VISIBLE
                    else -> findViewById<BottomNavigationView>(R.id.nav_view).visibility = View.GONE
                }
            }
        )
    }

    // Set Up Back Arrow at ToolBar
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}