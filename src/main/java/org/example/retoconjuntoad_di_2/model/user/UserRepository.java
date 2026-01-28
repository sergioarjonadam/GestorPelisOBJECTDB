package org.example.retoconjuntoad_di_2.model.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import org.example.retoconjuntoad_di_2.utils.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones CRUD de la entidad {@link User}.
 * <p>
 * Esta clase implementa la interfaz {@link Repository} y proporciona métodos
 * específicos para gestionar usuarios en la base de datos utilizando JPA/ObjectDB.
 * </p>
 * <p>
 * <strong>Implementación técnica:</strong>
 * Utiliza JPA con ObjectDB (EntityManager / EntityManagerFactory) en lugar
 * de Hibernate + MySQL. De esta forma los datos se almacenan en un fichero .odb
 * local y no es necesario un servidor de base de datos externo.
 * </p>
 * <p>
 * Cada operación crea su propio EntityManager y lo cierra después de completarse,
 * siguiendo el patrón de "una transacción por operación" para garantizar la
 * consistencia de los datos.
 * </p>
 *
 * @author Sistema de Gestión de Películas
 * @version 1.0
 * @see org.example.retoconjuntoad_di_2.model.user.User
 * @see org.example.retoconjuntoad_di_2.utils.Repository
 */
public class UserRepository implements Repository<User> {

    private final EntityManagerFactory entityManagerFactory;

    /**
     * Constructor que inicializa el repositorio con una factoría de EntityManager.
     *
     * @param entityManagerFactory Factoría de EntityManager (JPA + ObjectDB).
     */
    public UserRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Guarda un usuario en la base de datos.
     * Si el usuario no tiene ID, se inserta como nuevo; de lo contrario, se actualiza.
     *
     * @param entity Usuario a guardar.
     * @return El usuario gestionado (persistido o actualizado).
     */
    @Override
    public User save(User entity) {
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
     * Elimina un usuario de la base de datos.
     *
     * @param entity Usuario a eliminar.
     * @return Un Optional que contiene el usuario eliminado.
     */
    @Override
    public Optional<User> delete(User entity) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            em.getTransaction().begin();
            User managed = em.contains(entity) ? entity : em.merge(entity);
            em.remove(managed);
            em.getTransaction().commit();
            return Optional.of(entity);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un usuario de la base de datos por su ID.
     *
     * @param id ID del usuario a eliminar.
     * @return Un Optional que contiene el usuario eliminado, si existía.
     */
    @Override
    public Optional<User> deleteById(Long id) {
        Optional<User> user = findById(id);
        if (user.isPresent()) {
            delete(user.get());
        }
        return user;
    }

    /**
     * Busca un usuario en la base de datos por su ID.
     *
     * @param id ID del usuario a buscar.
     * @return Un Optional que contiene el usuario encontrado, si existe.
     */
    @Override
    public Optional<User> findById(Long id) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return Optional.ofNullable(em.find(User.class, id.intValue()));
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene todos los usuarios almacenados en la base de datos.
     *
     * @return Una lista con todos los usuarios.
     */
    @Override
    public List<User> findAll() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<User> q = em.createQuery("select u from User u", User.class);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Cuenta el número total de usuarios en la base de datos.
     *
     * @return El número total de usuarios.
     */
    @Override
    public Long count() {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            return em.createQuery(
                    "select count(u) from User u", Long.class
            ).getSingleResult();
        } finally {
            em.close();
        }
    }

    /**
     * Busca un usuario en la base de datos por su nombre de usuario.
     *
     * @param nombreUsuario Nombre de usuario a buscar.
     * @return Un Optional que contiene el usuario encontrado, si existe.
     */
    public Optional<User> findByNombreUsuario(String nombreUsuario) {
        EntityManager em = entityManagerFactory.createEntityManager();
        try {
            TypedQuery<User> q = em.createQuery(
                    "select u from User u where u.nombreUsuario = :nombreUsuario",
                    User.class
            );
            q.setParameter("nombreUsuario", nombreUsuario);
            List<User> result = q.getResultList();
            return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
        } finally {
            em.close();
        }
    }
}
