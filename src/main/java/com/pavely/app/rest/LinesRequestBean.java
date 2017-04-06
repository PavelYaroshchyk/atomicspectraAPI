package com.pavely.app.rest;

import java.util.List;

public class LinesRequestBean {
	private List<String> elements;
	private double wlFrom;
	private double wlTo;
	public List<String> getElements() {
		return elements;
	}
	public void setElements(List<String> elements) {
		this.elements = elements;
	}
	public double getWlFrom() {
		return wlFrom;
	}
	public void setWlFrom(double wlFrom) {
		this.wlFrom = wlFrom;
	}
	public double getWlTo() {
		return wlTo;
	}
	public void setWlTo(double wlTo) {
		this.wlTo = wlTo;
	}
}
