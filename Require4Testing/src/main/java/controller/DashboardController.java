package controller;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Map;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import dao.TestCaseDAO;
import enums.TestResult;

@Named
@SessionScoped
public class DashboardController implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
    private TestCaseDAO testCaseDao;

	private BarChartModel testCaseBarChart;

	@PostConstruct
    public void init() {
        buildBarChart();
    }

	// Method to build the bar chart for test case results
	private void buildBarChart() {
        Map<TestResult, Long> resultCounts = testCaseDao.countByResult();

        testCaseBarChart = new BarChartModel();
        ChartSeries series = new ChartSeries();
        series.setLabel("Testfälle");

        resultCounts.forEach((result, count) ->
            series.set(result.getDisplayName(), count)
        );

        testCaseBarChart.addSeries(series);
        testCaseBarChart.setTitle("Testfall-Ergebnisse");
        testCaseBarChart.setLegendPosition("ne");
        testCaseBarChart.setAnimate(true);
        Axis xAxis = testCaseBarChart.getAxis(AxisType.X);
        xAxis.setLabel("Ergebnis");

        Axis yAxis = testCaseBarChart.getAxis(AxisType.Y);
        yAxis.setLabel("Anzahl");
        yAxis.setMin(0);
        yAxis.setMax(resultCounts.size() + 2);
        yAxis.setTickInterval("1");
        yAxis.setTickFormat("%.0f"); 
    }
	
	// Method to check if returning from test cases and refresh the chart
	public void checkAndRefresh() {
	    Map<String, Object> sessionMap = FacesContext.getCurrentInstance()
	        .getExternalContext().getSessionMap();

	    Boolean fromTestcases = (Boolean) sessionMap.get("returningFromTestcases");

	    if (Boolean.TRUE.equals(fromTestcases)) {
	    	buildBarChart(); 
	        sessionMap.remove("returningFromTestcases");
	    }
	}


	// Redirect methods for navigation
	public String getRequirements() {
        return "requirement.xhtml?faces-redirect=true";
    }

	public String getTestRuns() {
		return "testRun.xhtml?faces-redirect=true";
	}

	public String getTestCases() {
		return "testCase.xhtml?faces-redirect=true";
	}

	// Getter for the bar chart model
    public BarChartModel getTestCaseBarChart() {
        return testCaseBarChart;
    }

}
