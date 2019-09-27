package `in`.mroyek.gardupln.fragment.tambahbay


import `in`.mroyek.gardupln.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_tambah_bay.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class TambahBayFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_tambah_bay, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*btn_transmisi.setOnClickListener { startActivity(Intent(view.context, TransmisiFragment::class.java)) }
        btn_diameter.setOnClickListener { startActivity(Intent(view.context, DiameterFragment::class.java)) }
        btn_trafo.setOnClickListener { startActivity(Intent(context, TrafoFragment::class.java)) }*/

      /*  btn_transmisi.setOnClickListener { replaceFragment(TransmisiFragment(), "transmisi") }
        btn_diameter.setOnClickListener { replaceFragment(DiameterFragment(), "diameter") }
        btn_trafo.setOnClickListener { replaceFragment(TrafoFragment(), "trafo") }*/

    }

   /* private fun replaceFragment(fragment: Fragment, tag: String) {
        val mFragmentTransaction = fragmentManager!!.beginTransaction()
        mFragmentTransaction.replace(R.id.bay_frame_container, fragment, tag)
        mFragmentTransaction.addToBackStack(null)
        mFragmentTransaction.commit()
    }*/
}
