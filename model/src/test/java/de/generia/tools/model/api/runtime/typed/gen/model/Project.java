package de.generia.tools.model.api.runtime.typed.gen.model;

import de.generia.tools.model.api.runtime.typed.gen.model.Company;
import de.generia.tools.model.api.runtime.typed.gen.model.Employee;
import java.util.Set;

/**
 * The 'Project' class was generated from 'Project' EClass api element.
 * 
 */
public interface Project {
    
    /**
     * Gets the 'name' property of the 'Project' EClass.
     * 
     * @return 'name' property of 'Project' EClass
     */
    String getName();
    
    /**
     * Sets the 'name' property of the 'Project' EClass.
     * 
     * @param name new value for the 'name' property
     */
    void setName(String name);
    
    /**
     * Gets the 'description' property of the 'Project' EClass.
     * 
     * @return 'description' property of 'Project' EClass
     */
    String getDescription();
    
    /**
     * Sets the 'description' property of the 'Project' EClass.
     * 
     * @param description new value for the 'description' property
     */
    void setDescription(String description);
    
    /**
     * Gets the 'size' property of the 'Project' EClass.
     * 
     * @return 'size' property of 'Project' EClass
     */
    Project.Size getSize();
    
    /**
     * Sets the 'size' property of the 'Project' EClass.
     * 
     * @param size new value for the 'size' property
     */
    void setSize(Project.Size size);
    
    /**
     * Gets the 'company' property of the 'Project' EClass.
     * 
     * @return 'company' property of 'Project' EClass
     */
    Company getCompany();
    
    /**
     * Sets the 'company' property of the 'Project' EClass.
     * 
     * @param company new value for the 'company' property
     */
    void setCompany(Company company);
    
    /**
     * Gets the 'employees' property of the 'Project' EClass.
     * 
     * @return 'employees' property of 'Project' EClass
     */
    Set<Employee> getEmployees();
    
    /**
     * Sets the 'employees' property of the 'Project' EClass.
     * 
     * @param employees new value for the 'employees' property
     */
    void setEmployees(Set<Employee> employees);
    
    /**
     * The 'Size' enum was generated from 'Size' EEnum api element.
     * 
     */
    public enum Size {
    
        /**
         * The 'small' literal. 
    	 * 
         */
        small, 
    
        /**
         * The 'medium' literal. 
    	 * 
         */
        medium, 
    
        /**
         * The 'big' literal. 
    	 * 
         */
        big, 
        ;
    }
}
