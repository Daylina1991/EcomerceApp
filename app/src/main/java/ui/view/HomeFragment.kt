package com.tapia.myapplication2025.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tapia.myapplication2025.databinding.FragmentHomeBinding
import com.tapia.myapplication2025.model.Producto
import com.tapia.myapplication2025.ui.viewmodel.ProductoViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: ProductoViewModel
    private lateinit var adapter: ProductoAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(requireActivity())[ProductoViewModel::class.java]

        adapter = ProductoAdapter(emptyList())
        binding.recyclerProductos.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerProductos.adapter = adapter

        viewModel.productos.observe(viewLifecycleOwner) {
            adapter.actualizarLista(it)
        }
        binding.btnAgregar.setOnClickListener {
            val producto = Producto(nombre = "Café", categoria = "Bebida", precio = 120.0)
            viewModel.agregar(producto)
        }
    }
}
