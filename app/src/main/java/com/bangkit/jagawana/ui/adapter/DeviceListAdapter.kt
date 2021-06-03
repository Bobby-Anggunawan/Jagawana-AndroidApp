package com.bangkit.jagawana.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.jagawana.R
import com.google.android.material.card.MaterialCardView

class DeviceListAdapter(val Data: ArrayList<RowData>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //ini untuk menampung data type yang dipakai tiap row
    data class RowData(
        var namaDevice: String?,
        var Region: String?,
        var isHeader: Boolean,
    )

    var onItemClick: ((RowData) -> Unit)? = null

    inner class ItemData_Holder(ItemLayout: View) : RecyclerView.ViewHolder(ItemLayout) {
        var deviceName: TextView = ItemLayout.findViewById(R.id.regionName)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(Data[adapterPosition])
            }
        }
    }

    inner class HeaderData_Holder(ItemLayout: View): RecyclerView.ViewHolder(ItemLayout){
        var header: TextView = ItemLayout.findViewById(R.id.header)
    }



    override fun getItemViewType(position: Int): Int {
        if(Data[position].isHeader){
            return 1
        }
        else{
            return 0
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_region_list, parent, false)
        val header: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_device_list_header, parent, false)
        if(viewType == 0) return ItemData_Holder(view)
        else return HeaderData_Holder(header)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val An_Item = Data[position]
        if(holder.getItemViewType() == 0){
            holder as ItemData_Holder
            holder.deviceName.text = An_Item.namaDevice
        }
        else{
            holder as HeaderData_Holder
            holder.header.text = An_Item.Region
        }
    }

    override fun getItemCount(): Int {
        return Data.size
    }
}