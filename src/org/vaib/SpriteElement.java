package org.vaib;

import java.util.Collections;
import java.util.List;


public class SpriteElement {
	
	private int acess;
	private Cordenate cordinate;
	private List<Coin> coins;
	
	public SpriteElement( int acess , Cordenate cordinate){
		this.acess = acess;
		this.cordinate = cordinate;
		this.coins = null;
	}

	public List<Coin> getCoins() {
		return coins;
	}

	public void setCoins(List<Coin> coins) {
		this.coins = coins;
	}
	public void addCoin(Coin coin){
		this.coins.add(coin);
	}
	public String toString(){
		StringBuilder builder = new StringBuilder("SpriteElement : ")
		.append("acess : ").append(acess).append(" cordinate : ")
		.append(cordinate).append(" coins : ").append(coins);
		
		return builder.toString();
	}

	public int getAcess() {
		return acess;
	}

	public Cordenate getCordinate() {
		return cordinate;
	}
	
}
