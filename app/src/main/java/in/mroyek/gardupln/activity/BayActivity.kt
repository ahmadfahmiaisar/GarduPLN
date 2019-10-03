package `in`.mroyek.gardupln.activity

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.activity.crud.CrudBayActivity
import `in`.mroyek.gardupln.model.BayResponse
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_bay.*

class BayActivity : AppCompatActivity() {

    private var adapter: FirestoreRecyclerAdapter<BayResponse, BayHolder>? = null
    lateinit var db: FirebaseFirestore
    private lateinit var progressDialog: ProgressDialog
    /*    lateinit var idgardu: String
        lateinit var gardu: String
        lateinit var idbay: String*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bay)

        init()
        showBay()
        /*showBay(idgardu)*/
        btn_ke_main.setOnClickListener { startActivity(Intent(applicationContext, MainMenuActivity::class.java)) }
        btn_tambahBay.setOnClickListener { startActivity(Intent(applicationContext, CrudBayActivity::class.java)) }
        /*btn_ke_gardu.setOnClickListener { startActivity(Intent(applicationContext, GarduActivity::class.java)) }*/
        /*btn_tambahBay.setOnClickListener {
            val pindahin = Intent(this, CrudBayActivity::class.java)
            pindahin.putExtra(key.ID_GARDU, idgardu)
            pindahin.putExtra("gardu", gardu)
            startActivity(pindahin)
        }*/
    }

    @SuppressLint("WrongConstant")
    private fun init() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_bay.layoutManager = linearLayoutManager
        db = FirebaseFirestore.getInstance()
        progressDialog = ProgressDialog(this)
    }

    private fun showBay() {
        val query: Query = db.collection("Bay")
        val bayResponse = FirestoreRecyclerOptions.Builder<BayResponse>()
                .setQuery(query, BayResponse::class.java)
                .build()
        adapter = object : FirestoreRecyclerAdapter<BayResponse, BayHolder>(bayResponse) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BayHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bay_layout, parent, false)
                return BayHolder(view)
            }

            override fun onBindViewHolder(holder: BayHolder, position: Int, response: BayResponse) {
                holder.bindData(response)
                holder.btnDelete.setOnClickListener {
                    val iddel = bayResponse.snapshots.getSnapshot(position).id
                    db.collection("Bay").document(iddel)
                            .delete()
                            .addOnCompleteListener { Toast.makeText(applicationContext, "okeh", Toast.LENGTH_SHORT).show() }
                            .addOnFailureListener { Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show() }
                }
            }
        }
        adapter!!.notifyDataSetChanged()
        rv_bay.adapter = adapter
    }

    class BayHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvBay: TextView = view.findViewById(R.id.tv_bay)
        val btnDelete: ImageView = view.findViewById(R.id.iv_delete_bay)
        fun bindData(response: BayResponse) {
            tvBay.text = response.namabay
        }
    }
    /*private fun choiceDialog(namabay: String, idgardu: String, idbay: String) {
        val builder = AlertDialog.Builder(this)
        val options = arrayOf<CharSequence>("Inspeksi Level 1", "Inspeksi Level 2", "Laporan Beban", "Laporan Gangguan")
        builder.setItems(options) { _, which ->
            val choice = options[which]
            when {
                choice.contains("Inspeksi Level 1") -> when {
                    namabay.contains("transmisi") -> {
                        Toast.makeText(applicationContext, "$namabay inspek 1", Toast.LENGTH_SHORT).show()
                        pindahin("transmisi", idgardu, idbay, Intent(applicationContext, Transmisi1Activity::class.java))
                    }
                    namabay.contains("diameter") -> {
                        T   oast.makeText(applicationContext, "$namabay inspek 1", Toast.LENGTH_SHORT).show()
                        pindahin("diameter", idgardu, idbay, Intent(applicationContext, Transmisi1Activity::class.java))
                    }
                    namabay.contains("trafo") -> {
                        pindahin("trafo", idgardu, idbay, Intent(applicationContext, Trafo1Activity::class.java))
                    }
                }
                choice.contains("Inspeksi Level 2") -> when {
                    namabay.contains("transmisi") -> startActivity(Intent(applicationContext, Transmisi2Activity::class.java))
                    namabay.contains("diameter") -> startActivity(Intent(applicationContext, Transmisi2Activity::class.java))
                    namabay.contains("trafo") -> startActivity(Intent(applicationContext, Transmisi2Activity::class.java))
                }
                choice.contains("Laporan Beban") -> {
                    startActivity(Intent(applicationContext, LaporanBebanActivity::class.java))
                    *//*namabay.contains("transmisi") -> startActivity(Intent(applicationContext, Transmisi1Activity::class.java))
                    namabay.contains("diameter") -> startActivity(Intent(applicationContext, Transmisi1Activity::class.java))
                    namabay.contains("trafo") -> startActivity(Intent(applicationContext, Transmisi1Activity::class.java))*//*
                }
                choice.contains("Laporan Gangguan") -> {
                    startActivity(Intent(applicationContext, LaporanGangguanActivity::class.java))
                    *//*namabay.contains("transmisi") -> startActivity(Intent(applicationContext, Transmisi1Activity::class.java))
                    namabay.contains("diameter") -> startActivity(Intent(applicationContext, Transmisi1Activity::class.java))
                    namabay.contains("trafo") -> startActivity(Intent(applicationContext, Transmisi1Activity::class.java))*//*
                }
            }
        }
        builder.show()
    }*/

    /* private fun pindahin(value: String, idgardu: String, idbay: String, intent: Intent) {
         intent.putExtra("title", value)
         intent.putExtra(key.ID_GARDU, idgardu)
         intent.putExtra(key.ID_BAY, idbay)
         startActivity(intent)
     }*/

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }

}
