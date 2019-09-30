package `in`.mroyek.gardupln.model

//data class BayResponse(val idgardu: String, val bay: String)

class BayResponse {
    var id: String? = null
    var bay: String? = null

    constructor(){}
    constructor(id: String?, bay: String?) {
        this.id = id
        this.bay = bay
    }

}