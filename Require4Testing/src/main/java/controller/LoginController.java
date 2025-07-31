package controller;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.io.Serializable;
import org.mindrot.jbcrypt.BCrypt;

import dao.UserDAO;
import entity.User;
import enums.Role;

@Named
@SessionScoped
public class LoginController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
    private String password;

    private User currentUser;

    @Inject
    private UserDAO userDao;

    public String login() {
        User user = userDao.findByName(username);

        // Check if user exists and password matches
        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            this.currentUser = user;

            // Store the current user in the session
            FacesContext.getCurrentInstance()
            .getExternalContext()
            .getSessionMap()
            .put("loginController", this);

            // Redirect to the dashboard page
            return "dashboard.xhtml?faces-redirect=true";
        }

        // If login fails, add an error message
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                             "Login fehlgeschlagen",
                             "Benutzername oder Passwort ist ungültig."));
        // Reset the password field
        return null; 
    }

    public String logout() {
    	FacesContext.getCurrentInstance()
        .getExternalContext()
        .invalidateSession();

        this.currentUser = null;
        return "login.xhtml?faces-redirect=true";
    }
    
    public boolean hasRole(Role role) {
        return currentUser != null && currentUser.getRoles().contains(role);
    }



    // Getter & Setter
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getCurrentUser() {
        return currentUser;
    }
}
