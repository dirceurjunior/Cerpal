/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.controller;

import info.drj.model.Anexo;
import info.drj.model.Produto;
import info.drj.repository.Produtos;
import info.drj.service.CadastroProdutoService;
import info.drj.util.jsf.FacesUtil;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Dirceu Junior
 */
@Named
@ViewScoped
public class CadastroProdutoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private Produtos produtos;

    @Inject
    private CadastroProdutoService cadastroProdutoService;

    @Inject
    ImagemBean imagemBean;

    private Produto produto;

    private Anexo anexo;

    private Anexo anexoSelecionado;

    private StreamedContent imagem;

    private List<Produto> todosProdutos = new ArrayList<>();

    public CadastroProdutoBean() {
        limpar();
    }

    public void inicializar() {
        todosProdutos = produtos.todos();
    }

    private void limpar() {
        produto = new Produto();
        this.anexo = new Anexo();
        imagem = new DefaultStreamedContent();
    }

    public boolean isEditando() {
        if (this.produto != null) {
            return produto.getId() != null;
        }
        return false;
    }

    public void salvar() {
        this.produto = cadastroProdutoService.salvar(this.produto);
        limpar();
        FacesUtil.addInfoMessage("Produto salvo com sucesso!");
    }

    public void adicionarAnexo(FileUploadEvent event) {
        try {
            imagem = new DefaultStreamedContent(event.getFile().getInputstream());
            //Cria um arquivo UploadFile, para receber o arquivo do evento
            UploadedFile arq = event.getFile();
            //Transformar a imagem em bytes para salvar em banco de dados
            byte[] bimagem = arq.getContents();
            anexo.setArquivo(bimagem);
            anexo.setNome(arq.getFileName());
            anexo.setProduto(produto);
            produto.getAnexos().add(anexo);
            imagemBean.uploadImagem(this.anexo.getArquivo());
            novoAnexo();
            FacesUtil.addInfoMessage("ARQUIVO " + event.getFile().getFileName() + " ADICIONADO");
        } catch (IOException ex) {
            Logger.getLogger(CadastroProdutoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void removerAnexo() {
        if (anexoSelecionado != null) {
            produto.getAnexos().remove(anexoSelecionado);
        }
        novoAnexo();
    }

    public StreamedContent baixarAnexo(Anexo anexo) {
        return new DefaultStreamedContent(new ByteArrayInputStream(anexo.getArquivo()), "application/octet-stream", anexo.getNome());
    }

    public void novoAnexo() {
        this.anexo = new Anexo();
        this.anexoSelecionado = null;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        if (produto != null) {
            this.produto = produto;
        }
    }

    public List<Produto> getTodosProdutos() {
        return todosProdutos;
    }

    public void setTodosProdutos(List<Produto> todosProdutos) {
        this.todosProdutos = todosProdutos;
    }

    public Anexo getAnexo() {
        return anexo;
    }

    public void setAnexo(Anexo anexo) {
        this.anexo = anexo;
    }

    public Anexo getAnexoSelecionado() {
        return anexoSelecionado;
    }

    public void setAnexoSelecionado(Anexo anexoSelecionado) {
        this.anexoSelecionado = anexoSelecionado;
    }

    public StreamedContent getImagem() {
        return imagem;
    }

    public void setImagem(StreamedContent imagem) {
        this.imagem = imagem;
    }

}
