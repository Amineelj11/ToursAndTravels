package com.toursandtravel.service;

import com.toursandtravel.entity.Type;

import java.util.List;

public interface TypeService {
    Type add(Type transport);

    Type update(Type transport);

    Type getById(int id);

    List<Type> getAllByStatus(String status);
}
