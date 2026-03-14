package com.example.demo.Service;

import com.example.demo.Model.Pedido;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PedidoService {

    // Soporte para solicitudes concurrentes y alta carga
    private final List<Pedido> pedidos = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public PedidoService() {
        pedidos.add(new Pedido(idGenerator.getAndIncrement(), 1L, 1L, 2, "PENDIENTE"));
        pedidos.add(new Pedido(idGenerator.getAndIncrement(), 2L, 1L, 1, "ENVIADO"));
    }

    public List<Pedido> obtenerTodos() {
        return pedidos;
    }

    public Optional<Pedido> obtenerPorId(Long id) {
        return pedidos.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public Pedido crearPedido(Pedido nuevo) {
        nuevo.setId(idGenerator.getAndIncrement());
        if(nuevo.getEstado() == null) nuevo.setEstado("PENDIENTE");
        pedidos.add(nuevo);
        return nuevo;
    }

    public Pedido actualizarPedido(Long id, Pedido actualizado) {
        Optional<Pedido> existente = obtenerPorId(id);
        if (existente.isPresent()) {
            Pedido pedido = existente.get();
            if (actualizado.getCantidad() != null) pedido.setCantidad(actualizado.getCantidad());
            if (actualizado.getEstado() != null) pedido.setEstado(actualizado.getEstado());
            return pedido;
        }
        return null;
    }

    public boolean eliminarPedido(Long id) {
        return pedidos.removeIf(p -> p.getId().equals(id));
    }
}
