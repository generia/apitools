package de.generia.tools.model.api.runtime.typed.gen.model;

import de.generia.tools.model.api.runtime.typed.gen.model.Company;
import de.generia.tools.model.api.runtime.typed.gen.model.Contact;
import de.generia.tools.model.api.runtime.typed.gen.model.Project;
import de.generia.tools.model.api.runtime.typed.gen.model.Workstation;
import java.util.Set;

/**
 * The 'Employee' class was generated from 'Employee' EClass api element.
 * 
 */
public interface Employee {
    
    /**
     * Gets the 'logins' property of the 'Employee' EClass.
     * 
     * @return 'logins' property of 'Employee' EClass
     */
    String getLogins();
    
    /**
     * Sets the 'logins' property of the 'Employee' EClass.
     * 
     * @param logins new value for the 'logins' property
     */
    void setLogins(String logins);
    
    /**
     * Gets the 'name' property of the 'Employee' EClass.
     * 
     * @return 'name' property of 'Employee' EClass
     */
    String getName();
    
    /**
     * Sets the 'name' property of the 'Employee' EClass.
     * 
     * @param name new value for the 'name' property
     */
    void setName(String name);
    
    /**
     * Gets the 'title' property of the 'Employee' EClass.
     * 
     * @return 'title' property of 'Employee' EClass
     */
    String getTitle();
    
    /**
     * Sets the 'title' property of the 'Employee' EClass.
     * 
     * @param title new value for the 'title' property
     */
    void setTitle(String title);
    
    /**
     * Gets the 'contact' property of the 'Employee' EClass.
     * 
     * @return 'contact' property of 'Employee' EClass
     */
    Contact getContact();
    
    /**
     * Sets the 'contact' property of the 'Employee' EClass.
     * 
     * @param contact new value for the 'contact' property
     */
    void setContact(Contact contact);
    
    /**
     * Gets the 'company' property of the 'Employee' EClass.
     * 
     * @return 'company' property of 'Employee' EClass
     */
    Company getCompany();
    
    /**
     * Sets the 'company' property of the 'Employee' EClass.
     * 
     * @param company new value for the 'company' property
     */
    void setCompany(Company company);
    
    /**
     * Gets the 'workstation' property of the 'Employee' EClass.
     * 
     * @return 'workstation' property of 'Employee' EClass
     */
    Workstation getWorkstation();
    
    /**
     * Sets the 'workstation' property of the 'Employee' EClass.
     * 
     * @param workstation new value for the 'workstation' property
     */
    void setWorkstation(Workstation workstation);
    
    /**
     * Gets the 'projects' property of the 'Employee' EClass.
     * 
     * @return 'projects' property of 'Employee' EClass
     */
    Set<Project> getProjects();
    
    /**
     * Sets the 'projects' property of the 'Employee' EClass.
     * 
     * @param projects new value for the 'projects' property
     */
    void setProjects(Set<Project> projects);
}
