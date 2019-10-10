package `in`.mroyek.gardupln.activity.gangguan

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.adapter.ItemSinyalAdapter
import `in`.mroyek.gardupln.model.Sinyal
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

/**
 * Dibuat dengan kerjakerasbagaiquda oleh Shifu pada tanggal 09/10/2019.
 *
 */
class LaporanGangguanAdapter(val options: FirestoreRecyclerOptions<Sinyal>) : FirestoreRecyclerAdapter<Sinyal, LaporanGangguanAdapter.SinyalHolder>(options) {
    lateinit private var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SinyalHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sinyal, parent, false)
        context = parent.context
        return SinyalHolder(view)
    }

    override fun onBindViewHolder(holder: SinyalHolder, position: Int, sinyal: Sinyal) {
        holder.bindData(sinyal, context)
    }


    class SinyalHolder(view: View) : RecyclerView.ViewHolder(view) {
        val alarm: TextView = view.findViewById(R.id.tvAlarm)
        val rvItemSinyal: RecyclerView = view.findViewById(R.id.rvItemSinyal)

        fun bindData(sinyal: Sinyal, context: Context) {
            alarm.text = sinyal.alarm
            loadSinyal(context, sinyal.sinyal)
        }

        fun loadSinyal(context: Context, sinyalValue: ArrayList<String>) {
            lateinit var adapter: ItemSinyalAdapter

            val layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            rvItemSinyal.layoutManager = layoutManager

            adapter = ItemSinyalAdapter(sinyalValue)
            rvItemSinyal.adapter = adapter
        }
    }


}