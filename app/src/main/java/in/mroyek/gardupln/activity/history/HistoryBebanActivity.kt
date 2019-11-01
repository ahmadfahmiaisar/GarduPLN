package `in`.mroyek.gardupln.activity.history

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.activity.beban.LaporanBebanResponses
import android.content.Context
import android.content.Intent
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_beban)
        init()
        /* val intent = intent.extras
         val date = intent?.getString("tanggal")*/

//        val query: Query = db.collection("Laporin").document(date.toString()).collection("Laporr")
        val query: Query = db.collection("Laporin")
        val bebanresponse = FirestoreRecyclerOptions.Builder<LaporanBebanResponses>()
                .setQuery(query, LaporanBebanResponses::class.java).build()
        adapter = object : FirestoreRecyclerAdapter<LaporanBebanResponses, BebanHolder>(bebanresponse) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BebanHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
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
        private val tvTanggal: TextView = view.findViewById(R.id.item_history_tanggal)
        private val tvJam: TextView = view.findViewById(R.id.item_history_jam)
        val btnHapus: ImageButton = view.findViewById(R.id.btnHapus)
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
            db.collection("Laporin").document("$tanggal $waktu").delete()
            var deleted = 0
            val koleksi: Query = db.collection("Laporin").document("$tanggal $waktu").collection("Laporr")
            koleksi.get()
                    .addOnCompleteListener {
                        for (doc in it.result?.documents!!) {
                            doc.reference.delete()
                            ++deleted
                        }
                    }
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
