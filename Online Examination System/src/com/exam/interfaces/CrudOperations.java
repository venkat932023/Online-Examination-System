package com.exam.interfaces;

public interface CrudOperations<T, ID> {
    void add(T item);
    void update(ID id, T item);
    void delete(ID id);
    T findById(ID id);
}
