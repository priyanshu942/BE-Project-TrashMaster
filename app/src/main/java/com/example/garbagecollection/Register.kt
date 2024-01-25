package com.example.garbagecollection

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.lang.System.err

class Register : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    lateinit var name : EditText
    lateinit var mobno : EditText
    lateinit var email1 : EditText
    lateinit var create_pass : EditText
    lateinit var confirm_pass : EditText
    lateinit var coll_id : EditText
    lateinit var register : Button

    var iscollector : Boolean = false

    lateinit var firebase : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val languages = resources.getStringArray(R.array.Languages)

        // access the spinner
        coll_id = findViewById(R.id.collector_id)

        val spinner = findViewById<Spinner>(R.id.spinner1)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, languages)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {

                    if(languages[position]=="User"){

                        coll_id.visibility= View.INVISIBLE
                        iscollector = false
                    }
                    else{
                        coll_id.visibility= View.VISIBLE
                        iscollector = true
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }

        auth = FirebaseAuth.getInstance()
        name = findViewById(R.id.PersonName)
        mobno = findViewById(R.id.phonenumber)


        email1 = findViewById(R.id.email)
        create_pass = findViewById(R.id.rpasw)
        confirm_pass = findViewById(R.id.rcpas)

        register = findViewById(R.id.register_butt)


        val alreadyHaveAnAccount: TextView = findViewById(R.id.alreadyaccount)

        alreadyHaveAnAccount.setOnClickListener {
            val intent= Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        register.setOnClickListener {

            createuser()
        }
    }

    private fun createuser(){

        val email = email1.getText().toString()
        val pass = confirm_pass.getText().toString()
        val pass2 = create_pass.getText().toString()
        val name = name.getText().toString()
        val mob = mobno.getText().toString()

        if(email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){

            if(pass.isNotEmpty() && pass==pass2){

                auth.createUserWithEmailAndPassword(email,pass)

                    .addOnCompleteListener {

                        if(it.isSuccessful){

                            Toast.makeText(this,"Registered Successfully", Toast.LENGTH_SHORT).show()

                            if(iscollector){

                                firebase = Firebase.database.reference
                                //val id = firebase.push().key!!

                                val collector_id = coll_id.getText().toString()
                                val collector = collector_data(name,mob,email,pass,collector_id)

                                firebase.child("Collector").child(mob).setValue(collector)
                                    .addOnCompleteListener {
                                        Toast.makeText(this,"Data Inserted Successfully",Toast.LENGTH_LONG).show()
                                        val intent = Intent(this,MainActivity::class.java)
                                        startActivity(intent)
                                    }
                                    .addOnFailureListener { err->
                                        Toast.makeText(this,"Error ${err.message} ",Toast.LENGTH_LONG).show()
                                    }


                            }
                            else{

                                //firebase = FirebaseDatabase.getInstance().getReference("User")
                                //val id = firebase.push().key!!

                                firebase = Firebase.database.reference

                                val user = User_data(name,mob,email,pass)

                                firebase.child("User").child(mob).setValue(user)
                                    .addOnCompleteListener {
                                        Toast.makeText(this,"Data Inserted Successfully",Toast.LENGTH_LONG).show()
                                        val intent = Intent(this,MainActivity::class.java)
                                        startActivity(intent)
                                    }
                                    .addOnFailureListener { err->
                                        Toast.makeText(this,"Error ${err.message} ",Toast.LENGTH_LONG).show()
                                    }
                            }


                        }
                        else{

                            Toast.makeText(this,it.exception.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }

                    .addOnFailureListener {

                        Toast.makeText(this, "Registration Error!! Try Again", Toast.LENGTH_SHORT)
                            .show()
                    }
            }
            else if(pass != pass2){

                confirm_pass.setError("Plaese Enter Correct Password!")
            }
            else{
                confirm_pass.setError("Please Enter Your Password!")
            }
        }
        else if(email.isEmpty()){

            email1.setError("Please Enter Your Email")
        }
        else{
            email1.setError("Please Enter Your Correct Email")
        }
    }
}