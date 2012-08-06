package org.vaib.event;

import java.util.EventObject;

import org.vaib.Coin;

public class LudoCutEvent  extends EventObject {
	
	public LudoCutEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Coin coin;

	public Coin getCoin() {
		return coin;
	}

	public void setCoin(Coin coin) {
		this.coin = coin;
	}
	

	
}
