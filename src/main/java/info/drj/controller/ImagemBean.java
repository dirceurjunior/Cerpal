/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.controller;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Dirceu Junior
 */
@Named
@SessionScoped
public class ImagemBean implements Serializable {

    private static final long serialVersionUID = 1L;

    StreamedContent st;

    public void uploadImagem(byte[] imagem) {
        st = new DefaultStreamedContent(new ByteArrayInputStream(imagem));
    }

    public StreamedContent getSt() {
        return st;
    }

}
