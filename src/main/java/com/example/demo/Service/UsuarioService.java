package com.example.demo.Service;

import com.example.demo.Model.Usuario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UsuarioService {

    // Soporte para solicitudes concurrentes y alta carga
    private final List<Usuario> usuarios = new CopyOnWriteArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public UsuarioService() {
        usuarios.add(new Usuario(idGenerator.getAndIncrement(), "Juan Perez", "juan@ejemplo.com"));
        usuarios.add(new Usuario(idGenerator.getAndIncrement(), "Maria Lopez", "maria@ejemplo.com"));
    }

    public List<Usuario> obtenerTodos() {
        return usuarios;
    }

    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst();
    }

    public Usuario crearUsuario(Usuario nuevo) {
        nuevo.setId(idGenerator.getAndIncrement());
        usuarios.add(nuevo);
        return nuevo;
    }

    public Usuario actualizarUsuario(Long id, Usuario actualizado) {
        Optional<Usuario> existente = obtenerPorId(id);
        if (existente.isPresent()) {
            Usuario usuario = existente.get();
            if (actualizado.getNombre() != null) usuario.setNombre(actualizado.getNombre());
            if (actualizado.getEmail() != null) usuario.setEmail(actualizado.getEmail());
            return usuario;
        }
        return null; 
    }

    public boolean eliminarUsuario(Long id) {
        return usuarios.removeIf(u -> u.getId().equals(id));
    }
}
