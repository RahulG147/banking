package com.lti.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lti.entity.Account;
import com.lti.entity.Registration;
import com.lti.repository.CustomerRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private CustomerRepository customerRepository;
	
	//business logic to autogenerated id
	public long register(Registration customer) {
		if(customerRepository.isCustomerPresent(customer.getAadhaarNo()))
			throw new ServiceException("Customer already registered !");
		else {
			Registration updateCustomer = (Registration) customerRepository.save(customer);
			return updateCustomer.getReferenceNo();
		}
	}
	
	public Account login(long customerId, String loginPassword) {
		try {
			long id = customerRepository.fetchIdByLoginIdAndPassword(customerId, loginPassword);
			Account account = customerRepository.find(Account.class, id);
			return account;
		}
		catch(EmptyResultDataAccessException e) {
			throw new ServiceException("Invalid email/password");
		}
	}

}
