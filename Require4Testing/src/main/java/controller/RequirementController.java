package controller;

import java.io.Serializable;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.annotation.PostConstruct;
import java.util.List;
import org.primefaces.event.RowEditEvent;

import entity.Requirement;
import entity.User;
import dao.RequirementDAO;

@Named
@ViewScoped
public class RequirementController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
    private RequirementDAO requirementDao;
	@Inject
	private LoginController loginController;
	
	private User currentUser;

    private List<Requirement> requirements;
    private Requirement selectedRequirement;

    @PostConstruct
    public void init() {
    	// Load all requirements from the database
        requirements = requirementDao.findAll();
    	currentUser = loginController.getCurrentUser();
    }
    
	/**
	 * Redirects to the dashboard page.
	 * 
	 * @return the navigation string to the dashboard page
	 */
    public String redirectToDashboard() {
        return "dashboard.xhtml?faces-redirect=true";
    }

	public void createNew() {
		// Create a new requirement with the current user as creator
        Requirement newRequirement = new Requirement();
        newRequirement.setCreatedBy(currentUser);
        requirements.add(newRequirement);
    }

    public void deleteSelected(Requirement selRequirement) {
        if (selRequirement != null) {
        	// Check, if the requirement has associated test cases
        	if (selRequirement.getTestCases() != null && !selRequirement.getTestCases().isEmpty()) {
        		FacesContext.getCurrentInstance().addMessage(null,
        	            new FacesMessage(FacesMessage.SEVERITY_WARN,
        	                "Nicht gelöscht",
        	                "Die Anforderung kann nicht gelöscht werden, da sie bereits Testfälle enthält."));
        	        return;
	        }    
        	// Delete the requirement
            requirementDao.delete(selRequirement);
            requirements.remove(selRequirement);
            
            // Show success message
    		FacesContext.getCurrentInstance().addMessage(null,
    				new FacesMessage(FacesMessage.SEVERITY_INFO,
    								"Gelöscht",
    								"Anforderung wurde gelöscht."));
        	       	
        }

    }
    
    public void onRowEdit(RowEditEvent<Requirement> event) {
        Requirement req = event.getObject();

        //Validate title
        if (req.getTitle() == null || req.getTitle().isBlank()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Titel fehlt", "Bitte einen Titel angeben."));
            return;
        }
        
        // Validate uniqueness of title
		if (requirementDao.titleExists(req.getTitle())
				&& requirementDao.findById(req.getId()).getTitle() != req.getTitle()) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Titel existiert bereits", "Bitte einen anderen Titel wählen."));
			return;
		}

        //Save the requirement
        if (req.getId() == 0) {
            requirementDao.create(req);
        } else {
            requirementDao.update(req);
        }

        // Show success message
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Gespeichert", "Anforderung wurde gespeichert."));
    }

    // Getters and Setters
    public List<Requirement> getRequirements() {
		return requirements;
	}

	public void setRequirements(List<Requirement> requirements) {
		this.requirements = requirements;
	}

	public Requirement getSelectedRequirement() {
		return selectedRequirement;
	}

	public void setSelectedRequirement(Requirement selectedRequirement) {
		this.selectedRequirement = selectedRequirement;
	}


}