package com.example.das_challenge_2.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.das_challenge_2.R
import com.example.das_challenge_2.adapters.BillAdapter
import com.example.das_challenge_2.models.BillModel
import com.example.das_challenge_2.utils.getFirebaseReference
import com.example.das_challenge_2.utils.showToast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_record.*

class RecordFragment : Fragment() {

    private lateinit var adapter: BillAdapter
    private var bills: MutableList<BillModel> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_record, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        billRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = BillAdapter(bills, requireContext())
        billRecyclerView.adapter = adapter

        loadBills()

    }

    private fun loadBills() {
        //Get bill reference
        val billReference = getFirebaseReference("bills")
        billReference.addValueEventListener(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                showToast(requireContext(), p0.message)
            }

            override fun onDataChange(p0: DataSnapshot) {
                if (bills.size > 0) {
                    bills.clear()
                }

                if (p0.exists()) {
                    for (tmp in p0.children) {
                        val bill = tmp.getValue(BillModel::class.java)
                        bills.add(bill!!)
                        adapter.notifyDataSetChanged()
                    }
                }
            }

        })

    }

}
