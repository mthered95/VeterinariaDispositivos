package com.example.veterinaria

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap

class MainActivityGoogleMaps : AppCompatActivity() {
//    //google maps
    private  lateinit var map: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contactenos)

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
//        createFragment()
    }


//
///funcion google maps
//  private fun createFragment() {
//    val mapFragment : SupportMapFragment = supportFragmentManager.findFragmentById(R.id.frameLayout) as SupportMapFragment
//    mapFragment.getMapAsync(this)
//}
//
//    override fun onMapReady(googleMap: GoogleMap) {
//        map = googleMap
//        createMarker()
//    }
//    //animacion mapa
//    private fun createMarker() {
//        val coordinates = LatLng(9.86279411646072, -83.91765302764719)
//        val marker = MarkerOptions().position(coordinates).title("Veterinaria Dispositivos")
//        map.addMarker(marker)
//        map.animateCamera(
//            CameraUpdateFactory.newLatLngZoom(coordinates, 18f),
//            4000,
//            null
//        )
//    }

//end mapa //

    // funciones contactenos
//    val test: ImageButton = findViewById<ImageButton>(R.id.bt_whatsapp)
//
//    private fun enviarWhatsApp() {
//        val valor = "62618670"
//        if(valor.isNotEmpty()){ // si el correo tiene algo, se intenta enviar el correo
//            val intent = Intent(Intent.ACTION_SEND)
//            val uri = "whatsapp://send?phone=506$valor&text="+
//                    getString(R.string.msg_saludos)
//            intent.setPackage("com.whatsapp")
//            intent.data = Uri.parse(uri)
//
//            intent.putExtra(Intent.EXTRA_TEXT,
//                getString(R.string.msg_mensaje_correo))
//            startActivity(intent)
//        } else { // no hay info, no se puede realizar la acción
//            Toast.makeText(
//                this,
//                getString(R.string.msg_data), Toast.LENGTH_LONG
//            ).show()
//        }
//    }

}