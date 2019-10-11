package `in`.mroyek.gardupln.adapter

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.activity.beban.LaporanBebanResponses
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*

class LaporanBebanAdapter : FirestoreRecyclerAdapter<LaporanBebanResponses,LaporanBebanAdapter.TransmisiHolder>{
    private val db: FirebaseFirestore? = FirebaseFirestore.getInstance()

    constructor(options: FirestoreRecyclerOptions<LaporanBebanResponses>) : super(options)

    class TransmisiHolder(viewTransmisi: View) : RecyclerView.ViewHolder(viewTransmisi) {
        var tvTransmisi: TextView = viewTransmisi.findViewById(R.id.tv_beban_transmisi)
        val id: String = UUID.randomUUID().toString()
        var ettransmisi_i: EditText = viewTransmisi.findViewById(R.id.et_transmisi_I)
        var ettransmisi_u: EditText = viewTransmisi.findViewById(R.id.et_transmisi_U)
        @SuppressLint("ResourceType")
        fun bindData(response: LaporanBebanResponses) {
            tvTransmisi.text = response.namabay.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransmisiHolder {
        val viewTransmisi = LayoutInflater.from(parent.context).inflate(R.layout.item_transmisi, parent, false)
        return TransmisiHolder(viewTransmisi)
    }

    override fun onBindViewHolder(p0: TransmisiHolder, p1: Int, p2: LaporanBebanResponses) {
        p0.bindData(p2)
        p0.ettransmisi_i.text.toString()
    }

    val queryTransmisi: Query = db!!.collection("Bay")
            .whereGreaterThan("namabay", "transmisi")
            .whereGreaterThan("namabay", "trafo")

    fun transmisiOption() : FirestoreRecyclerOptions<LaporanBebanResponses> {
       val transmisi = FirestoreRecyclerOptions.Builder<LaporanBebanResponses>()
                .setQuery(queryTransmisi, LaporanBebanResponses::class.java)
                .build()

        return transmisi
    }

    /*val transmisiOptions = FirestoreRecyclerOptions.Builder<LaporanBebanResponses>()
    .setQuery(queryTransmisi, LaporanBebanResponses::class.java)
    .build() */

}

