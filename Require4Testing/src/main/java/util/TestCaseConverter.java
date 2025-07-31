package util;

import dao.TestCaseDAO;
import entity.TestCase;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "testCaseConverter", managed = true)
public class TestCaseConverter implements Converter<TestCase> {

    private TestCaseDAO testCaseDAO = CDI.current().select(TestCaseDAO.class).get();

    @Override
    public TestCase getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) return null;
        try {
            int id = Integer.parseInt(value);
            return testCaseDAO.findById(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, TestCase testCase) {
        return (testCase != null && testCase.getId() != 0) ? String.valueOf(testCase.getId()) : "";
    }
}