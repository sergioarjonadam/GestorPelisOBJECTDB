package org.example.retoconjuntoad_di_2.model.pelicula;

import jakarta.persistence.*;
import lombok.*;
import org.example.retoconjuntoad_di_2.model.copia.Copia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa una película en el sistema.
 * <p>
 * Esta entidad JPA almacena la información catalogada de una película, incluyendo
 * su título, género, año de lanzamiento, descripción y director. Además, mantiene
 * una relación bidireccional con las copias físicas asociadas a través de la entidad
 * {@link Copia}.
 * </p>
 * <p>
 * Las películas pueden tener múltiples copias físicas asociadas, cada una perteneciente
 * a diferentes usuarios. La relación se gestiona mediante una asociación OneToMany
 * con carga eager para facilitar el acceso a las copias.
 * </p>
 * <p>
 * Esta clase utiliza las anotaciones de Lombok para generar automáticamente
 * los métodos getter, setter, constructores y otros métodos comunes.
 * </p>
 *
 * @author Sistema de Gestión de Películas
 * @version 1.0
 * @see org.example.retoconjuntoad_di_2.model.copia.Copia
 */
@Entity
@Table(name = "Peliculas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pelicula implements Serializable {

    /**
     * Identificador único de la película.
     * <p>
     * Este campo es la clave primaria de la entidad y se genera automáticamente
     * por la base de datos utilizando una estrategia de identidad.
     * </p>
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Título de la película.
     * <p>
     * Nombre oficial o más comúnmente conocido de la película.
     * </p>
     */
    private String titulo;

    /**
     * Género cinematográfico de la película.
     * <p>
     * Ejemplos: "Drama", "Comedia", "Acción", "Ciencia ficción", "Fantasía", etc.
     * </p>
     */
    private String genero;

    /**
     * Año de lanzamiento de la película.
     * <p>
     * Año en que se estrenó la película. Se almacena como un valor corto (Short)
     * ya que solo necesitamos representar años desde 1900 hasta aproximadamente 2100.
     * </p>
     * <p>
     * <strong>Nota:</strong> En la base de datos este campo se almacena con el nombre
     * "año" (con tilde), pero en Java se utiliza "anio" (sin tilde) para evitar
     * problemas de codificación.
     * </p>
     */
    @Column(name = "año")
    private Short anio;

    /**
     * Descripción o sinopsis de la película.
     * <p>
     * Texto que describe brevemente el argumento o contenido de la película.
     * Este campo es opcional y puede ser {@code null}.
     * </p>
     */
    private String descripcion;

    /**
     * Director o directores de la película.
     * <p>
     * Nombre del director principal responsable de la realización de la película.
     * Puede contener múltiples nombres si hay co-directores.
     * Este campo es opcional y puede ser {@code null}.
     * </p>
     */
    private String director;

    /**
     * Lista de copias físicas asociadas a esta película.
     * <p>
     * Relación de uno a muchos con la entidad {@link Copia}. Una película puede
     * tener múltiples copias físicas, cada una perteneciente a diferentes usuarios.
     * </p>
     * <p>
     * La relación utiliza:
     * <ul>
     *   <li>Cascada ALL: Las operaciones de persistencia se propagan a las copias</li>
     *   <li>mappedBy: La relación inversa está definida en el campo "pelicula" de Copia</li>
     *   <li>FetchType.EAGER: Las copias se cargan automáticamente al cargar la película</li>
     * </ul>
     * </p>
     */
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "pelicula", fetch = FetchType.EAGER)
    private List<Copia> copias = new ArrayList<>();

    /**
     * Añade una copia a la lista de copias asociadas a la película.
     * <p>
     * Este método de conveniencia establece la relación bidireccional entre
     * la película y la copia. Además de añadir la copia a la lista, también
     * establece la referencia inversa en el objeto Copia.
     * </p>
     *
     * @param copia La copia a añadir. No debe ser {@code null}.
     */
    public void addCopia(Copia copia) {
        copia.setPelicula(this);
        this.copias.add(copia);
    }

    /**
     * Representación en forma de cadena de la película.
     * <p>
     * Devuelve una descripción legible que incluye el título y el año de lanzamiento
     * en formato: "Título (Año)".
     * </p>
     *
     * @return Cadena que describe la película en formato legible.
     */
    @Override
    public String toString() {
        return titulo + " (" + anio + ")";
    }
}
