package com.example.das_challenge_2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.das_challenge_2.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setBottomNavigationView()

        // Write a message to the database
        /* val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("dasguia2")

        myRef.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(baseContext, p0.message, Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(p0: DataSnapshot) {
               val value = p0.getValue(String::class.java)
                Toast.makeText(baseContext, value, Toast.LENGTH_SHORT).show()
            }

        })*/
        //myRef.setValue("Hello, World!")*/

    }

    private fun setBottomNavigationView() {
        bottomNavigation.setupWithNavController(Navigation.findNavController(this, R.id.navHostFragment))
    }

}
