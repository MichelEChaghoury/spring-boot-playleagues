package org.ultims.playleagues.service;

import java.util.List;

public interface EntityService<T, S> {

    List<T> retrieveAll();

    T retrieveById(S id);

    boolean create(T entity);

    boolean update(T entity);

    boolean deleteById(S id);

    boolean doesExist(S id);
}
