package com.example.das_challenge_2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.das_challenge_2.R
import com.example.das_challenge_2.models.ProductModel
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setBottomNavigationView()

        /*val reference = FirebaseDatabase.getInstance().getReference("products")
        val id = reference.push().key

        reference.child(id!!).setValue(ProductModel(
            id,
            "XBOX ONE",
            "Videogame console",
            10,
            3,
            299.99,
            "https://exitocol.vteximg.com.br/arquivos/ids/254567-1920-auto?width=1920&height=auto&aspect=true"))*/


    }

    private fun setBottomNavigationView() {
        bottomNavigation.setupWithNavController(Navigation.findNavController(this, R.id.navHostFragment))
    }

}
