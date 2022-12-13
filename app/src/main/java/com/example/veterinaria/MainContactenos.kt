package com.example.veterinaria

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.example.veterinaria.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class MainContactenos : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contactenos)

        // Código del google maps
        val bt_google_maps = findViewById(R.id.bt_google_maps) as ImageView
        bt_google_maps.setOnClickListener {
            val intent = Intent(this, MainActivityGoogleMaps::class.java)
            startActivity(intent)
        }

        // Código del telefono
        val bt_phone = findViewById(R.id.bt_phone) as ImageView
        bt_phone.setOnClickListener {
            val number = "62618670"
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + "+506 " + number)
            startActivity(dialIntent)
        }

        // Código del Whatsapp
        val bt_whatsapp = findViewById(R.id.bt_whatsapp) as ImageView
        bt_whatsapp.setOnClickListener {
            val number = "62618670"
            val url = "https://api.whatsapp.com/send?phone=$number"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        // Código del Web
        val bt_web = findViewById(R.id.bt_web) as ImageView
        bt_web.setOnClickListener {
            val valor = "almacen.caccoronado.com/"
            if (valor.isNotEmpty()) { // si el sitio web tiene algo, se intenta enviar el correo
                val uri = "http://$valor"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
                startActivity(intent)
            } else { // no hay info, no se puede realizar la acción
                Toast.makeText(
                    this,
                    getString(R.string.msg_data), Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}