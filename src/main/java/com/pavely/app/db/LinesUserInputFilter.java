package com.pavely.app.db;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class LinesUserInputFilter {
	private List<String> allowedElements = Arrays.asList("H", "He", "Li", "Be", 
			"B", "C", "N", "O", "F", "Ne", "Na", "Mg", "Al", "Si", "P", "S", "Cl", 
			"Ar", "K", "Ca", "Sc", "Ti", "V", "Cr", "Mn", "Fe", "Co", "Ni", "Cu", "Zn", "Ga",
			"Ge", "As", "Se", "Br", "Kr", "Rb", "Sr", "Y", "Zr", "Nb", "Mo", "Tc", "Rb", "Rh",
			"Pd", "Ag", "Cd", "In", "Sn", "Sb", "Te", "I", "Xe");

	public List<String> getAllowedElements() {
		return allowedElements;
	}

	public void setAllowedElements(List<String> allowedElements) {
		this.allowedElements = allowedElements;
	}

}
