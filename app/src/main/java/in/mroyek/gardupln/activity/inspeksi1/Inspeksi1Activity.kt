package `in`.mroyek.gardupln.activity.inspeksi1

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.key
import `in`.mroyek.gardupln.model.BayResponse
import android.annotation.SuppressLint
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
import kotlinx.android.synthetic.main.activity_inspeksi1.*

class Inspeksi1Activity : AppCompatActivity() {
    private var adapter: FirestoreRecyclerAdapter<BayResponse, Holder>? = null
    private var db: FirebaseFirestore? = FirebaseFirestore.getInstance()
    private lateinit var progressDialog: ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inspeksi1)
        init()
        showList()
    }

    private fun showList() {
        progressDialog.setTitle("loading...")
        progressDialog.show()
        val query: Query = db!!.collection("Bay")
        val response = FirestoreRecyclerOptions.Builder<BayResponse>()
                .setQuery(query, BayResponse::class.java)
                .build()
        adapter = object : FirestoreRecyclerAdapter<BayResponse, Holder>(response) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_bay, parent, false)
                return Holder(view)
            }

            override fun onBindViewHolder(holder: Holder, position: Int, bayResponse: BayResponse) {
                progressDialog.dismiss()
                holder.binding(bayResponse)
                holder.itemView.setOnClickListener {
                    val idbay = response.snapshots.getSnapshot(position).id
                    when {
                        bayResponse.namabay!!.contains("transmisi") -> {
                            pindahin("transmisi", idbay, Intent(applicationContext, Transmisi1Activity::class.java))
                        }
                        bayResponse.namabay!!.contains("diameter") -> {
                            pindahin("diameter", idbay, Intent(applicationContext, Transmisi1Activity::class.java))
                        }
                        bayResponse.namabay!!.contains("trafo") -> {
                            pindahin("trafo", idbay, Intent(applicationContext, Trafo1Activity::class.java))
                        }
                    }
                }
            }
        }
        adapter!!.notifyDataSetChanged()
        rv_bay_inspeksi1.adapter = adapter
    }

    private fun pindahin(value: String, idbay: String, intent: Intent) {
        intent.putExtra("title", value)
        intent.putExtra(key.ID_BAY, idbay)
        startActivity(intent)
    }

    class Holder(view: View) : RecyclerView.ViewHolder(view) {
        val tvListBay: TextView = view.findViewById(R.id.tv_list_bay)
        fun binding(response: BayResponse) {
            tvListBay.text = response.namabay
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

    @SuppressLint("WrongConstant")
    private fun init() {
        progressDialog = ProgressDialog(this)
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_bay_inspeksi1.layoutManager = linearLayoutManager
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }
}
