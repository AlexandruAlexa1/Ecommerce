package com.ecommerce.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.admin.paging.PagingAndSortingHelper;
import com.ecommerce.common.entity.Role;
import com.ecommerce.common.entity.User;

@Service
@Transactional
public class UserService {
	public static final int USERS_PER_PAGE = 4;

	@Autowired private UserRepository userRepository;
	
	@Autowired private RoleRepository roleRepository;
	
	@Autowired private PasswordEncoder passwordEncoder;
	
	public List<User> listUsers() {
		List<User> users = (List<User>) userRepository.findAll(Sort.by("firstName").ascending());
		return users;
	}
	
	public void listByPage(int pageNum, PagingAndSortingHelper helper) {
		helper.listEntities(pageNum, USERS_PER_PAGE, userRepository);
	}
	
	public List<Role> listRoles() {
		List<Role> roles = (List<Role>) roleRepository.findAll();
		return roles;
	}
	
	public User getByEmail(String email) {
		return userRepository.getUserByEmail(email);
	}

	public User save(User user) {
		boolean isEditMode = (user.getId() != null);
		
		if (isEditMode) {
			User userInDB = userRepository.findById(user.getId()).get();
			
			if (user.getPassword().isEmpty()) {
				user.setPassword(userInDB.getPassword());
			} else {
				encodePassword(user);
			}
		} else {
			encodePassword(user);
		}
		return userRepository.save(user);
	}

	private void encodePassword(User user) {
		String passwordEncoded = passwordEncoder.encode(user.getPassword());
		user.setPassword(passwordEncoded);
	}
	
	public User updateAccount(User userInForm) {
		User userInDB = userRepository.findById(userInForm.getId()).get();
		
		if (!userInForm.getPassword().isEmpty()) {
			userInDB.setPassword(userInForm.getPassword());
			encodePassword(userInDB);
		}
		
		if (userInForm.getPhotos() != null) {
			userInDB.setPhotos(userInForm.getPhotos());
		}
		
		userInDB.setFirstName(userInForm.getFirstName());
		userInDB.setLastName(userInForm.getLastName());
		
		return userRepository.save(userInDB);
	}
	
	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepository.getUserByEmail(email);
		
		if (userByEmail == null) return true;
		
		boolean isCreatingNew = (id == null);
		
		if (isCreatingNew) {
			if (userByEmail != null) return false;
		} else {
			if (userByEmail.getId() != id) {
				return false;
			}
		}
		
		return true;
	}

	public User get(Integer id) throws UserNotFoundException {
		try {
			return userRepository.findById(id).get();
		} catch (NoSuchElementException ex) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
	}
	
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userRepository.countById(id);
		if (countById == null || countById == 0) {
			throw new UserNotFoundException("Could not find any user with ID " + id);
		}
		
		userRepository.deleteById(id);
	}
	
	public void updateUserEnableStatus(Integer id, boolean enabled) {
		userRepository.updateEnabledStatus(id, enabled);
	}

	
}
