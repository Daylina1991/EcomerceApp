package com.tapia.myapplication2025.ui.fragment

import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.button.MaterialButton

class DetalleProductoFragment : Fragment() {

    private val args: DetalleProductoFragmentArgs by navArgs()

    // Referencias de vista (se limpian en onDestroyView)
    private var rootLayout: LinearLayout? = null
    private var imagenView: ImageView? = null
    private var tvNombre: TextView? = null
    private var tvPrecio: TextView? = null
    private var tvDescripcion: TextView? = null
    private var btnAgregar: MaterialButton? = null

    override fun onCreateView(inflater: android.view.LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): android.view.View? {
        // Creamos el layout programáticamente (no depende de XML)
        val contexto = requireContext()
        val padding = (16 * resources.displayMetrics.density).toInt()

        rootLayout = LinearLayout(contexto).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(padding, padding, padding, padding)
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        imagenView = ImageView(contexto).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                (240 * resources.displayMetrics.density).toInt()
            )
            scaleType = ImageView.ScaleType.CENTER_CROP
        }

        tvNombre = TextView(contexto).apply {
            textSize = 20f
        }

        tvPrecio = TextView(contexto).apply {
            textSize = 18f
        }

        tvDescripcion = TextView(contexto).apply {
            textSize = 16f
        }

        btnAgregar = MaterialButton(contexto).apply {
            text = "Agregar al carrito"
        }

        // Añadimos las vistas al layout
        rootLayout?.apply {
            addView(imagenView)
            addView(tvNombre)
            addView(tvPrecio)
            addView(tvDescripcion)
            addView(btnAgregar)
        }

        return rootLayout
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val producto = args.producto
        Log.d("DetalleProducto", "Producto recibido: id=${producto.id}, nombre=${producto.nombre}, imagenResId=${producto.imagenResId}")

        // Carga segura de imagen
        try {
            val resId = producto.imagenResId
            if (resId != 0) {
                val drawable = ContextCompat.getDrawable(requireContext(), resId)
                if (drawable != null) {
                    imagenView?.setImageDrawable(drawable)
                } else {
                    Log.w("DetalleProducto", "Drawable no encontrado para id=$resId. Uso placeholder del sistema.")
                    imagenView?.setImageResource(android.R.drawable.ic_menu_report_image)
                }
            } else {
                Log.w("DetalleProducto", "imagenResId == 0. Uso placeholder del sistema.")
                imagenView?.setImageResource(android.R.drawable.ic_menu_report_image)
            }
        } catch (e: Resources.NotFoundException) {
            Log.e("DetalleProducto", "ResourceNotFoundException para imagenResId=${producto.imagenResId}", e)
            imagenView?.setImageResource(android.R.drawable.ic_menu_report_image)
        } catch (e: Exception) {
            Log.e("DetalleProducto", "Error al cargar imagen", e)
            imagenView?.setImageResource(android.R.drawable.ic_menu_report_image)
        }

        // Seteos texto
        tvNombre?.text = producto.nombre
        tvPrecio?.text = "$${String.format("%.2f", producto.precio)}"
        tvDescripcion?.text = producto.descripcion

        btnAgregar?.setOnClickListener {
            Toast.makeText(requireContext(), "${producto.nombre} agregado al carrito", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        // Limpiar referencias para evitar leaks
        rootLayout = null
        imagenView = null
        tvNombre = null
        tvPrecio = null
        tvDescripcion = null
        btnAgregar = null
        super.onDestroyView()
    }
}
