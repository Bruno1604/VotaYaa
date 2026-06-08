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
import com.example.votaya.databinding.FragmentEquiposBinding
import com.example.votaya.home.adapters.EquiposAdapter
import kotlinx.coroutines.launch

class EquiposFragment : Fragment() {
    private var _binding: FragmentEquiposBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: EquiposAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEquiposBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        fetchEquipos()
    }

    private fun setupRecyclerView() {
        adapter = EquiposAdapter(emptyList())
        binding.equiposRv.layoutManager = LinearLayoutManager(context)
        binding.equiposRv.adapter = adapter
    }

    private fun fetchEquipos() {
        binding.loadingPb.visibility = View.VISIBLE
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.instance.getUsers(20)
                adapter.updateData(response.users)
            } catch (e: Exception) {
                Toast.makeText(context, "Error al cargar equipos: ${e.message}", Toast.LENGTH_SHORT).show()
            } finally {
                binding.loadingPb.visibility = View.GONE
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
