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
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Verifica se existe acerto específico (não apenas tentativa).
     * Usado para bloquear apenas notícias acertadas.
     */
    public boolean existsAcertoByUsuarioAndNoticia(Long usuarioId, Long noticiaId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                "SELECT COUNT(r.id) FROM Resposta r WHERE r.usuarioId = :usuarioId AND r.noticiaId = :noticiaId AND r.estaCorreta = true",
                Long.class)
                .setParameter("usuarioId", usuarioId)
                .setParameter("noticiaId", noticiaId)
                .uniqueResult();
            return count != null && count > 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Busca uma resposta existente do usuário para uma notícia específica.
     * Retorna null se não existir.
     */
    public Resposta findByUsuarioAndNoticia(Long usuarioId, Long noticiaId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM Resposta WHERE usuarioId = :usuarioId AND noticiaId = :noticiaId",
                Resposta.class)
                .setParameter("usuarioId", usuarioId)
                .setParameter("noticiaId", noticiaId)
                .uniqueResult();
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {
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
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Conta quantas notícias únicas o usuário acertou em uma categoria específica.
     * @param usuarioId ID do usuário
     * @param categoriaId ID da categoria
     * @return quantidade de notícias únicas acertadas
     */
    public long countAcertosByUsuarioAndCategoria(Long usuarioId, Long categoriaId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                "SELECT COUNT(DISTINCT r.noticiaId) FROM Resposta r " +
                "JOIN Noticia n ON n.id = r.noticiaId " +
                "WHERE r.usuarioId = :usuarioId AND r.estaCorreta = true AND n.categoriaId = :categoriaId",
                Long.class)
                .setParameter("usuarioId", usuarioId)
                .setParameter("categoriaId", categoriaId)
                .uniqueResult();
            return count != null ? count : 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Retorna todas as respostas do usuário em uma categoria específica (para calcular tentativas).
     * Como só salvamos acertos, este método retorna apenas acertos da categoria.
     * @param usuarioId ID do usuário
     * @param categoriaId ID da categoria
     * @return lista de respostas na categoria
     */
    public List<Resposta> findByUsuarioAndCategoria(Long usuarioId, Long categoriaId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery(
                "SELECT r FROM Resposta r JOIN Noticia n ON n.id = r.noticiaId " +
                "WHERE r.usuarioId = :usuarioId AND n.categoriaId = :categoriaId",
                Resposta.class)
                .setParameter("usuarioId", usuarioId)
                .setParameter("categoriaId", categoriaId)
                .list();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Conta total de tentativas (acertos + erros) do usuário em uma categoria.
     * @param usuarioId ID do usuário
     * @param categoriaId ID da categoria
     * @return total de tentativas
     */
    public long countAllByUsuarioAndCategoria(Long usuarioId, Long categoriaId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            Long count = session.createQuery(
                "SELECT COUNT(r.id) FROM Resposta r JOIN Noticia n ON n.id = r.noticiaId " +
                "WHERE r.usuarioId = :usuarioId AND n.categoriaId = :categoriaId",
                Long.class)
                .setParameter("usuarioId", usuarioId)
                .setParameter("categoriaId", categoriaId)
                .uniqueResult();
            return count != null ? count : 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
