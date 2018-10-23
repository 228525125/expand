package org.cx.game.command;

import org.cx.game.core.AbstractHost;
import org.cx.game.core.AbstractPlayer;
import org.cx.game.core.Player;
import org.cx.game.exception.ValidatorException;

public class StartCommand extends InteriorCommand {

	private Player player = null;
	
	public StartCommand(AbstractPlayer player) {
		// TODO Auto-generated constructor stub
		super(player);
		
		this.player = (Player) player;
	}
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		AbstractHost host = this.player.getHost();
		host.start(player);
	}
}
