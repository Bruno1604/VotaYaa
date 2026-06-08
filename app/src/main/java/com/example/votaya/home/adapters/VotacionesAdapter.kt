package com.example.votaya.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.votaya.core.network.DummyPost
import com.example.votaya.databinding.ItemVotacionBinding

class VotacionesAdapter(private var posts: List<DummyPost>) :
    RecyclerView.Adapter<VotacionesAdapter.VotacionViewHolder>() {

    class VotacionViewHolder(val binding: ItemVotacionBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VotacionViewHolder {
        val binding = ItemVotacionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VotacionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VotacionViewHolder, position: Int) {
        val post = posts[position]
        holder.binding.votacionTitleTv.text = post.title
        holder.binding.votacionDescTv.text = post.body
        holder.binding.statusChip.text = if (post.id % 2 == 0) "Abierta" else "Finalizada"
        holder.binding.reactionsTv.text = "Votos: ${post.reactions}"
    }

    override fun getItemCount(): Int = posts.size

    fun updateData(newPosts: List<DummyPost>) {
        posts = newPosts
        notifyDataSetChanged()
    }
}
