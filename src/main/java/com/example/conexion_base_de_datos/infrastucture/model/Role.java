package com.example.conexion_base_de_datos.infrastucture.model;

public enum Role {
    ADMIN,      // Acceso total
    VENDEDOR,   // Puede crear ventas
    USER        // Solo lectura
}