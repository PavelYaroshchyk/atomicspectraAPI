package com.pavely.app.db;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@Entity
@Table(name="elements")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Elements implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Elements(){}
	public Elements(String atomicName, int atomicNumber, Set<Line> lines){
		this.atomicName = atomicName;
		this.atomicNumber = atomicNumber;
		this.lines = lines;
	}
	
	private String atomicName;
	private Set<Line> lines = new HashSet<Line>(0);
	private int atomicNumber;
	
	@Id
	@Column(name="name", unique=true, nullable=false)
	public String getAtomicName() {
		return atomicName;
	}
	
	public void setAtomicName(String atomicName) {
		this.atomicName = atomicName;
	}
	
	@Column(name="number", unique=true, nullable=false)
	public int getAtomicNumber() {
		return atomicNumber;
	}
	public void setAtomicNumber(int atomicNumber) {
		this.atomicNumber = atomicNumber;
	}
	
	@OneToMany(/*fetch=FetchType.LAZY, */mappedBy = "element") 
	public Set<Line> getLines() {
		return lines;
	}
	public void setLines(Set<Line> lines) {
		this.lines = lines;
	}

}
