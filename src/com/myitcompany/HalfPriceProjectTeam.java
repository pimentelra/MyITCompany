/**
 * @author Pimentel
 * @date December 2016
 */
package com.myitcompany;

public class HalfPriceProjectTeam extends ProjectTeam {

	public HalfPriceProjectTeam() {
		ProjectTeam.increaseNumberOfProjectTeams();
	}

	@Override
	public int getSalary() {
		return super.getSalary()/2;
	}

}
