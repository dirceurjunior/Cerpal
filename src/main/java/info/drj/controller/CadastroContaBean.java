/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.controller;

import java.awt.geom.AffineTransform;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import info.drj.model.Conta;
import info.drj.repository.Contas;
import info.drj.repository.filter.ContaFilter;
import info.drj.security.UsuarioLogado;
import info.drj.security.UsuarioSistema;
import info.drj.service.CadastroContaService;
import info.drj.service.NegocioException;
import info.drj.util.jsf.FacesUtil;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
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
public class CadastroContaBean implements Serializable {

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
    private Contas contas;

    @Inject
    private CadastroContaService cadastroContaService;

    private Conta conta;

    private Conta existente;

    private ContaFilter filtro;

    private List<Conta> listaContas = new ArrayList<>();

    private List<Conta> listaContaArquivos = new ArrayList<>();

    private List<String> listaSucesso = new ArrayList<>();

    private List<String> listaAlerta = new ArrayList<>();

    private List<String> listaErro = new ArrayList<>();

    private String documento;

    private String clienteSelecionado;

    public CadastroContaBean() {
        limpar();
    }

    private void limpar() {
        filtro = new ContaFilter();
        conta = new Conta();
        existente = new Conta();
    }

    public void salvar() {
        this.conta = cadastroContaService.salvar(this.conta);
        limpar();
    }

    public void pesquisar() {
        if (filtro.getDocumento().isEmpty() && filtro.getNc().isEmpty()) {
            throw new NegocioException("FAVOR PREENCHER PELO MENOS UMA INFORMAÇÃO");
        }
        listaContas = contas.filtrados(filtro);
        if (listaContas.isEmpty()) {
            throw new NegocioException("NENHUM REGISTRO ENCONTRADO");
        } else {
            setClienteSelecionado(listaContas.get(0).getNome());
        }
    }

    public void adicionarContas(FileUploadEvent event) throws IOException, InterruptedException, DocumentException {
        //Cria um arquivo UploadFile, para receber o arquivo do evento
        //UploadedFile file = event.getFile();
        byte[] arquivo = compactarArquivo(event);
        try (PDDocument document = PDDocument.load(arquivo)) {
            if (!document.isEncrypted()) {
                PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                stripper.setSortByPosition(true);
                PDFTextStripper tStripper = new PDFTextStripper();
                String pdfFileInText = tStripper.getText(document);
                String lines[] = pdfFileInText.split("\\r?\\n");
                System.out.println("linha");
                System.out.println(lines[4].substring(12, lines[4].length() - 17).trim());
                conta.setNotaFiscal(lines[2].substring(lines[2].length() - 7, lines[2].length()).trim());
                existente = contas.porNotaFiscal(conta.getNotaFiscal());
                if (existente != null) {
                    listaAlerta.add("COOPERADO " + existente.getNome() + " REFERENCIA " + existente.getDataReferencia() + " JÁ FOI LANÇADA\r\n");
                    limpar();
                } else {
                    conta.setNome(lines[1].substring(0, lines[1].length() - 25).trim());
                    conta.setBairro(lines[2].substring(0, lines[2].length() - 11).trim());
                    conta.setEndereco(lines[3].substring(0, lines[3].length() - 26).trim());
                    conta.setCodigoMunicipio(lines[4].substring(7, 11).trim());
                    conta.setMunicipio(lines[4].substring(12, lines[4].length() - 17).trim());
                    conta.setMatricula(lines[6].substring(45, 50).trim());
                    int controle = 0;
                    for (String line : lines) {
                        controle++;
                        if (line.equals("DADOS DA UNIDADE CONSUMIDORA DATAS DAS LEITURAS")) {
                            int dados = controle;
                            String linha = lines[dados++];
                            linha = lines[dados++];
                            if (linha.substring(linha.length() - 31, linha.length() - 16).trim().replaceAll(" ", "").length() == 14) {
                                conta.setCpf(linha.substring(linha.length() - 31, linha.length() - 16).trim().replaceAll(" ", ""));
                            }
                            linha = lines[dados + 2];
                            if (linha.substring(5, 23).trim().replaceAll(" ", "").length() == 18) {
                                conta.setCnpj(linha.substring(5, 24).trim().replaceAll(" ", ""));
                            }
                        }
                        if (line.equals("ATENDIMENTO CERPAL SEU CÓDIGO CONTA MÊS VENCIMENTO VALOR A PAGAR (R$)")) {
                            String linha = lines[controle++];
                            conta.setCadastro(linha.substring(16, 22).trim());
                            conta.setDataReferencia(linha.substring(22, 29).trim());
                            conta.setDataVencimento(linha.substring(30, 41).trim());
                            conta.setValorTotal(linha.substring(41, linha.length()).trim());
                            break;
                        }
                    }
                    conta.setArquivo(arquivo);
                    conta.setDataHoraLancamento(LocalDateTime.now());
                    listaSucesso.add("COOPERADO " + conta.getNome() + " REFERENCIA " + conta.getDataReferencia() + " LANÇADA COM SUCESSO\r\n");
                    salvar();
                }
            } else {
                FacesUtil.addInfoMessage("DOCUMENTO " + event.getFile().getFileName() + " PROTEGIDO");
            }
        } catch (Exception e) {
            listaErro.add("ERRO " + e.toString() + " - ARQUIVO " + event.getFile().getFileName());
        }
    }

