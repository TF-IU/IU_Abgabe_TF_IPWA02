package entity;

import jakarta.persistence.*;
import util.DateUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
public class Requirement{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "created_by_user_ref")
	private User createdBy;
	@Column(unique = true)
	private String title;

	@Column(length = 8000)
    private String description;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "requirementref")
    private List<TestCase> testCases;
    
    // Default constructor
    public Requirement() {
    	this.createdAt = LocalDateTime.now();
    }
    
    // Returns the creation date formatted as a string
    public String getCreatedDateFormatted() {
        return DateUtils.format(this.getCreatedAt());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Requirement)) return false;
        Requirement that = (Requirement) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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
	public List<TestCase> getTestCases() {
		return testCases;
	}
	public void setTestCases(List<TestCase> testCases) {
		this.testCases = testCases;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}