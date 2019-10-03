package `in`.mroyek.gardupln.model

//data class BayResponse(val idgardu: String, val namabay: String)

class BayResponse {
    var id: String? = null
    var namabay: String? = null

    constructor(){}
    constructor(id: String?, bay: String?) {
        this.id = id
        this.namabay = bay
    }

}