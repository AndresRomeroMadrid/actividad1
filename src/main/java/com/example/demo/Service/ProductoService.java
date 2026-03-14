package com.example.demo.Service;

import com.example.demo.Exceptions.InvalidProductEx;
import com.example.demo.Model.Producto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ProductoService {

    private final List<Producto> productos = new ArrayList<>();
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
        return null; // En una app real, aquí lanzaríamos una excepción personalizada (ej.
                     // ResourceNotFoundException)
    }

    public boolean eliminarProducto(Long id) {
        return productos.removeIf(p -> p.getId().equals(id));
    }
}
