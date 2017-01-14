package org.cx.game.card.effect;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.TauntBuff;
import org.cx.game.card.skill.PassiveSkill;
import org.cx.game.widget.IGround;

/**
 * 嘲讽，这个类只是一个标记，嘲讽的逻辑写在AttackLock、AttackLockBuff、AttackTauntValidator、MoveTauntValidator、AttackCommand、MoveCommand中
 * 1、嘲讽只在召唤时触发，使受影响的随从带上一个嘲讽buff，并且锁定嘲讽随从；
 * 2、当随从攻击具有嘲讽的敌人时，它不受锁定的影响；
 * 3、当身边有具有嘲讽的敌人时，发起的锁定无效，主要是反击时会遇到这种情况；
 * 4、当身边具有嘲讽随从时，只能攻击具有嘲讽的随从；
 * 5、嘲讽效果只能在近身时有效；
 * @author chenxian
 *
 */
public class Taunt extends PassiveSkill {

	private final static Integer Taunt_ID = 10200006;
	
	public Taunt() {
		super(Taunt_ID);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getCall().addIntercepter(this);
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		super.affect(objects);
		
		List<LifeCard> ls = new ArrayList<LifeCard>();
		IGround ground = getOwner().getPlayer().getGround();
		List<Integer> list = ground.easyAreaForDistance(getOwner().getContainerPosition(), getRange(), IGround.Contain);
		for(Integer position : list){
			LifeCard life = ground.getCard(position);
			if(null!=life){
				ls.add(life);
			}
		}
		
		for(LifeCard life : ls)
			new TauntBuff(getOwner(),life);
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

	}

}
