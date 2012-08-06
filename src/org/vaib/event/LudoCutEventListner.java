package org.vaib.event;

import java.util.EventListener;

public interface LudoCutEventListner extends EventListener{

	public void cutCoin(LudoCutEvent event);
}
