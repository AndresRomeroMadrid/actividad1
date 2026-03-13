package com.example.demo.Controller;

import com.example.demo.Model.Producto;
import com.example.demo.Service.ProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // CREATE (C)
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        Producto productoCreado = productoService.agregarProducto(producto);
        return new ResponseEntity<>(productoCreado, HttpStatus.CREATED);
    }

    // READ (R) - Todos
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos() {
        List<Producto> productos = productoService.obtenerTodos();
        return new ResponseEntity<>(productos, HttpStatus.OK);
    }

    // READ (R) - Por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable Long id) {
        Optional<Producto> producto = productoService.obtenerPorId(id);
        return producto.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // UPDATE (U)
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizarProducto(@PathVariable Long id, @RequestBody Producto producto) {
        Producto productoActualizado = productoService.actualizarProducto(id, producto);
        if (productoActualizado != null) {
            return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Devuelve 404 si el ID no existe
    }

    // DELETE (D)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProducto(@PathVariable Long id) {
        boolean eliminado = productoService.eliminarProducto(id);
        if (eliminado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content para borrados exitosos
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 si el ID no existe
    }
}
