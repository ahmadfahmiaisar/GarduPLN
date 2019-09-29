package `in`.mroyek.gardupln.model

class GarduResponse{
    var id: String? = null
    var gardu: String? = null

    constructor(){}
    constructor(id: String?, gardu: String?) {
        this.id = id
        this.gardu = gardu
    }
}
