package org.vaib;
//Codeing by bits 
/*
 *  values 0 & 1
 *  
 */
public enum Axcis {
	POSITIVEX(1),
	POSITIVEY(2),
	NEGITIVEX(3),
	NEGITIVEY(4);
	
	private int axcisCode;
	private Axcis(int axcisCode){
		this.axcisCode = axcisCode;
	}
	 public int getAxcisCode(){
		 return axcisCode;
	 }
}
