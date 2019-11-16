package `in`.mroyek.gardupln.activity.crud

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.activity.GarduActivity
import `in`.mroyek.gardupln.model.GarduResponse
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_crud_gardu.*
import java.util.*

class CrudGarduActivity : AppCompatActivity() {

     private var db: FirebaseFirestore? = FirebaseFirestore.getInstance()
     lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crud_gardu)

        btn_tambahGardu.setOnClickListener {
            val tanggal: String = UUID.randomUUID().toString()
            val etGardu = et_tambah_gardu.text.toString().trim()
            val doc = hashMapOf(
                    "idgardu" to tanggal,
                    "gardu" to etGardu
            )
            db!!.collection("Gardu").document().set(doc)
                    .addOnSuccessListener {
                        Toast.makeText(applicationContext, "okeh", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(applicationContext, GarduActivity::class.java))
                    }
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("log", "onstopgardu")
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
        Log.d("log", "ondestroygardu")
    }
}
