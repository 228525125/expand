package org.cx.game.card.skill;

import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.core.Context;
import org.cx.game.widget.IGround;

/**
 * 每当你召唤一种生物，便获得攻击力
 * @author chenxian
 *
 */
public class Zhaohuanzengjiagongji extends PassiveSkill {

	public final static Integer Zhaohuanzengjiagongji_ID = 10200014;
	
	private Integer atkUpValue = 0;
	private Integer atkUpTotal = 0;
	private Integer stirps = 0;
	private Integer range = 0;
	
	public Zhaohuanzengjiagongji(Integer atkUpValue, Integer stirps, Integer range) {
		super(Zhaohuanzengjiagongji_ID);
		// TODO Auto-generated constructor stub
		this.atkUpValue = atkUpValue;
		this.stirps = stirps;
		this.range = range;
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getPlayer().getGround().addIntercepter(this);		
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		getOwner().getAttack().addToAtk(this.atkUpValue);
		this.atkUpTotal += this.atkUpValue;
	}
	
	public Boolean isTrigger(Object[] args){
		Boolean ret = false;
		
		if(super.activation){
			Integer position = (Integer) args[0];
			LifeCard life = (LifeCard) args[1];
			
			IGround ground = getOwner().getPlayer().getGround();
			if(this.range>=ground.easyDistance(position, getOwner().getContainerPosition())){
				List<Integer> tags = Context.queryForObject(getOwner().getId());
				
				if(getOwner().getPlayer().equals(life.getPlayer()) && tags.contains(this.stirps)){
					ret = true;
				}
			}
		}

		return ret;
	}
	
	@Override
	public void invalid() {
		// TODO Auto-generated method stub
		super.invalid();
		
		getOwner().getAttack().addToAtk(-this.atkUpTotal);
		this.atkUpTotal = 0;
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		if(isTrigger(args))
			affect();
	}
	
	@Override
	public String getIntercepterMethod() {
		// TODO Auto-generated method stub
		return "add";
	}

}
