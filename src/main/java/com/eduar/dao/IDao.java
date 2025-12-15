package com.eduar.dao;

import java.util.ArrayList;

public interface IDao<T> {
    void guardar(T objeto);
    T buscarPorId(int id); 
    ArrayList<T> listar();
    boolean actualizar(T objeto);
    boolean eliminar(int id);
    int contar();
}