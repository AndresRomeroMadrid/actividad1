package com.example.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventario {
    private Long id;
    private Long productoId;
    private Integer cantidadStock;
    private String ubicacion;
}
