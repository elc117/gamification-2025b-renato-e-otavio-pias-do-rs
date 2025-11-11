package com.renato.repository;

import com.renato.model.PecaUsuario;
import com.renato.config.HibernateConfig;
import org.hibernate.Session;
import java.util.List;

public class PecaUsuarioRepository extends GenericRepository<PecaUsuario> {

    public PecaUsuarioRepository() {
        super(PecaUsuario.class);
    }

    public List<PecaUsuario> findByUsuario(Long usuarioId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM PecaUsuario WHERE usuarioId = :usuarioId", PecaUsuario.class)
                .setParameter("usuarioId", usuarioId)
                .list();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
