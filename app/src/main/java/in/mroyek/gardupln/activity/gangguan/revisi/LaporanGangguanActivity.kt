package `in`.mroyek.gardupln.activity.gangguan.revisi

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.model.Sinyal
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_laporan_gangguan.*


class LaporanGangguanActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var adapter: LaporanGangguanAdapter
    private val db: FirebaseFirestore? = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_gangguan)
        btnOpenAddSinyal.setOnClickListener(this)
        initList()
        loadDatabase()
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnOpenAddSinyal -> {
                Toast.makeText(this, "ADD SINYAL", Toast.LENGTH_SHORT).show()
                initDialog()
            }
            R.id.btnAddSinyal -> { upload() }
        }
    }

    fun initDialog() {
        val dialogBuilder = AlertDialog.Builder(this).create()
        val dialogView = layoutInflater.inflate(R.layout.dialog_sinyal, null)
        val etAlarm = dialogView.findViewById<EditText>(R.id.etAlarm)
        val etSinyal = dialogView.findViewById<EditText>(R.id.etSinyal)
        dialogBuilder.setView(dialogView)
        dialogBuilder.show()

        dialogView.findViewById<Button>(R.id.btnAddSinyal).setOnClickListener {
            val alarm = etAlarm.text.toString()
            val sinyal = etSinyal.text.toString()

            if (checkDuplicate(alarm)) {
                db!!.collection("Sinyal").document("alarm_$alarm").update("sinyal", FieldValue.arrayUnion(sinyal))
            } else {
                val doc = hashMapOf(
                        "tanggal" to "alarm_$alarm",
                        "alarm" to alarm.trim(),
                        "sinyal" to arrayListOf(sinyal.trim()))
                db!!.collection("Sinyal").document("alarm_$alarm").set(doc)
            }
        }
    }

    fun initList() {
        val layoutSinyal = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvSinyal.layoutManager = layoutSinyal
    }

    fun loadDatabase() {
        val query: Query = db!!.collection("Sinyal")
        val response = FirestoreRecyclerOptions.Builder<Sinyal>()
                .setQuery(query, Sinyal::class.java)
                .build()

        Log.d("HELLO", "DATA = ${response}")
        adapter = LaporanGangguanAdapter(response)
        //TODO: BUAT LIST SINYAL, BELUM MUNCUL (DONE)
        adapter.notifyDataSetChanged()
        rvSinyal.adapter = adapter

        //LOAD SINYAL

    }

    fun upload() {

    }

    fun checkDuplicate(alarm: String): Boolean {
        val itemCount = rvSinyal.adapter?.itemCount
        var hasil = false

        val queryTransmisi: Query = db!!.collection("Sinyal")
                .whereGreaterThan("alarm", alarm)

        //TODO iki iseh urung iso filter
        val response = FirestoreRecyclerOptions.Builder<Sinyal>()
                .setQuery(queryTransmisi, Sinyal::class.java)
                .build()
        var tes = ""
        for (i in 0..itemCount!!) {
//            Toast.makeText(applicationContext, "ANAK = ${rvSinyal.adapter?.itemCount}", Toast.LENGTH_SHORT).show()

            var alarme = rvSinyal.findViewHolderForAdapterPosition(i)?.itemView?.findViewById<TextView>(R.id.tvAlarm)?.text.toString()

            tes = "$tes, $alarme"

            if (alarm.equals(alarme, true)) {
                Toast.makeText(applicationContext, "posisi = $i", Toast.LENGTH_SHORT).show()
                return true
            }

//            hasil = alarm.equals(childText.text.toString(), true)
        }

//        Toast.makeText(applicationContext, "ALARM = ${response}", Toast.LENGTH_SHORT).show()
        return hasil
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
