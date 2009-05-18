/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.evaluation.parameter;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.openmrs.module.evaluation.EvaluationContext;

/**
 * A Parameter represents a value that may be used to configure an Object at runtime.
 * Examples of classes where Parameters are used are ReportSchema, CohortDefinition, and DataSetDefinition
 * If an object is parameterizable, it is able to accept a parameter like this. 
 * Examples of a parameter would be "What start date do you want to use?"
 * Typically Parameter Values are retrieved in the context of an {@link EvaluationContext}
 * 
 * @see Parameterizable
 * @see EvaluationContext
 */
public class Parameter implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    /**
     * Validation method that validates whether the defaultValue is compatible with the class
     * @throws ParameterException
     */
    public static void validateValueForClass(Object value, Class<?> clazz) throws ParameterException {
		if (value != null &&  clazz != null) {
			if (!clazz.isAssignableFrom(value.getClass())) {
				throw new ParameterException("The class " + value.getClass() + " of " + value + 
											 " is incompatible " + "with the clazz of " + clazz);
			}
		}
    }
	
	//***********************
	// PROPERTIES
	//***********************
	
	/**
	 * The name by which this Parameter can be uniquely retrieve 
	 * in the Context within which it is being used.
	 */
	private String name;
	
	/**
	 * The text displayed to the user if input is needed
	 */
	private String label;
	
	/**
	 * The datatype of this parameter
	 */
	private Class<?> clazz;

	/**
	 * The default value given to this parameter.
	 */
	private Object defaultValue;
	
	/**
	 * Indicates whether this Parameter must be provided a value either
	 * as a defaultValue or from user input.
	 * By default, this is true
	 */
	private boolean required = true;
	
	//***********************
	// CONSTRUCTORS
	//***********************
	
	/**
	 * Default constructor
	 */
	public Parameter() { }
	
	/**
	 * Initialize this Parameter with the given values
	 * 
	 * @param name The defined descriptive name
	 * @param label The label to display to the user if value is needed
	 * @param clazz The data type of this parameter
	 * @param defaultValue The value to fill in if nothing provided by the user
	 * @param required The flag indicating whether a value is required for this parameter
	 */
	public Parameter(String name, String label, Class<?> clazz, Object defaultValue, boolean required) {
		super();
		setName(name);
		setLabel(label);
		setClazz(clazz);
		setDefaultValue(defaultValue);
		setRequired(required);
	}
	
	//***********************
	// INSTANCE METHODS
	//***********************
	
	/**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("Parameter<name="+name+",label="+label);
    	sb.append(",clazz="+ (clazz == null ? "null" : clazz.getName()));
    	sb.append(",defaultValue="+defaultValue+",required=" + required+">");
    	return sb.toString();
    }
    
	/**
	 * @see Object#equals(Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Parameter) {
			Parameter p = (Parameter) obj;
			return StringUtils.equalsIgnoreCase(p.getName(), getName());
		}
		return false;
	}

	/**
	 * @see Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (name == null ? 0 : 31 * name.toUpperCase().hashCode());
	}
    
	//***********************
	// PROPERTY ACCESS
	//***********************

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the clazz
	 */
	public Class<?> getClazz() {
		return clazz;
	}

	/**
	 * @param clazz the clazz to set
	 */
	public void setClazz(Class<?> clazz) {
		validateValueForClass(defaultValue, this.clazz);
		this.clazz = clazz;
	}

	/**
	 * @return the defaultValue
	 */
	public Object getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(Object defaultValue) {
		validateValueForClass(defaultValue, this.clazz);
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the required
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * @param required the required to set
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}
}