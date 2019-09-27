package `in`.mroyek.gardupln.fragment.tambahbay


import `in`.mroyek.gardupln.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_crud_bay.*
import kotlinx.android.synthetic.main.fragment_transmisi.*
import kotlinx.android.synthetic.main.fragment_transmisi.btn_choose_transmisi
import kotlinx.android.synthetic.main.fragment_transmisi.btn_close_transmisi
import kotlinx.android.synthetic.main.fragment_transmisi.ll_transmisi
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TransmisiFragment : Fragment() {
    val db: FirebaseFirestore? = FirebaseFirestore.getInstance()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transmisi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
        btn_choose_transmisi.setOnClickListener {
            val id: String = UUID.randomUUID().toString()
            val et_bay = et_bay_transmisi.text.toString().trim()
            val doc = hashMapOf(
                    "id" to id,
                    "etBay" to et_bay
            )
            db!!.collection("Bay").document().set(doc)
                    .addOnSuccessListener { Toast.makeText(context, "okeeh", Toast.LENGTH_SHORT).show() }
                    .addOnFailureListener { Toast.makeText(context, "yaah gagak", Toast.LENGTH_SHORT).show() }
        }
        btn_close_transmisi.setOnClickListener { ll_transmisi.visibility = View.GONE }*/
    }

}
