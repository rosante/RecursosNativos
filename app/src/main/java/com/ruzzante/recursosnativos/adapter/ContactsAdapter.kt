package com.ruzzante.recursosnativos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ruzzante.recursosnativos.R
import com.ruzzante.recursosnativos.model.Contact

class ContactsAdapter(private val context: Context, private val list:List<Contact>) :
    RecyclerView.Adapter<ContactsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById(R.id.tv_contact_name) as TextView
        val phone = itemView.findViewById(R.id.tv_contact_phone) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.name.text = item.name
        holder.phone.text = item.phone
    }

    override fun getItemCount() = list.size
}
