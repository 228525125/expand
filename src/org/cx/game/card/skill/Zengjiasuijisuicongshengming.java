package org.cx.game.card.skill;

import java.util.Collections;
import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.widget.IGround;

/**
 * 在你的回合结束时，使另一个随机友方随从获得生命值
 * @author chenxian
 *
 */
public class Zengjiasuijisuicongshengming extends PassiveSkill {

	public final static Integer Zengjiasuijisuicongshengming_ID = 10100006;
	
	private Integer hpUpValue = 0;
	
	public Zengjiasuijisuicongshengming(Integer hpUpValue) {
		super(Zengjiasuijisuicongshengming_ID);
		// TODO Auto-generated constructor stub
		
		this.hpUpValue = hpUpValue;
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		getOwner().getActivate().addIntercepter(this);
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		IGround ground = getOwner().getPlayer().getGround();
		List<LifeCard> listLife = ground.list(getOwner().getContainerPosition(), Aureole.Default_Effect_Range, IGround.Contain);
		if(!listLife.isEmpty()){
			Collections.shuffle(listLife);
			LifeCard life = listLife.get(0);
			life.getDeath().addToHp(hpUpValue);
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

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		Boolean activate = (Boolean) ((Object[]) args[0])[0];
		if(!activate){
			affect();
		}
	}

}
