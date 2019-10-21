package com.stackroute.datamunger.query.parser;

/*
 * This class is used for storing name of field, condition and value for 
 * each conditions
 * generate getter and setter for this class,
 * Also override toString method
 * */

public class Restriction {

	private static String name;
	private static String condition;
	private static String value;

	public Restriction() {

	}
	public Restriction(String name, String value, String condition) {
		this.name = name;
		this.value = value;
		this.condition = condition;
	}
	public static String getName() {
		return name;
	}

	public static void setName(String name) {
		Restriction.name = name;
	}

	public static String getCondition() {
		return condition;
	}

	public static void setCondition(String condition) {
		Restriction.condition = condition;
	}

	public static String getValue() {
		return value;
	}

	public static void setValue(String value) {
		Restriction.value = value;
	}

	public String toString() {
		return this.name+" "+this.value+" "+this.condition;
	}
}