package com.example.veterinaria

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.veterinaria.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.videogame_content.view.*

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue

import kotlinx.android.synthetic.main.activity_main2.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private val fileResult = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        updateUI()

        binding.updateProfileAppCompatButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()

            updateProfile(name)
        }

        binding.profileImageView.setOnClickListener {
            fileManager()
        }

        binding.deleteAccountTextView.setOnClickListener {
            val intent = Intent(this, DeleteAccountActivity::class.java)
            this.startActivity(intent)
        }

        binding.updatePasswordTextView.setOnClickListener {
            val intent = Intent(this, UpdatePasswordActivity::class.java)
            this.startActivity(intent)
        }

        binding.signOutImageView.setOnClickListener {
            signOut()
        }

    }

    private  fun updateProfile (name : String) {

        val user = auth.currentUser

        val profileUpdates = userProfileChangeRequest {
            displayName = name
        }

        user!!.updateProfile(profileUpdates)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Se realizaron los cambios correctamente.",
                        Toast.LENGTH_SHORT).show()
                    updateUI()
                }
            }
    }
//
//
//    private fun onRedirect(){
//        binding.buttonClickAppCompatButton.setOnClickListener {
////            val buttonClick = findViewById<Button>(R.id.button_click)
////            buttonClick.setOnClickListener {
//                val intent = Intent(this, MainProducto::class.java)
//                startActivity(intent)
////            }
//        }
//    }


    private fun fileManager() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, fileResult)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == fileResult) {
            if (resultCode == RESULT_OK && data != null) {
                val uri = data.data

                uri?.let { imageUpload(it) }

            }
        }
    }

    private fun imageUpload(mUri: Uri) {

        val user = auth.currentUser
        val folder: StorageReference = FirebaseStorage.getInstance().reference.child("Users")
        val fileName: StorageReference = folder.child("img"+user!!.uid)

        fileName.putFile(mUri).addOnSuccessListener {
            fileName.downloadUrl.addOnSuccessListener { uri ->

                val profileUpdates = userProfileChangeRequest {
                    photoUri = Uri.parse(uri.toString())
                }

                user.updateProfile(profileUpdates)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Se realizaron los cambios correctamente.",
                                Toast.LENGTH_SHORT).show()
                            updateUI()
                        }
                    }
            }
        }.addOnFailureListener {
            Log.i("TAG", "file upload error")
        }
    }



    private  fun updateUI () {
        val user = auth.currentUser

        if (user != null){
            binding.emailTextView.text = user.email

            if(user.displayName != null){
                binding.nameTextView.text = user.displayName
                binding.nameEditText.setText(user.displayName)
            }

            Glide
                .with(this)
                .load(user.photoUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_profile)
                .into(binding.profileImageView)
            Glide
                .with(this)
                .load(user.photoUrl)
                .centerCrop()
                .placeholder(R.drawable.ic_profile)
                .into(binding.bgProfileImageView)
        }

    }

    private  fun signOut(){
        auth.signOut()
        val intent = Intent(this, SignInActivity::class.java)
        this.startActivity(intent)
    }

//
//    private val database = Firebase.database
//    private lateinit var messagesListener: ValueEventListener
//    private val listVideogames:MutableList<Videogame> = ArrayList()
//    val myRef = database.getReference("videogame")
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main2)
//
//        newFloatingActionButton.setOnClickListener { v ->
//            val intent = Intent(this, AddActivity::class.java)
//            v.context.startActivity(intent)
//        }
//
//        listVideogames.clear()
//        setupRecyclerView(videogameRecyclerView)
//
//    }
//
//    private fun setupRecyclerView(recyclerView: RecyclerView) {
//
//        messagesListener = object : ValueEventListener {
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                listVideogames.clear()
//                dataSnapshot.children.forEach { child ->
//                    val videogame: Videogame? =
//                        Videogame(child.child("name").getValue<String>(),
//                            child.child("date").getValue<String>(),
//                            child.child("description").getValue<String>(),
//                            child.child("url").getValue<String>(),
//                            child.key)
//                    videogame?.let { listVideogames.add(it) }
//                }
//                recyclerView.adapter = VideogameViewAdapter(listVideogames)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.e("TAG", "messages:onCancelled: ${error.message}")
//            }
//        }
//        myRef.addValueEventListener(messagesListener)
//
//        deleteSwipe(recyclerView)
//    }
//
//    class VideogameViewAdapter(private val values: List<Videogame>) :
//        RecyclerView.Adapter<VideogameViewAdapter.ViewHolder>() {
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//            val view = LayoutInflater.from(parent.context)
//                .inflate(R.layout.videogame_content, parent, false)
//            return ViewHolder(view)
//        }
//
//        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//            val videogame = values[position]
//            holder.mNameTextView.text = videogame.name
//            holder.mDateTextView.text = videogame.date
//            holder.mPosterImgeView?.let {
//                Glide.with(holder.itemView.context)
//                    .load(videogame.url)
//                    .into(it)
//            }
//
//            holder.itemView.setOnClickListener { v ->
//                val intent = Intent(v.context, VideogameDetail::class.java).apply {
//                    putExtra("key", videogame.key)
//                }
//                v.context.startActivity(intent)
//            }
//
//            holder.itemView.setOnLongClickListener{ v ->
//                val intent = Intent(v.context, EditActivity::class.java).apply {
//                    putExtra("key", videogame.key)
//                }
//                v.context.startActivity(intent)
//                true
//            }
//
//        }
//
//        override fun getItemCount() = values.size
//
//        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//            val mNameTextView: TextView = view.nameTextView
//            val mDateTextView: TextView = view.dateTextView
//            val mPosterImgeView: ImageView? = view.posterImgeView
//        }
//    }
//
//    private fun deleteSwipe(recyclerView: RecyclerView){
//        val touchHelperCallback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
//            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                listVideogames.get(viewHolder.adapterPosition).key?.let { myRef.child(it).setValue(null) }
//                listVideogames.removeAt(viewHolder.adapterPosition)
//                recyclerView.adapter?.notifyItemRemoved(viewHolder.adapterPosition)
//                recyclerView.adapter?.notifyDataSetChanged()
//            }
//        }
//        val itemTouchHelper = ItemTouchHelper(touchHelperCallback)
//        itemTouchHelper.attachToRecyclerView(recyclerView)
//    }

}