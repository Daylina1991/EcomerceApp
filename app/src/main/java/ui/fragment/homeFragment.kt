package com.tapia.myapplication2025.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.tapia.myapplication2025.model.Producto
import com.tapia.myapplication2025.ui.viewmodel.ProductoViewModel
import androidx.navigation.fragment.findNavController
import com.tapia.myapplication2025.R

class HomeFragment : Fragment() {

    private lateinit var productoViewModel: ProductoViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val layout = LinearLayout(requireContext()).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        productoViewModel = ViewModelProvider(this)[ProductoViewModel::class.java]

        productoViewModel.productos.observe(viewLifecycleOwner) { productos ->
            layout.removeAllViews()
            productos.forEach { producto ->
                val card = LinearLayout(requireContext()).apply {
                    orientation = LinearLayout.VERTICAL
                    setPadding(16, 16, 16, 16)
                    setBackgroundResource(android.R.drawable.dialog_holo_light_frame)
                }

                val tv = TextView(requireContext()).apply {
                    text = "${producto.nombre} - ${producto.precio}"
                    textSize = 18f
                }

                val btnVerMas = Button(requireContext()).apply {
                    text = "Ver más"
                    setOnClickListener {
                        val action = HomeFragmentDirections.actionHomeToDetalle(producto)
                        findNavController().navigate(action)
                    }
                }

                card.addView(tv)
                card.addView(btnVerMas)
                layout.addView(card)
            }
        }

        return layout
    }
}
