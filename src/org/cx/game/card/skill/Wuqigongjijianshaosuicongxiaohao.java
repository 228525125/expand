package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.widget.Container;
import org.cx.game.widget.IWeapon;

/**
 * 当你的武器每有1点攻击力，该牌的消耗减少1点
 * 
 * @author chenxian
 *
 */
public class Wuqigongjijianshaosuicongxiaohao extends PassiveSkill {

	public final static Integer Wuqigongjijianshaosuicongxiaohao_ID = 1;
	
	public Wuqigongjijianshaosuicongxiaohao() {
		super(Wuqigongjijianshaosuicongxiaohao_ID);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getCall().addIntercepter(this);
	}
	
	@Override
	public String getIntercepterMethod() {
		// TODO Auto-generated method stub
		return "getConsume";
	}
	
	private void updateConsume(){
		Integer consume = getOwner().getConsume();
		Integer consumeDownValue = 0;
		IWeapon weapon = getOwner().getPlayer().getHeroCard().getAttack().getWeapon();
		if(null!=weapon){
			consumeDownValue = -weapon.getAtk();
		}
		consume += consumeDownValue;
		consume = consume < 0 ? 0 : consume;

		getOwner().getCall().setConsume(consume);
	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		updateConsume();
	}

	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		getOwner().getCall().setConsume(getOwner().getConsume());
	}

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub

	}

}
