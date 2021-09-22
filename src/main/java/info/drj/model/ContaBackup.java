/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Dirceu Junior
 */
//@Entity
public class ContaBackup implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data_emissao", length = 10)
    private String dataEmissao;

    @Column(name = "data_vencimento", length = 10)
    private String dataVencimento;

    @Column(name = "data_apresentacao", length = 10)
    private String dataApresentacao;

    @Column(name = "data_referencia", length = 7)
    private String dataReferencia;

    @Column(name = "nota_fiscal", length = 7)
    private String notaFiscal;

    private String nome;

    private String codigoBairro;

    private String descricaoBairro;

    private String codigoMunicipio;

    private String descricaoMunicipio;

    private String matricula;

    private String cadastro;

    private String cnpj;

    private String inscricaoEstadual;

    private String plano;

    private String carga;

    private String produtor;

    @NotNull(message = "CPF deve ser informado!")
    private String cpf;

    private String perdas;

    private String fatorPotencia;

    private String medidor;

    private String constante;

    private String leituraAnterior;

    private String leituraAtual;

    private String demanda;

    private String livro;

    private String dataLeitura;

    private String competencia01;

    private String consumo01;

    private String competencia02;

    private String consumo02;

    private String competencia03;

    private String consumo03;

    private String competencia04;

    private String consumo04;

    private String competencia05;

    private String consumo05;

    private String competencia06;

    private String consumo06;

    private String competencia07;

    private String consumo07;

    private String competencia08;

    private String consumo08;

    private String competencia09;

    private String consumo09;

    private String competencia10;

    private String consumo10;

    private String competencia11;

    private String consumo11;

    private String competencia12;

    private String consumo12;

    private String descricao01;

    private String descricao02;

    private String descricao03;

    private String descricao04;

    private String descricao05;

    private String descricao06;

    private String descricao07;

    private String descricao08;

    private String fator01;

    private String fator02;

    private String fator03;

    private String fator04;

    private String fator05;

    private String fator06;

    private String fator07;

    private String fator08;

    private String valorUnitario01;

    private String valorUnitario02;

    private String valorUnitario03;

    private String valorUnitario04;

    private String valorUnitario05;

    private String valorUnitario06;

    private String valorUnitario07;

    private String valorUnitario08;

    private String valorTotal01;

    private String valorTotal02;

    private String valorTotal03;

    private String valorTotal04;

    private String valorTotal05;

    private String valorTotal06;

    private String valorTotal07;

    private String valorTotal08;

    private String valorSemAcrescimo;

    private String valorAcrescimo;

    private String valorComAcrescimo;

    private String baseIcms;

    private String aliquota;

    private String valorIcms;

    private String codigoBanco;

    private String codigoAgencia;

    private String codigoConta;

    private Boolean quitado = Boolean.FALSE;

    @Column(name = "data_pagamento", length = 10)
    private String dataPagamento;

    @Transient
    public boolean isNovo() {
        return getId() == null;
    }

    @Transient
    public boolean isExistente() {
        return !isNovo();
    }

    @Transient
    public boolean isSituacao() {
        return quitado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(String dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getDataApresentacao() {
        return dataApresentacao;
    }

    public void setDataApresentacao(String dataApresentacao) {
        this.dataApresentacao = dataApresentacao;
    }

    public String getDataReferencia() {
        return dataReferencia;
    }

    public void setDataReferencia(String dataReferencia) {
        this.dataReferencia = dataReferencia;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigoBairro() {
        return codigoBairro;
    }

    public void setCodigoBairro(String codigoBairro) {
        this.codigoBairro = codigoBairro;
    }

    public String getDescricaoBairro() {
        return descricaoBairro;
    }

    public void setDescricaoBairro(String descricaoBairro) {
        this.descricaoBairro = descricaoBairro;
    }

    public String getCodigoMunicipio() {
        return codigoMunicipio;
    }

    public void setCodigoMunicipio(String codigoMunicipio) {
        this.codigoMunicipio = codigoMunicipio;
    }

    public String getDescricaoMunicipio() {
        return descricaoMunicipio;
    }

    public void setDescricaoMunicipio(String descricaoMunicipio) {
        this.descricaoMunicipio = descricaoMunicipio;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCadastro() {
        return cadastro;
    }

    public void setCadastro(String cadastro) {
        this.cadastro = cadastro;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getPlano() {
        return plano;
    }

    public void setPlano(String plano) {
        this.plano = plano;
    }

    public String getCarga() {
        return carga;
    }

    public void setCarga(String carga) {
        this.carga = carga;
    }

    public String getProdutor() {
        return produtor;
    }

    public void setProdutor(String produtor) {
        this.produtor = produtor;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPerdas() {
        return perdas;
    }

    public void setPerdas(String perdas) {
        this.perdas = perdas;
    }

    public String getFatorPotencia() {
        return fatorPotencia;
    }

    public void setFatorPotencia(String fatorPotencia) {
        this.fatorPotencia = fatorPotencia;
    }

    public String getMedidor() {
        return medidor;
    }

    public void setMedidor(String medidor) {
        this.medidor = medidor;
    }

    public String getConstante() {
        return constante;
    }

    public void setConstante(String constante) {
        this.constante = constante;
    }

    public String getLeituraAnterior() {
        return leituraAnterior;
    }

    public void setLeituraAnterior(String leituraAnterior) {
        this.leituraAnterior = leituraAnterior;
    }

    public String getLeituraAtual() {
        return leituraAtual;
    }

    public void setLeituraAtual(String leituraAtual) {
        this.leituraAtual = leituraAtual;
    }

    public String getDemanda() {
        return demanda;
    }

    public void setDemanda(String demanda) {
        this.demanda = demanda;
    }

    public String getLivro() {
        return livro;
    }

    public void setLivro(String livro) {
        this.livro = livro;
    }

    public String getDataLeitura() {
        return dataLeitura;
    }

    public void setDataLeitura(String dataLeitura) {
        this.dataLeitura = dataLeitura;
    }

    public String getCompetencia01() {
        return competencia01;
    }

    public void setCompetencia01(String competencia01) {
        this.competencia01 = competencia01;
    }

    public String getConsumo01() {
        return consumo01;
    }

    public void setConsumo01(String consumo01) {
        this.consumo01 = consumo01;
    }

    public String getCompetencia02() {
        return competencia02;
    }

    public void setCompetencia02(String competencia02) {
        this.competencia02 = competencia02;
    }

    public String getConsumo02() {
        return consumo02;
    }

    public void setConsumo02(String consumo02) {
        this.consumo02 = consumo02;
    }

    public String getCompetencia03() {
        return competencia03;
    }

    public void setCompetencia03(String competencia03) {
        this.competencia03 = competencia03;
    }

    public String getConsumo03() {
        return consumo03;
    }

    public void setConsumo03(String consumo03) {
        this.consumo03 = consumo03;
    }

    public String getCompetencia04() {
        return competencia04;
    }

    public void setCompetencia04(String competencia04) {
        this.competencia04 = competencia04;
    }

    public String getConsumo04() {
        return consumo04;
    }

    public void setConsumo04(String consumo04) {
        this.consumo04 = consumo04;
    }

    public String getCompetencia05() {
        return competencia05;
    }

    public void setCompetencia05(String competencia05) {
        this.competencia05 = competencia05;
    }

    public String getConsumo05() {
        return consumo05;
    }

    public void setConsumo05(String consumo05) {
        this.consumo05 = consumo05;
    }

    public String getCompetencia06() {
        return competencia06;
    }

    public void setCompetencia06(String competencia06) {
        this.competencia06 = competencia06;
    }

    public String getConsumo06() {
        return consumo06;
    }

    public void setConsumo06(String consumo06) {
        this.consumo06 = consumo06;
    }

    public String getCompetencia07() {
        return competencia07;
    }

    public void setCompetencia07(String competencia07) {
        this.competencia07 = competencia07;
    }

    public String getConsumo07() {
        return consumo07;
    }

    public void setConsumo07(String consumo07) {
        this.consumo07 = consumo07;
    }

    public String getCompetencia08() {
        return competencia08;
    }

    public void setCompetencia08(String competencia08) {
        this.competencia08 = competencia08;
    }

    public String getConsumo08() {
        return consumo08;
    }

    public void setConsumo08(String consumo08) {
        this.consumo08 = consumo08;
    }

    public String getCompetencia09() {
        return competencia09;
    }

    public void setCompetencia09(String competencia09) {
        this.competencia09 = competencia09;
    }

    public String getConsumo09() {
        return consumo09;
    }

    public void setConsumo09(String consumo09) {
        this.consumo09 = consumo09;
    }

    public String getCompetencia10() {
        return competencia10;
    }

    public void setCompetencia10(String competencia10) {
        this.competencia10 = competencia10;
    }

    public String getConsumo10() {
        return consumo10;
    }

    public void setConsumo10(String consumo10) {
        this.consumo10 = consumo10;
    }

    public String getCompetencia11() {
        return competencia11;
    }

    public void setCompetencia11(String competencia11) {
        this.competencia11 = competencia11;
    }

    public String getConsumo11() {
        return consumo11;
    }

    public void setConsumo11(String consumo11) {
        this.consumo11 = consumo11;
    }

    public String getCompetencia12() {
        return competencia12;
    }

    public void setCompetencia12(String competencia12) {
        this.competencia12 = competencia12;
    }

    public String getConsumo12() {
        return consumo12;
    }

    public void setConsumo12(String consumo12) {
        this.consumo12 = consumo12;
    }

    public String getDescricao01() {
        return descricao01;
    }

    public void setDescricao01(String descricao01) {
        this.descricao01 = descricao01;
    }

    public String getDescricao02() {
        return descricao02;
    }

    public void setDescricao02(String descricao02) {
        this.descricao02 = descricao02;
    }

    public String getDescricao03() {
        return descricao03;
    }

    public void setDescricao03(String descricao03) {
        this.descricao03 = descricao03;
    }

    public String getDescricao04() {
        return descricao04;
    }

    public void setDescricao04(String descricao04) {
        this.descricao04 = descricao04;
    }

    public String getDescricao05() {
        return descricao05;
    }

    public void setDescricao05(String descricao05) {
        this.descricao05 = descricao05;
    }

    public String getDescricao06() {
        return descricao06;
    }

    public void setDescricao06(String descricao06) {
        this.descricao06 = descricao06;
    }

    public String getDescricao07() {
        return descricao07;
    }

    public void setDescricao07(String descricao07) {
        this.descricao07 = descricao07;
    }

    public String getDescricao08() {
        return descricao08;
    }

    public void setDescricao08(String descricao08) {
        this.descricao08 = descricao08;
    }

    public String getFator01() {
        return fator01;
    }

    public void setFator01(String fator01) {
        this.fator01 = fator01;
    }

    public String getFator02() {
        return fator02;
    }

    public void setFator02(String fator02) {
        this.fator02 = fator02;
    }

    public String getFator03() {
        return fator03;
    }

    public void setFator03(String fator03) {
        this.fator03 = fator03;
    }

    public String getFator04() {
        return fator04;
    }

    public void setFator04(String fator04) {
        this.fator04 = fator04;
    }

    public String getFator05() {
        return fator05;
    }

    public void setFator05(String fator05) {
        this.fator05 = fator05;
    }

    public String getFator06() {
        return fator06;
    }

    public void setFator06(String fator06) {
        this.fator06 = fator06;
    }

    public String getFator07() {
        return fator07;
    }

    public void setFator07(String fator07) {
        this.fator07 = fator07;
    }

    public String getFator08() {
        return fator08;
    }

    public void setFator08(String fator08) {
        this.fator08 = fator08;
    }

    public String getValorUnitario01() {
        return valorUnitario01;
    }

    public void setValorUnitario01(String valorUnitario01) {
        this.valorUnitario01 = valorUnitario01;
    }

    public String getValorUnitario02() {
        return valorUnitario02;
    }

    public void setValorUnitario02(String valorUnitario02) {
        this.valorUnitario02 = valorUnitario02;
    }

    public String getValorUnitario03() {
        return valorUnitario03;
    }

    public void setValorUnitario03(String valorUnitario03) {
        this.valorUnitario03 = valorUnitario03;
    }

    public String getValorUnitario04() {
        return valorUnitario04;
    }

    public void setValorUnitario04(String valorUnitario04) {
        this.valorUnitario04 = valorUnitario04;
    }

    public String getValorUnitario05() {
        return valorUnitario05;
    }

    public void setValorUnitario05(String valorUnitario05) {
        this.valorUnitario05 = valorUnitario05;
    }

    public String getValorUnitario06() {
        return valorUnitario06;
    }

    public void setValorUnitario06(String valorUnitario06) {
        this.valorUnitario06 = valorUnitario06;
    }

    public String getValorUnitario07() {
        return valorUnitario07;
    }

    public void setValorUnitario07(String valorUnitario07) {
        this.valorUnitario07 = valorUnitario07;
    }

    public String getValorUnitario08() {
        return valorUnitario08;
    }

    public void setValorUnitario08(String valorUnitario08) {
        this.valorUnitario08 = valorUnitario08;
    }

    public String getValorTotal01() {
        return valorTotal01;
    }

    public void setValorTotal01(String valorTotal01) {
        this.valorTotal01 = valorTotal01;
    }

    public String getValorTotal02() {
        return valorTotal02;
    }

    public void setValorTotal02(String valorTotal02) {
        this.valorTotal02 = valorTotal02;
    }

    public String getValorTotal03() {
        return valorTotal03;
    }

    public void setValorTotal03(String valorTotal03) {
        this.valorTotal03 = valorTotal03;
    }

    public String getValorTotal04() {
        return valorTotal04;
    }

    public void setValorTotal04(String valorTotal04) {
        this.valorTotal04 = valorTotal04;
    }

    public String getValorTotal05() {
        return valorTotal05;
    }

    public void setValorTotal05(String valorTotal05) {
        this.valorTotal05 = valorTotal05;
    }

    public String getValorTotal06() {
        return valorTotal06;
    }

    public void setValorTotal06(String valorTotal06) {
        this.valorTotal06 = valorTotal06;
    }

    public String getValorTotal07() {
        return valorTotal07;
    }

    public void setValorTotal07(String valorTotal07) {
        this.valorTotal07 = valorTotal07;
    }

    public String getValorTotal08() {
        return valorTotal08;
    }

    public void setValorTotal08(String valorTotal08) {
        this.valorTotal08 = valorTotal08;
    }

    public String getValorSemAcrescimo() {
        return valorSemAcrescimo;
    }

    public void setValorSemAcrescimo(String valorSemAcrescimo) {
        this.valorSemAcrescimo = valorSemAcrescimo;
    }

    public String getValorAcrescimo() {
        return valorAcrescimo;
    }

    public void setValorAcrescimo(String valorAcrescimo) {
        this.valorAcrescimo = valorAcrescimo;
    }

    public String getValorComAcrescimo() {
        return valorComAcrescimo;
    }

    public void setValorComAcrescimo(String valorComAcrescimo) {
        this.valorComAcrescimo = valorComAcrescimo;
    }

    public String getBaseIcms() {
        return baseIcms;
    }

    public void setBaseIcms(String baseIcms) {
        this.baseIcms = baseIcms;
    }

    public String getAliquota() {
        return aliquota;
    }

    public void setAliquota(String aliquota) {
        this.aliquota = aliquota;
    }

    public String getValorIcms() {
        return valorIcms;
    }

    public void setValorIcms(String valorIcms) {
        this.valorIcms = valorIcms;
    }

    public String getCodigoBanco() {
        return codigoBanco;
    }

    public void setCodigoBanco(String codigoBanco) {
        this.codigoBanco = codigoBanco;
    }

    public String getCodigoAgencia() {
        return codigoAgencia;
    }

    public void setCodigoAgencia(String codigoAgencia) {
        this.codigoAgencia = codigoAgencia;
    }

    public String getCodigoConta() {
        return codigoConta;
    }

    public void setCodigoConta(String codigoConta) {
        this.codigoConta = codigoConta;
    }

    public Boolean getQuitado() {
        return quitado;
    }

    public void setQuitado(Boolean quitado) {
        this.quitado = quitado;
    }

    public String getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(String dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ContaBackup other = (ContaBackup) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

}
