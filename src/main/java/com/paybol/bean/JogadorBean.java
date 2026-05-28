package com.paybol.bean;

import com.paybol.dao.JogadorDAO;
import com.paybol.model.Jogador;
import com.paybol.model.StatusPagamento;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * CDI Managed Bean para a entidade Jogador.
 *
 * Esta classe funciona como o "Controller" na arquitetura MVC,
 * fazendo a ponte entre a interface JSF (View) e o DAO (Model).
 *
 * Anotações:
 * - @Named: Registra o bean no contexto CDI com o nome "jogadorBean",
 *   permitindo que as páginas JSF acessem seus métodos e propriedades
 *   usando Expression Language (EL): #{jogadorBean.propriedade}
 *
 * - @SessionScoped: Define que o bean vive durante toda a sessão
 *   do usuário no navegador. Os dados são mantidos enquanto o
 *   usuário não fechar o navegador ou a sessão expirar.
 */
@Named("jogadorBean")
@SessionScoped
public class JogadorBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Instância do DAO para operações de banco de dados */
    private final JogadorDAO dao = new JogadorDAO();

    /** Jogador atual sendo editado ou cadastrado */
    private Jogador jogador = new Jogador();

    /** Lista de todos os jogadores (exibida na tabela) */
    private List<Jogador> jogadores;

    /**
     * Método executado automaticamente após a criação do bean.
     * Carrega a lista inicial de jogadores do banco de dados.
     */
    @PostConstruct
    public void init() {
        carregarJogadores();
    }

    /**
     * Retorna os valores possíveis do enum StatusPagamento.
     * Usado no dropdown do formulário JSF.
     *
     * @return array com PAGO e PENDENTE
     */
    public StatusPagamento[] getStatusPagamentoValues() {
        return StatusPagamento.values();
    }

    /**
     * Prepara o formulário para cadastrar um novo jogador.
     * Cria uma nova instância limpa de Jogador.
     *
     * @return navegação para a página de cadastro
     */
    public String novo() {
        this.jogador = new Jogador();
        this.jogador.setStatusPagamento(StatusPagamento.PENDENTE);
        this.jogador.setValor(new BigDecimal("0.00"));
        return "cadastrar?faces-redirect=true";
    }

    /**
     * Salva um novo jogador no banco de dados.
     * Chamado pelo botão "Salvar" do formulário de cadastro.
     *
     * @return navegação de volta para a lista
     */
    public String salvar() {
        dao.salvar(jogador);
        this.jogador = new Jogador();
        carregarJogadores();
        return "index?faces-redirect=true";
    }

    /**
     * Prepara o formulário de edição com os dados do jogador selecionado.
     *
     * @param jogador o jogador a ser editado
     * @return navegação para a página de edição
     */
    public String editar(Jogador jogador) {
        this.jogador = dao.buscarPorId(jogador.getId());
        return "editar?faces-redirect=true";
    }

    /**
     * Atualiza os dados de um jogador existente no banco.
     * Chamado pelo botão "Atualizar" do formulário de edição.
     *
     * @return navegação de volta para a lista
     */
    public String atualizar() {
        dao.atualizar(jogador);
        this.jogador = new Jogador();
        carregarJogadores();
        return "index?faces-redirect=true";
    }

    /**
     * Exclui um jogador do banco de dados.
     * Chamado pelo botão "Excluir" na tabela.
     *
     * @param jogador o jogador a ser excluído
     * @return null (permanece na mesma página, a lista é atualizada)
     */
    public String excluir(Jogador jogador) {
        dao.excluir(jogador.getId());
        carregarJogadores();
        return null;
    }

    /**
     * Alterna o status de pagamento de um jogador.
     * PAGO -> PENDENTE ou PENDENTE -> PAGO.
     *
     * @param jogador o jogador cujo status será alternado
     */
    public void alternarStatus(Jogador jogador) {
        if (jogador.getStatusPagamento() == StatusPagamento.PAGO) {
            jogador.setStatusPagamento(StatusPagamento.PENDENTE);
        } else {
            jogador.setStatusPagamento(StatusPagamento.PAGO);
        }
        dao.atualizar(jogador);
        carregarJogadores();
    }

    /**
     * Recarrega a lista de jogadores do banco de dados.
     */
    private void carregarJogadores() {
        this.jogadores = dao.listarTodos();
    }

    /**
     * Retorna o total de jogadores que já pagaram.
     */
    public long getTotalPagos() {
        if (jogadores == null) return 0;
        return jogadores.stream()
                .filter(j -> j.getStatusPagamento() == StatusPagamento.PAGO)
                .count();
    }

    /**
     * Retorna o total de jogadores com pagamento pendente.
     */
    public long getTotalPendentes() {
        if (jogadores == null) return 0;
        return jogadores.stream()
                .filter(j -> j.getStatusPagamento() == StatusPagamento.PENDENTE)
                .count();
    }

    /**
     * Retorna a soma total dos valores arrecadados (jogadores que pagaram).
     */
    public BigDecimal getTotalArrecadado() {
        if (jogadores == null) return BigDecimal.ZERO;
        return jogadores.stream()
                .filter(j -> j.getStatusPagamento() == StatusPagamento.PAGO)
                .map(Jogador::getValor)
                .filter(v -> v != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    // ========== Getters e Setters ==========

    public Jogador getJogador() {
        return jogador;
    }

    public void setJogador(Jogador jogador) {
        this.jogador = jogador;
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }
}
