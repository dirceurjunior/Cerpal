/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.controller;

import com.itextpdf.text.DocumentException;
import info.drj.model.Conta;
import info.drj.model.Cooperado;
import info.drj.repository.Contas;
import info.drj.repository.Cooperados;
import info.drj.repository.filter.CooperadoFilter;
import info.drj.security.UsuarioLogado;
import info.drj.security.UsuarioSistema;
import info.drj.service.CadastroCooperadoService;
import info.drj.util.jsf.FacesUtil;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Dirceu Junior
 */
@Named
@ViewScoped
public class CadastroCooperadoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private FacesContext facesContext;

    @Inject
    private HttpServletResponse response;

    @Inject
    private EntityManager manager;

    @Inject
    @UsuarioLogado
    private UsuarioSistema usuarioLogado;

    @Inject
    private Cooperados cooperados;

    @Inject
    private Contas contas;

    @Inject
    private CadastroCooperadoService cadastroCooperadoService;

    private Cooperado cooperado;

    private Cooperado existente;

    private CooperadoFilter filtro;

    private List<Cooperado> listaCooperados = new ArrayList<>();

    private List<Conta> listaContas = new ArrayList<>();

    private List<Cooperado> listaCooperadoArquivos = new ArrayList<>();

    private List<String> listaSucesso = new ArrayList<>();

    private List<String> listaAlerta = new ArrayList<>();

    private List<String> listaErro = new ArrayList<>();

    public CadastroCooperadoBean() {
        limpar();

    }

    private void limpar() {
        filtro = new CooperadoFilter();
        cooperado = new Cooperado();
        existente = new Cooperado();
    }

    public void salvar() {
        this.cooperado = cadastroCooperadoService.salvar(this.cooperado);
        limpar();
    }

    public void atualizarCooperados() {
        this.listaContas = contas.all();
        System.out.println("contas = " + listaContas.size());
        listaContas.forEach(conta -> {
            cooperado = cooperados.porMatricula(conta.getMatricula());
            if (cooperado.isNovo()) {
                cooperado = new Cooperado();
                cooperado.setNome(conta.getNome());
                cooperado.setMatricula(conta.getMatricula());
                cooperado.setEndereco(conta.getEndereco());
                cooperado.setBairro(conta.getBairro());
                cooperado.setCpf(conta.getCpf());
                cooperado.setCnpj(conta.getCnpj());
                listaSucesso.add("COOPERADO " + cooperado.getNome() + " MATRICULA " + conta.getMatricula() + " SALVO COM SUCESSO\r\n");
                salvar();
            }
        });
    }

    public void adicionarCooperados(FileUploadEvent event) throws IOException, InterruptedException, DocumentException {

//        try (PDDocument document = PDDocument.load(event.getFile().getContents())) {
//            if (!document.isEncrypted()) {
//                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
//                stripper.setSortByPosition(true);
//                PDFTextStripper tStripper = new PDFTextStripper();
//                String pdfFileInText = tStripper.getText(document);
//                String lines[] = pdfFileInText.split("\\r?\\n");
//
//                cooperado.setMatricula(lines[6].substring(45, 50).trim());
//
//                existente = cooperados.porNotaFiscal(cooperado.getNotaFiscal());
//
//                if (existente != null) {
//                    listaAlerta.add("COOPERADO " + existente.getNome() + " REFERENCIA " + existente.getDataReferencia() + " JÁ FOI LANÇADA\r\n");
//                    limpar();
//                } else {
//                    cooperado.setNome(lines[1].substring(0, lines[1].length() - 25).trim());
//                    cooperado.setBairro(lines[2].substring(0, lines[2].length() - 11).trim());
//                    cooperado.setEndereco(lines[3].substring(0, lines[3].length() - 26).trim());
//                    cooperado.setMatricula(lines[6].substring(45, 50).trim());
//
//                    this.cooperado.setCodigoMunicipio(line.substring(138, 143).trim());
//                    //System.out.println(line.substring(143, 183).trim());
//                    this.cooperado.setDescricaoMunicipio(line.substring(143, 183).trim());
//                    int controle = 0;
//                    for (String line : lines) {
//                        controle++;
//                        if (line.equals("DADOS DA UNIDADE CONSUMIDORA DATAS DAS LEITURAS")) {
//                            int dados = controle;
//                            String linha = lines[dados++];
//                            linha = lines[dados++];
//                            if (linha.substring(linha.length() - 31, linha.length() - 16).trim().replaceAll(" ", "").length() == 14) {
//                                cooperado.setCpf(linha.substring(linha.length() - 31, linha.length() - 16).trim().replaceAll(" ", ""));
//                            }
//                            linha = lines[dados + 2];
//                            if (linha.substring(5, 23).trim().replaceAll(" ", "").length() == 18) {
//                                cooperado.setCnpj(linha.substring(5, 24).trim().replaceAll(" ", ""));
//                            }
//                        }
//                        if (line.equals("ATENDIMENTO CERPAL SEU CÓDIGO CONTA MÊS VENCIMENTO VALOR A PAGAR (R$)")) {
//                            String linha = lines[controle++];
//                            cooperado.setCadastro(linha.substring(16, 22).trim());
//                            cooperado.setDataReferencia(linha.substring(22, 29).trim());
//                            cooperado.setDataVencimento(linha.substring(30, 41).trim());
//                            cooperado.setValorTotal(linha.substring(41, linha.length()).trim());
//                            break;
//                        }
//                    }
//                    //byte[] bimagem = arquivo;
//                    cooperado.setArquivo(arquivo);
//                    cooperado.setDataHoraLancamento(LocalDateTime.now());
//                    listaSucesso.add("COOPERADO " + cooperado.getNome() + " REFERENCIA " + cooperado.getDataReferencia() + " LANÇADA COM SUCESSO\r\n");
//                    salvar();
//                }
//            } else {
//                FacesUtil.addInfoMessage("DOCUMENTO " + event.getFile().getFileName() + " PROTEGIDO");
//            }
//        } catch (Exception e) {
//            listaErro.add("ERRO " + e.toString() + " - ARQUIVO " + event.getFile().getFileName());
//        }
    }

    public void terminou() {
        if (!listaErro.isEmpty()) {
            atualizaMensagemErro();
        }
        if (!listaAlerta.isEmpty()) {
            atualizaMensagemAlerta();
        }
        if (!listaSucesso.isEmpty()) {
            atualizaMensagemSucesso();
        }
    }

    public void atualizaMensagemSucesso() {
        //FacesUtil.addInfoMessage(" ---- BAIXAS REALIZADAS COM SUCESSO ----");
        for (Iterator iterator = listaSucesso.iterator(); iterator.hasNext();) {
            String mensagem = (String) iterator.next();
            FacesUtil.addInfoMessage(mensagem);
        }
        listaSucesso.clear();
    }

    public void atualizaMensagemAlerta() {
        for (Iterator iterator = listaAlerta.iterator(); iterator.hasNext();) {
            String mensagem = (String) iterator.next();
            FacesUtil.addWarnMessage(mensagem);
        }
        listaAlerta.clear();
    }

    public void atualizaMensagemErro() {
        for (Iterator iterator = listaErro.iterator(); iterator.hasNext();) {
            String mensagem = (String) iterator.next();
            FacesUtil.addErrorMessage(mensagem);
        }
        listaErro.clear();
    }

    public Cooperado getCooperado() {
        return cooperado;
    }

    public void setCooperado(Cooperado cooperado) {
        this.cooperado = cooperado;
    }

    public List<Cooperado> getListaCooperados() {
        return listaCooperados;
    }

    public void setListaCooperados(List<Cooperado> listaCooperados) {
        this.listaCooperados = listaCooperados;
    }

    public CooperadoFilter getFiltro() {
        return filtro;
    }

    public void setFiltro(CooperadoFilter filtro) {
        this.filtro = filtro;
    }

    public Cooperado getExistente() {
        return existente;
    }

    public void setExistente(Cooperado existente) {
        this.existente = existente;
    }

    public List<String> getListaSucesso() {
        return listaSucesso;
    }

    public void setListaSucesso(List<String> listaSucesso) {
        this.listaSucesso = listaSucesso;
    }

    public List<String> getListaAlerta() {
        return listaAlerta;
    }

    public void setListaAlerta(List<String> listaAlerta) {
        this.listaAlerta = listaAlerta;
    }

    public List<String> getListaErro() {
        return listaErro;
    }

    public void setListaErro(List<String> listaErro) {
        this.listaErro = listaErro;
    }

    public List<Conta> getListaContas() {
        return listaContas;
    }

    public void setListaContas(List<Conta> listaContas) {
        this.listaContas = listaContas;
    }

    public List<Cooperado> getListaCooperadoArquivos() {
        return listaCooperadoArquivos;
    }

    public void setListaCooperadoArquivos(List<Cooperado> listaCooperadoArquivos) {
        this.listaCooperadoArquivos = listaCooperadoArquivos;
    }

}

