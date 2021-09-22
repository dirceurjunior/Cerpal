/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Dirceu Junior
 */
@Entity
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "envia_email")
    private Boolean enviaEmail;

    @Column(name = "altera_data_criacao_pedido")
    private Boolean alteradaDataCriacaoPedido;

    @ManyToOne
    @JoinColumn(name = "usuario_ultima_alteracao_id")
    private Usuario usuarioUltimaAlteracao;

    @Column(name = "data_ultima_alteracao")
    private LocalDateTime dataUltimaAlteracao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEnviaEmail() {
        return enviaEmail;
    }

    public void setEnviaEmail(Boolean enviaEmail) {
        this.enviaEmail = enviaEmail;
    }

    public Usuario getUsuarioUltimaAlteracao() {
        return usuarioUltimaAlteracao;
    }

    public void setUsuarioUltimaAlteracao(Usuario usuarioUltimaAlteracao) {
        this.usuarioUltimaAlteracao = usuarioUltimaAlteracao;
    }

    public LocalDateTime getDataUltimaAlteracao() {
        return dataUltimaAlteracao;
    }

    public void setDataUltimaAlteracao(LocalDateTime dataUltimaAlteracao) {
        this.dataUltimaAlteracao = dataUltimaAlteracao;
    }

    public Boolean getAlteradaDataCriacaoPedido() {
        return alteradaDataCriacaoPedido;
    }

    public void setAlteradaDataCriacaoPedido(Boolean alteradaDataCriacaoPedido) {
        this.alteradaDataCriacaoPedido = alteradaDataCriacaoPedido;
    }

}
