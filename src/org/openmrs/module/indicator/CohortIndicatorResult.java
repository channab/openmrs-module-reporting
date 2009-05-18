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
package org.openmrs.module.indicator;

import java.util.HashMap;
import java.util.Map;

import org.openmrs.Cohort;
import org.openmrs.module.evaluation.EvaluationContext;
import org.openmrs.module.indicator.aggregation.AggregationUtil;
import org.openmrs.module.indicator.aggregation.Aggregator;
import org.openmrs.module.indicator.aggregation.CountAggregator;
import org.openmrs.module.indicator.dimension.CohortDimension;

/**
 * Cohort-based indicator
 */
public class CohortIndicatorResult implements IndicatorResult {
	
    private static final long serialVersionUID = 1L;
    
    //***** PROPERTIES *****

    private CohortIndicator indicator;
    private Map<CohortDimension, String> dimensions;
    private EvaluationContext evaluationContext;
    private Map<Integer, Number> cohortValues;

    //***** CONSTRUCTORS *****
    
    /**
     * Default Constructor
     */
    public CohortIndicatorResult() {
    	super();
    }
    
    //***** INSTANCE METHODS *****
    
    public Number getValue() {
    	Class<? extends Aggregator> aggregator = getIndicator().getAggregator();
    	if (aggregator == null) {
    		aggregator = CountAggregator.class;
    	}
    	return AggregationUtil.aggregate(cohortValues.values(), aggregator);
    }
    
    public void addCohortValue(Integer memberId, Number value) {
    	getCohortValues().put(memberId, value);
    }
    
    public void addCohortDimension(CohortDimension dimension, String option, Cohort cohort) {
    	getDimensions().put(dimension, option);
    	getCohortValues().keySet().retainAll(cohort.getMemberIds());
    }
    
    public Cohort getCohort() {
    	return new Cohort(getCohortValues().keySet());
    }
    
	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		Number value = getValue();
		return (value == null ? "null" : value.toString());
	}
    
    //***** Property Access *****

	/**
	 * @return the indicator
	 */
	public CohortIndicator getIndicator() {
		return indicator;
	}

	/**
	 * @param indicator the indicator to set
	 */
	public void setIndicator(CohortIndicator indicator) {
		this.indicator = indicator;
	}
	
	/**
	 * @return the evaluationContext
	 */
	public EvaluationContext getEvaluationContext() {
		return evaluationContext;
	}

	/**
	 * @param evaluationContext the evaluationContext to set
	 */
	public void setEvaluationContext(EvaluationContext evaluationContext) {
		this.evaluationContext = evaluationContext;
	}

	/**
	 * @return the dimensions
	 */
	public Map<CohortDimension, String> getDimensions() {
		if (dimensions == null) {
			dimensions = new HashMap<CohortDimension, String>();
		}
		return dimensions;
	}

	/**
	 * @param dimensions the dimensions to set
	 */
	public void setDimensions(Map<CohortDimension, String> dimensions) {
		this.dimensions = dimensions;
	}

	/**
	 * @return the cohortValues
	 */
	public Map<Integer, Number> getCohortValues() {
		if (cohortValues == null) {
			cohortValues = new HashMap<Integer, Number>();
		}
		return cohortValues;
	}

	/**
	 * @param cohortValues the cohortValues to set
	 */
	public void setCohortValues(Map<Integer, Number> cohortValues) {
		this.cohortValues = cohortValues;
	}
}