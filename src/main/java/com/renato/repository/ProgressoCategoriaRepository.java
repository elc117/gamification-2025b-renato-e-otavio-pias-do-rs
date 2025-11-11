package com.renato.repository;

import com.renato.model.ProgressoCategoria;
import com.renato.config.HibernateConfig;
import org.hibernate.Session;
import java.util.List;

public class ProgressoCategoriaRepository extends GenericRepository<ProgressoCategoria> {

    public ProgressoCategoriaRepository() {
        super(ProgressoCategoria.class);
    }

    public ProgressoCategoria findByUsuarioAndCategoria(Long usuarioId, Long categoriaId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM ProgressoCategoria WHERE usuarioId = :usuarioId AND categoriaId = :categoriaId",
                ProgressoCategoria.class)
                .setParameter("usuarioId", usuarioId)
                .setParameter("categoriaId", categoriaId)
                .uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<ProgressoCategoria> findByUsuario(Long usuarioId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM ProgressoCategoria WHERE usuarioId = :usuarioId", ProgressoCategoria.class)
                .setParameter("usuarioId", usuarioId)
                .list();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
