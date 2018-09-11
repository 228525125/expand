package org.cx.game.widget;

import org.cx.game.core.AbstractPlayer;

public class ZoneControlQueue extends AbstractControlQueue<AbstractPlayer> {
	
	@Override
	public void add(AbstractPlayer t) {
		// TODO Auto-generated method stub
		Place<AbstractPlayer> place = new Place<AbstractPlayer>(t){

			@Override
			void loadSpeed(AbstractPlayer player, AbstractControlQueue<AbstractPlayer>.Place<AbstractPlayer> place) {
				// TODO Auto-generated method stub
				place.setSpeed(100);
			}
			
		};
		place.setCount(0);
		
		insert(place);
	}

}
