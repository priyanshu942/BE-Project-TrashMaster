package com.example.garbagecollection

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    lateinit var firebase : DatabaseReference
    lateinit var auth: FirebaseAuth
    lateinit var email1 : EditText
    lateinit var pass1 : EditText
    lateinit var newuser : TextView
    lateinit var login : Button

    var isuser = false
    var iscollector = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newuser=findViewById(R.id.newuser)
        login = findViewById(R.id.login)
        email1 = findViewById(R.id.EmailLogin)
        pass1 = findViewById(R.id.Loginpassword)

        newuser.setOnClickListener {
            val intent= Intent (this,Register::class.java)
            startActivity(intent)
        }

        auth = FirebaseAuth.getInstance()

        login.setOnClickListener {

           // loginuser()
           startActivity(Intent(this,User_activity_1::class.java))
            //startActivity(Intent(this,Collector_activity_1::class.java))
        }
    }

    private fun loginuser(){

        val email :String? = email1.getText().toString()
        val pass = pass1.getText().toString()

        var user : User_data
        val collector : collector_data
        var e : String? = null

        if(email!=null && Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            if(pass.isNotEmpty()){

                if (email != null) {
                    auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener {

                        if(it.isSuccessful){

                            firebase = FirebaseDatabase.getInstance().getReference("User")

                            var flag=0

                            val postListener = object : ValueEventListener {

                                override fun onDataChange(dataSnapshot: DataSnapshot) {

                                    for(i in dataSnapshot.children){

                                        e  = i.getValue<User_data>()?.email.toString()

                                        if(e == email){

                                            isuser=true
                                        }

                                        Log.d(TAG, "loadPost:onCancelled ${i.getValue<User_data>()} and ${e} and ${email}")


                                    }

                                }

                                override fun onCancelled(databaseError: DatabaseError) {
                                    // Getting Post failed, log a message
                                    Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                                }
                            }
                            firebase.addValueEventListener(postListener)

                            if(isuser){

                                startActivity(Intent(this,User_activity_1::class.java))
                            } else{

                                startActivity(Intent(this,Collector_activity_1::class.java))
                            }
                        } else{

                            Toast.makeText(this,"Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }
            else{
                pass1.setError("Please Enter Your Password!")
            }
        }
        else if(email!=null){

            email1.setError("Please Enter Your Email")
        }
        else{
            email1.setError("Please Enter Your Correct Email")
        }
    }


}