package com.stackroute.datamunger.query.parser;

import java.util.List;

/* 
 * This class will contain the elements of the parsed Query String such as conditions,
 * logical operators,aggregate functions, file name, fields group by fields, order by
 * fields, Query Type
 * */

public class QueryParameter {

	private String fileName;
	private String baseQuery;
	private List<Restriction> restrictions;
	private List<String> logicalOperators;
	private List<String> fields;
	private List<AggregateFunction> aggregateFunctions;
	private List<String> groupByFields;
	private List<String> orderByFields;

	public QueryParameter() {

	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setBaseQuery(String baseQuery) {
		this.baseQuery = baseQuery;
	}

	public String getBaseQuery() {
		return baseQuery;
	}

	public void setRestrictions(List<Restriction> restrictions) {
		this.restrictions = restrictions;
	}

	public List<Restriction> getRestrictions() {
		return restrictions;
	}

	public void setLogicalOperators(List<String> logicalOperators) {
		this.logicalOperators = logicalOperators;
	}

	public List<String> getLogicalOperators() {
		return logicalOperators;
	}

	public void setFields(List<String> fields) {
		this.fields = fields;
	}

	public List<String> getFields() {
		return fields;
	}
	public void setAggregateFunctions(List<AggregateFunction> aggregateFunctions) {
		this.aggregateFunctions = aggregateFunctions;
	}

	public List<AggregateFunction> getAggregateFunctions() {
		return aggregateFunctions;
	}

	public List<String> getGroupByFields() {
		return groupByFields;
	}

	public void setGroupByFields(List<String> groupByFields) {
		this.groupByFields = groupByFields;
	}

	public void setOrderByFields(List<String> orderByFields) {
		this.orderByFields = orderByFields;
	}

	public List<String> getOrderByFields() {
		return orderByFields;
	}

	@Override
	public String toString() {
		return "QueryParameter [fileName=" + fileName + ", baseQuery=" + baseQuery + ", restrictions=" + restrictions
				+ ", logicalOperators=" + logicalOperators + ", fields=" + fields + ", aggregateFunctions="
				+ aggregateFunctions + ", groupByFields=" + groupByFields + "]";
	}

}