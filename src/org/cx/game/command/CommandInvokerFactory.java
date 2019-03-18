package org.cx.game.command;

import org.cx.game.core.AbstractHost;
import org.cx.game.core.HostManager;
import org.cx.game.core.Player;

public class CommandInvokerFactory {

	public static IInvoker getInstance(String playNo, Integer troop) {
		AbstractHost host = HostManager.getHost(playNo);
		Player player = host.getPlayer(troop);
		IInvoker invoker = new WithCacheInvoker(player.getCommandBuffer());
		return invoker;
	}
	
	public static IInvoker getInstance() {
		IInvoker invoker = new SimpleInvoker();
		return invoker;
	}
}
