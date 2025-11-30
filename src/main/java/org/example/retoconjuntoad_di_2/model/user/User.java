package org.example.retoconjuntoad_di_2.model.user;

import jakarta.persistence.*;
import lombok.*;
import org.example.retoconjuntoad_di_2.model.copia.Copia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un usuario en el sistema.
 * Contiene información sobre el nombre de usuario, contraseña, rol de administrador y las copias asociadas.
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

    /**
     * Lista de copias asociadas al usuario.
     * Relación de uno a muchos con la entidad Copia.
     * Las operaciones en cascada se aplican a todas las copias asociadas.
     */
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "user", fetch = FetchType.EAGER)
    private List<Copia> copias = new ArrayList<>();

    /**
     * Añade una copia a la lista de copias asociadas al usuario.
     * También establece la relación inversa entre la copia y el usuario.
     *
     * @param copia La copia a añadir.
     */
    public void addCopia(Copia copia) {
        copia.setUser(this);
        this.copias.add(copia);
    }
}
