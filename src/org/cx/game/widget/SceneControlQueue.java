package org.cx.game.widget;

import org.cx.game.corps.Corps;

public class SceneControlQueue extends AbstractControlQueue<Corps> {
	
	@Override
	public void add(Corps t) {
		// TODO Auto-generated method stub
		Place<Corps> place = new Place<Corps>(t){

			@Override
			void loadSpeed(Corps corps, AbstractControlQueue<Corps>.Place<Corps> place) {
				// TODO Auto-generated method stub
				Integer speed = corps.getActivate().getSpeed();
				place.setSpeed(speed);
			}
			
		};
		place.setCount(t.getSpeed());
		
		insert(place);
	}

}
