package org.example.retoconjuntoad_di_2.model.user;

import org.example.retoconjuntoad_di_2.utils.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones CRUD de la entidad User.
 * Utiliza Hibernate para interactuar con la base de datos.
 */
public class UserRepository implements Repository<User> {

    private SessionFactory sessionFactory; // Fábrica de sesiones de Hibernate.

    /**
     * Constructor que inicializa el repositorio con una fábrica de sesiones.
     *
     * @param sessionFactory Fábrica de sesiones de Hibernate.
     */
    public UserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(entity);
            session.getTransaction().commit();
            return entity;
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
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
            return Optional.of(entity);
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
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(User.class, id.intValue()));
        }
    }

    /**
     * Obtiene todos los usuarios almacenados en la base de datos.
     *
     * @return Una lista con todos los usuarios.
     */
    @Override
    public List<User> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from User", User.class).list();
        }
    }

    /**
     * Cuenta el número total de usuarios en la base de datos.
     *
     * @return El número total de usuarios.
     */
    @Override
    public Long count() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "select count(u) from User u", Long.class
            ).getSingleResult();
        }
    }

    /**
     * Busca un usuario en la base de datos por su nombre de usuario.
     *
     * @param nombreUsuario Nombre de usuario a buscar.
     * @return Un Optional que contiene el usuario encontrado, si existe.
     */
    public Optional<User> findByNombreUsuario(String nombreUsuario) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> q = session.createQuery(
                    "from User where nombreUsuario = :nombreUsuario",
                    User.class
            );
            q.setParameter("nombreUsuario", nombreUsuario);
            return Optional.ofNullable(q.uniqueResult());
        }
    }
}
