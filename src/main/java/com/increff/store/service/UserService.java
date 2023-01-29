package com.increff.store.service;

import com.increff.store.dao.UserDao;
import com.increff.store.pojo.UserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional(rollbackOn = ApiException.class)
public class UserService {

	@Autowired
	private UserDao dao;

	public Integer add(UserPojo p) throws ApiException {
		normalize(p);
		UserPojo existing = dao.select(p.getEmail());
		if (existing != null) {
			throw new ApiException("User with given email already exists");
		}
		return dao.insert(p);
	}

	public UserPojo getById(Integer id) throws ApiException
	{
			UserPojo pojo = dao.select(id);
			if(pojo == null)
				throw new ApiException("No user found with given Id");
			return pojo;
	}

	public UserPojo getByEmail(String email) throws ApiException {
		return dao.select(email);
	}

	public List<UserPojo> getAll() {
		return dao.selectAll();
	}

	public void delete(Integer id) {
		dao.delete(id);
	}

	protected static void normalize(UserPojo p) {
		p.setEmail(p.getEmail().toLowerCase().trim());
		p.setRole(p.getRole().toLowerCase().trim());
	}
}
