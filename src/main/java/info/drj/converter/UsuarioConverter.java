package info.drj.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import info.drj.model.Usuario;
import info.drj.repository.Usuarios;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter {

    @Inject
    private Usuarios usuarios;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Usuario retorno = null;
        if (StringUtils.isNotEmpty(value)) {
            Long id = new Long(value);
            retorno = usuarios.porId(id);
        }
        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Usuario usuario = (Usuario) value;
            return usuario.getId() == null ? null : usuario.getId().toString();
        }
        return "";
    }
}
