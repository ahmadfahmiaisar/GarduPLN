package `in`.mroyek.gardupln.activity

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.activity.crud.CrudGarduActivity
import `in`.mroyek.gardupln.key
import `in`.mroyek.gardupln.model.GarduResponse
import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import android.content.SharedPreferences
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
import kotlinx.android.synthetic.main.activity_gardu.*

class GarduActivity : AppCompatActivity() {

    companion object {
        const val IDGARDU = "ID_GARDU"
    }
    private var adapter: FirestoreRecyclerAdapter<GarduResponse, GarduHolder>? = null
    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var progressDialog: ProgressDialog
    /*   private var mDelayHandler: Handler? = null
       private val SPLASH_DELAY: Long = 3000

       internal val mRunnable: Runnable = Runnable {
           if (!isFinishing) {
               startActivity(Intent(applicationContext, MainMenuActivity::class.java))
               finish()
           }
       }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gardu)
/*        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)*/
/*    }

    override fun onDestroy() {
        if (mDelayHandler != null) {
            mDelayHandler!!.removeCallbacks(mRunnable)
        }
        super.onDestroy()
    }
}*/
//        tvTambahBay.setOnClickListener { startActivity(Intent(applicationContext, MainMenuActivity::class.java)) }
        btn_crudGardu.setOnClickListener { startActivity(Intent(applicationContext, CrudGarduActivity::class.java)) }
        init()
        showGardu()
    }

    private fun showGardu() {
        val query = db.collection("Gardu")
        val garduResponse = FirestoreRecyclerOptions.Builder<GarduResponse>()
                .setQuery(query, GarduResponse::class.java)
                .build()
        adapter = object : FirestoreRecyclerAdapter<GarduResponse, GarduHolder>(garduResponse) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GarduHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gardu_layout, parent, false)
                return GarduHolder(view)
            }

            override fun onBindViewHolder(holder: GarduHolder, position: Int, response: GarduResponse) {
                holder.bindData(response)
                holder.btnDelete.setOnClickListener {
                    val id = garduResponse.snapshots.getSnapshot(position).id
                   /* var deleted = 0
                    val koleksi: Query = db.collection("Gardu").document(id).collection("Laporin").document("$tanggal $waktu").collection("Laporr")
                    koleksi.get()
                            .addOnCompleteListener {
                                for (doc in it.result?.documents!!) {
                                    doc.reference.delete()
                                    ++deleted
                                }
                            }*/
                    //TODO delete gardu
                    db.collection("Gardu").document(id)
                            .delete()
                            .addOnFailureListener { Toast.makeText(applicationContext, "gagal", Toast.LENGTH_SHORT).show() }
                            .addOnCompleteListener { Toast.makeText(applicationContext, "okeh", Toast.LENGTH_SHORT).show() }
                }
                holder.tvGardu.setOnClickListener {
                    val id = garduResponse.snapshots.getSnapshot(position).id
                    val sharPref: SharedPreferences = getSharedPreferences("idgardunya", 0)
                    val editor: SharedPreferences.Editor = sharPref.edit()
                    editor.putString(IDGARDU, id)
                    editor.apply()
                    val intent = Intent(this@GarduActivity, MainMenuActivity::class.java)
//                    intent.putExtra(IDGARDU, id)
                    startActivity(intent)
                }
            }

        }
        adapter!!.notifyDataSetChanged()
        rv_gardu.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }

    class GarduHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvGardu = itemView.findViewById<TextView>(R.id.tv_gardu)
        var btnDelete = itemView.findViewById<ImageView>(R.id.iv_delete_gardu)
        //        var btn
        fun bindData(response: GarduResponse) {
            tvGardu.text = response.gardu
        }

    }

    @SuppressLint("WrongConstant")
    private fun init() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_gardu.layoutManager = linearLayoutManager
        progressDialog = ProgressDialog(this)
    }
}
