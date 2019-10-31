package `in`.mroyek.gardupln.activity.history

import `in`.mroyek.gardupln.R
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_detail_history_beban.*


class DetailHistoryBebanActivity : AppCompatActivity() {

    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    lateinit var adapter: FirestoreRecyclerAdapter<HistoryBebanResponse, BebanHistoryHolder>
    lateinit var context: Context
    lateinit var tanggal: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_history_beban)
        init()
//        loadData()
        getDataHistory()
        getLaporanCOunt()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.copy, menu)
        return true
    }

    @Suppress("UNREACHABLE_CODE")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.copy -> copyTextToClipboard()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun copyTextToClipboard() {
        var clipboardManager: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        var clipData: ClipData = ClipData.newPlainText("Laporan", bulkText)
        clipboardManager.setPrimaryClip(clipData)
    }

    private fun getLaporanCOunt() {
        val query = db.collection("Laporin").document().get().addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("TESS", "HASIL ${it.result!!}")
            }
        }

/*        val resposeQuery = FirestoreRecyclerOptions.Builder<HistoryBebanResponse>()
                .setQuery(query, HistoryBebanResponse::class.java)
                .build()*/
    }

    private fun getDataHistory() {
        val query = db.collection("Laporin").document(tanggal).collection("Laporr")
        val resposeQuery = FirestoreRecyclerOptions.Builder<HistoryBebanResponse>()
                .setQuery(query, HistoryBebanResponse::class.java)
                .build()
        adapter = object : FirestoreRecyclerAdapter<HistoryBebanResponse, BebanHistoryHolder>(resposeQuery) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
            ): BebanHistoryHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_laporan_value, parent, false)
                context = parent.context
                return BebanHistoryHolder(view)
            }

            override fun onBindViewHolder(p0: BebanHistoryHolder, p1: Int, p2: HistoryBebanResponse) {
                if(!p2.namabay.equals("null")){
                    p0.bindData(p2, context)
                }
            }
        }
        adapter.notifyDataSetChanged()
        rv_historyDetailBeban.adapter = adapter
    }

    private fun init() {
        val intent = intent.extras
        tanggal = intent?.get("tanggal").toString()
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_historyDetailBeban.layoutManager = linearLayoutManager
    }

    var bulkText: String = ""

    inner class BebanHistoryHolder(view: View) : RecyclerView.ViewHolder(view) {
       /* val tanggal: TextView = view.findViewById(R.id.item_detail_history_tanggal)
        val waktu: TextView = view.findViewById(R.id.item_detail_history_jam)*/
        val u: TextView = view.findViewById(R.id.tv_item_U)
        val i: TextView = view.findViewById(R.id.tv_item_I)
        //        var rvItemLaporanHistory: RecyclerView = view.findViewById(R.id.rv_item_laporanbeban_history)
        fun bindData(response: HistoryBebanResponse, context: Context) {

            copyText(response)

            u.text = response.u
            i.text = response.i
            /*tanggal.text = response.tanggal
            waktu.text = response.waktu*/

//            rvItemLaporanHistory = response.laporan

            Log.d("TESS", "LAPORAN = ${response}")

            /* if (response.laporan != null) {
                 loadItem(context, response.laporan)
 //                Log.d("TESS", "LAPORAN = ${response.laporan}")
             }*/

        }

        /* private fun loadItem(context: Context, laporan: List<Lapor>?) {
             lateinit var adapter: ItemLaporanHistoryAdapter
             val layoutmanager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
             rvItemLaporanHistory.layoutManager = layoutmanager

             adapter = ItemLaporanHistoryAdapter(laporan)
             rvItemLaporanHistory.adapter = adapter
             adapter.notifyDataSetChanged()
         }*/
    }

    private fun copyText(response: HistoryBebanResponse) {
        bulkText += "U = ${response.u},\n"
        bulkText += "U = ${response.u},"
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
