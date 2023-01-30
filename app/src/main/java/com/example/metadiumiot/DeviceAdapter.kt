package com.example.metahackathon

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.metadiumiot.DeviceData
import com.example.metadiumiot.R

class DeviceAdapter(private val items: List<DeviceData>) :
    RecyclerView.Adapter<DeviceAdapter.DeviceDataHolder>() {

    private var callback : ItemClickListener? = null

    fun setItemClickListener(callback: ItemClickListener) {
        this.callback = callback
    }

    inner class DeviceDataHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeviceDataHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_data_view, parent, false)
        return DeviceDataHolder(view)
    }

    override fun onBindViewHolder(holder: DeviceDataHolder, position: Int) {
        val currentItem: DeviceData = items[position]

        val device: TextView = holder.itemView.findViewById(R.id.device)
        device.text = currentItem.deviceName

        val macAddress: TextView = holder.itemView.findViewById(R.id.mac_address)
        macAddress.text = currentItem.macAddress

        val owner: TextView = holder.itemView.findViewById(R.id.owner)
        owner.text = currentItem.ownerName

        val phone: TextView = holder.itemView.findViewById(R.id.phone)
        phone.text = currentItem.ownerPhone

        holder.itemView.setOnClickListener {
            callback?.onClick(items[position])
        }
    }
    override fun getItemCount(): Int {
        return items.size
    }


}

interface ItemClickListener {
    fun onClick(value: DeviceData)
}