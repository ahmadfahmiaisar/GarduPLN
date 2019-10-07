package `in`.mroyek.gardupln.activity.beban

import `in`.mroyek.gardupln.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_history_beban.*

class HistoryBebanActivity : AppCompatActivity() {
    private var adapter: FirestoreRecyclerAdapter<BebanResponse, BebanHolder>? = null
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_beban)
        init()
        val query: Query = db.collection("Laporan Beban")
        val bebanresponse = FirestoreRecyclerOptions.Builder<BebanResponse>()
                .setQuery(query, BebanResponse::class.java).build()
        adapter = object : FirestoreRecyclerAdapter<BebanResponse, BebanHolder>(bebanresponse) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BebanHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
                return BebanHolder(view)
            }

            override fun onBindViewHolder(p0: BebanHolder, p1: Int, p2: BebanResponse) {
                p0.bindData(p2)
                p0.itemView.setOnClickListener {
                    val id = bebanresponse.snapshots.getSnapshot(p1).id
                    startActivity(Intent(applicationContext, DetailHistoryBebanActivity::class.java).putExtra("idbeban", id))
                }
            }
        }
        adapter!!.notifyDataSetChanged()
        rv_history_laporan_beban.adapter = adapter
    }

    private fun init() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_history_laporan_beban.layoutManager = linearLayoutManager
    }

    class BebanHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val tvTanggal: TextView = view.findViewById(R.id.item_history_tanggal)
        private val tvJam: TextView = view.findViewById(R.id.item_history_jam)
        fun bindData(response: BebanResponse) {
            tvTanggal.text = response.tanggal
            tvJam.text = response.waktu
        }

    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }
}
