package com.tapia.myapplication2025.ui.view

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tapia.myapplication2025.R
import androidx.navigation.fragment.findNavController
import com.tapia.myapplication2025.model.Producto
import com.tapia.myapplication2025.ui.fragment.DetalleProductoFragment

class ProductoAdapter(
    private val onClick: (Producto) -> Unit
) : ListAdapter<Producto, ProductoAdapter.ProductoViewHolder>(DiffCallback()) {

    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombre: TextView = itemView.findViewById(R.id.item_nombre)
        private val tvCategoria: TextView = itemView.findViewById(R.id.item_categoria)
        private val tvPrecio: TextView = itemView.findViewById(R.id.item_precio)
        private val ivImagen: ImageView = itemView.findViewById(R.id.item_imagen)
        private val btnVerMas: Button = itemView.findViewById(R.id.item_btn_ver_mas)

        fun bind(producto: Producto) {
            tvNombre.text = producto.nombre
            tvCategoria.text = producto.categoria
            tvPrecio.text = "$${String.format("%.2f", producto.precio)}"


            val resIdFromName = if (producto.imagenNombre.isNotBlank()) {
                itemView.context.resources.getIdentifier(
                    producto.imagenNombre, "drawable", itemView.context.packageName
                )
            } else {
                0
            }

            val finalRes = when {
                resIdFromName != 0 -> resIdFromName
                producto.imagenResId != 0 -> producto.imagenResId
                else -> R.drawable.imagen_no_disponible
            }

            Log.d("ProductoAdapter", "bind -> ${producto.nombre} -> imagenNombre='${producto.imagenNombre}' resId=$finalRes")
            ivImagen.setImageResource(finalRes)

            // CLICK DEL BOTÓN VER MÁS
            btnVerMas.setOnClickListener {
                Log.d("ProductoAdapter", "Click VER MÁS -> ${producto.nombre}")
                onClick(producto)
            }

            itemView.setOnClickListener {
                onClick(producto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Producto>() {
        override fun areItemsTheSame(oldItem: Producto, newItem: Producto): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Producto, newItem: Producto): Boolean {
            return oldItem == newItem
        }
    }
}
