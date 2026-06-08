package com.example.votaya.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.votaya.core.network.DummyUser
import com.example.votaya.databinding.ItemEquipoBinding

class EquiposAdapter(private var users: List<DummyUser>) :
    RecyclerView.Adapter<EquiposAdapter.EquipoViewHolder>() {

    class EquipoViewHolder(val binding: ItemEquipoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipoViewHolder {
        val binding = ItemEquipoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EquipoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EquipoViewHolder, position: Int) {
        val user = users[position]
        holder.binding.equipoNameTv.text = "${user.firstName} ${user.lastName}"
        holder.binding.equipoDescTv.text = user.email
        
        Glide.with(holder.itemView.context)
            .load(user.image)
            .into(holder.binding.equipoIv)
    }

    override fun getItemCount(): Int = users.size

    fun updateData(newUsers: List<DummyUser>) {
        users = newUsers
        notifyDataSetChanged()
    }
}
