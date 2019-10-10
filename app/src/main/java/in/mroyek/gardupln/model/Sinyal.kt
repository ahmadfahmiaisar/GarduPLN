package `in`.mroyek.gardupln.model

/**
 * Dibuat dengan kerjakerasbagaiquda oleh Shifu pada tanggal 09/10/2019.
 *
 */

class Sinyal{
        var id: String? = null
        var alarm: String? = null
        lateinit var sinyal: ArrayList<String>

    constructor()
    constructor(id: String, alarm: String, sinyal: ArrayList<String>){
        this.id = id
        this.alarm
        this.sinyal = sinyal
    }
}

class Sinyale(
        val value_sinyal: String
)