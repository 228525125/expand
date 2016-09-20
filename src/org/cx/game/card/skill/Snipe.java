package org.cx.game.card.skill;

import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.PassiveSkill;
import org.cx.game.widget.IGround;

/**
 * 狙击，必须与致命一击联动 attack （重新设计）
 * @author chenxian
 *
 */
public class Snipe extends PassiveSkill {

	private Integer step;
	private Integer elevateScale;
	private Integer elevateChance;
	
	public Snipe(Integer step, Integer elevateScale) {
		super();
		// TODO Auto-generated constructor stub
		this.step = step;
		this.elevateScale = elevateScale;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub

		super.affect(objects);
	}

	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		IGround ground = getOwner().getPlayer().getGround();
		List<Integer> list = ground.easyAreaForDistance(getOwner().getContainerPosition(), step, IGround.Contain);
		Boolean bool = false;
		for(Integer position : list){
			LifeCard life = ground.getCard(position);
			if(null!=life&&!getOwner().getPlayer().equals(life.getPlayer())){
				bool = true;
				break;
			}
		}
		
		if(bool){
			affect();
			bool = false;
		}
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getAttack().addIntercepter(this);
	}

}
