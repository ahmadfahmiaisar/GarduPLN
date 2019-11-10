package `in`.mroyek.gardupln.activity.gangguan

class LaporanGangguanResponses {
    var bay: String? = null
    var tanggal: String? = null
    var waktu: String? = null
    var signal: String? = null
    var fl: String? = null
    var catatan: String? = null
    var conPMT: String? = null
    var conGangguan: String? = null
    var conLa: String? = null
    var bpadam: String? = null
    var bnormal: String? = null
    var analisa: String? = null


    constructor(bay: String?, tanggal: String?, waktu: String?, signal: String?, fl: String?, catatan: String?, conPMT: String?, conGangguan: String?, conLa: String?, bpadam: String?, bnormal: String?, analisa: String?) {
        this.bay = bay
        this.tanggal = tanggal
        this.waktu = waktu
        this.signal = signal
        this.fl = fl
        this.catatan = catatan
        this.conPMT = conPMT
        this.conGangguan = conGangguan
        this.conLa = conLa
        this.bpadam = bpadam
        this.bnormal = bnormal
        this.analisa = analisa
    }

    constructor(){}
}