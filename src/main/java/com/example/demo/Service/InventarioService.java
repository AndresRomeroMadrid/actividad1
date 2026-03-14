package com.example.demo.Service;

import com.example.demo.Model.Inventario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class InventarioService {

    // Soporte para solicitudes concurrentes y alta carga
    private final List<Inventario> inventarios = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public InventarioService() {
        // Datos de prueba concurrent-safe para el stock
        inventarios.add(new Inventario(idGenerator.getAndIncrement(), 1L, 50, "Almacén Central"));
        inventarios.add(new Inventario(idGenerator.getAndIncrement(), 2L, 10, "Sucursal Norte"));
    }

    public List<Inventario> obtenerTodos() {
        return inventarios;
    }

    public Optional<Inventario> obtenerPorId(Long id) {
        return inventarios.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
    }

    public Optional<Inventario> obtenerPorProductoId(Long productoId) {
        return inventarios.stream()
                .filter(i -> i.getProductoId().equals(productoId))
                .findFirst();
    }

    public Inventario registrarInventario(Inventario nuevo) {
        nuevo.setId(idGenerator.getAndIncrement());
        if(nuevo.getCantidadStock() == null) nuevo.setCantidadStock(0);
        inventarios.add(nuevo);
        return nuevo;
    }

    public Inventario actualizarStock(Long id, Inventario actualizado) {
        Optional<Inventario> existente = obtenerPorId(id);
        if (existente.isPresent()) {
            Inventario inventario = existente.get();
            if (actualizado.getCantidadStock() != null) inventario.setCantidadStock(actualizado.getCantidadStock());
            if (actualizado.getUbicacion() != null) inventario.setUbicacion(actualizado.getUbicacion());
            return inventario;
        }
        return null;
    }

    public boolean eliminarRegistroInventario(Long id) {
        return inventarios.removeIf(i -> i.getId().equals(id));
    }
}
