package org.example.retoconjuntoad_di_2.model.pelicula;

import org.example.retoconjuntoad_di_2.model.pelicula.Pelicula;
import org.example.retoconjuntoad_di_2.utils.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones CRUD de la entidad Pelicula.
 * Utiliza Hibernate para interactuar con la base de datos.
 */
public class PeliculaRepository implements Repository<Pelicula> {

    private final SessionFactory sessionFactory; // Fábrica de sesiones de Hibernate.

    /**
     * Constructor que inicializa el repositorio con una fábrica de sesiones.
     *
     * @param sessionFactory Fábrica de sesiones de Hibernate.
     */
    public PeliculaRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * Guarda una película en la base de datos.
     * Si la película no tiene ID, se inserta como nueva; de lo contrario, se actualiza.
     *
     * @param entity Película a guardar.
     * @return La película gestionada (persistida o actualizada).
     */
    @Override
    public Pelicula save(Pelicula entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return entity;
        }
    }

    /**
     * Elimina una película de la base de datos.
     *
     * @param entity Película a eliminar.
     * @return Un Optional que contiene la película eliminada.
     */
    @Override
    public Optional<Pelicula> delete(Pelicula entity) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
            return Optional.of(entity);
        }
    }

    /**
     * Elimina una película de la base de datos por su ID.
     *
     * @param id ID de la película a eliminar.
     * @return Un Optional que contiene la película eliminada, si existía.
     */
    @Override
    public Optional<Pelicula> deleteById(Long id) {
        Optional<Pelicula> pelicula = findById(id);
        pelicula.ifPresent(this::delete);
        return pelicula;
    }

    /**
     * Busca una película en la base de datos por su ID.
     *
     * @param id ID de la película a buscar.
     * @return Un Optional que contiene la película encontrada, si existe.
     */
    @Override
    public Optional<Pelicula> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            // Versión moderna para Hibernate 7 (evita el método deprecated get)
            return Optional.ofNullable(
                    session.byId(Pelicula.class).load(id.intValue())
            );
        }
    }

    /**
     * Obtiene todas las películas almacenadas en la base de datos.
     *
     * @return Una lista con todas las películas.
     */
    @Override
    public List<Pelicula> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Pelicula", Pelicula.class).list();
        }
    }

    /**
     * Cuenta el número total de películas en la base de datos.
     *
     * @return El número total de películas.
     */
    @Override
    public Long count() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "select count(p) from Pelicula p", Long.class
            ).getSingleResult();
        }
    }
}
