package com.pavely.app.db;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name="lines")
@FilterDefs({
	@FilterDef(name = "ionisationFilter", parameters = @ParamDef(name = "ionLevel", type = "java.lang.String")),
	@FilterDef(name = "elementFilter", parameters = @ParamDef(name = "elementName", type = "java.lang.String")),
	@FilterDef(name = "wavelengthFromFilter", parameters = @ParamDef(name = "wavelengthFrom", type = "double")),
	@FilterDef(name = "wavelengthToFilter", parameters = @ParamDef(name = "wavelengthTo", type = "double")),
	@FilterDef(name = "listOfElementsFilter", parameters = { @ParamDef(name = "elementNames", type = "java.lang.String") })
})
@Filters({
	@Filter(name = "ionisationFilter", condition = "ion = :ionLevel"),
	@Filter(name = "elementFilter", condition = "element = :elementName"),
	@Filter(name = "wavelengthFromFilter", condition = " obs_wl_air >= :wavelengthFrom"),
	@Filter(name = "wavelengthToFilter", condition = "obs_wl_air <= :wavelengthTo"),
	@Filter(name = "listOfElementsFilter", condition =  "element in (:elementNames)")
})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Line implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public Line(){}
	public Line( 
			/*Elements element,*/ 
			String ion, 
			double wl, 
			String relInt, 
			double aki, 
			String acc,
			double ei,
			double ek,
			int gi,
			int gk,
			String tpRef,
			String lineRef){
		
		/*this.setElement(element);*/
		this.ion = ion;
		this.wl = wl;
		this.relInt = relInt;
		this.aki = aki;
		this.acc = acc;
		this.ei = ei;
		this.ek = ek;
		this.gi = gi;
		this.gk = gk;
		this.tpRef = tpRef;
		this.lineRef = lineRef;
	}

	private Elements element;
	private String elName;
	private String id;
	private String ion;	
	private double wl;
	private String relInt;
	private double aki;
	private String acc;
	private double ei;
	private double ek;
	private int gi;
	private int gk;
	private String tpRef;
	private String lineRef;
	
	
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="id", unique=true, nullable=false)
	public String getId() {
		return id;
	}
	
	@Column(name="element")
	public String getElName() {
		return elName;
	}
	public void setElName(String elName) {
		this.elName = elName;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="ion", nullable=false)
	public String getIon() {
		return ion;
	}

	public void setIon(String ion) {
		this.ion = ion;
	}

	@Column(name="obs_wl_air", nullable=false)
	public double getWl() {
		return wl;
	}

	public void setWl(double wl) {
		this.wl = wl;
	}

	@Column(name="rel_int")
	public String getRelInt() {
		return relInt;
	}

	public void setRelInt(String relInt) {
		this.relInt = relInt;
	}

	@Column(name="aki")
	public double getAki() {
		return aki;
	}

	public void setAki(double aki) {
		this.aki = aki;
	}

	@Column(name="acc")
	public String getAcc() {
		return acc;
	}

	public void setAcc(String acc) {
		this.acc = acc;
	}

	@Column(name="ei")
	public double getEi() {
		return ei;
	}

	public void setEi(double ei) {
		this.ei = ei;
	}

	@Column(name="ek")
	public double getEk() {
		return ek;
	}

	public void setEk(double ek) {
		this.ek = ek;
	}

	@Column(name="gi")
	public int getGi() {
		return gi;
	}

	public void setGi(int gi) {
		this.gi = gi;
	}

	@Column(name="gk")
	public int getGk() {
		return gk;
	}

	public void setGk(int gk) {
		this.gk = gk;
	}

	
	@JsonIgnore
	@Column(name="tp_ref")
	public String getTpRef() {
		return tpRef;
	}

	public void setTpRef(String tpRef) {
		this.tpRef = tpRef;
	}

	@JsonIgnore
	@Column(name="line_ref")
	public String getLineRef() {
		return lineRef;
	}
	
	public void setLineRef(String lineRef) {
		this.lineRef = lineRef;
	}
	
	@JsonIgnore
	@ManyToOne/*(fetch = FetchType.LAZY)*/
	@JoinColumn(name="element", referencedColumnName="name", nullable=false, insertable = false, updatable = false)
	public Elements getElement() {
		return element;
	}

	public void setElement(Elements element) {
		this.element = element;
	}
}
