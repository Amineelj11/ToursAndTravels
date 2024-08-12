package com.toursandtravel.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toursandtravel.dao.LodgingDao;
import com.toursandtravel.entity.Lodging;

@Service
public class LodgingServiceImpl implements LodgingService {

	@Autowired
	private LodgingDao lodgingDao;

	@Override
	public Lodging add(Lodging lodging) {
		// TODO Auto-generated method stub
		return lodgingDao.save(lodging);
	}

	@Override
	public Lodging update(Lodging lodging) {
		// TODO Auto-generated method stub
		return lodgingDao.save(lodging);
	}

	@Override
	public Lodging getById(int id) {

		Optional<Lodging> optionalLodging = this.lodgingDao.findById(id);

		if (optionalLodging.isEmpty()) {
			return null;
		}

		return optionalLodging.get();
	}

	@Override
	public List<Lodging> getAllByStatus(String status) {
		// TODO Auto-generated method stub
		return this.lodgingDao.findByStatus(status);
	}

}
