package `in`.mroyek.gardupln.adapter

import `in`.mroyek.gardupln.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Dibuat dengan kerjakerasbagaiquda oleh Shifu pada tanggal 10/10/2019.
 *
 */
class ItemSinyalAdapter(val items: List<String>): RecyclerView.Adapter<ItemSinyalAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_sinyal_value, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(items[position])
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val sinyalValue: TextView =view.findViewById(R.id.tvSinyalValue)

        fun bindData(sinyal_value: String){
            sinyalValue.text = sinyal_value
        }
    }
}