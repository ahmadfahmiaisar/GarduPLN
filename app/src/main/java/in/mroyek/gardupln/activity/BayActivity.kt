package `in`.mroyek.gardupln.activity

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.activity.crud.CrudBayActivity
import `in`.mroyek.gardupln.key
import `in`.mroyek.gardupln.model.BayResponse
import `in`.mroyek.gardupln.model.GarduResponse
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import kotlinx.android.synthetic.main.item_bay_layout.*
import java.io.Serializable

class BayActivity : AppCompatActivity() {

    private var adapter: FirestoreRecyclerAdapter<BayResponse, BayHolder>? = null
    lateinit var db: FirebaseFirestore
    private lateinit var progressDialog: ProgressDialog
    lateinit var id: String
    lateinit var gardu: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bay)

        if (intent.extras != null) {
            val bundle = intent.extras
            id = bundle!!.getString(key.ID).toString()
            gardu = bundle.getString("gardu").toString()
        }
        tv_title_gardu.text = "$id , $gardu"
        init()
        showBay(id)

        btn_ke_gardu.setOnClickListener { startActivity(Intent(applicationContext, GarduActivity::class.java)) }
        btn_tambahBay.setOnClickListener {
            val pindahin = Intent(this, CrudBayActivity::class.java)
            pindahin.putExtra(key.ID, id)
            pindahin.putExtra("gardu", gardu)
            startActivity(pindahin)
        }
    }

    @SuppressLint("WrongConstant")
    private fun init() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_bay.layoutManager = linearLayoutManager
        db = FirebaseFirestore.getInstance()
        progressDialog = ProgressDialog(this)

    }

    private fun showBay(id: String) {

        val query: Query = db.collection("Gardu").document(id).collection("Bay")
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
                holder.itemView.setOnClickListener {
                    when {
                        response.bay!!.contains("transmisi") -> Toast.makeText(applicationContext, "okeeh ini transmisi", Toast.LENGTH_SHORT).show()
                        response.bay!!.contains("trafo") -> Toast.makeText(applicationContext, "okeeh ini trafo", Toast.LENGTH_SHORT).show()
                        response.bay!!.contains("diameter") -> Toast.makeText(applicationContext, "okeeh ini diameter", Toast.LENGTH_SHORT).show()
                    }
                }
            }

        }
        adapter!!.notifyDataSetChanged()
        rv_bay.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }

    class BayHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvBay: TextView = view.findViewById(R.id.tv_bay)
        fun bindData(response: BayResponse) {
            tvBay.text = response.bay
        }
    }
}
