package com.eduar.dao;

import java.util.ArrayList;

/**
 * Interfaz genérica DAO
 * Define operaciones básicas de acceso a datos
 */
public interface IDao<T> {

    void guardar(T obj);

    ArrayList<T> listar();
}
