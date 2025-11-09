package com.renato.repository;

import com.renato.model.Usuario;
import com.renato.config.HibernateConfig;
import org.hibernate.Session;

public class UsuarioRepository extends GenericRepository<Usuario> {
    
    public UsuarioRepository() {
        super(Usuario.class);
    }

    public Usuario findByEmail(String email) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("from Usuario where email = :email", Usuario.class)
                    .setParameter("email", email)
                    .uniqueResult();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}