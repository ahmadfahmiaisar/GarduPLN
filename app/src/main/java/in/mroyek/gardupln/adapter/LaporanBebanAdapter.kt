package `in`.mroyek.gardupln.adapter

import `in`.mroyek.gardupln.activity.beban.LaporanBebanActivity
import `in`.mroyek.gardupln.activity.beban.TransmisiResponse
import com.firebase.ui.firestore.FirestoreRecyclerAdapter

class LaporanBebanAdapter(val transmisiResponse: TransmisiResponse) : FirestoreRecyclerAdapter<TransmisiResponse, LaporanBebanActivity.TransmisiHolder> {

}