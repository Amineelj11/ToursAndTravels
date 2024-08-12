package com.toursandtravel.service;

import com.toursandtravel.dao.TypeDao;

import com.toursandtravel.entity.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TypeProgServiceImpl implements TypeService{
    @Autowired
    private TypeDao transportDao;

    @Override
    public Type add(Type transport) {
        return transportDao.save(transport);
    }

    @Override
    public Type update(Type transport) {
        return transportDao.save(transport);
    }

    @Override
    public Type getById(int id) {
        Optional<Type> optionalTransport = this.transportDao.findById(id);

        if (optionalTransport.isEmpty()) {
            return null;
        }

        return optionalTransport.get();
    }

    @Override
    public List<Type> getAllByStatus(String status) {
        return this.transportDao.findByStatus(status);
    }
}
