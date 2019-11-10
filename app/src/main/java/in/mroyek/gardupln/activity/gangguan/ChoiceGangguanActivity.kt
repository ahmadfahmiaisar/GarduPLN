package `in`.mroyek.gardupln.activity.gangguan

import `in`.mroyek.gardupln.R
import `in`.mroyek.gardupln.model.BayResponse
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_choice_gangguan.*

class ChoiceGangguanActivity : AppCompatActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choice_gangguan)
        choice_Gombong.setOnClickListener(this)
        choice_purworejo.setOnClickListener(this)
        choice_trafo1.setOnClickListener(this)
        choice_trafo2.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view!!.id) {
            R.id.choice_Gombong -> startActivity(Intent(applicationContext, LaporinGangguanActivity::class.java))
        }
    }
}
