package `in`.mroyek.gardupln.activity.history.beban

/*
class ItemLaporanHistoryAdapter(val listLaporan: List<Lapor>?) : RecyclerView.Adapter<ItemLaporanHistoryAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_laporan_value, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listLaporan!!.size

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
        fun bindData(laporanValue: Lapor) {
//            transmisi.text = laporanValue.beban
            u.text = laporanValue.u.toString()
            i.text = laporanValue.i.toString()
            p.text = laporanValue.p.toString()
            q.text = laporanValue.q.toString()
            `in`.text = laporanValue.`in`.toString()
            beban.text = laporanValue.beban.toString()
        }

    }
}*/
