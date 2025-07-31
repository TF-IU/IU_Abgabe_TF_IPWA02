package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DualListModel;

import entity.TestCase;
import entity.TestRun;
import entity.User;
import enums.Role;
import dao.TestCaseDAO;
import dao.TestRunDAO;
import dao.UserDAO;

@Named
@ViewScoped
public class TestRunController implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private TestRunDAO testRunDao;
    @Inject
    private LoginController loginController;
    @Inject
    private UserDAO userDao;
    @Inject
    private TestCaseDAO testCaseDao;


    private List<TestRun> testRuns;
    private List<User> userTester;
    private Map<Integer, DualListModel<TestCase>> pickListsByRun = new HashMap<>();

    private User currentUser;
    
 
    @PostConstruct
    public void init() {
        testRuns = testRunDao.findAll();
        currentUser = loginController.getCurrentUser();
        userTester = userDao.findByRole(Role.TESTER);

        // Initialize pick lists for each test run
        for (TestRun run : testRuns) {
            prepareTestCaseAssignment(run);
        }


    }
    
	/**
	 * Prepares the dual list model for test case assignment in a test run. This
	 * method separates assigned and available test cases for the given run.
	 *
	 * @param run The test run for which to prepare the assignment.
	 */
    public void prepareTestCaseAssignment(TestRun run) {
        List<TestCase> allCases = testCaseDao.findAll();
        List<TestCase> assigned = run.getTestCases();
        List<TestCase> available = new ArrayList<>(allCases);
        available.removeAll(assigned);

        pickListsByRun.put(run.getId(), new DualListModel<>(available, assigned));
    }

	/**
	 * Redirects to the dashboard page.
	 *
	 * @return the navigation string to the dashboard page
	 */
    public String redirectToDashboard() {
        return "dashboard.xhtml?faces-redirect=true";
    }

    // Method to create a new test run
    public void createNew() {
        TestRun newRun = new TestRun();
        newRun.setCreatedBy(currentUser);
        testRuns.add(newRun);
        pickListsByRun.put(newRun.getId(), new DualListModel<>(testCaseDao.findAll(), new ArrayList<>()));
    }

    // Method to delete a test run
    public void delete(TestRun run) {
        if (run != null) {       	
            testRunDao.delete(run);
            testRuns.remove(run);
            pickListsByRun.remove(run.getId());
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "Gelöscht", "Testlauf wurde gelöscht."));
        }
    }

    // Method to handle row edit events for test runs
    public void onRowEdit(RowEditEvent<TestRun> event) {
        TestRun run = event.getObject();

        // Validate the run title
        if (run.getTitle() == null || run.getTitle().isBlank()) {
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Titel fehlt", "Bitte einen Titel angeben."));
            return;
        }
        
        // Handle the case where the run is new (id == 0) or already exists
        if (run.getId() == 0) {
            testRunDao.create(run);
            DualListModel<TestCase> dualList = pickListsByRun.get(0);
            run.setTestCases(dualList.getTarget());
            testRunDao.update(run);
            pickListsByRun.remove(0); // Remove the placeholder for new runs
            pickListsByRun.put(run.getId(), dualList); // Add the run with its ID
        } else {
        	DualListModel<TestCase> dualList = pickListsByRun.get(run.getId());
            run.setTestCases(dualList.getTarget());
            testRunDao.update(run);
        }

        // Add success message
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Gespeichert", "Testlauf gespeichert."));
    }
    
	/**
	 * Retrieves a dual list model for test cases associated with a specific test
	 * run. This method allows the user to select test cases to assign to the run.
	 *
	 * @param run The test run for which to get the dual list model.
	 * @return A DualListModel containing available and assigned test cases.
	 */
    public DualListModel<TestCase> getTestfallDualListFor(TestRun run) {
        return pickListsByRun.computeIfAbsent(run.getId(), id -> {
            List<TestCase> allCases = testCaseDao.findAll();
            List<TestCase> assigned = run.getTestCases();
            List<TestCase> available = new ArrayList<>(allCases);
            available.removeAll(assigned);
            return new DualListModel<>(available, assigned);
        });

    }


    public List<TestRun> getTestRuns() {
        return testRuns;
    }

    public void setTestRuns(List<TestRun> testRuns) {
        this.testRuns = testRuns;
    }

	public List<User> getUserTester() {
		return userTester;
	}

	public void setUserTester(List<User> userTester) {
		this.userTester = userTester;
	}
	
	public Map<Integer, DualListModel<TestCase>> getPickListsByRun() {
	    return pickListsByRun;
	}


	
}