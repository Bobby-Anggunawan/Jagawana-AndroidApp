package com.bangkit.jagawana.utility

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KProperty1

object MyRecyclerView {
    class setUp<T>(val data: ArrayList<T>) {
        private var myActivity: Activity? = null
        private var myRecyclerView: RecyclerView? = null
        private var itemStorage: buildItemStorage = buildItemStorage()
        private var listItemLayout: Int? = null
        private var onItemClick: ((T) -> Unit)? = null

        fun recyclerView(recyclerView: RecyclerView): setUp<T> {
            myRecyclerView = recyclerView
            return this
        }

        fun setListItemLayout(layout: Int): setUp<T> {
            listItemLayout = layout
            return this
        }

        fun setActivity(activity: Activity): setUp<T> {
            myActivity = activity
            return this
        }

        fun addTextView(property: String, viewId: Int): setUp<T> {
            itemStorage?.addTextView(property, viewId)
            return this
        }

        fun addRatingBar(property: String, viewId: Int): setUp<T> {
            itemStorage?.addRatingBar(property, viewId)
            return this
        }

        fun setItemOnClick_ThenRun(myFunction: ((T) -> Unit)) {
            onItemClick = myFunction
            run()
        }

        fun run() {
            myRecyclerView?.layoutManager = LinearLayoutManager(myActivity)
            val ListAdapter = myAdapter(data, listItemLayout ?: 0, itemStorage, onItemClick)
            myRecyclerView?.adapter = ListAdapter
        }
    }

    //sumber fungsi readInstanceProperty = https://stackoverflow.com/questions/35525122/kotlin-data-class-how-to-read-the-value-of-property-if-i-dont-know-its-name-at
    @Suppress("UNCHECKED_CAST")
    fun <T> readInstanceProperty(instance: Any, propertyName: String): T {
        val property =
                instance::class.members.first { it.name == propertyName } as KProperty1<Any, *>
        return property.get(instance) as T
    }

    data class datatoView(
            val propertyName: String,
            val viewId: Int
    )

    data class dataToTextView(
            val propertyName: String,
            val myTextView: TextView
    )

    data class dataToRatingBar(
            val propertyName: String,
            val myRatingBar: RatingBar
    )

    class buildItemStorage {
        val textList: ArrayList<datatoView> = arrayListOf()
        val ratingList: ArrayList<datatoView> = arrayListOf()

        fun addTextView(property: String, viewId: Int): buildItemStorage {
            textList.add(datatoView(property, viewId))
            return this
        }

        fun addRatingBar(property: String, viewId: Int): buildItemStorage {
            ratingList.add(datatoView(property, viewId))
            return this
        }
    }

    class myViewHolder<T>(
            val ItemLayout: View,
            val Data: ArrayList<T>,
            val Items: buildItemStorage,
            val onItemClick: ((T) -> Unit)?
    ) : RecyclerView.ViewHolder(ItemLayout) {
        val textList: ArrayList<dataToTextView> = arrayListOf()
        val ratingList: ArrayList<dataToRatingBar> = arrayListOf()

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(Data[adapterPosition])
            }

            //add Textview
            Items.textList.forEach {
                val v = ItemLayout.findViewById<TextView>(it.viewId)
                textList.add(dataToTextView(it.propertyName, v))
            }

            //add RatingBar
            Items.ratingList.forEach {
                val v = ItemLayout.findViewById<RatingBar>(it.viewId)
                ratingList.add(dataToRatingBar(it.propertyName, v))
            }
        }
    }

    class myAdapter<T>(
            val Data: ArrayList<T>,
            val itemLayout: Int,
            val Items: buildItemStorage,
            val onItemClick: ((T) -> Unit)?
    ) : RecyclerView.Adapter<myViewHolder<T>>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder<T> {
            val view: View = LayoutInflater.from(parent.context).inflate(itemLayout, parent, false)
            val ret = myViewHolder<T>(view, Data, Items, onItemClick)
            return ret
        }

        override fun onBindViewHolder(holder: myViewHolder<T>, position: Int) {
            val myItem = Data[position]
            holder.textList.forEach {
                val value = readInstanceProperty<String>(myItem as Any, it.propertyName)
                it.myTextView.text = value
            }

            holder.ratingList.forEach {
                val value = readInstanceProperty<Double>(myItem as Any, it.propertyName)
                it.myRatingBar.rating = value.toFloat()
            }
        }

        override fun getItemCount(): Int {
            return Data.count()
        }
    }
}