package tattool.entity;



import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtil {
	
	

	
	private static EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("oficina");
	
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void close(EntityManager em) {
		em.close();
	}
	
public static void main(String[] args){
        
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("oficina");
        
        factory.close();
    }
}
