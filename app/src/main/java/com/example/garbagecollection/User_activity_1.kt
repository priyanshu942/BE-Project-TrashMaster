package com.example.garbagecollection

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationView

class User_activity_1 : AppCompatActivity() {

    lateinit var toggle:ActionBarDrawerToggle
    lateinit var cameraBtn: ImageButton
    lateinit var drawerLayout: DrawerLayout
    val REQUEST_IMAGE_CAPTURE=100
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user1)

         drawerLayout =findViewById(R.id.drawer)
        val navView: NavigationView =findViewById(R.id.nav_view)

        toggle= ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)


        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.img)
        navView.setNavigationItemSelectedListener {
            it.isChecked=true
            when(it.itemId){
               R.id.home->{
                   it.isChecked=true
                   val intent=Intent(this,User_activity_1::class.java)
                   startActivity(intent)
               }
                R.id.acc-> Replace(myAccount(),"My Account")
                R.id.rew-> Replace(Rewards(),it.title.toString())
                R.id.supp-> Replace(Support(),it.title.toString())
            }
            true
        }


        get_permissions()
        cameraBtn=findViewById(R.id.cambtn)

        cameraBtn.setOnClickListener {
            val takePictureIntent= Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            try {
                startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE)
            }catch(e: ActivityNotFoundException)
            {
                Toast.makeText(this,"ERRor",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode==REQUEST_IMAGE_CAPTURE && resultCode== RESULT_OK)
        {
            val imageBitmap=data?.extras?.get("data") as Bitmap
            val intent1=Intent(this,Image_upload::class.java)
            intent1.putExtra("bit",imageBitmap)
            startActivity(intent1)
            // image.setImageBitmap(imageBitmap)
        }
        else{
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bar,menu)
        return true
    }
    //select item
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item))
        {
            return true
        }
        else if(item.itemId==R.id.notifiy)
        {
            Replace(Notification(),"Notification")
        }
        return super.onOptionsItemSelected(item)
    }
    //camera
    @RequiresApi(Build.VERSION_CODES.M)
    fun get_permissions()
    {
        var permissionsLst = mutableListOf<String>()

        if(checkSelfPermission(android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)permissionsLst.add(android.Manifest.permission.CAMERA)
        if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)permissionsLst.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)permissionsLst.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if(permissionsLst.size>0)
        {
            requestPermissions(permissionsLst.toTypedArray(),101)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        grantResults.forEach {
            if(it!= PackageManager.PERMISSION_GRANTED)
            {
                get_permissions()
            }
        }
    }
    fun Replace(fragment: Fragment, title:String)
    {
        val fragmentManager=supportFragmentManager
        val transcation =fragmentManager.beginTransaction()
        transcation.replace(R.id.frame,fragment)
        transcation.commit()
        drawerLayout.closeDrawers()
        setTitle(title)

    }

}