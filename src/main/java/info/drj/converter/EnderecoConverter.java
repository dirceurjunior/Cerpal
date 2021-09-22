package info.drj.converter;

import info.drj.model.Endereco;
import info.drj.repository.Enderecos;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

@FacesConverter(forClass = Endereco.class)
public class EnderecoConverter implements Converter {

    @Inject
    private Enderecos enderecos;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Endereco retorno = null;
        if (StringUtils.isNotEmpty(value)) {
            Long id = new Long(value);
            retorno = enderecos.enderecoPorId(id);
        }
        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Endereco endereco = (Endereco) value;
            return endereco.getId() == null ? null : endereco.getId().toString();
        }
        return "";
    }
}
