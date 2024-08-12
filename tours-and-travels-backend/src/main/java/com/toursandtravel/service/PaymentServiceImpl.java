package com.toursandtravel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.toursandtravel.dao.PaymentDao;
import com.toursandtravel.entity.Payment;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private PaymentDao paymentDao;

	@Override
	public Payment addPayment(Payment payment) {
		// TODO Auto-generated method stub
		return paymentDao.save(payment);
	}

}
