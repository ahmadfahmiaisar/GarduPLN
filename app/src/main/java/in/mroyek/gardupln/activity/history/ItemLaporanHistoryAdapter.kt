package `in`.mroyek.gardupln.activity.history

import `in`.mroyek.gardupln.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ItemLaporanHistoryAdapter(val listLaporan: List<String>?) : RecyclerView.Adapter<ItemLaporanHistoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_laporan_value, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listLaporan?.count()!!

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(listLaporan!!.get(position))
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val transmisi: TextView = view.findViewById(R.id.tv_item_title_laporan)
        val u: TextView = view.findViewById(R.id.tv_item_U)
        val i: TextView = view.findViewById(R.id.tv_item_I)
        val p: TextView = view.findViewById(R.id.tv_item_P)
        val q: TextView = view.findViewById(R.id.tv_item_Q)
        val `in`: TextView = view.findViewById(R.id.tv_item_In)
        val beban: TextView = view.findViewById(R.id.tv_item_beban)
        fun bindData(laporanValue: String) {
            transmisi.text = laporanValue.toString()
            u.text = laporanValue.toString()
            i.text = laporanValue.toString()
            p.text = laporanValue.toString()
            q.text = laporanValue.toString()
            `in`.text = laporanValue.toString()
            beban.text = laporanValue.toString()
        }

    }
}