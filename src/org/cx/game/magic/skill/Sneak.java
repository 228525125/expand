package org.cx.game.magic.skill;

import org.cx.game.corps.Corps;
import org.cx.game.magic.buff.SneakBuff;

public class Sneak extends ActiveSkill {

	public static final Integer Sneak_ID = 10200002;
	
	public Sneak(Integer type) {
		super(type);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getRange() {
		// TODO Auto-generated method stub
		return "0-0";
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		Corps corps = getOwner();
		corps.getMove().changeHide(true);
		
		new SneakBuff(10, corps).effect();
	}
	
	@Override
	public Corps getOwner() {
		// TODO Auto-generated method stub
		return (Corps) super.getOwner();
	}
	
	@Override
	public void setUseItOnYouself(Boolean useItOnYouself) {
		// TODO Auto-generated method stub
		super.setUseItOnYouself(useItOnYouself);
	}
	
	@Override
	public void setUseItOnFriendOrFoeOrAll(Integer useItOnFriendOrFoeOrAll) {
		// TODO Auto-generated method stub
		super.setUseItOnFriendOrFoeOrAll(useItOnFriendOrFoeOrAll);
	}

}
