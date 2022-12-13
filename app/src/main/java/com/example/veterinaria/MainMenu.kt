package com.example.veterinaria

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.veterinaria.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainMenu : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

        auth = Firebase.auth
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_expediente ->  {
                val intent = Intent(this, MainProducto::class.java)
                startActivity(intent)
            }
            R.id.action_producto -> Toast.makeText(this, "Salir", Toast.LENGTH_SHORT).show()
            R.id.action_perfil ->  {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            R.id.action_cerrar_sesion -> {
                auth.signOut()
                val intent = Intent(this, SignInActivity::class.java)
                this.startActivity(intent)
            }

        }
            return super.onOptionsItemSelected(item)
        }
    }