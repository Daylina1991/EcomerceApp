package com.tapia.myapplication2025.ui.view

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
import com.tapia.myapplication2025.model.Producto

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
            tvPrecio.text = "$${producto.precio}"

            val resId = itemView.context.resources.getIdentifier(
                producto.imagenNombre, "drawable", itemView.context.packageName
            )
            ivImagen.setImageResource(resId)

            btnVerMas.setOnClickListener {
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
