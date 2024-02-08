package com.example.garbagecollection

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class Image_upload : AppCompatActivity() {
    lateinit var fusedLocationProviderClient:FusedLocationProviderClient
    private lateinit var longitude:TextView
    private lateinit var latitude:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_upload)
        val send=findViewById<Button>(R.id.snd)
        val image=findViewById<ImageView>(R.id.img1)
        latitude=findViewById(R.id.langi)
        longitude=findViewById(R.id.longi)
        fusedLocationProviderClient=LocationServices.getFusedLocationProviderClient(this)
//        val uri:Uri =intent.getParcelableExtra("img")!!
//        image.setImageURI(uri)
        val bit=intent.getParcelableExtra<Bitmap>("bit")
        image.setImageBitmap(bit)

        send.setOnClickListener {
            getLocaton()
        }
    }
//get logitude and latitude
    private fun getLocaton() {
//        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED
//            && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED)
//        {
//            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),100)
//
//            return
//        }
    if(checkPermission())
    {
        if(isLocationEnabled())
        {
//            val location=fusedLocationProviderClient.lastLocation
//            location.addOnSuccessListener {
//                if(it!=null)
//                {
//                    val textLatitude="Latitude :"+it.latitude.toString()
//                    val textLongitude="Longitude :"+it.longitude.toString()
//
//                    latitude.text=textLatitude
//                    longitude.text=textLongitude
//
//                }
//
//            }
            fusedLocationProviderClient.lastLocation.addOnCompleteListener(this)
            {
                task->
                val location:Location?=task.result

                if(location==null)
                {
                    Toast.makeText(this,"Restart the APP",Toast.LENGTH_SHORT).show()
                }
                else{
                    val textLatitude="Latitude :"+location.latitude.toString()
                    val textLongitude="Longitude :"+location.longitude.toString()

                    //idhar Save horai locattion
                    latitude.text=textLatitude
                    longitude.text=textLongitude
                }
            }
        }
        else{
            Toast.makeText(this,"Turn On Location",Toast.LENGTH_SHORT).show()
            val intent=Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }
    }
else{
        requestPermissions()
    }

    }

    private fun isLocationEnabled():Boolean
    {
        val locationManager:LocationManager=getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)||locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf( android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_ACCESS_LOCATION
        )
    }

    companion object{
        private const val PERMISSION_REQUEST_ACCESS_LOCATION=100
    }
    private fun checkPermission():Boolean
    {
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED)
        {
           return true


        }
        return false

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==PERMISSION_REQUEST_ACCESS_LOCATION)
        {
            if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(applicationContext,"Granted",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(applicationContext,"Denied",Toast.LENGTH_SHORT).show()
            }
        }
    }
}