package org.example.retoconjuntoad_di_2.model.user;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

/**
 * Clase que representa un usuario en el sistema.
 * <p>
 * Esta entidad JPA almacena la información de autenticación y autorización
 * de los usuarios del sistema. Cada usuario tiene un nombre de usuario único,
 * una contraseña y un indicador de si tiene privilegios de administrador.
 * </p>
 * <p>
 * Los usuarios pueden tener múltiples copias de películas asociadas a través
 * de la relación definida en la entidad {@link org.example.retoconjuntoad_di_2.model.copia.Copia}.
 * </p>
 * <p>
 * Esta clase utiliza las anotaciones de Lombok ({@code @Data}) para generar
 * automáticamente los métodos getter, setter, toString, equals y hashCode.
 * </p>
 *
 * @author Sistema de Gestión de Películas
 * @version 1.0
 * @see org.example.retoconjuntoad_di_2.model.copia.Copia
 */
@Data
@Entity
@Table(name = "Usuarios")
public class User implements Serializable {

    /**
     * Identificador único del usuario.
     * <p>
     * Este campo es la clave primaria de la entidad y se genera automáticamente
     * por la base de datos utilizando una estrategia de identidad.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Nombre de usuario utilizado para el inicio de sesión.
     * <p>
     * Este campo es obligatorio y debe ser único en toda la base de datos.
     * Se utiliza junto con la contraseña para autenticar al usuario.
     * </p>
     */
    @Column(name = "nombre_usuario", nullable = false, unique = true)
    private String nombreUsuario;

    /**
     * Contraseña del usuario para autenticación.
     * <p>
     * Este campo es obligatorio y almacena la contraseña en texto plano.
     * <strong>Nota:</strong> En una aplicación de producción, las contraseñas
     * deberían almacenarse utilizando algoritmos de hash seguros (por ejemplo, BCrypt).
     * </p>
     */
    @Column(name = "contraseña", nullable = false)
    private String contrasena;

    /**
     * Indica si el usuario tiene privilegios de administrador.
     * <p>
     * Los usuarios administradores tienen acceso a funcionalidades adicionales,
     * como la creación de nuevas películas en el sistema.
     * </p>
     */
    @Column(name = "es_admin", nullable = false)
    private boolean esAdmin;

}
