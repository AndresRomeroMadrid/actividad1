package com.example.demo.Service;

import com.example.demo.Exceptions.InvalidProductEx;
import com.example.demo.Model.Producto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductoService {

    // Se usa CopyOnWriteArrayList para garantizar alta disponibilidad y manejar múltiples solicitudes concurrentes
    // de una manera segura y thread-safe (sin problemas por condiciones de carrera o bloqueos).
    private final List<Producto> productos = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public ProductoService() {
        productos.add(new Producto(idGenerator.getAndIncrement(), "Laptop Pro", 1200.50));
    }

    public List<Producto> obtenerTodos() {
        return productos;
    }

    public Optional<Producto> obtenerPorId(Long id) {
        return productos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public Producto agregarProducto(Producto nuevoProducto) {
        nuevoProducto.setId(idGenerator.getAndIncrement());
        if(nuevoProducto.getNombre() == null || nuevoProducto.getPrecio() == null){
            throw new InvalidProductEx("Verifica los campos");
        }
        productos.add(nuevoProducto);
        return nuevoProducto;
    }

    public Producto actualizarProducto(Long id, Producto productoActualizado) {
        Optional<Producto> productoExistente = obtenerPorId(id);
        if (productoExistente.isPresent()) {
            Producto producto = productoExistente.get();
            producto.setNombre(productoActualizado.getNombre());
            producto.setPrecio(productoActualizado.getPrecio());
            return producto;
        }
        return null;
    }

    public boolean eliminarProducto(Long id) {
        return productos.removeIf(p -> p.getId().equals(id));
    }
}
