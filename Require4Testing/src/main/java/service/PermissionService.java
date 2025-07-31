package service;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.Collections;
import java.util.Optional;

import controller.LoginController;
import entity.TestCase;
import enums.Role;

@Named
@RequestScoped
public class PermissionService {

    @Inject
    private LoginController loginController;

    public boolean canEditRequirements() {
        return loginController.hasRole(Role.REQUIREMENTS_ENGINEER);
    }
    
	public boolean canEditTestRuns() {
		return loginController.hasRole(Role.TESTMANAGER);
	}
	
    public boolean canEditTestCase() {
        return loginController.hasRole(Role.TESTFALLERSTELLER) || loginController.hasRole(Role.TESTER) ;
    }
    
    public boolean canCreateAndDeleteTestCase() {
        return loginController.hasRole(Role.TESTFALLERSTELLER);
    }

    
    public boolean canEditTestResult(TestCase tc) {
    	//bidirectional relationship with TestRun and test if 
    	//assignedTo current User in any of those runs
		if (loginController.hasRole(Role.TESTER) && 
				Optional.ofNullable(tc.getTestRuns())
		        .orElse(Collections.emptyList())
		        .stream()
		        .anyMatch(tr -> tr.getAssignedTo().equals(loginController.getCurrentUser()))) {
			return true;
		}
        return false;
    }
  
}