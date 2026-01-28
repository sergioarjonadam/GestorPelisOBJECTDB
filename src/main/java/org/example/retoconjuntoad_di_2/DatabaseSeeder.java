package org.example.retoconjuntoad_di_2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.retoconjuntoad_di_2.model.copia.Copia;
import org.example.retoconjuntoad_di_2.model.pelicula.Pelicula;
import org.example.retoconjuntoad_di_2.model.user.User;
import org.example.retoconjuntoad_di_2.utils.DataProvider;

/**
 * Clase de utilidad para inicializar datos de ejemplo en la base de datos ObjectDB.
 * <p>
 * Esta clase proporciona un método estático para poblar la base de datos con
 * datos iniciales de ejemplo si está vacía. Es útil para desarrollo y pruebas,
 * permitiendo tener datos de trabajo sin necesidad de crearlos manualmente.
 * </p>
 * <p>
 * <strong>Datos creados:</strong>
 * <ul>
 *   <li>Un usuario administrador (usuario: "admin", contraseña: "admin")</li>
 *   <li>Tres películas de ejemplo con información completa</li>
 *   <li>Tres copias asociadas al usuario administrador</li>
 * </ul>
 * </p>
 * <p>
 * <strong>Nota importante:</strong>
 * Este seeder está diseñado para ejecutarse sobre una base de datos vacía.
 * Si se ejecuta múltiples veces, generará registros duplicados. Para evitar
 * problemas con el mapeo de metadatos en consultas JPQL con ObjectDB, no se
 * realiza una consulta previa para verificar si la base de datos está vacía.
 * </p>
 *
 * @author Sistema de Gestión de Películas
 * @version 1.0
 */
public class DatabaseSeeder {

    /**
     * Ejecuta la siembra de datos en la base de datos ObjectDB.
     * <p>
     * Este método crea datos de ejemplo si la base de datos está vacía.
     * Se puede llamar al inicio de la aplicación para asegurar que siempre
     * haya datos disponibles para trabajar.
     * </p>
     * <p>
     * <strong>Datos creados:</strong>
     * <ul>
     *   <li>Usuario administrador: nombre "admin", contraseña "admin"</li>
     *   <li>Película: "El Señor de los Anillos: La Comunidad del Anillo" (2001)</li>
     *   <li>Película: "Matrix" (1999)</li>
     *   <li>Película: "El Padrino" (1972)</li>
     *   <li>Tres copias asociadas al usuario administrador</li>
     * </ul>
     * </p>
     * <p>
     * <strong>Manejo de errores:</strong>
     * Si ocurre algún error durante la inserción, se realiza un rollback
     * de la transacción y se imprime el error en la consola.
     * </p>
     */
    public static void seedIfEmpty() {
        EntityManagerFactory emf = DataProvider.getEntityManagerFactory();
        EntityManager em = emf.createEntityManager();

        try {
            // NOTA: Para evitar problemas con el mapeo de metadatos en consultas JPQL
            // con ObjectDB, no realizamos aquí una consulta previa (select count).
            // Este seeder está pensado para usarse sobre una base de datos vacía;
            // si se ejecuta varias veces, simplemente generará registros duplicados.
            em.getTransaction().begin();

            // Crear usuario administrador por defecto
            User admin = new User();
            admin.setNombreUsuario("admin");
            admin.setContrasena("admin");
            admin.setEsAdmin(true);
            em.persist(admin);

            // Crear algunas películas de ejemplo
            Pelicula p1 = new Pelicula();
            p1.setTitulo("El Señor de los Anillos: La Comunidad del Anillo");
            p1.setGenero("Fantasía");
            p1.setAnio((short) 2001);
            p1.setDirector("Peter Jackson");
            p1.setDescripcion("Un grupo de héroes intenta destruir el Anillo Único.");
            em.persist(p1);

            Pelicula p2 = new Pelicula();
            p2.setTitulo("Matrix");
            p2.setGenero("Ciencia ficción");
            p2.setAnio((short) 1999);
            p2.setDirector("Lana y Lilly Wachowski");
            p2.setDescripcion("Un hacker descubre la verdadera naturaleza de su realidad.");
            em.persist(p2);

            Pelicula p3 = new Pelicula();
            p3.setTitulo("El Padrino");
            p3.setGenero("Drama");
            p3.setAnio((short) 1972);
            p3.setDirector("Francis Ford Coppola");
            p3.setDescripcion("La historia de la familia Corleone en el mundo de la mafia.");
            em.persist(p3);

            // Crear algunas copias asociadas al usuario admin
            Copia c1 = new Copia();
            c1.setUser(admin);
            c1.setPelicula(p1);
            c1.setEstado("Nueva");
            c1.setSoporte("Blu-ray");
            em.persist(c1);

            Copia c2 = new Copia();
            c2.setUser(admin);
            c2.setPelicula(p2);
            c2.setEstado("Buena");
            c2.setSoporte("DVD");
            em.persist(c2);

            Copia c3 = new Copia();
            c3.setUser(admin);
            c3.setPelicula(p3);
            c3.setEstado("Usada");
            c3.setSoporte("VHS");
            em.persist(c3);

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}

