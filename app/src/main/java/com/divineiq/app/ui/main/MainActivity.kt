package com.divineiq.app.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.divineiq.app.R
import com.divineiq.app.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

/**
 * Single activity host for DivineIQ. Owns the toolbar, the bottom
 * navigation bar, and the [NavController] that swaps between the Home,
 * Notes, Quiz, and Profile feature fragments.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.action_search) {
                Snackbar.make(binding.root, R.string.search_hint, Snackbar.LENGTH_SHORT).show()
                true
            } else {
                false
            }
        }
    }
}
