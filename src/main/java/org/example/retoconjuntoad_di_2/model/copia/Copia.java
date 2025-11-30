package org.example.retoconjuntoad_di_2.model.copia;

import jakarta.persistence.*;
import lombok.*;
import org.example.retoconjuntoad_di_2.model.pelicula.Pelicula;
import org.example.retoconjuntoad_di_2.model.user.User;

import java.io.Serializable;

/**
 * Clase que representa una copia de una película en el sistema.
 * Cada copia está asociada a una película, un usuario, un estado y un soporte.
 */
@Entity
@Table(name = "Copias")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Copia implements Serializable {

    /**
     * Identificador único de la copia.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Película asociada a la copia.
     * Relación de muchos a uno con la entidad Pelicula.
     */
    @ManyToOne
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Pelicula pelicula;

    /**
     * Usuario propietario de la copia.
     * Relación de muchos a uno con la entidad User.
     */
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private User user;

    /**
     * Estado de la copia (por ejemplo, "Nueva", "Usada", etc.).
     * Este campo es obligatorio.
     */
    @Column(nullable = false)
    private String estado;

    /**
     * Soporte físico de la copia (por ejemplo, "DVD", "Blu-ray", etc.).
     * Este campo es obligatorio.
     */
    @Column(nullable = false)
    private String soporte;

    /**
     * Representación en forma de cadena de la copia.
     * Incluye el ID, el título de la película, el nombre del usuario, el estado y el soporte.
     *
     * @return Cadena que describe la copia.
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
