package com.example.das_challenge_2.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.das_challenge_2.R
import com.example.das_challenge_2.models.BillModel
import kotlinx.android.synthetic.main.item_bill.view.*

class BillAdapter(list: List<BillModel>, context: Context): RecyclerView.Adapter<BillAdapter.ViewHolder>() {

    private var bills: List<BillModel> = arrayListOf()
    private lateinit var context: Context

    init {
        this.bills = list
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bill, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return bills.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bill = bills[position]

        holder.name.text = "A nombre de: ${bill.bill_client}"
        holder.date.text = bill.bill_date
        holder.total.text = "Total: $${bill.bill_total}"

    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val name = view.billNameTxt
        val date = view.billDateTxt
        val total = view.billTotalTxt

    }
}