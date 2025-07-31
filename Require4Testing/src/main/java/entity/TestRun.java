package entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class TestRun{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;
	@ManyToOne
	@JoinColumn(name = "created_by_user_ref")
	private User createdBy;
	@ManyToOne
	@JoinColumn(name = "assigned_to_user_ref")
	private User assignedTo;
    @ManyToMany
    @JoinTable(
        name = "TestRun_TestCase",
        joinColumns = @JoinColumn(name = "TestRun_ref"),
        inverseJoinColumns = @JoinColumn(name = "TestCase_ref")
    )
    private List<TestCase> testCases;
    
    // Default constructor
    public TestRun() {
    	
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestRun)) return false;
        TestRun that = (TestRun) o;
        return this.id == that.id;
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
	public User getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}
	public User getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(User assignedTo) {
		this.assignedTo = assignedTo;
	}
	public List<TestCase> getTestCases() {
		return testCases;
	}
	public void setTestCases(List<TestCase> testCases) {
		this.testCases = testCases;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

}
