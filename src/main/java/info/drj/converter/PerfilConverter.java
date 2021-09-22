package info.drj.converter;

import info.drj.model.Perfil;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import info.drj.repository.Perfis;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

@FacesConverter(forClass = Perfil.class)
public class PerfilConverter implements Converter {

    @Inject
    private Perfis perfis;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Perfil retorno = null;
        if (StringUtils.isNotEmpty(value)) {
            Long id = new Long(value);
            retorno = perfis.porId(id);
        }
        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Perfil perfil = (Perfil) value;
            return perfil.getId() == null ? null : perfil.getId().toString();
        }
        return "";
    }
}
