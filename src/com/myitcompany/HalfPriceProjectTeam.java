/*
 * Copyright (c) 2017. Raul Pimentel.
 */

package com.myitcompany;

class HalfPriceProjectTeam extends ProjectTeam {

	HalfPriceProjectTeam() {
		ProjectTeam.increaseNumberOfProjectTeams();
	}

	@Override
	int getSalary() {
		return super.getSalary()/2;
	}

}
