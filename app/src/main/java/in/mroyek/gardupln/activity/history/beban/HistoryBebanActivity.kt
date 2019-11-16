package `in`.mroyek.gardupln.activity.history.beban

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.activity.GarduActivity
import `in`.mroyek.gardupln.activity.beban.LaporanBebanResponses
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_history_beban.*

class HistoryBebanActivity : AppCompatActivity() {
    lateinit var adapter: FirestoreRecyclerAdapter<LaporanBebanResponses, BebanHolder>
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var idgardu: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_beban)
        val sharePref: SharedPreferences = getSharedPreferences("idgardunya", 0)
        idgardu = sharePref.getString(GarduActivity.IDGARDU, "").toString()
        init()
        /* val intent = intent.extras
         val date = intent?.getString("tanggal")*/

//        val query: Query = db.collection("Laporin").document(date.toString()).collection("Laporr")
        val query: Query = db.collection("Gardu").document(idgardu).collection("Laporin")
        val bebanresponse = FirestoreRecyclerOptions.Builder<LaporanBebanResponses>()
                .setQuery(query, LaporanBebanResponses::class.java).build()
        adapter = object : FirestoreRecyclerAdapter<LaporanBebanResponses, BebanHolder>(bebanresponse) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BebanHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date_gangguan, parent, false)
                return BebanHolder(view)
            }

            override fun onBindViewHolder(p0: BebanHolder, p1: Int, p2: LaporanBebanResponses) {
                p0.bindData(p0.itemView.context, p2)
                val tgl = p2.tanggal.toString()
                val waktu = p2.waktu.toString()
                p0.itemView.setOnClickListener {
                    //                    val tanggal = bebanresponse.snapshots.getSnapshot(p1).tanggal
                    startActivity(Intent(applicationContext, DetailHistoryBebanActivity::class.java).putExtra("tanggal", tgl).putExtra("waktu", waktu))
                }
                /*p0.btnHapus.setOnClickListener {
                }*/
            }
        }
        adapter.notifyDataSetChanged()
        rv_history_laporan_beban.adapter = adapter
    }

    private fun init() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_history_laporan_beban.layoutManager = linearLayoutManager
    }

    inner class BebanHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTanggal: TextView = view.findViewById(R.id.tv_gangguan_item_history_tanggal)
        private val tvJam: TextView = view.findViewById(R.id.tv_gangguan_item_history_jam)
        val btnHapus: ImageButton = view.findViewById(R.id.btnHapus_historyGangguan)
        fun bindData(context: Context, response: LaporanBebanResponses) {
            if (response.tanggal == response.tanggal) {
                tvTanggal.text = response.tanggal
                tvJam.text = response.waktu
            }
            btnHapus.setOnClickListener {
                hapusDokumen(response.tanggal, response.waktu)
            }
        }

        private fun hapusDokumen(tanggal: String?, waktu: String?) {
            var deleted = 0
            val koleksi: Query = db.collection("Gardu").document(idgardu).collection("Laporin").document("$tanggal $waktu").collection("Laporr")
            koleksi.get()
                    .addOnCompleteListener {
                        for (doc in it.result?.documents!!) {
                            doc.reference.delete()
                            ++deleted
                        }
                    }
            db.collection("Gardu").document(idgardu).collection("Laporin").document("$tanggal $waktu").delete()
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}
