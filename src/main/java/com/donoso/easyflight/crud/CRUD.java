package com.donoso.easyflight.crud;

import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<E> {

    boolean add(E element);

    E search(E element);

    boolean update(E element);

    boolean delete(E element) throws SQLException;

    List<E> listAll();

}
