package com.project.segunfrancis.newsnow.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.firebase.database.FirebaseDatabase
import com.project.segunfrancis.newsnow.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_web_view.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Offline Persistence
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        val navController = findNavController(R.id.main_fragment)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        val toolbar = findViewById<MaterialToolbar>(R.id.toolBar)
        toolbar.setupWithNavController(navController, appBarConfiguration)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            /**
             * Callback for when the [.getCurrentDestination] or its arguments change.
             * This navigation may be to a destination that has not been seen before, or one that
             * was previously on the back stack. This method is called after navigation is complete,
             * but associated transitions may still be playing.
             *
             * @param destination the new destination
             */
            when (destination.id) {
                R.id.newsFragment -> showToolBar()
                R.id.webViewFragment -> hideToolBar()
            }
        }
    }

    private fun hideToolBar() {
        toolBar.visibility = View.GONE
    }

    private fun showToolBar() {
        toolBar.visibility = View.VISIBLE
    }

    override fun onBackPressed() {
        if (findNavController(R.id.main_fragment).currentDestination?.id == R.id.webViewFragment) {
            if (webView.canGoBack()) {
                webView.goBack()
            }
        }
        super.onBackPressed()
    }
}
