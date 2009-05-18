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
package org.openmrs.module.dataset.definition;

import java.util.List;
import java.util.Vector;

import org.openmrs.Cohort;
import org.openmrs.module.cohort.definition.CohortDefinition;
import org.openmrs.module.dataset.column.DataSetColumn;
import org.openmrs.module.dataset.column.SimpleDataSetColumn;
import org.openmrs.module.evaluation.parameter.Mapped;

/**
 * Metadata that defines a CohortCrossTabDataSet. (i.e. a table of cohorts, each of which 
 * is the cross product between a "row" Cohort and a "column" Cohort).
 * 
 * @see CohortDataSet
 * @see CohortCrossTabDataSetProvider
 */
public class CohortCrossTabDataSetDefinition extends BaseDataSetDefinition {
	
	private static final long serialVersionUID = -658417752199413012L;

	private String rowColumnDelimiter = ".";
	private Mapped<CohortDataSetDefinition> rowCohortDataSetDefinition;
	private Mapped<CohortDataSetDefinition> columnCohortDataSetDefinition;
	
	/**
	 * Default constructor
	 */
	public CohortCrossTabDataSetDefinition() {
		rowCohortDataSetDefinition = new Mapped<CohortDataSetDefinition>();
		columnCohortDataSetDefinition = new Mapped<CohortDataSetDefinition>();
	}

	/**
	 * Add the given cohortDefinition as a "row" to this definition with the given key. 
	 * The name is also added as the description.
	 * 
	 * @param name key to refer by which to refer to this cohort
	 * @param cohortDefinition The cohortDefinition for this column
	 */
	public void addRowDefinition(String name, CohortDefinition cohortDefinition, String mappings) {
		rowCohortDataSetDefinition.getParameterizable().addStrategy(name, cohortDefinition, mappings);
	}	
	
	/**
	 * Add the given cohortDefinition as a "row" to this definition with the given key. 
	 * The name is also added as the description.
	 * 
	 * @param name key to refer by which to refer to this cohort
	 * @param cohortDefinition The cohortDefinition for this column
	 */
	public void addRowDefinition(String name, Mapped<CohortDefinition> cohortDefinition) {
		rowCohortDataSetDefinition.getParameterizable().addStrategy(name, cohortDefinition);
	}
	
	/**
	 * Add the given cohortDefinition as a "column" to this definition with the given key. 
	 * The name is also added as the description.
	 * 
	 * @param name key to refer by which to refer to this cohort
	 * @param cohortDefinition The cohortDefinition for this column
	 */
	public void addColumnDefinition(String name, Mapped<CohortDefinition> cohortDefinition) {
		columnCohortDataSetDefinition.getParameterizable().addStrategy(name, cohortDefinition);
	}
	
	/**
	 * Add the given cohortDefinition as a "column" to this definition with the given key. 
	 * The name is also added as the description.
	 * 
	 * @param name key to refer by which to refer to this cohort
	 * @param cohortDefinition The cohortDefinition for this column
	 */
	public void addColumnDefinition(String name, CohortDefinition cohortDefinition, String mappings) {
		columnCohortDataSetDefinition.getParameterizable().addStrategy(name, cohortDefinition, mappings);
	}
	
	/**
	 * @see org.openmrs.module.datasetDefinition#getColumns()
	 */
	public List<DataSetColumn> getColumns() {
		List<DataSetColumn> cols = new Vector<DataSetColumn>();
		for (DataSetColumn rowColumn : rowCohortDataSetDefinition.getParameterizable().getColumns()) {
			for (DataSetColumn colColumn : columnCohortDataSetDefinition.getParameterizable().getColumns()) {
				String key = rowColumn.getKey() + rowColumnDelimiter + colColumn.getKey();
				String desc = rowColumn.getDescription() + rowColumnDelimiter + colColumn.getDescription();
				cols.add(new SimpleDataSetColumn(key, desc, Cohort.class));
			}
		}
		return cols;
	}

    /**
	 * @return the rowCohortDataSetDefinition
	 */
	public Mapped<CohortDataSetDefinition> getRowCohortDataSetDefinition() {
		return rowCohortDataSetDefinition;
	}

	
	/**
	 * @param rowCohortDataSetDefinition the rowCohortDataSetDefinition to set
	 */
	public void setRowCohortDataSetDefinition(CohortDataSetDefinition definition, String mappings) {
		this.rowCohortDataSetDefinition = new Mapped<CohortDataSetDefinition>(definition, mappings);
	}
	
	/**
	 * @param rowCohortDataSetDefinition the rowCohortDataSetDefinition to set
	 */
	public void setRowCohortDataSetDefinition(Mapped<CohortDataSetDefinition> rowCohortDataSetDefinition) {
		this.rowCohortDataSetDefinition = rowCohortDataSetDefinition;
	}

	/**
	 * @return the columnCohortDataSetDefinition
	 */
	public Mapped<CohortDataSetDefinition> getColumnCohortDataSetDefinition() {
		return columnCohortDataSetDefinition;
	}

	/**
	 * @param columnCohortDataSetDefinition the columnCohortDataSetDefinition to set
	 */
	public void setColumnCohortDataSetDefinition(Mapped<CohortDataSetDefinition> columnCohortDataSetDefinition) {
		this.columnCohortDataSetDefinition = columnCohortDataSetDefinition;
	}

	/**
	 * @param columnCohortDataSetDefinition the columnCohortDataSetDefinition to set
	 */
	public void setColumnCohortDataSetDefinition(CohortDataSetDefinition columnCohortDataSetDefinition, String mappings) {
		this.columnCohortDataSetDefinition = new Mapped<CohortDataSetDefinition>(columnCohortDataSetDefinition, mappings);
	}	
	
	/**
     * @return the rowColumnDelimiter
     */
    public String getRowColumnDelimiter() {
    	return rowColumnDelimiter;
    }

    /**
     * @param rowColumnDelimiter the rowColumnDelimiter to set
     */
    public void setRowColumnDelimiter(String rowColumnDelimiter) {
    	this.rowColumnDelimiter = rowColumnDelimiter;
    }

}