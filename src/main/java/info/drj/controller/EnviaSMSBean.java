/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.controller;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author dirceurjunior
 */
@Named
@ViewScoped
public class EnviaSMSBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String numero;

    private String mensagem;

    public void enviarSMS() {
        // Use your account SID and authentication token instead
        // of the placeholders shown here.
        String ACCOUNT_SID = "AC211845705d3e2eb28ac2b091b278943b";
        String AUTH_TOKEN = "b74cbf8cfb85dd4adeb54b8748d67f3d";
        String NUMBER = "+12482138435";

        // Initialize the Twilio client.
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(getNumero()),
                new com.twilio.type.PhoneNumber(NUMBER),
                getMensagem())
                .create();

        //System.out.println(message.getSid());
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
