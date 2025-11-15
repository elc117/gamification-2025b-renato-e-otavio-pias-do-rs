package com.renato.repository;

import com.renato.model.PecaRecompensa;
import com.renato.config.HibernateConfig;
import org.hibernate.Session;
import java.util.List;

public class PecaRecompensaRepository extends GenericRepository<PecaRecompensa> {

    public PecaRecompensaRepository() {
        super(PecaRecompensa.class);
    }

    public List<PecaRecompensa> findByCategoria(Long categoriaId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery(
                "FROM PecaRecompensa WHERE categoriaId = :categoriaId", PecaRecompensa.class)
                .setParameter("categoriaId", categoriaId)
                .list();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
