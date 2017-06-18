package de.generia.tools.model.api.runtime.typed.gen.model;

import de.generia.tools.model.api.runtime.typed.gen.model.Employee;

/**
 * The 'Manager' class was generated from 'Manager' EClass api element.
 * 
 */
public interface Manager extends Employee {
    
    /**
     * Gets the 'staffCar' property of the 'Manager' EClass.
     * 
     * @return 'staffCar' property of 'Manager' EClass
     */
    Boolean isStaffCar();
    
    /**
     * Sets the 'staffCar' property of the 'Manager' EClass.
     * 
     * @param staffCar new value for the 'staffCar' property
     */
    void setStaffCar(Boolean staffCar);
}
