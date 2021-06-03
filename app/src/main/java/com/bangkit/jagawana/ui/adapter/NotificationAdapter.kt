package com.bangkit.jagawana.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bangkit.jagawana.R

class NotificationAdapter(val Data: ArrayList<RowData>): RecyclerView.Adapter<NotificationAdapter.ItemData_Holder>() {

    //ini untuk menampung data type yang dipakai tiap row
    data class RowData(
        var title: String,
        var content: String,
        var time: String
    )

    var onItemClick: ((NotificationAdapter.RowData) -> Unit)? = null

    inner class ItemData_Holder(ItemLayout: View) : RecyclerView.ViewHolder(ItemLayout) {
        var judulNotifikasi: TextView = ItemLayout.findViewById(R.id.textView3)
        var isiNotifikasi: TextView = ItemLayout.findViewById(R.id.textView4)
        var time: TextView = ItemLayout.findViewById(R.id.waktu)
        val image: ImageView = ItemLayout.findViewById(R.id.panah)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(Data[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationAdapter.ItemData_Holder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ItemData_Holder(view)
    }

    override fun onBindViewHolder(holder: NotificationAdapter.ItemData_Holder, position: Int) {
        val An_Item = Data[position]
        holder.judulNotifikasi.text = An_Item.title
        holder.isiNotifikasi.text = An_Item.content
        holder.time.text = An_Item.time
        holder.image.visibility = View.GONE
    }

    override fun getItemCount(): Int {
        return Data.size
    }
}