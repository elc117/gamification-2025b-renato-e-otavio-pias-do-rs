package com.renato.repository;

import com.renato.model.ConquistaUsuario;
import com.renato.config.HibernateConfig;
import org.hibernate.Session;
import java.util.List;

public class ConquistaUsuarioRepository extends GenericRepository<ConquistaUsuario> {

    public ConquistaUsuarioRepository() {
        super(ConquistaUsuario.class);
    }

    public List<ConquistaUsuario> findByUsuario(Long usuarioId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM ConquistaUsuario WHERE usuarioId = :usuarioId", ConquistaUsuario.class)
                .setParameter("usuarioId", usuarioId)
                .list();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
