package controller;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.RowEditEvent;

import entity.Requirement;
import entity.TestCase;
import enums.TestResult;
import entity.User;
import dao.RequirementDAO;
import dao.TestCaseDAO;


@Named
@ViewScoped
public class TestCaseController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
    private TestCaseDAO testCaseDao;
    @Inject
    private LoginController loginController;
    @Inject
    private RequirementDAO requirementDao;


    private List<TestCase> testCases;
    private List<TestResult> testResults;
    private List<Requirement> requirements;
    private User currentUser;

    @PostConstruct
    public void init() {
    	// Load all test cases and requirements from the database
        testCases = testCaseDao.findAll();
        currentUser = loginController.getCurrentUser();
        testResults = Arrays.asList(TestResult.values());
        requirements = requirementDao.findAll();
    }

	/**
	 * Redirects to the dashboard page.
	 * 
	 * @return the navigation string to the dashboard page
	 */
    public String redirectToDashboard() {
    	FacesContext.getCurrentInstance().getExternalContext()
        .getSessionMap().put("returningFromTestcases", true);

        return "dashboard.xhtml?faces-redirect=true";
    }

    // Method to create a new test case
    public void createNew() {
        TestCase newCase = new TestCase();
        newCase.setCreatedBy(currentUser);
        testCases.add(newCase);
    }

    // Method to delete a test case
    public void delete(TestCase tc) {
        testCaseDao.delete(tc);
        testCases.remove(tc);
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Gelöscht", "Testfall gelöscht."));
    }

    // Method to handle row editing event
    public void onRowEdit(RowEditEvent<TestCase> event) {
        TestCase tc = event.getObject();

        // Validate that the title is not empty
        if (tc.getTitle() == null || tc.getTitle().isBlank()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Titel fehlt", "Bitte einen Titel angeben."));
            return;
        }
       
        //case if new or existed
        if (tc.getId() == 0) {
            testCaseDao.create(tc);
        } else {
            testCaseDao.update(tc);
        }

        // Add success message
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Gespeichert", "Testfall gespeichert."));
    }

    //Getter and Setter methods
    public List<TestCase> getTestCases() {
        return testCases;
    }

    public List<TestResult> getTestResults() {
        return testResults;
    }
    
    public List<Requirement> getRequirements() {
        return requirements;
    }

}