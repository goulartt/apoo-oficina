package tattool.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

import org.hibernate.Session;

import tattool.domain.model.Usuario;
import tattool.entity.HibernateUtil;

public class UsuarioDao {
	
		
		 
		public Usuario verificaLogin(String username, String senha){
			EntityManager em = new HibernateUtil().getEntityManager();
		  try{
			  em.getTransaction().begin();
			  Session session = em.unwrap(Session.class);
			  Usuario query = session.createQuery("select u from Usuario u where u.usuario = :pUsuario and u.senha = :pSenha", Usuario.class)
					  .setParameter("pUsuario", username).setParameter("pSenha", senha).getSingleResult();
			  em.getTransaction().commit();
			  session.close();
			  return query;
		  }catch(Exception e) {
			  return null;
		  }
		  
		 }
		public void save(Usuario u){
			EntityManager em = new HibernateUtil().getEntityManager();
			try{
				em.getTransaction().begin();
				em.persist(u);
				em.getTransaction().commit();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		public void atualiza(Usuario u){
			EntityManager em = new HibernateUtil().getEntityManager();
			try{
				em.getTransaction().begin();
				em.merge(u);
				em.getTransaction().commit();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		public void delete(Usuario u){
			EntityManager em = new HibernateUtil().getEntityManager();
			try{
				em.getTransaction().begin();
				em.remove(u);
				em.getTransaction().commit();
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		public List<Usuario> findAll(){
			EntityManager em = new HibernateUtil().getEntityManager();
			List<Usuario> usuarios = new ArrayList<>();
			try{
				em.getTransaction().begin();
				usuarios = em.createQuery("select u from Usuario u", Usuario.class).getResultList();
				em.getTransaction().commit();
				return usuarios;
			}catch(Exception e) {
				e.printStackTrace();
				return null;
			}
			
		}
		public Usuario existeUsuario(String username){
			EntityManager em = new HibernateUtil().getEntityManager();
			try{
				em.getTransaction().begin();
				Session session = em.unwrap(Session.class);
				Usuario query = session.createQuery("select u from Usuario u where u.usuario = :pUsuario", Usuario.class)
						.setParameter("pUsuario", username).getSingleResult();
				em.getTransaction().commit();
				session.close();
				return query;
			}catch(Exception e) {
				return null;
			}
			
		}
		public void verificaAdmin(){
			EntityManager em = new HibernateUtil().getEntityManager();
			Session session = em.unwrap(Session.class);
			try{
				em.getTransaction().begin();
				
				Usuario query = session.createQuery("select u from Usuario u where u.usuario = :pUsuario and u.senha = :pSenha", Usuario.class)
						.setParameter("pUsuario", "admin").setParameter("pSenha", "1").getSingleResult();
				
				em.getTransaction().commit();
				 session.close();
			}catch(NoResultException e) {
				Usuario admin = new Usuario("admin", "1", "Administrador", 1);
				em.persist(admin);
				em.getTransaction().commit();
				session.close();
			}
			
		}
		
}
