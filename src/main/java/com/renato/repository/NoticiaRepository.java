package com.renato.repository;

import com.renato.model.Noticia;
import com.renato.config.HibernateConfig;
import org.hibernate.Session;
import java.util.List;

public class NoticiaRepository extends GenericRepository<Noticia> {

    public NoticiaRepository() {
        super(Noticia.class);
    }

    public List<Noticia> findByCategoria(Long categoriaId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM Noticia WHERE categoriaId = :categoriaId", Noticia.class)
                .setParameter("categoriaId", categoriaId)
                .list();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<Noticia> findNaoRespondidasPorUsuario(Long usuarioId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery(
                "SELECT n FROM Noticia n WHERE NOT EXISTS " +
                "(SELECT 1 FROM Resposta r WHERE r.noticiaId = n.id AND r.usuarioId = :usuarioId)",
                Noticia.class)
                .setParameter("usuarioId", usuarioId)
                .list();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Conta o total de notícias em uma categoria específica.
     * @param categoriaId ID da categoria
     * @return quantidade total de notícias
     */
    public long countByCategoria(Long categoriaId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery(
                "SELECT COUNT(n) FROM Noticia n WHERE n.categoriaId = :categoriaId", Long.class)
                .setParameter("categoriaId", categoriaId)
                .getSingleResult();
        }
        catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}

