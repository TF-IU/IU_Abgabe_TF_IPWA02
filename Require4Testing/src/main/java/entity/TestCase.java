package entity;

import jakarta.persistence.*;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import enums.TestResult;

@Entity
public class TestCase{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "created_by_user_ref")
	private User createdBy;
	@ManyToOne
	@JoinColumn(name = "requirement_ref")
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private Requirement requirementref;
	@ManyToMany(mappedBy = "testCases")
    private List<TestRun> testRuns;
	@Enumerated(EnumType.STRING)
	private TestResult result;
	private String title;
	@Column(length = 8000)
	private String description;

	// Default constructor
	public TestCase() {
		result = TestResult.NOT_TESTED;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj) return true;
	    if (!(obj instanceof TestCase)) return false;
	    return this.id == ((TestCase) obj).getId();
	}

	@Override
	public int hashCode() {
	    return Integer.hashCode(id);
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Requirement getRequirementRef() {
		return requirementref;
	}

	public void setRequirementRef(Requirement requirementRef) {
		this.requirementref = requirementRef;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
        this.description = description;
    }
	
	public User getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	public Requirement getRequirementref() {
		return requirementref;
	}

	public void setRequirementref(Requirement requirementref) {
		this.requirementref = requirementref;
	}

	public TestResult getResult() {
		return result;
	}

	public void setResult(TestResult result) {
		this.result = result;
	}

	public List<TestRun> getTestRuns() {
		return testRuns;
	}

	public void setTestRuns(List<TestRun> testRuns) {
		this.testRuns = testRuns;
	}
}
