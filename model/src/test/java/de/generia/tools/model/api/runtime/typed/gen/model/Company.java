package de.generia.tools.model.api.runtime.typed.gen.model;

import de.generia.tools.model.api.runtime.typed.gen.model.Employee;
import de.generia.tools.model.api.runtime.typed.gen.model.Project;
import de.generia.tools.model.api.runtime.typed.gen.model.Workstation;
import java.util.List;
import java.util.Set;

/**
 * The 'Company' class was generated from 'Company' EClass api element.
 * 
 */
public interface Company {
    
    /**
     * Gets the 'name' property of the 'Company' EClass.
     * 
     * @return 'name' property of 'Company' EClass
     */
    String getName();
    
    /**
     * Sets the 'name' property of the 'Company' EClass.
     * 
     * @param name new value for the 'name' property
     */
    void setName(String name);
    
    /**
     * Gets the 'homepage' property of the 'Company' EClass.
     * 
     * @return 'homepage' property of 'Company' EClass
     */
    String getHomepage();
    
    /**
     * Sets the 'homepage' property of the 'Company' EClass.
     * 
     * @param homepage new value for the 'homepage' property
     */
    void setHomepage(String homepage);
    
    /**
     * Gets the 'workstations' property of the 'Company' EClass.
     * 
     * @return 'workstations' property of 'Company' EClass
     */
    List<Workstation> getWorkstations();
    
    /**
     * Sets the 'workstations' property of the 'Company' EClass.
     * 
     * @param workstations new value for the 'workstations' property
     */
    void setWorkstations(List<Workstation> workstations);
    
    /**
     * Gets the 'employees' property of the 'Company' EClass.
     * 
     * @return 'employees' property of 'Company' EClass
     */
    Set<Employee> getEmployees();
    
    /**
     * Sets the 'employees' property of the 'Company' EClass.
     * 
     * @param employees new value for the 'employees' property
     */
    void setEmployees(Set<Employee> employees);
    
    /**
     * Gets the 'projects' property of the 'Company' EClass.
     * 
     * @return 'projects' property of 'Company' EClass
     */
    Set<Project> getProjects();
    
    /**
     * Sets the 'projects' property of the 'Company' EClass.
     * 
     * @param projects new value for the 'projects' property
     */
    void setProjects(Set<Project> projects);
}
