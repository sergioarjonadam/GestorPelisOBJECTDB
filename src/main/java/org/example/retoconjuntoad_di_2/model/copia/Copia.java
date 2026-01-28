package org.example.retoconjuntoad_di_2.model.copia;

import jakarta.persistence.*;
import lombok.*;
import org.example.retoconjuntoad_di_2.model.pelicula.Pelicula;
import org.example.retoconjuntoad_di_2.model.user.User;

import java.io.Serializable;

/**
 * Clase que representa una copia física de una película en el sistema.
 * <p>
 * Esta entidad JPA representa una copia física individual de una película
 * que pertenece a un usuario específico. Cada copia tiene asociada:
 * <ul>
 *   <li>Una película (relación ManyToOne con {@link Pelicula})</li>
 *   <li>Un usuario propietario (relación ManyToOne con {@link User})</li>
 *   <li>Un estado que describe su condición física</li>
 *   <li>Un tipo de soporte físico (DVD, Blu-ray, VHS, etc.)</li>
 * </ul>
 * </p>
 * <p>
 * Esta clase utiliza las anotaciones de Lombok para generar automáticamente
 * los métodos getter, setter, constructores y otros métodos comunes.
 * </p>
 *
 * @author Sistema de Gestión de Películas
 * @version 1.0
 * @see org.example.retoconjuntoad_di_2.model.pelicula.Pelicula
 * @see org.example.retoconjuntoad_di_2.model.user.User
 */
@Entity
@Table(name = "Copias")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Copia implements Serializable {

    /**
     * Identificador único de la copia.
     * <p>
     * Este campo es la clave primaria de la entidad y se genera automáticamente
     * por la base de datos utilizando una estrategia de identidad.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Película asociada a esta copia.
     * <p>
     * Relación de muchos a uno con la entidad {@link Pelicula}.
     * Múltiples copias pueden pertenecer a la misma película.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Pelicula pelicula;

    /**
     * Usuario propietario de esta copia.
     * <p>
     * Relación de muchos a uno con la entidad {@link User}.
     * Un usuario puede tener múltiples copias de diferentes películas.
     * </p>
     */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User user;

    /**
     * Estado físico de la copia.
     * <p>
     * Describe la condición de la copia física. Valores comunes incluyen:
     * "Nueva", "Buena", "Usada", "Deteriorada".
     * </p>
     * <p>
     * Este campo es obligatorio y no puede ser {@code null}.
     * </p>
     */
    @Column(nullable = false)
    private String estado;

    /**
     * Tipo de soporte físico de la copia.
     * <p>
     * Indica el formato físico en el que está almacenada la película.
     * Valores comunes incluyen: "DVD", "Blu-ray", "VHS".
     * </p>
     * <p>
     * Este campo es obligatorio y no puede ser {@code null}.
     * </p>
     */
    @Column(nullable = false)
    private String soporte;

    /**
     * Representación en forma de cadena de la copia.
     * <p>
     * Devuelve una descripción legible de la copia que incluye:
     * el ID, el título de la película, el nombre del usuario propietario,
     * el estado y el tipo de soporte.
     * </p>
     *
     * @return Cadena que describe la copia en formato legible.
     */
    @Override
    public String toString() {
        return "Copia{" +
                "id=" + id +
                ", pelicula=" + (pelicula != null ? pelicula.getTitulo() : "null") +
                ", user=" + (user != null ? user.getNombreUsuario() : "null") +
                ", estado='" + estado + '\'' +
                ", soporte='" + soporte + '\'' +
                '}';
    }
}
