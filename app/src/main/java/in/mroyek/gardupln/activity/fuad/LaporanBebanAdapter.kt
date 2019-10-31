package `in`.mroyek.gardupln.activity.fuad

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.activity.beban.LaporanBebanResponses
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class LaporanBebanAdapter(val options: FirestoreRecyclerOptions<LaporanBebanResponses>) : FirestoreRecyclerAdapter<LaporanBebanResponses, LaporanBebanAdapter.TransmisiHolder>(options) {
    lateinit private var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransmisiHolder {
        val viewTransmisi = LayoutInflater.from(parent.context).inflate(R.layout.item_transmisi, parent, false)
        context = parent.context
        return TransmisiHolder(viewTransmisi)
    }

    override fun onBindViewHolder(transmisiHolder: TransmisiHolder, position: Int, response: LaporanBebanResponses) {
        transmisiHolder.bindData(response)
    }

    var holderHashMap: HashMap<Int, RecyclerView.ViewHolder> = HashMap()

    fun getHolder():HashMap<Int, RecyclerView.ViewHolder>{
        return holderHashMap
    }

    override fun onViewDetachedFromWindow(holder: TransmisiHolder) {
        holderHashMap.put(holder.adapterPosition, holder)
        super.onViewDetachedFromWindow(holder)
    }

    override fun onViewAttachedToWindow(holder: TransmisiHolder) {
        holderHashMap.remove(holder.adapterPosition)
        super.onViewAttachedToWindow(holder)
    }

    class TransmisiHolder(viewTransmisi: View?) : RecyclerView.ViewHolder(viewTransmisi!!) {
        var tvTransmisi: TextView = viewTransmisi!!.findViewById(R.id.tv_beban_transmisi)
        @SuppressLint("ResourceType")
        fun bindData(response: LaporanBebanResponses) {
            tvTransmisi.text = response.namabay.toString()
        }
    }
}