    public StreamedContent baixarConta(Conta conta) throws IOException, DocumentException {
        if (conta.isQuitado()) {
            return new DefaultStreamedContent(new ByteArrayInputStream(adicionarMarca(conta)), "application/octet-stream", conta.getDataReferencia().replaceAll("/", "") + "_" + conta.getCadastro() + "_" + conta.getNome() + ".pdf");
        } else {
            return new DefaultStreamedContent(new ByteArrayInputStream(conta.getArquivo()), "application/octet-stream", conta.getDataReferencia().replaceAll("/", "") + "_" + conta.getCadastro() + "_" + conta.getNome() + ".pdf");
        }
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

    public static float FACTOR = 1.0f;

    public byte[] compactarArquivo(FileUploadEvent event) throws IOException, InterruptedException, DocumentException {
        PdfName key = new PdfName("ITXT_SpecialId");
        PdfName value = new PdfName("123456789");
        // Read the file
        PdfReader reader = new PdfReader(event.getFile().getContents());
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PdfStamper stamper = new PdfStamper(reader, output);
        int n = reader.getXrefSize();
        PdfObject object = null;
        PRStream stream = null;
        // Look for image and manipulate image stream
        for (int i = 0; i < n; i++) {
            object = reader.getPdfObject(i);
            if (object == null || !object.isStream()) {
                continue;
            }
            stream = (PRStream) object;
            PdfObject pdfsubtype = stream.get(PdfName.SUBTYPE);
            if (pdfsubtype != null && pdfsubtype.toString().equals(PdfName.IMAGE.toString())) {
                PdfImageObject image = new PdfImageObject(stream);
                BufferedImage bi = image.getBufferedImage();
                if (bi == null) {
                    continue;
                }
                int width = (int) (bi.getWidth() * FACTOR);
                int height = (int) (bi.getHeight() * FACTOR);
                BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                AffineTransform at = AffineTransform.getScaleInstance(FACTOR, FACTOR);
                Graphics2D g = img.createGraphics();
                g.drawRenderedImage(bi, at);
                ByteArrayOutputStream imgBytes = new ByteArrayOutputStream();
                ImageIO.write(img, "JPG", imgBytes);
                stream.clear();
                stream.setData(imgBytes.toByteArray(), false, PRStream.BEST_COMPRESSION);
                stream.put(PdfName.TYPE, PdfName.XOBJECT);
                stream.put(PdfName.SUBTYPE, PdfName.IMAGE);
                stream.put(key, value);
                stream.put(PdfName.FILTER, PdfName.DCTDECODE);
                stream.put(PdfName.WIDTH, new PdfNumber(width));
                stream.put(PdfName.HEIGHT, new PdfNumber(height));
                stream.put(PdfName.BITSPERCOMPONENT, new PdfNumber(8));
                stream.put(PdfName.COLORSPACE, PdfName.DEVICERGB);
            }
        }
        stamper.close();
        reader.close();
        return output.toByteArray();
    }

    public void carregarArquivoBaixa(FileUploadEvent event) throws FileNotFoundException, IOException {
        UploadedFile selectedFile = event.getFile();
        lerArquivoBaixa(selectedFile);
    }

    public void lerArquivoBaixa(UploadedFile file) throws IOException {
        Scanner in = new Scanner(file.getInputstream());
        String dataPagamento = null;
        String dataReferencia = null;
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.length() == 118) {
                //String matricula = line.substring(0, 6).trim();
                String cadastro = Integer.valueOf(line.substring(6, 12).trim()).toString();
                //String cnpj = line.substring(12, 30).trim();
                //String ie = line.substring(30, 48).trim();
                SimpleDateFormat formato1 = new SimpleDateFormat("yyyyMM");
                SimpleDateFormat formato2 = new SimpleDateFormat("MM/yyyy");
                try {
                    dataReferencia = formato2.format(formato1.parse(line.substring(48, 54).trim()));//line.substring(48, 54).trim();
                } catch (ParseException ex) {
                    Logger.getLogger(CadastroContaBean.class.getName()).log(Level.SEVERE, null, ex);
                }
                //String notaFiscal = line.substring(54, 60).trim();
                String nome = line.substring(60, 110).trim();
                formato1 = new SimpleDateFormat("yyyyMMdd");
                formato2 = new SimpleDateFormat("dd/MM/yyyy");
                try {
                    dataPagamento = formato2.format(formato1.parse(line.substring(110, 118).trim()));
                } catch (ParseException ex) {
                    Logger.getLogger(CadastroContaBean.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    conta = contas.porCadastroDataReferencia(cadastro, dataReferencia);
                    if (!conta.isQuitado()) {
                        conta.setQuitado(Boolean.TRUE);
                        conta.setDataPagamento(dataPagamento);
                        listaSucesso.add("COOPERADO " + conta.getNome() + " REFERENCIA " + conta.getDataReferencia() + " EFETUADA BAIXA COM SUCESSO");
                        salvar();
                    } else {
                        listaAlerta.add("COOPERADO " + conta.getNome() + " REFERENCIA " + conta.getDataReferencia() + " JÁ SE ENCONTRA BAIXADA");
                    }
                } catch (Exception e) {
                    listaErro.add("COOPERADO " + nome + " REFERENCIA " + dataReferencia + " NÃO SE ENCONTRA NA BASE DE DADOS");
                }
            }
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

    public byte[] adicionarMarca(Conta conta) throws IOException, DocumentException {
        PdfReader reader = new PdfReader(conta.getArquivo());
        int n = reader.getNumberOfPages();
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PdfStamper stamper = new PdfStamper(reader, output);
        //PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("C:/CONVERTIDOS/" + event.getFile().getFileName()));
        // text watermark
        Font f = new Font(FontFamily.HELVETICA, 120);
        Phrase p = new Phrase("PAGO", f);
        // image watermark
        //Image img = Image.getInstance("C:/CONVERTIDOS/logo.png");
        //float w = img.getScaledWidth();
        //float h = img.getScaledHeight();
        // transparency
        PdfGState gs1 = new PdfGState();
        gs1.setFillOpacity(0.3f);
        // properties
        PdfContentByte over;
        Rectangle pagesize;
        float x, y;
        // loop over every page
        for (int i = 1; i <= n; i++) {
            pagesize = reader.getPageSizeWithRotation(i);
            x = (pagesize.getLeft() + pagesize.getRight()) / 2;
            y = (pagesize.getTop() + pagesize.getBottom()) / 2;
            over = stamper.getOverContent(i);
            over.saveState();
            over.setGState(gs1);
            //if (i % 2 == 1) {
            ColumnText.showTextAligned(over, Element.ALIGN_CENTER, p, x, y, 40);
            //} else {
            //over.addImage(img, w, 0, 0, h, x - (w / 2), y - (h / 2));
            //}
            over.restoreState();
        }
        stamper.close();
        reader.close();
        byte[] novo = output.toByteArray();
        return novo;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public List<Conta> getListaContas() {
        return listaContas;
    }

    public void setListaContas(List<Conta> listaContas) {
        this.listaContas = listaContas;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public ContaFilter getFiltro() {
        return filtro;
    }

    public void setFiltro(ContaFilter filtro) {
        this.filtro = filtro;
    }

    public String getClienteSelecionado() {
        return clienteSelecionado;
    }

    public void setClienteSelecionado(String clienteSelecionado) {
        this.clienteSelecionado = clienteSelecionado;
    }

    public Conta getExistente() {
        return existente;
    }

    public void setExistente(Conta existente) {
        this.existente = existente;
    }

    public List<String> getListaSucesso() {
        return listaSucesso;
    }

    public void setListaSucesso(List<String> listaSucesso) {
        this.listaSucesso = listaSucesso;
    }

    public List<String> getListaErro() {
        return listaErro;
    }

    public void setListaErro(List<String> listaErro) {
        this.listaErro = listaErro;
    }

    public List<String> getListaAlerta() {
        return listaAlerta;
    }

    public void setListaAlerta(List<String> listaAlerta) {
        this.listaAlerta = listaAlerta;
    }

}

//    public void carregarArquivoConta(FileUploadEvent event) throws FileNotFoundException, IOException {
//        UploadedFile selectedFile = event.getFile();
//        lerArquivoConta(selectedFile);
//    }
//    public void lerArquivoConta(UploadedFile file) throws FileNotFoundException, IOException {
//        Scanner in = new Scanner(file.getInputstream());
//        if (!contas.porDataReferencia(in.nextLine().substring(30, 37).trim()).isEmpty()) {
//            throw new NegocioException("CONTAS DE " + in.nextLine().substring(30, 37).trim() + " JÁ FORAM ADICIONADAS ANTERIORMENTE");
//        }
//        while (in.hasNextLine()) {
//            String line = in.nextLine();
//            line = String.format("%-1124s", line);
//            ////System.out.println();
//            this.conta.setDataEmissao(line.substring(0, 10).trim());
//            ////System.out.println(line.substring(10, 20).trim());
//            this.conta.setDataVencimento(line.substring(10, 20).trim());
//            ////System.out.println(line.substring(20, 30).trim());
//            this.conta.setDataApresentacao(line.substring(20, 30).trim());
//            ////System.out.println(line.substring(30, 37).trim());
//            this.conta.setDataReferencia(line.substring(30, 37).trim());
//            ////System.out.println(line.substring(37, 43).trim());
//            this.conta.setNotaFiscal(line.substring(37, 43).trim());
//            //System.out.println(line.substring(43, 93).trim());
//            this.conta.setNome(line.substring(43, 93).trim());
//            //System.out.println(line.substring(93, 98).trim());
//            this.conta.setCodigoBairro(line.substring(93, 98).trim());
//            //System.out.println(line.substring(98, 138).trim());
//            this.conta.setDescricaoBairro(line.substring(98, 138).trim());
//            //System.out.println(line.substring(138, 143).trim());
//            this.conta.setCodigoMunicipio(line.substring(138, 143).trim());
//            //System.out.println(line.substring(143, 183).trim());
//            this.conta.setDescricaoMunicipio(line.substring(143, 183).trim());
//            //System.out.println(line.substring(183, 189).trim());
//            this.conta.setMatricula(line.substring(183, 189).trim());
//            //System.out.println(line.substring(189, 195).trim());
//            this.conta.setCadastro(line.substring(189, 195).trim());
//            //System.out.println(line.substring(195, 213).trim());
//            this.conta.setCnpj(line.substring(195, 213).trim());
//            //System.out.println(line.substring(213, 231).trim());
//            this.conta.setInscricaoEstadual(line.substring(213, 231).trim());
//
//            //System.out.println(line.substring(231, 236).trim());
//            this.conta.setPlano(line.substring(231, 236).trim());
//
//            //System.out.println(line.substring(236, 243).trim());
//            this.conta.setCarga(line.substring(236, 243).trim());
//
//            //System.out.println(line.substring(243, 261).trim());
//            this.conta.setProdutor(line.substring(243, 261).trim());
//
//            //System.out.println(line.substring(261, 279).trim());
//            this.conta.setCpf(line.substring(261, 279).trim());
//
//            //System.out.println(line.substring(279, 282).trim());
//            this.conta.setPerdas(line.substring(279, 282).trim());
//
//            //System.out.println(line.substring(282, 292).trim());
//            this.conta.setFatorPotencia(line.substring(282, 292).trim());
//
//            //System.out.println(line.substring(292, 302).trim());
//            this.conta.setMedidor(line.substring(292, 302).trim());
//
//            //System.out.println(line.substring(302, 310).trim());
//            this.conta.setConstante(line.substring(302, 310).trim());
//
//            //System.out.println(line.substring(310, 316).trim());
//            this.conta.setLeituraAnterior(line.substring(310, 316).trim());
//
//            //System.out.println(line.substring(316, 322).trim());
//            this.conta.setLeituraAtual(line.substring(316, 322).trim());
//
//            //System.out.println(line.substring(322, 332).trim());
//            this.conta.setDemanda(line.substring(322, 332).trim());
//
//            //System.out.println(line.substring(332, 337).trim());
//            this.conta.setLivro(line.substring(332, 337).trim());
//
//            //System.out.println(line.substring(337, 347).trim());
//            this.conta.setDataLeitura(line.substring(337, 347).trim());
//
//            //System.out.println(line.substring(347, 354).trim());
//            this.conta.setCompetencia01(line.substring(347, 354).trim());
//
//            //System.out.println(line.substring(354, 360).trim());
//            this.conta.setConsumo01(line.substring(354, 360).trim());
//
//            //System.out.println(line.substring(360, 367).trim());
//            this.conta.setCompetencia02(line.substring(360, 367).trim());
//
//            //System.out.println(line.substring(367, 373).trim());
//            this.conta.setConsumo02(line.substring(367, 373).trim());
//
//            //System.out.println(line.substring(373, 380).trim());
//            this.conta.setCompetencia03(line.substring(373, 380).trim());
//
//            //System.out.println(line.substring(380, 386).trim());
//            this.conta.setConsumo03(line.substring(380, 386).trim());
//
//            //System.out.println(line.substring(386, 393).trim());
//            this.conta.setCompetencia04(line.substring(386, 393).trim());
//
//            //System.out.println(line.substring(393, 399).trim());
//            this.conta.setConsumo04(line.substring(393, 399).trim());
//
//            //System.out.println(line.substring(399, 406).trim());
//            this.conta.setCompetencia05(line.substring(399, 406).trim());
//
//            //System.out.println(line.substring(406, 412).trim());
//            this.conta.setConsumo05(line.substring(406, 412).trim());
//
//            //System.out.println(line.substring(412, 419).trim());
//            this.conta.setCompetencia06(line.substring(412, 419).trim());
//
//            //System.out.println(line.substring(419, 425).trim());
//            this.conta.setConsumo06(line.substring(419, 425).trim());
//
//            //System.out.println(line.substring(425, 432).trim());
//            this.conta.setCompetencia07(line.substring(425, 432).trim());
//
//            //System.out.println(line.substring(432, 438).trim());
//            this.conta.setConsumo07(line.substring(432, 438).trim());
//
//            //System.out.println(line.substring(438, 445).trim());
//            this.conta.setCompetencia08(line.substring(438, 445).trim());
//
//            //System.out.println(line.substring(445, 451).trim());
//            this.conta.setConsumo08(line.substring(445, 451).trim());
//
//            //System.out.println(line.substring(451, 458).trim());
//            this.conta.setCompetencia09(line.substring(451, 458).trim());
//
//            //System.out.println(line.substring(458, 464).trim());
//            this.conta.setConsumo09(line.substring(458, 464).trim());
//
//            //System.out.println(line.substring(464, 471).trim());
//            this.conta.setCompetencia10(line.substring(464, 471).trim());
//
//            //System.out.println(line.substring(471, 477).trim());
//            this.conta.setConsumo10(line.substring(471, 477).trim());
//
//            //System.out.println(line.substring(477, 484).trim());
//            this.conta.setCompetencia11(line.substring(477, 484).trim());
//
//            //System.out.println(line.substring(484, 490).trim());
//            this.conta.setConsumo11(line.substring(484, 490).trim());
//
//            //System.out.println(line.substring(490, 497).trim());
//            this.conta.setCompetencia12(line.substring(490, 497).trim());
//
//            //System.out.println(line.substring(497, 503).trim());
//            this.conta.setConsumo12(line.substring(497, 503).trim());
//
//            //System.out.println(line.substring(503, 543).trim());
//            this.conta.setDescricao01(line.substring(503, 543).trim());
//
//            //System.out.println(line.substring(543, 583).trim());
//            this.conta.setDescricao02(line.substring(543, 583).trim());
//
//            //System.out.println(line.substring(583, 623).trim());
//            this.conta.setDescricao03(line.substring(583, 623).trim());
//
//            //System.out.println(line.substring(623, 663).trim());
//            this.conta.setDescricao04(line.substring(623, 663).trim());
//
//            //System.out.println(line.substring(663, 703).trim());
//            this.conta.setDescricao05(line.substring(663, 703).trim());
//
//            //System.out.println(line.substring(703, 743).trim());
//            this.conta.setDescricao06(line.substring(703, 743).trim());
//
//            //System.out.println(line.substring(743, 783).trim());
//            this.conta.setDescricao07(line.substring(743, 783).trim());
//
//            //System.out.println(line.substring(783, 823).trim());
//            this.conta.setDescricao08(line.substring(783, 823).trim());
//
//            //System.out.println(line.substring(823, 829).trim());
//            this.conta.setFator01(line.substring(823, 829).trim());
//
//            //System.out.println(line.substring(829, 835).trim());
//            this.conta.setFator02(line.substring(829, 835).trim());
//
//            //System.out.println(line.substring(835, 844).trim());
//            this.conta.setFator03(line.substring(835, 844).trim());
//
//            //System.out.println(line.substring(844, 850).trim());
//            this.conta.setFator04(line.substring(844, 850).trim());
//
//            //System.out.println(line.substring(850, 856).trim());
//            this.conta.setFator05(line.substring(850, 856).trim());
//
//            //System.out.println(line.substring(856, 862).trim());
//            this.conta.setFator06(line.substring(856, 862).trim());
//
//            //System.out.println(line.substring(862, 868).trim());
//            this.conta.setFator07(line.substring(862, 868).trim());
//
//            //System.out.println(line.substring(868, 874).trim());
//            this.conta.setFator08(line.substring(868, 874).trim());
//
//            //System.out.println(line.substring(874, 884).trim());
//            this.conta.setValorUnitario01(line.substring(874, 884).trim());
//
//            //System.out.println(line.substring(884, 894).trim());
//            this.conta.setValorUnitario02(line.substring(884, 894).trim());
//
//            //System.out.println(line.substring(894, 904).trim());
//            this.conta.setValorUnitario03(line.substring(894, 904).trim());
//
//            //System.out.println(line.substring(904, 914).trim());
//            this.conta.setValorUnitario04(line.substring(904, 914).trim());
//
//            //System.out.println(line.substring(914, 924).trim());
//            this.conta.setValorUnitario05(line.substring(914, 924).trim());
//
//            //System.out.println(line.substring(924, 934).trim());
//            this.conta.setValorUnitario06(line.substring(924, 934).trim());
//
//            //System.out.println(line.substring(934, 944).trim());
//            this.conta.setValorUnitario07(line.substring(934, 944).trim());
//
//            //System.out.println(line.substring(944, 954).trim());
//            this.conta.setValorUnitario08(line.substring(944, 954).trim());
//
//            //System.out.println(line.substring(954, 964).trim());
//            this.conta.setValorTotal01(line.substring(954, 964).trim());
//
//            //System.out.println(line.substring(964, 974).trim());
//            this.conta.setValorTotal02(line.substring(964, 974).trim());
//
//            //System.out.println(line.substring(974, 984).trim());
//            this.conta.setValorTotal03(line.substring(974, 984).trim());
//
//            //System.out.println(line.substring(984, 994).trim());
//            this.conta.setValorTotal04(line.substring(984, 994).trim());
//
//            //System.out.println(line.substring(994, 1004).trim());
//            this.conta.setValorTotal05(line.substring(994, 1004).trim());
//
//            //System.out.println(line.substring(1004, 1014).trim());
//            this.conta.setValorTotal06(line.substring(1004, 1014).trim());
//
//            //System.out.println(line.substring(1014, 1024).trim());
//            this.conta.setValorTotal07(line.substring(1014, 1024).trim());
//
//            //System.out.println(line.substring(1024, 1034).trim());
//            this.conta.setValorTotal08(line.substring(1024, 1034).trim());
//
//            //System.out.println(line.substring(1034, 1044).trim());
//            this.conta.setValorSemAcrescimo(line.substring(1034, 1044).trim());
//
//            //System.out.println(line.substring(1044, 1054).trim());
//            this.conta.setValorAcrescimo(line.substring(1044, 1054).trim());
//
//            //System.out.println(line.substring(1054, 1064).trim());
//            this.conta.setValorComAcrescimo(line.substring(1054, 1064).trim());
//
//            //System.out.println(line.substring(1064, 1074).trim());
//            this.conta.setBaseIcms(line.substring(1064, 1074).trim());
//
//            //System.out.println(line.substring(1074, 1084).trim());
//            this.conta.setAliquota(line.substring(1074, 1084).trim());
//
//            //System.out.println(line.substring(1084, 1094).trim());
//            this.conta.setValorIcms(line.substring(1084, 1094).trim());
//
//            //System.out.println(line.substring(1094, 1099).trim());
//            this.conta.setCodigoBanco(line.substring(1094, 1099).trim());
//
//            //System.out.println(line.substring(1099, 1104).trim());
//            this.conta.setCodigoAgencia(line.substring(1099, 1104).trim());
//
//            //System.out.println(line.substring(1104, 1124).trim());
//            this.conta.setCodigoConta(line.substring(1104, 1124).trim());
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
//                    Logger.getLogger(CadastroContaBean.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                try {
////                    conta = contas.porNotaFiscal(notaFiscal);
//                    if (conta.isExistente() && !conta.isSituacao()) {
//                        conta.setQuitado(Boolean.TRUE);
//                        conta.setDataPagamento(dataPagamento);
//                        salvar();
//                    }
//                } catch (Exception e) {
//                }
//            }
//    public void imprimirFatura(Long id) {
//        Conta conta = contas.porId(id);
//        Map<String, Object> parametros = new HashMap<>();
//        InputStream inputStreamDaImagem = null;
//        String caminhoImagem = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/resources/serenity-layout/images/LogoCerpal.png";
//        File file = new File(caminhoImagem);
//        try {
//            inputStreamDaImagem = new FileInputStream(file);
//        } catch (FileNotFoundException ex) {
//            Logger.getLogger(CadastroContaBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        parametros.put("idFatura", conta.getId());
//        parametros.put("logo", inputStreamDaImagem);
//        String relatorio = null;
//        if (conta.isSituacao()) {
//            relatorio = "/relatorios/demonstrativo_fatura_paga.jasper";
//        } else {
//            relatorio = "/relatorios/demonstrativo_fatura_pendente.jasper";
//        }
//        ExecutorRelatorio executor = new ExecutorRelatorio(relatorio, this.response, parametros, "FATURA_" + conta.getNotaFiscal() + "_" + conta.getNome() + "_.pdf");
//        Session session = manager.unwrap(Session.class);
//        session.doWork(executor);
//        if (executor.isRelatorioGerado()) {
//            facesContext.responseComplete();
//        } else {
//            FacesUtil.addErrorMessage("A execução do relatório não retornou dados.");
//        }
//    }
