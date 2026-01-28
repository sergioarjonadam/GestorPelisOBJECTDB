package org.example.retoconjuntoad_di_2.model.pelicula;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.example.retoconjuntoad_di_2.utils.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones CRUD de la entidad Pelicula.
 * <p>
 * Implementado con JPA sobre ObjectDB (EntityManager / EntityManagerFactory),
 * sustituyendo al acceso anterior basado en Hibernate.
 */
public class PeliculaRepository implements Repository<Pelicula> {

    private final EntityManagerFactory entityManagerFactory;

    /**
     * Constructor que inicializa el repositorio con una factoría de EntityManager.
     *
     * @param entityManagerFactory Factoría de EntityManager (JPA + ObjectDB).
     */
    public PeliculaRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
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
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            if (entity.getId() == null) {
                em.persist(entity);
            } else {
                entity = em.merge(entity);
            }
            em.getTransaction().commit();
            return entity;
        } finally {
            em.close();
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
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            Pelicula managed = em.contains(entity) ? entity : em.merge(entity);
            em.remove(managed);
            em.getTransaction().commit();
            return Optional.of(entity);
        } finally {
            em.close();
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
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return Optional.ofNullable(em.find(Pelicula.class, id.intValue()));
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todas las películas almacenadas en la base de datos.
     *
     * @return Una lista con todas las películas.
     */
    @Override
    public List<Pelicula> findAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Pelicula> q = em.createQuery("select p from Pelicula p", Pelicula.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Cuenta el número total de películas en la base de datos.
     *
     * @return El número total de películas.
     */
    @Override
    public Long count() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return em.createQuery(
                    "select count(p) from Pelicula p", Long.class
            ).getSingleResult();
        } finally {
            em.close();
        }
    }
}
