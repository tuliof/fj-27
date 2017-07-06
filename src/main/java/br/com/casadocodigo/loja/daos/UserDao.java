package br.com.casadocodigo.loja.daos;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.casadocodigo.loja.models.User;

@Repository
public class UserDao implements UserDetailsService {

	@PersistenceContext
	private EntityManager manager;

	private final String jpql = "select u from User u where u.email = :email";
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		
		try {
			user = manager.createQuery(jpql, User.class)
			.setParameter("email", username)
			.getSingleResult();
		} catch (NoResultException ex) {
			throw new UsernameNotFoundException("User not found.", ex);
		}
		
		return user;
	}
}
