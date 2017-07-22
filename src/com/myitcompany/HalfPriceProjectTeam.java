/*
 * Copyright (c) 2017. Raul Pimentel. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

/*
  @author Pimentel
  @date December 2016
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
