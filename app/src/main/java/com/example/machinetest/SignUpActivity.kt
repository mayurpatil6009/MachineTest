package com.example.machinetest

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.machinetest.databinding.ActivitySignUpBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

class SignUpActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    private lateinit var locationRequest: LocationRequest
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    //    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
    }

    @SuppressLint("SuspiciousIndentation", "MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.profilePic.setOnClickListener {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 121)
        }

        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)
        val locationPermmisionRequest=registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ){ Permissions ->
            when {
                Permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION,false) ||
                Permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION,false)
                ->{
                    Toast.makeText(this,"location access granted",Toast.LENGTH_SHORT).show()
                if(isLocationEnabled()) {
                    val result = fusedLocationProviderClient.getCurrentLocation(
                        Priority.PRIORITY_BALANCED_POWER_ACCURACY,
                        CancellationTokenSource().token
                    )
                    result.addOnCompleteListener {
                        val location =
                            "Lattitude : " + it.result.latitude + "\n" +
                                    "Longitude: : " + it.result.longitude
                         binding.addressUser.setText(location)

                    }
                }else{
                    Toast.makeText(this,"Enable location",Toast.LENGTH_LONG).show()
                    createLocationRequest()
                }
                }
                else -> {
                    Toast.makeText(this,"no location access",Toast.LENGTH_SHORT).show()
                }

        }

        }
        binding.locationIcon.setOnClickListener {

          locationPermmisionRequest.launch(
              arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                     Manifest.permission.ACCESS_COARSE_LOCATION)
          )

        }



        binding.signupBtn.setOnClickListener {
//          name,email,mobile,addres,profile
//          pic,username,password )
            val name = binding.name.text.toString()
            val email = binding.emailuser.text.toString()
            val address = binding.addressUser.text.toString()
            val username = binding.username.text.toString()
            val password = binding.password.text.toString()
            val mobile = binding.mobileUser.text.toString()

            val user = UserData(name, email, mobile, address, username, password)

            databaseReference = FirebaseDatabase.getInstance().getReference("Users")
            databaseReference.child(username).setValue(user).addOnSuccessListener {

                Toast.makeText(this, "User Registered successfully", Toast.LENGTH_SHORT).show()
                binding.name.text.clear()
                binding.emailuser.text.clear()
                binding.mobileUser.text.clear()
                binding.addressUser.text.clear()
                binding.username.text.clear()
                binding.password.text.clear()
                val intent = Intent(this, HomepageActivity::class.java)
                finish()
                startActivity(intent)
            }.addOnFailureListener {
                Log.d("error", it.message.toString())
                Toast.makeText(this, "Failed to register", Toast.LENGTH_SHORT).show()
            }

        }
        binding.logintxt.setOnClickListener {
            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun createLocationRequest() {
    val locationRequest = LocationRequest.Builder(
       Priority.PRIORITY_HIGH_ACCURACY,
        1000
    ).setMinUpdateIntervalMillis(5000).build()
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this)
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {

        }
        task.addOnFailureListener {e ->
            if(e is ResolvableApiException){
                try{
                    e.startResolutionForResult(
                        this,100
                    )
                }
                catch(SendEx : java.lang.Exception){
                }
            }

        }


    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager

        try {
            return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
//    private fun registerForActivityResult(requestMultiplePermissions: ActivityResultContracts.RequestMultiplePermissions): Any {
//
//    }


}

