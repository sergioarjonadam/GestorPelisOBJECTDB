package org.example.retoconjuntoad_di_2.utils;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz genérica que define las operaciones CRUD básicas para entidades del modelo.
 * <p>
 * Esta interfaz proporciona un contrato común para todos los repositorios de la aplicación,
 * garantizando que todas las entidades tengan acceso a las mismas operaciones básicas
 * de persistencia: crear, leer, actualizar y eliminar.
 * </p>
 * <p>
 * Las implementaciones de esta interfaz utilizan JPA/ObjectDB para realizar las operaciones
 * de persistencia en la base de datos.
 * </p>
 *
 * @param <T> Tipo de entidad que gestiona el repositorio.
 * @author Sistema de Gestión de Películas
 * @version 1.0
 */
public interface Repository<T> {
    
    /**
     * Guarda una entidad en la base de datos.
     * <p>
     * Si la entidad no tiene ID (es nueva), se inserta en la base de datos.
     * Si la entidad ya tiene ID, se actualiza en la base de datos.
     * </p>
     *
     * @param entity La entidad a guardar o actualizar.
     * @return La entidad guardada (puede ser una nueva instancia gestionada por JPA).
     */
    T save(T entity);
    
    /**
     * Elimina una entidad de la base de datos.
     * <p>
     * La entidad debe estar persistida en la base de datos para poder ser eliminada.
     * Si la entidad no está gestionada por el EntityManager, se hace merge antes de eliminarla.
     * </p>
     *
     * @param entity La entidad a eliminar.
     * @return Un Optional que contiene la entidad eliminada si la operación fue exitosa,
     *         o un Optional vacío si la entidad no existía.
     */
    Optional<T> delete(T entity);
    
    /**
     * Elimina una entidad de la base de datos por su ID.
     * <p>
     * Busca la entidad por su ID y, si existe, la elimina de la base de datos.
     * </p>
     *
     * @param id El ID de la entidad a eliminar.
     * @return Un Optional que contiene la entidad eliminada si existía,
     *         o un Optional vacío si no se encontró ninguna entidad con ese ID.
     */
    Optional<T> deleteById(Long id);

    /**
     * Busca una entidad en la base de datos por su ID.
     * <p>
     * Utiliza el método {@code find} de JPA para buscar la entidad de forma eficiente.
     * </p>
     *
     * @param id El ID de la entidad a buscar.
     * @return Un Optional que contiene la entidad encontrada si existe,
     *         o un Optional vacío si no se encontró ninguna entidad con ese ID.
     */
    Optional<T> findById(Long id);
    
    /**
     * Obtiene todas las entidades almacenadas en la base de datos.
     * <p>
     * Ejecuta una consulta JPQL para recuperar todas las instancias de la entidad.
     * </p>
     *
     * @return Una lista con todas las entidades encontradas. La lista estará vacía
     *         si no hay entidades en la base de datos.
     */
    List<T> findAll();
    
    /**
     * Cuenta el número total de entidades almacenadas en la base de datos.
     * <p>
     * Ejecuta una consulta de agregación COUNT para obtener el total de registros.
     * </p>
     *
     * @return El número total de entidades en la base de datos.
     */
    Long count();
}

