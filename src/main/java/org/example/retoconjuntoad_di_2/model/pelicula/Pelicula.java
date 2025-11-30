package org.example.retoconjuntoad_di_2.model.pelicula;

import jakarta.persistence.*;
import lombok.*;
import org.example.retoconjuntoad_di_2.model.copia.Copia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una película en el sistema.
 * Contiene información sobre el título, género, año, descripción, director y las copias asociadas.
 */
@Entity
@Table(name = "Peliculas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pelicula implements Serializable {

    /**
     * Identificador único de la película.
     * Generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Título de la película.
     */
    private String titulo;

    /**
     * Género de la película.
     */
    private String genero;

    /**
     * Año de lanzamiento de la película.
     * En la base de datos se almacena como "año".
     */
    @Column(name = "año")
    private Short anio;   // en BD es "año", en Java "anio"

    /**
     * Descripción de la película.
     */
    private String descripcion;

    /**
     * Director de la película.
     */
    private String director;

    /**
     * Lista de copias asociadas a la película.
     * Relación de uno a muchos con la entidad Copia.
     * Las operaciones en cascada se aplican a todas las copias asociadas.
     */
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "pelicula", fetch = FetchType.EAGER)
    private List<Copia> copias = new ArrayList<>();

    /**
     * Añade una copia a la lista de copias asociadas a la película.
     * También establece la relación inversa entre la copia y la película.
     *
     * @param copia La copia a añadir.
     */
    public void addCopia(Copia copia) {
        copia.setPelicula(this);
        this.copias.add(copia);
    }

    /**
     * Representación en forma de cadena de la película.
     * Incluye el título y el año de lanzamiento.
     *
     * @return Cadena que describe la película.
     */
    @Override
    public String toString() {
        return titulo + " (" + anio + ")";
    }
}
