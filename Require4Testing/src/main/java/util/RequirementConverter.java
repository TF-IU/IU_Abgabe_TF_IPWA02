package util;

import dao.RequirementDAO;
import entity.Requirement;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "requirementConverter", managed = true)
public class RequirementConverter implements Converter<Requirement> {

    private RequirementDAO requirementDAO = CDI.current().select(RequirementDAO.class).get();

    @Override
    public Requirement getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) return null;
        try {
            int id = Integer.parseInt(value);
            return requirementDAO.findById(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Requirement requirement) {
        return (requirement != null && requirement.getId() != 0) ? String.valueOf(requirement.getId()) : "";
    }
}