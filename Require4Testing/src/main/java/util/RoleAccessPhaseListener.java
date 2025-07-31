package util;

import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;
import jakarta.faces.context.FacesContext;
import jakarta.faces.application.NavigationHandler;

import controller.LoginController;

/**
 * RoleAccessPhaseListener is a JSF PhaseListener that checks user
 * authentication before rendering any view except for login and DSGVO pages. If
 * the user is not authenticated, it redirects them to the login page.
 */
public class RoleAccessPhaseListener implements PhaseListener {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    public void afterPhase(PhaseEvent event) {
        FacesContext context = event.getFacesContext();
        String viewId = context.getViewRoot().getViewId();

        if (viewId.contains("login.xhtml") || viewId.contains("dsgvo.xhtml")) return;

        LoginController loginCtrl = context.getApplication()
            .evaluateExpressionGet(context, "#{loginController}", LoginController.class);

        if (loginCtrl == null || loginCtrl.getCurrentUser() == null) {
            redirect(context, "login.xhtml");
            return;
        }

    }

    private void redirect(FacesContext context, String page) {
        NavigationHandler navHandler = context.getApplication().getNavigationHandler();
        navHandler.handleNavigation(context, null, page + "?faces-redirect=true");
        context.renderResponse(); 
    }

    @Override
    public void beforePhase(PhaseEvent event) {}

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
}

