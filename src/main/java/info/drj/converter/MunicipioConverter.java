package info.drj.converter;

import info.drj.model.Municipio;
import info.drj.repository.Municipios;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

@FacesConverter(forClass = Municipio.class)
public class MunicipioConverter implements Converter {

    @Inject
    private Municipios municipios;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Municipio retorno = null;
        if (StringUtils.isNotEmpty(value)) {
            Long id = new Long(value);
            retorno = municipios.municipioPorId(id);
        }
        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Municipio municipio = (Municipio) value;
            return municipio.getId() == null ? null : municipio.getId().toString();
        }
        return "";
    }
}
