package org.example.retoconjuntoad_di_2.model.copia;

import org.example.retoconjuntoad_di_2.model.user.User;
import org.example.retoconjuntoad_di_2.utils.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para gestionar las operaciones CRUD de la entidad Copia.
 * Utiliza Hibernate para interactuar con la base de datos.
 */
public class CopiaRepository implements Repository<Copia> {

    private final SessionFactory sessionFactory; // Fábrica de sesiones de Hibernate.

    /**
     * Constructor que inicializa el repositorio con una fábrica de sesiones.
     *
     * @param sessionFactory Fábrica de sesiones de Hibernate.
     */
    public CopiaRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
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
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Copia managed;
            if (entity.getId() == null) {
                // Nueva copia -> INSERT
                session.persist(entity);
                managed = entity;
            } else {
                // Copia ya existente -> UPDATE
                managed = session.merge(entity);
            }

            session.getTransaction().commit();
            return managed;
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
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(entity);
            session.getTransaction().commit();
            return Optional.of(entity);
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
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(
                    session.byId(Copia.class).load(id.intValue())
            );
        }
    }

    /**
     * Obtiene todas las copias almacenadas en la base de datos.
     *
     * @return Una lista con todas las copias.
     */
    @Override
    public List<Copia> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("from Copia", Copia.class).list();
        }
    }

    /**
     * Cuenta el número total de copias en la base de datos.
     *
     * @return El número total de copias.
     */
    @Override
    public Long count() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "select count(c) from Copia c", Long.class
            ).getSingleResult();
        }
    }

    /**
     * Obtiene todas las copias asociadas a un usuario específico.
     *
     * @param user Usuario cuyas copias se desean obtener.
     * @return Una lista con las copias del usuario.
     */
    public List<Copia> findByUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            Query<Copia> query = session.createQuery(
                    "from Copia c where c.user = :user", Copia.class
            );
            query.setParameter("user", user);
            return query.list();
        }
    }

}
