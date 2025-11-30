package org.example.retoconjuntoad_di_2.utils;

public interface SessionService<T> {

    /**
     * Establece el objeto (usuario en nuestro caso) como activo en la sesión.
     */
    void login(T user);

    /**
     * Indica si hay alguien logueado.
     */
    boolean isLoggedIn();

    /**
     * Cierra la sesión actual y limpia los datos.
     */
    void logout();

    /**
     * Devuelve el objeto activo (usuario logueado).
     */
    T getActive();

    /**
     * Guarda un objeto cualquiera asociado a una clave en la sesión.
     */
    void setObject(String key, Object o);

    /**
     * Recupera un objeto asociado a una clave de la sesión.
     */
    Object getObject(String key);
}

