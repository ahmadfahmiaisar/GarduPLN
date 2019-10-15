package `in`.mroyek.gardupln.activity.history

class HistoryBebanResponse {
    var id: String? = null
    var namabay: String? = null
    var tanggal: String? = null
    var waktu: String? = null
    var laporan: List<String>? = null
//    var laporan: ArrayList<String>? = null

    constructor() {}
    constructor(id: String?, namabay: String?, tanggal: String?, waktu: String?, laporan: List<String>) {
        this.id = id
        this.namabay = namabay
        this.tanggal = tanggal
        this.waktu = waktu
        this.laporan = laporan
    }
}