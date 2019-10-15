package `in`.mroyek.gardupln.activity.history

import `in`.mroyek.gardupln.R
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.item_transmisi.*


class DetailHistoryBebanActivity : AppCompatActivity() {

    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
//    lateinit var adapter: FirestoreRecyclerAdapter<HistoryBebanResponse, BebanHistoryHolder>
    lateinit var context: Context
    lateinit var tanggal: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_history_beban)
        init()
        loadData()
//        getDataHistory()
    }

    private fun loadData() {
//        private CollectionReference notebookRef = db.collection("Notebook");
        val colref: CollectionReference = db.collection("Laporin")
        colref.whereArrayContains("laporan transmisi", "100").get()
                .addOnSuccessListener { querySnapshot ->
                    var data = ""
                    for (docSnapshot in querySnapshot){
                        val response = docSnapshot.toObject(HistoryBebanResponse::class.java)
                        response.id = docSnapshot.id
                        val docid = response.id
                        data += "ID: $docid"
                        for (lapor in response.laporan.toString()){
                            data += "\n-$lapor"
                        }
                        data += "\n\n"
                    }
                    tv_beban_transmisi.text = data
                }
    }

    /*private fun getDataHistory() {
        val query = db.collection("Laporin")
                .whereGreaterThanOrEqualTo("tanggal", tanggal)
        val resposeQuery = FirestoreRecyclerOptions.Builder<HistoryBebanResponse>()
                .setQuery(query, HistoryBebanResponse::class.java)
                .build()
        adapter = object : FirestoreRecyclerAdapter<HistoryBebanResponse, BebanHistoryHolder>(resposeQuery) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BebanHistoryHolder {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history_detail_beban, parent, false)
                context = parent.context
                return BebanHistoryHolder(view)
            }

            override fun onBindViewHolder(p0: BebanHistoryHolder, p1: Int, p2: HistoryBebanResponse) {
                p0.bindData(p2, context)
            }
        }
        adapter.notifyDataSetChanged()
        rv_historyDetailBeban.adapter = adapter
    }
*/
    private fun init() {
        val intent = intent.extras
        tanggal = intent?.get("tanggal").toString()
        /*val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_historyDetailBeban.layoutManager = linearLayoutManager*/
    }

    /*class BebanHistoryHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tanggal: TextView = view.findViewById(R.id.item_detail_history_tanggal)
        val waktu: TextView = view.findViewById(R.id.item_detail_history_jam)
        val rvItemLaporanHistory: RecyclerView = view.findViewById(R.id.rv_item_laporanbeban_history)
        fun bindData(response: HistoryBebanResponse, context: Context) {
            tanggal.text = response.tanggal
            waktu.text = response.waktu
//            rvItemLaporanHistory = response.laporan
//            loadItem(context, response.laporan)
        }

        private fun loadItem(context: Context, laporan: List<String>?) {
            var adapter: ItemLaporanHistoryAdapter
            val layoutmanager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rvItemLaporanHistory.layoutManager = layoutmanager

            adapter = ItemLaporanHistoryAdapter(laporan)
            rvItemLaporanHistory.adapter = adapter
        }
    }*/

    /*override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }*/
}
