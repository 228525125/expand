package org.cx.game.card.skill;

import org.cx.game.card.effect.LastWords;

public class ChoupaiLastWords extends LastWords {

	public final static Integer ChoupaiLastWords_ID = 10200019;
	
	private Integer times = 0;
	
	public ChoupaiLastWords(Integer times) {
		super(ChoupaiLastWords_ID);
		// TODO Auto-generated constructor stub
		
		this.times = times;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		for(int i=0; i<times; i++){
			getOwner().getPlayer().takeCard();
		}
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub

	}

}
