package com.paybol.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Classe utilitária para gerenciar o EntityManagerFactory.
 *
 * O EntityManagerFactory é responsável por criar instâncias de EntityManager,
 * que por sua vez gerenciam as operações de persistência (CRUD) com o banco de dados.
 *
 * Utiliza o padrão Singleton para garantir que apenas uma instância do
 * EntityManagerFactory seja criada durante toda a execução da aplicação,
 * economizando recursos de conexão com o banco MySQL.
 */
public class JPAUtil {

    /**
     * Instância única do EntityManagerFactory.
     * Inicializada uma vez com a Persistence Unit "paybol-pu" definida no persistence.xml.
     */
    private static final EntityManagerFactory EMF =
            Persistence.createEntityManagerFactory("paybol-pu");

    /**
     * Cria e retorna um novo EntityManager.
     * Cada operação de banco deve usar seu próprio EntityManager.
     *
     * @return uma nova instância de EntityManager
     */
    public static EntityManager getEntityManager() {
        return EMF.createEntityManager();
    }

    /**
     * Fecha o EntityManagerFactory.
     * Deve ser chamado quando a aplicação for encerrada para liberar
     * as conexões com o banco de dados.
     */
    public static void close() {
        if (EMF != null && EMF.isOpen()) {
            EMF.close();
        }
    }
}
