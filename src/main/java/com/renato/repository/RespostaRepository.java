package com.renato.repository;

import com.renato.model.Resposta;
import com.renato.config.HibernateConfig;
import org.hibernate.Session;
import java.util.List;

public class RespostaRepository extends GenericRepository<Resposta> {

    public RespostaRepository() {
        super(Resposta.class);
    }

    public boolean existsByUsuarioAndNoticia(Long usuarioId, Long noticiaId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                "SELECT COUNT(r.id) FROM Resposta r WHERE r.usuarioId = :usuarioId AND r.noticiaId = :noticiaId",
                Long.class)
                .setParameter("usuarioId", usuarioId)
                .setParameter("noticiaId", noticiaId)
                .uniqueResult();
            return count != null && count > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<Resposta> findByUsuario(Long usuarioId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM Resposta WHERE usuarioId = :usuarioId", Resposta.class)
                .setParameter("usuarioId", usuarioId)
                .list();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public long countByUsuario(Long usuarioId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                "SELECT COUNT(r.id) FROM Resposta r WHERE r.usuarioId = :usuarioId", Long.class)
                .setParameter("usuarioId", usuarioId)
                .uniqueResult();
            return count != null ? count : 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public long countAcertosByUsuario(Long usuarioId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                "SELECT COUNT(r.id) FROM Resposta r WHERE r.usuarioId = :usuarioId AND r.estaCorreta = true",
                Long.class)
                .setParameter("usuarioId", usuarioId)
                .uniqueResult();
            return count != null ? count : 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
