package com.planemanagement.planemanagement.controlador;

import javafx.collections.ObservableList;

public interface CRUD<E> {

    boolean add(E element);

    E search(E element);

    boolean update(E element);

    boolean delete(E element);

    ObservableList<E> listAll();

}
