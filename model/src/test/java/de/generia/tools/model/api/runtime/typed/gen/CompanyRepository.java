package de.generia.tools.model.api.runtime.typed.gen;

import de.generia.tools.model.api.runtime.typed.gen.model.Company;
import java.util.Set;

/**
 * The 'CompanyRepository' class was generated from 'CompanyRepository' EClass api element.
 * 
 */
public interface CompanyRepository {
    
    /**
     * The 'findAll' operation of the 'CompanyRepository' EClass.
     * 
     */
    Set<Company> findAll();
    
    /**
     * The 'findByName' operation of the 'CompanyRepository' EClass.
     * 
     */
    Company findByName(String name);
}
