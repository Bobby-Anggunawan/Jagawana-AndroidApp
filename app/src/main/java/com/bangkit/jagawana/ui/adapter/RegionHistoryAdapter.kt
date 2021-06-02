package com.bangkit.jagawana.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.jagawana.R

class RegionHistoryAdapter(val Data: ArrayList<RowData>): RecyclerView.Adapter<RegionHistoryAdapter.ItemData_Holder>() {

    //ini untuk menampung data type yang dipakai tiap row
    data class RowData(
        var namaDevice: String,
        var hasilPrediksi: String,
        var time: String,
        var idClip: String
    )

    var onItemClick: ((RowData) -> Unit)? = null

    inner class ItemData_Holder(ItemLayout: View) : RecyclerView.ViewHolder(ItemLayout) {
        var NamaDevice: TextView = ItemLayout.findViewById(R.id.textView3)
        var HasilPrediksi: TextView = ItemLayout.findViewById(R.id.textView4)
        var Waktu: TextView = ItemLayout.findViewById(R.id.waktu)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(Data[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemData_Holder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ItemData_Holder(view)
    }

    override fun onBindViewHolder(holder: ItemData_Holder, position: Int) {
        val An_Item = Data[position]
        holder.NamaDevice.text = An_Item.namaDevice
        holder.HasilPrediksi.text = An_Item.hasilPrediksi
        holder.Waktu.text = An_Item.time
    }

    override fun getItemCount(): Int {
        return Data.size
    }
}