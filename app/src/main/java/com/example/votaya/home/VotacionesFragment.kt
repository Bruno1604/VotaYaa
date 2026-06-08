package com.example.votaya.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.votaya.core.network.RetrofitClient
import com.example.votaya.databinding.FragmentVotacionesBinding
import com.example.votaya.home.adapters.VotacionesAdapter
import kotlinx.coroutines.launch

class VotacionesFragment : Fragment() {
    private var _binding: FragmentVotacionesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: VotacionesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVotacionesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchVotaciones()
    }

    private fun setupRecyclerView() {
        adapter = VotacionesAdapter(emptyList())
        binding.votacionesRv.layoutManager = LinearLayoutManager(context)
        binding.votacionesRv.adapter = adapter
    }

    private fun fetchVotaciones() {
        binding.loadingVotacionesPb.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getPosts(20)
                adapter.updateData(response.posts)
            } catch (e: Exception) {
                Toast.makeText(context, "Error al cargar votaciones: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.loadingVotacionesPb.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
