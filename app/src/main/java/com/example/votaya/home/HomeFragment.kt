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
import com.example.votaya.databinding.FragmentHomeBinding
import com.example.votaya.home.adapters.VotacionesAdapter
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: VotacionesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchRecentActivity()
    }

    private fun setupRecyclerView() {
        adapter = VotacionesAdapter(emptyList())
        binding.recentActivityRv.layoutManager = LinearLayoutManager(context)
        binding.recentActivityRv.adapter = adapter
    }

    private fun fetchRecentActivity() {
        lifecycleScope.launch {
            try {
                // Fetch first 5 posts as recent activity
                val response = RetrofitClient.instance.getPosts(5)
                adapter.updateData(response.posts)
            } catch (e: Exception) {
                Toast.makeText(context, "Error al cargar actividad: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
