package info.drj.controller;

import info.drj.model.Produto;
import info.drj.repository.Produtos;
import info.drj.repository.filter.ProdutoFilter;
import info.drj.util.jsf.FacesUtil;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Dirceu Junior
 */
@Named
@ViewScoped
public class PesquisaProdutosBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Produtos produtos;

    private ProdutoFilter filtro;

    private Produto produtoSelecionado;

    private List<Produto> produtosFiltrados;

    public PesquisaProdutosBean() {
        filtro = new ProdutoFilter();
    }

    public void excluir() {
        produtos.remover(produtoSelecionado);
        produtosFiltrados.remove(produtoSelecionado);
        FacesUtil.addInfoMessage("Produto " + produtoSelecionado.getNome() + " excluído com sucesso.");
    }

    public void pesquisar() {
        produtosFiltrados = produtos.filtrados(filtro);
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

    public ProdutoFilter getFiltro() {
        return filtro;
    }

    public void setFiltro(ProdutoFilter filtro) {
        this.filtro = filtro;
    }

    public List<Produto> getProdutosFiltrados() {
        return produtosFiltrados;
    }

    public void setProdutosFiltrados(List<Produto> produtosFiltrados) {
        this.produtosFiltrados = produtosFiltrados;
    }

    public Produto getProdutoSelecionado() {
        return produtoSelecionado;
    }

    public void setProdutoSelecionado(Produto produtoSelecionado) {
        this.produtoSelecionado = produtoSelecionado;
    }

}
