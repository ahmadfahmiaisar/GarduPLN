package `in`.mroyek.gardupln.activity

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.activity.crud.CrudBayActivity
import `in`.mroyek.gardupln.activity.inspeksi1.Diameter1Activity
import `in`.mroyek.gardupln.activity.inspeksi1.Trafo1Activity
import `in`.mroyek.gardupln.activity.inspeksi1.Transmisi1Activity
import `in`.mroyek.gardupln.activity.inspeksi2.Diameter2Activity
import `in`.mroyek.gardupln.activity.inspeksi2.Trafo2Activity
import `in`.mroyek.gardupln.activity.inspeksi2.Transmisi2Activity
import `in`.mroyek.gardupln.key
import `in`.mroyek.gardupln.model.BayResponse
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
    lateinit var idgardu: String
    lateinit var gardu: String
    lateinit var idbay: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bay)

        if (intent.extras != null) {
            val bundle = intent.extras
            idgardu = bundle!!.getString(key.ID).toString()
            gardu = bundle.getString("gardu").toString()
        }
        tv_title_gardu.text = "$idgardu , $gardu"
        init()
        showBay(idgardu)

        btn_ke_gardu.setOnClickListener { startActivity(Intent(applicationContext, GarduActivity::class.java)) }
        btn_tambahBay.setOnClickListener {
            val pindahin = Intent(this, CrudBayActivity::class.java)
            pindahin.putExtra(key.ID, idgardu)
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
                idbay = bayResponse.snapshots.getSnapshot(position).id
                holder.bindData(response)
                holder.itemView.setOnClickListener {
                    choiceDialog(response.bay!!, idgardu, idbay)
                }
            }
        }
        adapter!!.notifyDataSetChanged()
        rv_bay.adapter = adapter
    }

    private fun choiceDialog(bay: String, idgardu: String, idbay: String) {
        val builder = AlertDialog.Builder(this)
        val options = arrayOf<CharSequence>("Inspeksi Level 1", "Inspeksi Level 2", "Laporan Beban", "Laporan Gangguan")
        builder.setItems(options) { _, which ->
            val choice = options[which]
            when {
                choice.contains("Inspeksi Level 1") -> when {
                    bay.contains("transmisi") -> {
                       pindahin("transmisi", idgardu, idbay, Intent(applicationContext, Transmisi1Activity::class.java))
                    }
                    bay.contains("diameter") -> {
                       pindahin("diameter", idgardu, idbay, Intent(applicationContext, Diameter1Activity::class.java))
                    }
                    bay.contains("trafo") -> {
                        pindahin("trafo", idgardu , idbay, Intent(applicationContext, Trafo1Activity::class.java))
                    }
                }
                choice.contains("Inspeksi Level 2") -> when {
                    bay.contains("transmisi") -> startActivity(Intent(applicationContext, Transmisi2Activity::class.java))
                    bay.contains("diameter") -> startActivity(Intent(applicationContext, Diameter2Activity::class.java))
                    bay.contains("trafo") -> startActivity(Intent(applicationContext, Trafo2Activity::class.java))
                }
                choice.contains("Laporan Beban") -> when {
                    /*bay.contains("transmisi") -> startActivity(Intent(applicationContext, Transmisi1Activity::class.java))
                    bay.contains("diameter") -> startActivity(Intent(applicationContext, Transmisi1Activity::class.java))
                    bay.contains("trafo") -> startActivity(Intent(applicationContext, Transmisi1Activity::class.java))*/
                }
                choice.contains("Laporan Gangguan") -> when {
                    /*bay.contains("transmisi") -> startActivity(Intent(applicationContext, Transmisi1Activity::class.java))
                    bay.contains("diameter") -> startActivity(Intent(applicationContext, Transmisi1Activity::class.java))
                    bay.contains("trafo") -> startActivity(Intent(applicationContext, Transmisi1Activity::class.java))*/
                }
            }
        }
        builder.show()
    }

    private fun pindahin(value: String, idgardu: String, idbay: String, intent: Intent) {
        intent.putExtra("title", value)
        intent.putExtra("idgardu", idgardu)
        intent.putExtra("idbay", idbay)
        startActivity(intent)
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
