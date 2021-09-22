package info.drj.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import info.drj.model.Config;
import info.drj.repository.Configs;
import javax.inject.Inject;
import org.apache.commons.lang3.StringUtils;

@FacesConverter(forClass = Config.class)
public class ConfigConverter implements Converter {

    @Inject
    private Configs configs;

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Config retorno = null;
        if (StringUtils.isNotEmpty(value)) {
            Long id = new Long(value);
            retorno = configs.porId(id);
        }
        return retorno;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value != null) {
            Config config = (Config) value;
            return config.getId() == null ? null : config.getId().toString();
        }
        return "";
    }
}
