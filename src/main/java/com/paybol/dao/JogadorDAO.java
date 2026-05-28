package com.paybol.dao;

import com.paybol.model.Jogador;
import com.paybol.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.io.Serializable;
import java.util.List;

/**
 * Data Access Object (DAO) para a entidade Jogador.
 *
 * Esta classe é responsável por todas as operações de CRUD
 * (Create, Read, Update, Delete) no banco de dados,
 * utilizando a API JPA através do EntityManager.
 *
 * Cada método obtém seu próprio EntityManager e gerencia
 * sua própria transação para garantir a integridade dos dados.
 */
public class JogadorDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * CREATE - Salva um novo jogador no banco de dados.
     * Utiliza em.persist() que executa um INSERT no MySQL.
     *
     * @param jogador o jogador a ser salvo
     */
    public void salvar(Jogador jogador) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(jogador);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * READ - Lista todos os jogadores cadastrados.
     * Utiliza JPQL (Jakarta Persistence Query Language) para o SELECT.
     *
     * @return lista de todos os jogadores
     */
    public List<Jogador> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT j FROM Jogador j ORDER BY j.nome", Jogador.class)
                     .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * READ - Busca um jogador pelo seu ID.
     * Utiliza em.find() que executa um SELECT WHERE id = ?.
     *
     * @param id o identificador do jogador
     * @return o jogador encontrado, ou null se não existir
     */
    public Jogador buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Jogador.class, id);
        } finally {
            em.close();
        }
    }

    /**
     * UPDATE - Atualiza os dados de um jogador existente.
     * Utiliza em.merge() que executa um UPDATE no MySQL.
     *
     * @param jogador o jogador com os dados atualizados
     * @return o jogador atualizado (managed entity)
     */
    public Jogador atualizar(Jogador jogador) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Jogador atualizado = em.merge(jogador);
            tx.commit();
            return atualizado;
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * DELETE - Remove um jogador do banco de dados.
     * Primeiro busca a entidade com em.find(), depois remove com em.remove().
     *
     * @param id o identificador do jogador a ser removido
     */
    public void excluir(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Jogador jogador = em.find(Jogador.class, id);
            if (jogador != null) {
                em.remove(jogador);
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }
}
