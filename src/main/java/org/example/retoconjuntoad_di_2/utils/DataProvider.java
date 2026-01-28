package org.example.retoconjuntoad_di_2.utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Proveedor de acceso a datos para la aplicación.
 * <p>
 * Esta clase proporciona un punto de acceso centralizado a la factoría de
 * EntityManager de JPA/ObjectDB. Utiliza el patrón Singleton para garantizar
 * que solo exista una instancia de EntityManagerFactory durante toda la ejecución
 * de la aplicación.
 * </p>
 * <p>
 * La base de datos se almacena en un archivo ObjectDB local en la ruta
 * {@code data/data.odb}. Si el archivo no existe, ObjectDB lo creará automáticamente
 * al realizar la primera operación de persistencia.
 * </p>
 *
 * @author Sistema de Gestión de Películas
 * @version 1.0
 */
public class DataProvider {

    /**
     * Ruta relativa al archivo de base de datos ObjectDB.
     * El archivo se creará automáticamente si no existe.
     */
    private static final String DB_FILE = "data/data.odb";

    /**
     * Factoría de EntityManager estática y compartida.
     * Se inicializa de forma lazy (bajo demanda) y se reutiliza durante toda la aplicación.
     */
    private static EntityManagerFactory entityManagerFactory = null;

    /**
     * Obtiene la instancia única de EntityManagerFactory.
     * <p>
     * Este método utiliza sincronización para garantizar la inicialización thread-safe
     * del EntityManagerFactory. Si la factoría aún no ha sido creada, la crea utilizando
     * la ruta de base de datos especificada. En llamadas posteriores, devuelve la misma
     * instancia reutilizada.
     * </p>
     * <p>
     * <strong>Nota:</strong> La EntityManagerFactory debe cerrarse al finalizar la aplicación
     * para liberar recursos. Sin embargo, en aplicaciones de escritorio simples esto puede
     * no ser crítico ya que los recursos se liberan al terminar el proceso.
     * </p>
     *
     * @return La instancia única de EntityManagerFactory configurada para ObjectDB.
     */
    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory(DB_FILE);
        }
        return entityManagerFactory;
    }
}
