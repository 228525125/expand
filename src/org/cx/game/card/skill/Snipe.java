package org.cx.game.card.skill;

import java.util.List;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.Deadly;
import org.cx.game.card.skill.PassiveSkill;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.widget.IGround;

/**
 * 狙击，必须与致命一击联动 attack
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
	
	private Deadly deadly = null;
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		List<IIntercepter> list =  getOwner().getAttack().getIntercepterList().get("attack");
		for(IIntercepter in : list){
			if (in instanceof Deadly) {
				deadly = (Deadly) in;
				Integer chance = deadly.getChance();
				this.elevateChance = 100-chance;
				deadly.setChance(100);
			}
		}
		
		super.affect(objects);
	}

	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		if(null!=deadly){
			Integer chance = deadly.getChance();
			chance -= this.elevateChance;
			deadly.setChance(chance);
			deadly = null;
			this.elevateChance = 0;
		}
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
