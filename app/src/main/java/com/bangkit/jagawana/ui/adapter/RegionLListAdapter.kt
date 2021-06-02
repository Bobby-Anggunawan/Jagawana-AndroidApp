package com.bangkit.jagawana.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.jagawana.R

class RegionLListAdapter(val Data: ArrayList<RowData>): RecyclerView.Adapter<RegionLListAdapter.ItemData_Holder>() {

    //ini untuk menampung data type yang dipakai tiap row
    data class RowData(
        var nama: String,
        var latitude: Double,
        var longitude: Double
    )

    var onItemClick: ((RowData) -> Unit)? = null

    inner class ItemData_Holder(ItemLayout: View) : RecyclerView.ViewHolder(ItemLayout) {
        var title: TextView = ItemLayout.findViewById(R.id.regionName)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(Data[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RegionLListAdapter.ItemData_Holder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_region_list, parent, false)
        return ItemData_Holder(view)
    }

    override fun onBindViewHolder(holder: RegionLListAdapter.ItemData_Holder, position: Int) {
        val An_Item = Data[position]
        holder.title.text = An_Item.nama
    }

    override fun getItemCount(): Int {
        return Data.size
    }
}