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
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.example.keries.fragments.Events
import com.example.keries.fragments.Home
import com.example.keries.fragments.More
import com.example.keries.R
import com.example.keries.fragments.Schedule
import com.example.keries.fragments.Shop
import kotlin.system.exitProcess

class BaseHome : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    var permissionGranted = false
    val notificationPermissionCode = 100

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_events -> {
                    if (supportFragmentManager.findFragmentById(R.id.fragment_container) !is Events) {

                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        loadFragment(Events())
                        window.decorView.setBackgroundResource(R.drawable.splash_background)
                    }
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_schedule -> {
                    if (supportFragmentManager.findFragmentById(R.id.fragment_container) !is Schedule) {
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        loadFragment(Schedule())
                        window.decorView.setBackgroundResource(R.drawable.splash_background)

                    }
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_home -> {
                    if (supportFragmentManager.findFragmentById(R.id.fragment_container) !is Home) {
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        loadFragment(Home())
                        window.decorView.setBackgroundResource(R.drawable.homefragmentbackgorundimage)

                    }
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_shop -> {
                    if (supportFragmentManager.findFragmentById(R.id.fragment_container) !is Shop) {
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        loadFragment(Shop())
                        window.decorView.setBackgroundResource(R.drawable.splash_background)

                    }
                    return@OnNavigationItemSelectedListener true
                }

                R.id.navigation_more -> {
                    if (supportFragmentManager.findFragmentById(R.id.fragment_container) !is More) {
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        loadFragment(More())
                        window.decorView.setBackgroundResource(R.drawable.splash_background)

                    }
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        FirebaseApp.initializeApp(this)
        FirebaseMessaging.getInstance().subscribeToTopic("notification")

        window.statusBarColor = Color.TRANSPARENT
        checkPermissions()

        window.statusBarColor = ContextCompat.getColor(this, R.color.transparent)
        if (permissionGranted) {
            setContentView(R.layout.activity_base_home)
            bottomNavigationView = findViewById(R.id.bottomNavigationView)
            bottomNavigationView.setOnNavigationItemSelectedListener(
                onNavigationItemSelectedListener
            )
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            loadFragment(Home())
            window.decorView.setBackgroundResource(R.drawable.homefragmentbackgorundimage)
            bottomNavigationView.selectedItemId = R.id.navigation_home
        }
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
                supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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

    private fun showPermissionDeniedDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Notification Permission Required")
        builder.setMessage("To use this app, please grant notification permissions.")
        builder.setPositiveButton("OK") { dialogInterface: DialogInterface, i: Int ->
            exitProcess(0)
        }
        builder.setCancelable(false)
        builder.show()
    }
}