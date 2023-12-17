package com.example.keries.activity

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Build
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.keries.fragments.Events
import com.example.keries.fragments.Home
import com.example.keries.fragments.More
import com.example.keries.R
import com.example.keries.fragments.Schedule
import com.example.keries.fragments.Shop

class BaseHome : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    var permissionGranted = false
    val notificationPermissionCode = 100

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_events -> {
                    if (supportFragmentManager.findFragmentById(R.id.fragment_container) !is Events) {
                        loadFragment(Events())
                        window.decorView.setBackgroundResource(R.drawable.splash_background)
                    }
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_schedule -> {
                    if (supportFragmentManager.findFragmentById(R.id.fragment_container) !is Schedule) {
                        loadFragment(Schedule())
                        window.decorView.setBackgroundResource(R.drawable.splash_background)

                    }
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_home -> {
                    if (supportFragmentManager.findFragmentById(R.id.fragment_container) !is Home) {
                        loadFragment(Home())
                        window.decorView.setBackgroundResource(R.drawable.homefragmentbackgorundimage)

                    }
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_shop -> {
                    if (supportFragmentManager.findFragmentById(R.id.fragment_container) !is Shop) {
                        loadFragment(Shop())
                        window.decorView.setBackgroundResource(R.drawable.splash_background)

                    }
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_more -> {
                    if (supportFragmentManager.findFragmentById(R.id.fragment_container) !is More) {
                        loadFragment(More())
                        window.decorView.setBackgroundResource(R.drawable.splash_background)

                    }
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().subscribeToTopic("notification")

        window.statusBarColor = Color.TRANSPARENT
        checkPermissions()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
        }
        if (permissionGranted) {
            setContentView(R.layout.activity_base_home)

//
//            val fragmentContainer = findViewById<ViewGroup>(R.id.fragment_container)
//            val fragmentContainerHeight = fragmentContainer.height
//            resources.getDimensionPixelSize(R.dimen.fragment_container_height)
//            if (fragmentContainerHeight > 0) {
//                val prefs = getSharedPreferences("com.example.keries", MODE_PRIVATE)
//                prefs.edit().putInt("fragment_container_height", fragmentContainerHeight).apply()
//            }


            bottomNavigationView = findViewById(R.id.bottomNavigationView)
            bottomNavigationView.setOnNavigationItemSelectedListener(
                onNavigationItemSelectedListener
            )
            loadFragment(Home())
            window.decorView.setBackgroundResource(R.drawable.homefragmentbackgorundimage)


            bottomNavigationView.selectedItemId = R.id.navigation_home
        }
    }

    private fun checkPermissions() {
        val notification = ContextCompat.checkSelfPermission(
            this, android.Manifest.permission.POST_NOTIFICATIONS
        )
        if (notification == PackageManager.PERMISSION_GRANTED) {
            permissionGranted = true
        } else {
            makeRequest()
        }
    }

    private fun makeRequest() {
        val notification = android.Manifest.permission.POST_NOTIFICATIONS
        ActivityCompat.requestPermissions(this, arrayOf(notification), notificationPermissionCode)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == notificationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                permissionGranted = true
                setContentView(R.layout.activity_base_home)
                bottomNavigationView = findViewById(R.id.bottomNavigationView)
                bottomNavigationView.setOnNavigationItemSelectedListener(
                    onNavigationItemSelectedListener
                )
                loadFragment(Home())
                window.decorView.setBackgroundResource(R.drawable.homefragmentbackgorundimage)
                bottomNavigationView.selectedItemId = R.id.navigation_home
            } else {
                showPermissionDeniedDialog()
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showPermissionDeniedDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Notification Permission Required")
        builder.setMessage("To use this app, please grant notification permissions.")
        builder.setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
            System.exit(0)
        }
        builder.setCancelable(false)
        builder.show()
    }
}