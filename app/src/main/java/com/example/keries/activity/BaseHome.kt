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
    val notificationPermissionCode=100

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_events -> {
                    loadFragment(Events()) // Load Events Fragment
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_schedule -> {
                    loadFragment(Schedule()) // Load Schedule Fragment
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_home -> {
                    loadFragment(Home()) // Load Home Fragment
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_shop -> {
                    loadFragment(Shop()) // Load Shop Fragment
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_more -> {
                    loadFragment(More()) // Load More Fragment
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
        if(permissionGranted){
            setContentView(R.layout.activity_base_home)

            bottomNavigationView = findViewById(R.id.bottomNavigationView)
            bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
            loadFragment(Home())
            bottomNavigationView.selectedItemId = R.id.navigation_home
        }
    }
    private fun checkPermissions() {
        val notification =
            ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS)
        if (notification == PackageManager.PERMISSION_GRANTED) {
            permissionGranted=true
        }
        else{
            makeRequest()
        }
    }
    private fun makeRequest() {
        val notification= android.Manifest.permission.POST_NOTIFICATIONS
        ActivityCompat.requestPermissions(this, arrayOf(notification),notificationPermissionCode)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == notificationPermissionCode){
            if(grantResults.isNotEmpty() &&
                grantResults[0]==PackageManager.PERMISSION_GRANTED){
                permissionGranted=true
                setContentView(R.layout.activity_base_home)
                bottomNavigationView = findViewById(R.id.bottomNavigationView)
                bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
                loadFragment(Home())
                bottomNavigationView.selectedItemId = R.id.navigation_home
            }
            else{
                showPermissionDeniedDialog()
            }
        }
    }
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
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