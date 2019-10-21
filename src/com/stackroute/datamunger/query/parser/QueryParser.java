package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/*There are total 4 DataMungerTest file:
 * 
 * 1)DataMungerTestTask1.java file is for testing following 4 methods
 * a)getBaseQuery()  b)getFileName()  c)getOrderByClause()  d)getGroupByFields()
 * 
 * Once you implement the above 4 methods,run DataMungerTestTask1.java
 * 
 * 2)DataMungerTestTask2.java file is for testing following 2 methods
 * a)getFields() b) getAggregateFunctions()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask2.java
 * 
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getRestrictions()  b)getLogicalOperators()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 * 
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

public class QueryParser {

	private QueryParameter queryParameter = new QueryParameter();

	/*
	 * This method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
	public QueryParameter parseQuery(String queryString) {

		queryParameter.setFileName(getFileName(queryString));
		queryParameter.setBaseQuery(getBaseQuery(queryString));

		/*String[] orderByField = getOrderByFields(queryString);
		if(null == orderByField) {
			queryParameter.setOrderByFields(null);
		}else {
			queryParameter.setOrderByFields(Arrays.asList(orderByField));
		}*/
		queryParameter.setOrderByFields(getOrderByFields(queryString));

		String[] groupByField = getGroupByFields(queryString);
		if(null == groupByField) {
			queryParameter.setGroupByFields(null);
		}else {
			queryParameter.setGroupByFields(Arrays.asList(groupByField));
		}

		String[] fieldsName = getFields(queryString);
		if(null == fieldsName || fieldsName[0].equals("*")) {
			queryParameter.setFields(null);
		}else {
			queryParameter.setFields(Arrays.asList(fieldsName));
		}

		/*String[] conditionArr = getConditions(queryString);
		if(null == conditionArr) {
			queryParameter.setRestrictions(null);
		}else {
			List<String> conditionList = Arrays.asList(conditionArr);
			List<Restriction> restrictionsList = new ArrayList<>();
			// [season > 2014, city ='bangalore']
			for(String condition : conditionList) {
				String[] str = condition.replaceAll("=", "= ").split(" ");
				restrictionsList.add(new Restriction(str[0], str[2], str[1]));
			}
			queryParameter.setRestrictions(restrictionsList);
		}*/
		queryParameter.setRestrictions(getRestriction(queryString));

		/*String[] logicalOperator =  getLogicalOperators(queryString);
		if(null == logicalOperator || logicalOperator.length == 0) {
			queryParameter.setLogicalOperators(null);
		}else {
			queryParameter.setLogicalOperators(getLogicalOperators(queryString));
		}*/
		queryParameter.setLogicalOperators(getLogicalOperators(queryString));

		/*String[] aggregateFunction = getAggregateFunctions(queryString);
		if(null == aggregateFunction || aggregateFunction.length == 0) {
			queryParameter.setAggregateFunctions(null);
		}else {
			List<String> agreegateList = Arrays.asList(aggregateFunction);
			List<AggregateFunction> agreegateFunctionList = new ArrayList<>();
			for(String function : agreegateList) {
				String[] str = function.split(" ");
				agreegateFunctionList.add(new AggregateFunction(str[0], str[1]));
			}
			queryParameter.setAggregateFunctions(agreegateFunctionList);
		}*/
		queryParameter.setAggregateFunctions(getAggregateFunctions(queryString));


		return queryParameter;
	}

	/*
	 * Extract the name of the file from the query. File name can be found after the
	 * "from" clause.
	 */
	public String getFileName(String queryString) {

		String str = queryString.substring(queryString.indexOf("from")+5);
		String[] str1 = str.split(" ");

		return str1[0];
	}

	/*
	 * 
	 * Extract the baseQuery from the query.This method is used to extract the
	 * baseQuery from the query string. BaseQuery contains from the beginning of the
	 * query till the where clause
	 */
	public String getBaseQuery(String queryString) {

		String str1 = queryString.substring(0, queryString.lastIndexOf("from"));
		String[] str2 = queryString.substring(queryString.lastIndexOf("from")).split(" ");

		return str1 + str2[0] + " "+str2[1];
	}

	/*
	 * extract the order by fields from the query string. Please note that we will
	 * need to extract the field(s) after "order by" clause in the query, if at all
	 * the order by clause exists. For eg: select city,winner,team1,team2 from
	 * data/ipl.csv order by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one order by fields.
	 */
	/*public String[] getOrderByFields(String queryString) {

		if(queryString.contains("order by")) {

			queryString = queryString.substring(queryString.indexOf("order by")+9);

			return queryString.split(",");
		}else {
			return null;
		}
	}*/
	public List<String> getOrderByFields(String queryString) {
		List<String> orderByFields = null;
		String[] tempArray;
		if (queryString.contains("order by")) {
			orderByFields = new ArrayList<String>();
			tempArray = queryString.trim().split(" order by ");
			final String[] orderByArray = tempArray[1].trim().split(",");
			if (orderByArray.length == 1) {
				orderByFields.add(orderByArray[0]);
			} else {
				for (int i = 0; i < orderByArray.length; i++) {
					orderByFields.add(orderByArray[i]);
				}
			}
		}
		return orderByFields;

	}
	/*
	 * Extract the group by fields from the query string. Please note that we will
	 * need to extract the field(s) after "group by" clause in the query, if at all
	 * the group by clause exists. For eg: select city,max(win_by_runs) from
	 * data/ipl.csv group by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one group by fields.
	 */
	public String[] getGroupByFields(String queryString) {

		if(queryString.contains("group by")) {


			String str = queryString.toLowerCase().substring(queryString.indexOf("group by"));
			String[] str1 = str.split(" group by | having | order by ");
			str = str1[0].replace("group by ", "");
			return str.split(",");
		}
		return null;
	}
	/*
	 * Extract the selected fields from the query string. Please note that we will
	 * need to extract the field(s) after "select" clause followed by a space from
	 * the query string. For eg: select city,win_by_runs from data/ipl.csv from the
	 * query mentioned above, we need to extract "city" and "win_by_runs". Please
	 * note that we might have a field containing name "from_date" or "from_hrs".
	 * Hence, consider this while parsing.
	 */
	public String[] getFields(String queryString) {

		String[] str = queryString.split(" ");

		return str[1].split(",");
	}
	/*
	 * Extract the conditions from the query string(if exists). for each condition,
	 * we need to capture the following: 1. Name of field 2. condition 3. value
	 * 
	 * For eg: select city,winner,team1,team2,player_of_match from data/ipl.csv
	 * where season >= 2008 or toss_decision != bat
	 * 
	 * here, for the first condition, "season>=2008" we need to capture: 1. Name of
	 * field: season 2. condition: >= 3. value: 2008
	 * 
	 * the query might contain multiple conditions separated by OR/AND operators.
	 * Please consider this while parsing the conditions.
	 * 
	 */
	public List<Restriction> getRestriction(String queryString){

		String[] conditionString = getConditions(queryString);

		if(null == conditionString) {
			return null;
		}
		else {
			List<String> list = Arrays.asList(conditionString);
			List<Restriction> restriction = new ArrayList<Restriction>();
			for (String condition : conditionString) {
				String[] str = condition.replaceAll("=", "= ").split(" ");
				restriction.add(new Restriction(str[0], str[1], str[2]));
			}
			return restriction;

		}
	}
	public String[] getConditions(String queryString) {

		queryString = queryString.toLowerCase();
		if(queryString.contains(" where ")) {

			if(queryString.contains("order by")) {
				queryString = queryString.substring(0, queryString.indexOf(" order by"));
			}
			if(queryString.contains(" having ")) {
				queryString = queryString.substring(0, queryString.indexOf(" having "));
			}
			if(queryString.contains("group by")) {
				queryString = queryString.substring(0, queryString.indexOf(" group by"));
			}
			String str = queryString.substring(queryString.indexOf(" where ")+7);


			String[] str1 = str.split(" and | or ");

			return str1;

		}else {
			return null;
		}

	}

	/*
	 * Extract the logical operators(AND/OR) from the query, if at all it is
	 * present. For eg: select city,winner,team1,team2,player_of_match from
	 * data/ipl.csv where season >= 2008 or toss_decision != bat and city =
	 * bangalore
	 * 
	 * The query mentioned above in the example should return a List of Strings
	 * containing [or,and]
	 */
	/*public String[] getLogicalOperators(String queryString) {

		if(!queryString.toLowerCase().contains("or") && !queryString.toLowerCase().contains("and")) {
			return null;
		}
		String[] returnStr = new String[2];
		int i = 0;

		if(queryString.toLowerCase().contains("and ")) {
			returnStr[i] = "and";
		}
		if(queryString.toLowerCase().contains("or ")) {
			i++;
			returnStr[i] = "or";
		}
		return Arrays.stream(returnStr).filter(p -> null!= p).toArray(String[] :: new);
	}*/
	public List<String> getLogicalOperators(String queryString) {
		List<String> logical = null;
		final String[] query = queryString.split(" ");
		String getLogic = "";
		String[] logicTemp;
		if (queryString.contains("where ")) {
			logical = new ArrayList<String>();
			for (int i = 0; i < query.length; i++) {
				if (query[i].matches("and|or|not")) {

					getLogic += query[i] + " ";
				}
			}
			logicTemp = getLogic.toString().split(" ");
			for (int i = 0; i < logicTemp.length; i++) {
				logical.add(logicTemp[i]);
			}
		}

		return logical;

	}


	/*
	 * Extract the aggregate functions from the query. The presence of the aggregate
	 * functions can determined if we have either "min" or "max" or "sum" or "count"
	 * or "avg" followed by opening braces"(" after "select" clause in the query
	 * string. in case it is present, then we will have to extract the same. For
	 * each aggregate functions, we need to know the following: 1. type of aggregate
	 * function(min/max/count/sum/avg) 2. field on which the aggregate function is
	 * being applied.
	 * 
	 * Please note that more than one aggregate function can be present in a query.
	 * 
	 * 
	 */
	/*public String[] getAggregateFunctions(String queryString) {

		queryString = queryString.substring(queryString.indexOf("select ")+7, queryString.indexOf(" from ")).trim().replace("*", "");
		if(queryString.length() == 0) {
			return null;
		}
		String[] str = queryString.split(",");
		String[] str1 = new String[str.length];
		int i = 0;

		for(String s : str) {
			if(s.contains("count") || s.contains("sum") || s.contains("min") || s.contains("max") || s.contains("avg")) {
				str1[i] = s;
				i++;
			}
		}
		return Arrays.stream(str1).filter(p -> null!= p).toArray(String[] :: new);
	}*/
	public List<AggregateFunction> getAggregateFunctions(String queryString) {
		// queryString = queryString.toLowerCase();
		final List<AggregateFunction> aggregate = new ArrayList<AggregateFunction>();
		// boolean state = false;
		// String getAggregate = "";
		final int selectIndex = queryString.toLowerCase(Locale.US).indexOf("select");
		final int fromIndex = queryString.toLowerCase(Locale.US).indexOf(" from");
		final String query = queryString.toLowerCase(Locale.US).substring(selectIndex + 7, fromIndex);
		String[] aggQuery = null;
		aggQuery = query.split(",");
		for (int i = 0; i < aggQuery.length; i++) {
			if (aggQuery[i].startsWith("max(") && aggQuery[i].endsWith(")")
					|| aggQuery[i].startsWith("min(") && aggQuery[i].endsWith(")")
					|| aggQuery[i].startsWith("avg(") && aggQuery[i].endsWith(")")
					|| aggQuery[i].startsWith("sum") && aggQuery[i].endsWith(")")) {
				aggregate.add(new AggregateFunction(aggQuery[i].substring(4, aggQuery[i].length() - 1),
						aggQuery[i].substring(0, 3)));
				// getAggregate += aggQuery[i] + " ";
				// state = true;
			} else if (aggQuery[i].startsWith("count(") && aggQuery[i].endsWith(")")) {
				aggregate.add(new AggregateFunction(aggQuery[i].substring(6, aggQuery[i].length() - 1),
						aggQuery[i].substring(0, 5)));
				// } else {
				// Aggregate = null;
				// }
			}

		}
		return aggregate;

	}
}