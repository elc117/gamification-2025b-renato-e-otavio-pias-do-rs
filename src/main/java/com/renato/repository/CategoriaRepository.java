package com.renato.repository;

import com.renato.model.Categoria;
import com.renato.config.HibernateConfig;
import org.hibernate.Session;


public class CategoriaRepository extends GenericRepository<Categoria> {
    public CategoriaRepository() {
        super(Categoria.class);
    }

    public Categoria findByNome(String nome) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            return session.createQuery("FROM Categoria WHERE nome = :nome", Categoria.class)
                    .setParameter("nome", nome)
                    .uniqueResult();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}