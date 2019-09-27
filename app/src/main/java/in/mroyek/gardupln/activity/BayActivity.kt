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
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_bay.*
import kotlinx.android.synthetic.main.item_bay_layout.view.*

class BayActivity : AppCompatActivity() {

    private var adapter: FirestoreRecyclerAdapter<BayResponse, BayHolder>? = null
    lateinit var db: FirebaseFirestore
    private var bayList = mutableListOf<BayResponse>()
    lateinit var progressDialog: ProgressDialog

    /*private var recyclerView: RecyclerView? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private var progressDialog: ProgressDialog? = null*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bay)
        btn_ke_gardu.setOnClickListener { startActivity(Intent(applicationContext, GarduActivity::class.java)) }
        btn_tambahBay.setOnClickListener { startActivity(Intent(applicationContext, CrudBayActivity::class.java)) }
        init()
        showBay()
    }

    @SuppressLint("WrongConstant")
    private fun init() {
//        recyclerView = rv_bay
       val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_bay.layoutManager = linearLayoutManager
        db = FirebaseFirestore.getInstance()
//        recyclerView!!.layoutManager = linearLayoutManager
//        recyclerView!!.layoutManager = LinearLayoutManager(this)
        progressDialog = ProgressDialog(applicationContext)
    }

    private fun showBay() {
        val query: Query = db!!.collection("Bay")
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

   /* private inner class BayHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvBay = itemView.tv_bay
    }*/
    class BayHolder(view: View): RecyclerView.ViewHolder(view){
       var tvBay: TextView = view.findViewById(R.id.tv_bay)
       fun bindData(response: BayResponse){
           tvBay.text = response.bay
       }
   }
}
