package org.example.retoconjuntoad_di_2.model.copia;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.example.retoconjuntoad_di_2.model.user.User;
import org.example.retoconjuntoad_di_2.utils.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones CRUD de la entidad Copia.
 * <p>
 * Implementado con JPA sobre ObjectDB (EntityManager / EntityManagerFactory),
 * sustituyendo al acceso anterior basado en Hibernate.
 */
public class CopiaRepository implements Repository<Copia> {

    private final EntityManagerFactory entityManagerFactory;

    /**
     * Constructor que inicializa el repositorio con una factoría de EntityManager.
     *
     * @param entityManagerFactory Factoría de EntityManager (JPA + ObjectDB).
     */
    public CopiaRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Guarda una copia en la base de datos.
     * Si la copia no tiene ID, se inserta como nueva; de lo contrario, se actualiza.
     *
     * @param entity Copia a guardar.
     * @return La copia gestionada (persistida o actualizada).
     */
    @Override
    public Copia save(Copia entity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();

            Copia managed;
            if (entity.getId() == null) {
                // Nueva copia -> INSERT
                em.persist(entity);
                managed = entity;
            } else {
                // Copia ya existente -> UPDATE
                managed = em.merge(entity);
            }

            em.getTransaction().commit();
            return managed;
        } finally {
            em.close();
        }
    }

    /**
     * Elimina una copia de la base de datos.
     *
     * @param entity Copia a eliminar.
     * @return Un Optional que contiene la copia eliminada.
     */
    @Override
    public Optional<Copia> delete(Copia entity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            Copia managed = em.contains(entity) ? entity : em.merge(entity);
            em.remove(managed);
            em.getTransaction().commit();
            return Optional.of(entity);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina una copia de la base de datos por su ID.
     *
     * @param id ID de la copia a eliminar.
     * @return Un Optional que contiene la copia eliminada, si existía.
     */
    @Override
    public Optional<Copia> deleteById(Long id) {
        Optional<Copia> copia = findById(id);
        copia.ifPresent(this::delete);
        return copia;
    }

    /**
     * Busca una copia en la base de datos por su ID.
     *
     * @param id ID de la copia a buscar.
     * @return Un Optional que contiene la copia encontrada, si existe.
     */
    @Override
    public Optional<Copia> findById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return Optional.ofNullable(em.find(Copia.class, id.intValue()));
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todas las copias almacenadas en la base de datos.
     *
     * @return Una lista con todas las copias.
     */
    @Override
    public List<Copia> findAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Copia> q = em.createQuery("select c from Copia c", Copia.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Cuenta el número total de copias en la base de datos.
     *
     * @return El número total de copias.
     */
    @Override
    public Long count() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return em.createQuery(
                    "select count(c) from Copia c", Long.class
            ).getSingleResult();
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todas las copias asociadas a un usuario específico.
     *
     * @param user Usuario cuyas copias se desean obtener.
     * @return Una lista con las copias del usuario.
     */
    public List<Copia> findByUser(User user) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<Copia> query = em.createQuery(
                    "select c from Copia c where c.user = :user", Copia.class
            );
            query.setParameter("user", user);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

}
