package com.example.openlibraryarticles.ui.adapter.base

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
/**
 * constants represents the type of cell to be previewed
 * @Type_FOOTER user for the loading cell
 * @Type_ITEM used for the main item in the adapter (docs in owr case)
 */
const val TYPE_FOOTER = 0
const val TYPE_ITEM = 1

/**
 * abstract class used to has common features used in pagination
 */
abstract class BaseAdapter<T> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var arrayList: ArrayList<T>
    var hideFooter = true
    /**
     * @return 1 if the array is null or empty for the loading cell
     * else return the array size + 1
     */
    override fun getItemCount(): Int {
        return if (!::arrayList.isInitialized || arrayList.size == 0) 1 else arrayList.size + 1
    }

    /**
     * @param hideFooter the state of the footer if hidden or not
     * the method hide and show the footer
     */
    fun updateHideFooter(hideFooter: Boolean) {
        this.hideFooter = hideFooter
        notifyItemChanged(if (::arrayList.isInitialized) arrayList.size else 1)
    }


    /**
     * function to append the new list to the adapter and notify changes
     */
    open fun addList(list: ArrayList<T>?) {

        list?.let {
            val startIndex = arrayList.size
            arrayList.addAll(list)
            val endIndex = arrayList.size
            notifyItemRangeChanged(startIndex, endIndex)
        }
    }

    /**
     * function used to clear all the list in case of restarting pagination
     */
    fun clearList() {
        val endPosition = arrayList.size
        arrayList.clear()
        notifyItemRangeRemoved(0, endPosition)
    }
}