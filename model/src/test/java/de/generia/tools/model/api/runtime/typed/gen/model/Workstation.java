package de.generia.tools.model.api.runtime.typed.gen.model;

import de.generia.tools.model.api.runtime.typed.gen.model.Assessment;
import de.generia.tools.model.api.runtime.typed.gen.model.Company;
import de.generia.tools.model.api.runtime.typed.gen.model.Employee;

/**
 * The 'Workstation' class was generated from 'Workstation' EClass api element.
 * 
 */
public interface Workstation {
    
    /**
     * Gets the 'name' property of the 'Workstation' EClass.
     * 
     * @return 'name' property of 'Workstation' EClass
     */
    String getName();
    
    /**
     * Sets the 'name' property of the 'Workstation' EClass.
     * 
     * @param name new value for the 'name' property
     */
    void setName(String name);
    
    /**
     * Gets the 'assessment' property of the 'Workstation' EClass.
     * 
     * @return 'assessment' property of 'Workstation' EClass
     */
    Assessment getAssessment();
    
    /**
     * Sets the 'assessment' property of the 'Workstation' EClass.
     * 
     * @param assessment new value for the 'assessment' property
     */
    void setAssessment(Assessment assessment);
    
    /**
     * Gets the 'company' property of the 'Workstation' EClass.
     * 
     * @return 'company' property of 'Workstation' EClass
     */
    Company getCompany();
    
    /**
     * Sets the 'company' property of the 'Workstation' EClass.
     * 
     * @param company new value for the 'company' property
     */
    void setCompany(Company company);
    
    /**
     * Gets the 'employee' property of the 'Workstation' EClass.
     * 
     * @return 'employee' property of 'Workstation' EClass
     */
    Employee getEmployee();
    
    /**
     * Sets the 'employee' property of the 'Workstation' EClass.
     * 
     * @param employee new value for the 'employee' property
     */
    void setEmployee(Employee employee);
}
