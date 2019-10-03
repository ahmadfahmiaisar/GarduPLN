package `in`.mroyek.gardupln.activity.beban

class TransmisiResponse {
    var id: String? = null
    var namabay: String? = null
    var u: String? = null
    var i: String? = null
    var p: String? = null
    var q: String? = null
    var `in`: String? = null

    constructor() {}
    constructor(id: String?, namabay: String?, u: String?, i: String?, p: String?, q: String?, In: String?) {
        this.id = id
        this.namabay = namabay
        this.u = u
        this.i = i
        this.p = p
        this.q = q
        this.`in` = In
    }

}
