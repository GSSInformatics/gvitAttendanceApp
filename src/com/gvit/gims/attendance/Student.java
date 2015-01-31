/**
 * 
 */
package com.gvit.gims.attendance;

import java.io.Serializable;

/**
 * @author Ajaykumar Vasireddy
 * @version 0.1
 * @since 2014
 */
public class Student implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4786609301145802519L;
	String firstName;
	String regno;

	public Student(String fName,String regno) {
		this.firstName = fName;
		this.regno = regno;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getRegno() {
		return regno;
	}

	@Override
	public String toString() {
		return new StringBuilder(getRegno()).append(" ")
				.append(getFirstName()).append(" ")
				.toString();
	}
}