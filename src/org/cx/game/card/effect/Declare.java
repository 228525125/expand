package org.cx.game.card.effect;

import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.PassiveSkill;
import org.cx.game.core.IPlayer;
import org.cx.game.widget.IGround;

/**
 * 宣言，子类将宣言效果写在affect方法里面，将触发条件写在getTrigger方法里面；
 * @author chenxian
 *
 */
public abstract class Declare extends PassiveSkill {
	
	public abstract Boolean isTrigger(Object[] args);
	
	public Declare(Integer id) {
		// TODO Auto-generated constructor stub
		super(id);
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getCall().addIntercepter(this);
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		if(isTrigger(args))
			affect(args);
	}
	
	/**
	 * 查询战吼目标
	 * @param position 当前随从的位置
	 * @param step 搜索范围
	 * @return
	 */
	protected LifeCard queryTarget(Integer position,Integer step){
		LifeCard life = null;
		IPlayer player = getOwner().getPlayer();
		IGround ground = getOwner().getPlayer().getGround();
		List<Integer> list =  ground.easyAreaForDistance(position, step, IGround.Contain);
		
		for(Integer p : list){
			LifeCard card = ground.getCard(p);
			if(null!=card && card.getPlayer().equals(player)){
				life = card;
				break;
			}
		}
		
		return life;
	}
}