//    public void carregarArquivoCooperado(FileUploadEvent event) throws FileNotFoundException, IOException {
//        UploadedFile selectedFile = event.getFile();
//        lerArquivoCooperado(selectedFile);
//    }
//    public void lerArquivoCooperado(UploadedFile file) throws FileNotFoundException, IOException {
//        Scanner in = new Scanner(file.getInputstream());
//        if (!cooperados.porDataReferencia(in.nextLine().substring(30, 37).trim()).isEmpty()) {
//            throw new NegocioException("CONTAS DE " + in.nextLine().substring(30, 37).trim() + " JÁ FORAM ADICIONADAS ANTERIORMENTE");
//        }
//        while (in.hasNextLine()) {
//            String line = in.nextLine();
//            line = String.format("%-1124s", line);
//            ////System.out.println();
//            this.cooperado.setDataEmissao(line.substring(0, 10).trim());
//            ////System.out.println(line.substring(10, 20).trim());
//            this.cooperado.setDataVencimento(line.substring(10, 20).trim());
//            ////System.out.println(line.substring(20, 30).trim());
//            this.cooperado.setDataApresentacao(line.substring(20, 30).trim());
//            ////System.out.println(line.substring(30, 37).trim());
//            this.cooperado.setDataReferencia(line.substring(30, 37).trim());
//            ////System.out.println(line.substring(37, 43).trim());
//            this.cooperado.setNotaFiscal(line.substring(37, 43).trim());
//            //System.out.println(line.substring(43, 93).trim());
//            this.cooperado.setNome(line.substring(43, 93).trim());
//            //System.out.println(line.substring(93, 98).trim());
//            this.cooperado.setCodigoBairro(line.substring(93, 98).trim());
//            //System.out.println(line.substring(98, 138).trim());
//            this.cooperado.setDescricaoBairro(line.substring(98, 138).trim());
//            //System.out.println(line.substring(138, 143).trim());
//            this.cooperado.setCodigoMunicipio(line.substring(138, 143).trim());
//            //System.out.println(line.substring(143, 183).trim());
//            this.cooperado.setDescricaoMunicipio(line.substring(143, 183).trim());
//            //System.out.println(line.substring(183, 189).trim());
//            this.cooperado.setMatricula(line.substring(183, 189).trim());
//            //System.out.println(line.substring(189, 195).trim());
//            this.cooperado.setCadastro(line.substring(189, 195).trim());
//            //System.out.println(line.substring(195, 213).trim());
//            this.cooperado.setCnpj(line.substring(195, 213).trim());
//            //System.out.println(line.substring(213, 231).trim());
//            this.cooperado.setInscricaoEstadual(line.substring(213, 231).trim());
//
//            //System.out.println(line.substring(231, 236).trim());
//            this.cooperado.setPlano(line.substring(231, 236).trim());
//
//            //System.out.println(line.substring(236, 243).trim());
//            this.cooperado.setCarga(line.substring(236, 243).trim());
//
//            //System.out.println(line.substring(243, 261).trim());
//            this.cooperado.setProdutor(line.substring(243, 261).trim());
//
//            //System.out.println(line.substring(261, 279).trim());
//            this.cooperado.setCpf(line.substring(261, 279).trim());
//
//            //System.out.println(line.substring(279, 282).trim());
//            this.cooperado.setPerdas(line.substring(279, 282).trim());
//
//            //System.out.println(line.substring(282, 292).trim());
//            this.cooperado.setFatorPotencia(line.substring(282, 292).trim());
//
//            //System.out.println(line.substring(292, 302).trim());
//            this.cooperado.setMedidor(line.substring(292, 302).trim());
//
//            //System.out.println(line.substring(302, 310).trim());
//            this.cooperado.setConstante(line.substring(302, 310).trim());
//
//            //System.out.println(line.substring(310, 316).trim());
//            this.cooperado.setLeituraAnterior(line.substring(310, 316).trim());
//
//            //System.out.println(line.substring(316, 322).trim());
//            this.cooperado.setLeituraAtual(line.substring(316, 322).trim());
//
//            //System.out.println(line.substring(322, 332).trim());
//            this.cooperado.setDemanda(line.substring(322, 332).trim());
//
//            //System.out.println(line.substring(332, 337).trim());
//            this.cooperado.setLivro(line.substring(332, 337).trim());
//
//            //System.out.println(line.substring(337, 347).trim());
//            this.cooperado.setDataLeitura(line.substring(337, 347).trim());
//
//            //System.out.println(line.substring(347, 354).trim());
//            this.cooperado.setCompetencia01(line.substring(347, 354).trim());
//
//            //System.out.println(line.substring(354, 360).trim());
//            this.cooperado.setConsumo01(line.substring(354, 360).trim());
//
//            //System.out.println(line.substring(360, 367).trim());
//            this.cooperado.setCompetencia02(line.substring(360, 367).trim());
//
//            //System.out.println(line.substring(367, 373).trim());
//            this.cooperado.setConsumo02(line.substring(367, 373).trim());
//
//            //System.out.println(line.substring(373, 380).trim());
//            this.cooperado.setCompetencia03(line.substring(373, 380).trim());
//
//            //System.out.println(line.substring(380, 386).trim());
//            this.cooperado.setConsumo03(line.substring(380, 386).trim());
//
//            //System.out.println(line.substring(386, 393).trim());
//            this.cooperado.setCompetencia04(line.substring(386, 393).trim());
//
//            //System.out.println(line.substring(393, 399).trim());
//            this.cooperado.setConsumo04(line.substring(393, 399).trim());
//
//            //System.out.println(line.substring(399, 406).trim());
//            this.cooperado.setCompetencia05(line.substring(399, 406).trim());
//
//            //System.out.println(line.substring(406, 412).trim());
//            this.cooperado.setConsumo05(line.substring(406, 412).trim());
//
//            //System.out.println(line.substring(412, 419).trim());
//            this.cooperado.setCompetencia06(line.substring(412, 419).trim());
//
//            //System.out.println(line.substring(419, 425).trim());
//            this.cooperado.setConsumo06(line.substring(419, 425).trim());
//
//            //System.out.println(line.substring(425, 432).trim());
//            this.cooperado.setCompetencia07(line.substring(425, 432).trim());
//
//            //System.out.println(line.substring(432, 438).trim());
//            this.cooperado.setConsumo07(line.substring(432, 438).trim());
//
//            //System.out.println(line.substring(438, 445).trim());
//            this.cooperado.setCompetencia08(line.substring(438, 445).trim());
//
//            //System.out.println(line.substring(445, 451).trim());
//            this.cooperado.setConsumo08(line.substring(445, 451).trim());
//
//            //System.out.println(line.substring(451, 458).trim());
//            this.cooperado.setCompetencia09(line.substring(451, 458).trim());
//
//            //System.out.println(line.substring(458, 464).trim());
//            this.cooperado.setConsumo09(line.substring(458, 464).trim());
//
//            //System.out.println(line.substring(464, 471).trim());
//            this.cooperado.setCompetencia10(line.substring(464, 471).trim());
//
//            //System.out.println(line.substring(471, 477).trim());
//            this.cooperado.setConsumo10(line.substring(471, 477).trim());
//
//            //System.out.println(line.substring(477, 484).trim());
//            this.cooperado.setCompetencia11(line.substring(477, 484).trim());
//
//            //System.out.println(line.substring(484, 490).trim());
//            this.cooperado.setConsumo11(line.substring(484, 490).trim());
//
//            //System.out.println(line.substring(490, 497).trim());
//            this.cooperado.setCompetencia12(line.substring(490, 497).trim());
//
//            //System.out.println(line.substring(497, 503).trim());
//            this.cooperado.setConsumo12(line.substring(497, 503).trim());
//
//            //System.out.println(line.substring(503, 543).trim());
//            this.cooperado.setDescricao01(line.substring(503, 543).trim());
//
//            //System.out.println(line.substring(543, 583).trim());
//            this.cooperado.setDescricao02(line.substring(543, 583).trim());
//
//            //System.out.println(line.substring(583, 623).trim());
//            this.cooperado.setDescricao03(line.substring(583, 623).trim());
//
//            //System.out.println(line.substring(623, 663).trim());
//            this.cooperado.setDescricao04(line.substring(623, 663).trim());
//
//            //System.out.println(line.substring(663, 703).trim());
//            this.cooperado.setDescricao05(line.substring(663, 703).trim());
//
//            //System.out.println(line.substring(703, 743).trim());
//            this.cooperado.setDescricao06(line.substring(703, 743).trim());
//
//            //System.out.println(line.substring(743, 783).trim());
//            this.cooperado.setDescricao07(line.substring(743, 783).trim());
//
//            //System.out.println(line.substring(783, 823).trim());
//            this.cooperado.setDescricao08(line.substring(783, 823).trim());
//
//            //System.out.println(line.substring(823, 829).trim());
//            this.cooperado.setFator01(line.substring(823, 829).trim());
//
//            //System.out.println(line.substring(829, 835).trim());
//            this.cooperado.setFator02(line.substring(829, 835).trim());
//
//            //System.out.println(line.substring(835, 844).trim());
//            this.cooperado.setFator03(line.substring(835, 844).trim());
//
//            //System.out.println(line.substring(844, 850).trim());
//            this.cooperado.setFator04(line.substring(844, 850).trim());
//
//            //System.out.println(line.substring(850, 856).trim());
//            this.cooperado.setFator05(line.substring(850, 856).trim());
//
//            //System.out.println(line.substring(856, 862).trim());
//            this.cooperado.setFator06(line.substring(856, 862).trim());
//
//            //System.out.println(line.substring(862, 868).trim());
//            this.cooperado.setFator07(line.substring(862, 868).trim());
//
//            //System.out.println(line.substring(868, 874).trim());
//            this.cooperado.setFator08(line.substring(868, 874).trim());
//
//            //System.out.println(line.substring(874, 884).trim());
//            this.cooperado.setValorUnitario01(line.substring(874, 884).trim());
//
//            //System.out.println(line.substring(884, 894).trim());
//            this.cooperado.setValorUnitario02(line.substring(884, 894).trim());
//
//            //System.out.println(line.substring(894, 904).trim());
//            this.cooperado.setValorUnitario03(line.substring(894, 904).trim());
//
//            //System.out.println(line.substring(904, 914).trim());
//            this.cooperado.setValorUnitario04(line.substring(904, 914).trim());
//
//            //System.out.println(line.substring(914, 924).trim());
//            this.cooperado.setValorUnitario05(line.substring(914, 924).trim());
//
//            //System.out.println(line.substring(924, 934).trim());
//            this.cooperado.setValorUnitario06(line.substring(924, 934).trim());
//
//            //System.out.println(line.substring(934, 944).trim());
//            this.cooperado.setValorUnitario07(line.substring(934, 944).trim());
//
//            //System.out.println(line.substring(944, 954).trim());
//            this.cooperado.setValorUnitario08(line.substring(944, 954).trim());
//
//            //System.out.println(line.substring(954, 964).trim());
//            this.cooperado.setValorTotal01(line.substring(954, 964).trim());
//
//            //System.out.println(line.substring(964, 974).trim());
//            this.cooperado.setValorTotal02(line.substring(964, 974).trim());
//
//            //System.out.println(line.substring(974, 984).trim());
//            this.cooperado.setValorTotal03(line.substring(974, 984).trim());
//
//            //System.out.println(line.substring(984, 994).trim());
//            this.cooperado.setValorTotal04(line.substring(984, 994).trim());
//
//            //System.out.println(line.substring(994, 1004).trim());
//            this.cooperado.setValorTotal05(line.substring(994, 1004).trim());
//
//            //System.out.println(line.substring(1004, 1014).trim());
//            this.cooperado.setValorTotal06(line.substring(1004, 1014).trim());
//
//            //System.out.println(line.substring(1014, 1024).trim());
//            this.cooperado.setValorTotal07(line.substring(1014, 1024).trim());
//
//            //System.out.println(line.substring(1024, 1034).trim());
//            this.cooperado.setValorTotal08(line.substring(1024, 1034).trim());
//
//            //System.out.println(line.substring(1034, 1044).trim());
//            this.cooperado.setValorSemAcrescimo(line.substring(1034, 1044).trim());
//
//            //System.out.println(line.substring(1044, 1054).trim());
//            this.cooperado.setValorAcrescimo(line.substring(1044, 1054).trim());
//
//            //System.out.println(line.substring(1054, 1064).trim());
//            this.cooperado.setValorComAcrescimo(line.substring(1054, 1064).trim());
//
//            //System.out.println(line.substring(1064, 1074).trim());
//            this.cooperado.setBaseIcms(line.substring(1064, 1074).trim());
//
//            //System.out.println(line.substring(1074, 1084).trim());
//            this.cooperado.setAliquota(line.substring(1074, 1084).trim());
//
//            //System.out.println(line.substring(1084, 1094).trim());
//            this.cooperado.setValorIcms(line.substring(1084, 1094).trim());
//
//            //System.out.println(line.substring(1094, 1099).trim());
//            this.cooperado.setCodigoBanco(line.substring(1094, 1099).trim());
//
//            //System.out.println(line.substring(1099, 1104).trim());
//            this.cooperado.setCodigoAgencia(line.substring(1099, 1104).trim());
//
//            //System.out.println(line.substring(1104, 1124).trim());
//            this.cooperado.setCodigoCooperado(line.substring(1104, 1124).trim());
//
//            salvar();
//
//        }
//        FacesUtil.addInfoMessage("CONTAS ADICIONADAS COM SUCESSO!");
//    }
//            else if (line.length() == 121) {
//                String notaFiscal = line.substring(57, 63).trim();
//                SimpleDateFormat formato1 = new SimpleDateFormat("yyyyMMdd");
//                SimpleDateFormat formato2 = new SimpleDateFormat("dd/MM/yyyy");
//                try {
//                    dataPagamento = formato2.format(formato1.parse(line.substring(113, 121).trim()));
//                } catch (ParseException ex) {
//                    Logger.getLogger(CadastroCooperadoBean.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                try {
////                    cooperado = cooperados.porNotaFiscal(notaFiscal);
//                    if (cooperado.isExistente() && !cooperado.isSituacao()) {
//                        cooperado.setQuitado(Boolean.TRUE);
//                        cooperado.setDataPagamento(dataPagamento);
//                        salvar();
//                    }
//                } catch (Exception e) {
//                }
//            }
//    public void imprimirFatura(Long id) {
//        Cooperado cooperado = cooperados.porId(id);
//        Map<String, Object> parametros = new HashMap<>();
//        InputStream inputStreamDaImagem = null;
//        String caminhoImagem = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/resources/serenity-layout/images/LogoCerpal.png";
//        File file = new File(caminhoImagem);
//        try {
//            inputStreamDaImagem = new FileInputStream(file);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(CadastroCooperadoBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        parametros.put("idFatura", cooperado.getId());
//        parametros.put("logo", inputStreamDaImagem);
//        String relatorio = null;
//        if (cooperado.isSituacao()) {
//            relatorio = "/relatorios/demonstrativo_fatura_paga.jasper";
//        } else {
//            relatorio = "/relatorios/demonstrativo_fatura_pendente.jasper";
//        }
//        ExecutorRelatorio executor = new ExecutorRelatorio(relatorio, this.response, parametros, "FATURA_" + cooperado.getNotaFiscal() + "_" + cooperado.getNome() + "_.pdf");
//        Session session = manager.unwrap(Session.class);
//        session.doWork(executor);
//        if (executor.isRelatorioGerado()) {
//            facesContext.responseComplete();
//        } else {
//            FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
//        }
//    }
