package com.sherazsadiq.workwise


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.database.FirebaseDatabase
import java.io.Serializable

data class User(
    var name: String = "",
    var email: String = "",
    var contactNumber: String = "",
    var country: String = "",
    var city: String = "",
    var password: String = "",
    var profilePicture: String? = "",
    var id: String = "",
    var Type: String = ""
): Serializable
class SignUpActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "WrongViewCast")


    private var auth: FirebaseAuth=FirebaseAuth.getInstance()


    @SuppressLint("MissingInflatedId", "WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val em: EditText = findViewById(R.id.Email)
        val pass: EditText = findViewById(R.id.Password)
        val Confirm_pass: EditText = findViewById(R.id.Confirmpassword)

        val id = auth.currentUser?.uid.toString()
        val countrySpinner: Spinner = findViewById(R.id.Country)

        val citySpinner: Spinner = findViewById(R.id.City)

        val countriesArray: Array<String> = resources.getStringArray(R.array.countries)
        val ChoiceArray: Array<String> = resources.getStringArray(R.array.Choices)
        val countryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countriesArray)
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        countrySpinner.adapter = countryAdapter




        countrySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                updateCitySpinner(position)
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }



        val ChoiceSpinner: Spinner = findViewById(R.id.Choice)
        val ChoiceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ChoiceArray)
        ChoiceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        ChoiceSpinner.adapter = ChoiceAdapter


        ChoiceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
            }
        }


        val SigninButton: TextView = findViewById(R.id.Login)
        // Set onClickListener for the button
        SigninButton.setOnClickListener { // Handle the button click event
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        val SignUp_Button = findViewById<TextView>(R.id.Signup)
        SignUp_Button.setOnClickListener {

            val contactNumber = findViewById<EditText>(R.id.Phonenumber).text.toString()
            val name = findViewById<EditText>(R.id.Name).text.toString()
            val country=countrySpinner.selectedItem.toString()
            val city=citySpinner.selectedItem.toString()
            val Type=ChoiceSpinner.selectedItem.toString()
            val emailValue = em.text.toString()
            val passValue = pass.text.toString()
            val Confirm_passValue = Confirm_pass.text.toString()
            //get the id of the user
            if(TextUtils.isEmpty(emailValue))
            {
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(passValue))
            {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(Confirm_passValue != passValue)
            {
                Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(name))
            {
                Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            signup(name,emailValue,contactNumber,country,city,passValue,id,Type)

        }




    }
    fun signup(name: String, email: String, contactNumber: String, country: String, city: String, password: String, id: String, Type: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("TAG", "createUserWithEmail:success")

                    val user = auth.currentUser
                    storeInDatabase(name, email, contactNumber, country, city, password, id, Type)
                    if (Type == "Client") {
                        val secondActivityIntent = Intent(this, ClientHomeActivity::class.java)
                        startActivity(secondActivityIntent)
                    } else {
                        val secondActivityIntent = Intent(this, SellerHomeActivity::class.java)
                        startActivity(secondActivityIntent)
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    if (task.exception is FirebaseAuthUserCollisionException) {
                        Log.w("TAG", "createUserWithEmail:failure - email already in use")
                    } else {
                        Log.w("TAG", "createUserWithEmail:failure", task.exception)
                    }
                }
            }
    }

    private fun storeInDatabase(name: String, email: String, contactNumber: String, country: String, city: String, password: String,id: String, Type: String) {
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("Users")

        val user = User(name, email, contactNumber, country, city, password,"",id, Type)
        myRef.child(auth.currentUser?.uid.toString()).child("UserInfo").setValue(user)
            .addOnSuccessListener {
                Log.d("TAG", "User data stored successfully")
                val userRef = FirebaseDatabase.getInstance().getReference("Users").child(auth.currentUser?.uid.toString()).child("UserInfo")
                val id1 = auth.currentUser?.uid.toString()
                userRef.child("id").setValue(id1)
            }
            .addOnFailureListener { e ->
                Log.e("TAG", "Failed to store user data", e)
            }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun updateCitySpinner(countryPosition: Int) {
        val citySpinner: Spinner = findViewById(R.id.City)

        val citiesArrayResourceId = when (countryPosition) {
            0 -> R.array.cities_usa
            1 -> R.array.cities_canada
            2 -> R.array.cities_uk
            3 -> R.array.cities_germany
            4 -> R.array.cities_france
            else -> 0
        }

        if (citiesArrayResourceId != 0) {
            val citiesArray: Array<String> = resources.getStringArray(citiesArrayResourceId)
            val cityAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, citiesArray)
            cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            citySpinner.adapter = cityAdapter
        }
    }
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroyEvent() {
        // Call logout function when the app is being destroyed
        logout()
    }

    private fun logout() {
        auth.signOut()
        val logoutActivityIntent = Intent(this, LoginActivity::class.java)
        startActivity(logoutActivityIntent)
    }


}