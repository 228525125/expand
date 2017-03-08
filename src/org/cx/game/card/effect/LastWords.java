package org.cx.game.card.effect;

import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.PassiveSkill;
import org.cx.game.core.Context;
import org.cx.game.core.IPlayer;
import org.cx.game.widget.IGround;

/**
 * 遗言，子类只需覆盖affect方法就行；
 * @author chenxian
 *
 */
public abstract class LastWords extends PassiveSkill {

	protected final static Integer Stirps_Null = 0;
	
	public LastWords(Integer id) {
		// TODO Auto-generated constructor stub
		super(id);
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getDeath().addIntercepter(this);
	}

	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		if(isTrigger(args))
			affect();
	}
	
	/**
	 * 子类覆盖时，需判断父类返回值
	 * @param args
	 * @return
	 */
	public Boolean isTrigger(Object[] args){
		return super.activation;
	}
	
	/**
	 * 查询遗言目标
	 * @param position 当前随从的位置
	 * @param step 搜索范围
	 * @param self 是否友方
	 * @param hero 是否英雄
	 * @param stirps 种族
	 * @return
	 */
	protected LifeCard queryTarget(Integer position,Integer step, Boolean self, Boolean hero, Integer stirps){
		LifeCard life = null;
		IPlayer player = getOwner().getPlayer();
		IGround ground = getOwner().getPlayer().getGround();
		List<Integer> list =  ground.easyAreaForDistance(position, step, IGround.Contain);
		
		for(Integer p : list){
			LifeCard card = ground.getCard(p);
			
			if(null==card){
				continue;
			}
			
			if(null!=self && !self.equals(card.getPlayer().equals(player))){
				continue;
			}
			
			if(null!=hero && !hero.equals(card.getHero())){
				continue;
			}
			
			if(!Integer.valueOf(0).equals(stirps)){
				List<Integer> tags = Context.queryForObject(card.getId());
				if(!tags.contains(stirps)){
					continue;
				}
			}
			
			life = card;
			break;
		}
		
		return life;
	}

}
