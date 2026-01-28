package org.example.retoconjuntoad_di_2.model.user;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * Clase que representa un usuario en el sistema.
 * Contiene información sobre el nombre de usuario, contraseña y rol de administrador.
 */
@Data
@Entity
@Table(name = "Usuarios")
public class User implements Serializable {

    /**
     * Identificador único del usuario.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre de usuario.
     * Este campo es obligatorio y debe ser único.
     */
    @Column(name = "nombre_usuario", nullable = false, unique = true)
    private String nombreUsuario;

    /**
     * Contraseña del usuario.
     * Este campo es obligatorio.
     */
    @Column(name = "contraseña", nullable = false)
    private String contrasena;

    /**
     * Indica si el usuario tiene rol de administrador.
     * Este campo es obligatorio.
     */
    @Column(name = "es_admin", nullable = false)
    private boolean esAdmin;

}
