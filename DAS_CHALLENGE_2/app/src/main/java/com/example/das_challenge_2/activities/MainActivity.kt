package com.example.das_challenge_2.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.das_challenge_2.R
import com.example.das_challenge_2.utils.showToast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var codebarString: String = ""

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_actionbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.option_scan -> {
                startActivityForResult(Intent(baseContext, ScannerActivity::class.java), 1)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                codebarString = data?.data.toString()
                showToast(baseContext, codebarString)
            }
        }

    }

}
