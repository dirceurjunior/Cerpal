/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.util.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author Dirceu Junior
 */
@FacesValidator(value = "cpfCnpjValidator")
public class CPFCNPJ implements Validator {

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        FacesMessage message = new FacesMessage();
        message.setSeverity(FacesMessage.SEVERITY_ERROR);
        String documento = removerCaracteres(String.valueOf(value));
        if (value != null) {
            switch (documento.length()) {
                case 11:
                    if (!validaCPF(documento)) {
                        message.setSummary("CPF INVALIDO");
                        throw new ValidatorException(message);
                    }
                    break;
                case 14:
                    if (!validaCNPJ(documento)) {
                        message.setSummary("CNPJ INVALIDO");
                        throw new ValidatorException(message);
                    }
                    break;
                default:
                    message.setSummary("DADOS INCORRETOS");
                    throw new ValidatorException(message);
            }
        }
    }

    public String removerCaracteres(String value) {
        value = value.replace('.', ' ');
        value = value.replace('-', ' ');
        value = value.replace('/', ' ');
        value = value.replaceAll(" ", "");
        return value;
    }

    /**
     * Valida CPF do usuário. Não aceita CPF's padrões como 11111111111 ou
     * 22222222222
     *
     * @param cpf String valor com 11 dígitos
     */
    private static boolean validaCPF(String cpf) {
        cpf = cpf.replace('.', ' ');
        cpf = cpf.replace('-', ' ');
        cpf = cpf.replaceAll(" ", "");
        if (cpf == null || cpf.length() != 11 || isCPFPadrao(cpf)) {
            return false;
        }
        try {
            Long.parseLong(cpf);
        } catch (NumberFormatException e) { // CPF não possui somente números
            return false;
        }
        if (!calcDigVerif(cpf.substring(0, 9)).equals(cpf.substring(9, 11))) {
            return false;
        }
        return true;
    }

    /**
     *
     * @param cpf String valor a ser testado
     * @return boolean indicando se o usuário entrou com um CPF padrão
     */
    private static boolean isCPFPadrao(String cpf) {
        if (cpf.equals("11111111111")
                || cpf.equals("22222222222")
                || cpf.equals("33333333333")
                || cpf.equals("44444444444")
                || cpf.equals("55555555555")
                || cpf.equals("66666666666")
                || cpf.equals("77777777777")
                || cpf.equals("88888888888")
                || cpf.equals("99999999999")) {
            return true;
        }
        return false;
    }

    private static String calcDigVerif(String num) {
        Integer primDig, segDig;
        int soma = 0, peso = 10;
        for (int i = 0; i < num.length(); i++) {
            soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
        }

        if (soma % 11 == 0 | soma % 11 == 1) {
            primDig = new Integer(0);
        } else {
            primDig = new Integer(11 - (soma % 11));
        }

        soma = 0;
        peso = 11;
        for (int i = 0; i < num.length(); i++) {
            soma += Integer.parseInt(num.substring(i, i + 1)) * peso--;
        }

        soma += primDig.intValue() * 2;
        if (soma % 11 == 0 | soma % 11 == 1) {
            segDig = new Integer(0);
        } else {
            segDig = new Integer(11 - (soma % 11));
        }

        return primDig.toString() + segDig.toString();
    }

    public boolean validaCNPJ(String cnpj) {
        cnpj = cnpj.replace('.', ' ');
        cnpj = cnpj.replace('/', ' ');
        cnpj = cnpj.replace('-', ' ');
        cnpj = cnpj.replaceAll(" ", "");
        if (cnpj == null || cnpj.length() != 14 || isCNPJPadrao(cnpj)) {
            return false;
        }
        try {
            Long.parseLong(cnpj);
        } catch (NumberFormatException e) {
            return false;
        }
        if (!validarCNPJ((cnpj))) {
            return false;
        }
        return true;
    }

    public boolean isCNPJPadrao(String cnpj) {
        if (cnpj.equals("11111111111111") || cnpj.equals("22222222222222")
                || cnpj.equals("33333333333333")
                || cnpj.equals("44444444444444")
                || cnpj.equals("55555555555555")
                || cnpj.equals("66666666666666")
                || cnpj.equals("77777777777777")
                || cnpj.equals("88888888888888")
                || cnpj.equals("99999999999999")) {
            return true;
        }
        return false;
    }

    public boolean validarCNPJ(String strCNPJ) {
        int iSoma = 0, iDigito;
        char[] chCaracteresCNPJ;
        String strCNPJ_Calculado;

        if (!strCNPJ.substring(0, 1).equals("")) {
            try {
                strCNPJ = strCNPJ.replace('.', ' ');
                strCNPJ = strCNPJ.replace('/', ' ');
                strCNPJ = strCNPJ.replace('-', ' ');
                strCNPJ = strCNPJ.replaceAll(" ", "");
                strCNPJ_Calculado = strCNPJ.substring(0, 12);
                if (strCNPJ.length() != 14) {
                    return false;
                }
                chCaracteresCNPJ = strCNPJ.toCharArray();
                for (int i = 0; i < 4; i++) {
                    if ((chCaracteresCNPJ[i] - 48 >= 0) && (chCaracteresCNPJ[i] - 48 <= 9)) {
                        iSoma += (chCaracteresCNPJ[i] - 48) * (6 - (i + 1));
                    }
                }
                for (int i = 0; i < 8; i++) {
                    if ((chCaracteresCNPJ[i + 4] - 48 >= 0) && (chCaracteresCNPJ[i + 4] - 48 <= 9)) {
                        iSoma += (chCaracteresCNPJ[i + 4] - 48) * (10 - (i + 1));
                    }
                }
                iDigito = 11 - (iSoma % 11);
                strCNPJ_Calculado += ((iDigito == 10) || (iDigito == 11)) ? "0" : Integer.toString(iDigito);
                /* Segunda parte */
                iSoma = 0;
                for (int i = 0; i < 5; i++) {
                    if ((chCaracteresCNPJ[i] - 48 >= 0) && (chCaracteresCNPJ[i] - 48 <= 9)) {
                        iSoma += (chCaracteresCNPJ[i] - 48) * (7 - (i + 1));
                    }
                }
                for (int i = 0; i < 8; i++) {
                    if ((chCaracteresCNPJ[i + 5] - 48 >= 0) && (chCaracteresCNPJ[i + 5] - 48 <= 9)) {
                        iSoma += (chCaracteresCNPJ[i + 5] - 48) * (10 - (i + 1));
                    }
                }
                iDigito = 11 - (iSoma % 11);
                strCNPJ_Calculado += ((iDigito == 10) || (iDigito == 11)) ? "0" : Integer.toString(iDigito);
                return strCNPJ.equals(strCNPJ_Calculado);
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

}
