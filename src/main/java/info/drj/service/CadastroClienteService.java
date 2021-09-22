package info.drj.service;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package info.kabanas.service;
//
//import info.kabanas.repository.Clientes;
//import info.kabanas.model.Cliente;
//import info.kabanas.security.UsuarioLogado;
//import info.kabanas.security.UsuarioSistema;
//import info.kabanas.util.jpa.Transactional;
//import java.io.Serializable;
//import java.util.Date;
//import javax.inject.Inject;
//
///**
// *
// * @author Dirceu Junior
// */
//public class CadastroClienteService implements Serializable {
//
//    private static final long serialVersionUID = 1L;
//
//    @Inject
//    private Clientes clientes;
//
//    @Inject
//    @UsuarioLogado
//    private UsuarioSistema usuarioLogado;
//
//    @Transactional
//    public Cliente salvar(Cliente cliente) {
//        Cliente clienteExistente = clientes.porCpfCnpj(cliente.getCpfCnpj());
//        if (clienteExistente != null && !clienteExistente.equals(cliente)) {
//            throw new NegocioException("JÁ EXISTE UM CLIENTE COM CPF/CNPJ INFORMADO");
//        }
//        if (cliente.getEnderecos().isEmpty()) {
//            throw new NegocioException("OBRIGATÓRIO PELO MENOS 1 ENDEREÇO PARA O CLIENTE");
//        }
//        if (cliente.getEmails().isEmpty()) {
//            throw new NegocioException("OBRIGATÓRIO PELO MENOS 1 EMAIL PARA O CLIENTE");
//        }
//        if (cliente.getId() == null) {
//            cliente.setUsuarioCadastro(usuarioLogado.getUsuario());
//        } else {
//            cliente.setUsuarioUltimaAlteracao(usuarioLogado.getUsuario());
//            cliente.setDataUltimaAlteracao(new Date());
//        }
//        return clientes.guardar(cliente);
//    }
//
//}
