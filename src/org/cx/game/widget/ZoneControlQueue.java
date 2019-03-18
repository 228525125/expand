package org.cx.game.widget;

import org.cx.game.core.Player;

public class ZoneControlQueue extends AbstractControlQueue<Player> {
	
	@Override
	public void add(Player t) {
		// TODO Auto-generated method stub
		Place<Player> place = new Place<Player>(t){

			@Override
			void loadSpeed(Player player, AbstractControlQueue<Player>.Place<Player> place) {
				// TODO Auto-generated method stub
				place.setSpeed(100);
			}
			
		};
		place.setCount(0);
		
		insert(place);
	}

}
