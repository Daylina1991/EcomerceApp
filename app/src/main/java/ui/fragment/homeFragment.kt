package com.tapia.myapplication2025.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tapia.myapplication2025.databinding.FragmentHomeBinding
import com.tapia.myapplication2025.ui.adapter.ProductoAdapter
import com.tapia.myapplication2025.ui.viewmodel.ProductoViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var productoViewModel: ProductoViewModel
    private lateinit var adapter: ProductoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        productoViewModel = ViewModelProvider(requireActivity())[ProductoViewModel::class.java]

        adapter = ProductoAdapter { producto ->
            val action = HomeFragmentDirections
                .actionHomeFragmentToDetalleProductoFragment(producto)
            findNavController().navigate(action)
        }

        binding.recyclerProductos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerProductos.adapter = adapter

        productoViewModel.productos.observe(viewLifecycleOwner) { productos ->
            adapter.submitList(productos)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
