package util;
import dao.UserDAO;
import entity.User;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "userConverter", managed = true)
public class UserConverter implements Converter<User> {

    private UserDAO userDAO = CDI.current().select(UserDAO.class).get();

    @Override
    public User getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) return null;
        try {
            int id = Integer.parseInt(value);
            return userDAO.findById(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, User user) {
        return (user != null && user.getId() != 0) ? String.valueOf(user.getId()) : "";
    }
}