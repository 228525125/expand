package org.cx.game.card.skill;

import org.cx.game.card.LifeCard;
import org.cx.game.card.skill.PassiveSkill;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;
import org.cx.game.widget.IPlace;

/**
 * 冲刺，按移动步数增加攻击力，仅本回合
 * @author chenxian
 *
 */
public class Sprint extends PassiveSkill {

	private Integer upAtkScale;   //攻击提升比例
	
	private Integer begin = null;
	private Integer end = null;
	
	private IIntercepter done = null;
	
	/**
	 * 
	 * @param style
	 * @param type
	 * @param upAtkScale 攻击提升比例
	 * @param life
	 */
	public Sprint(Integer atkScale) {
		super();
		// TODO Auto-generated constructor stub
		this.upAtkScale = atkScale;
		
		this.done = new Intercepter("done"){

			@Override
			public void finish(Object[] args) {
				// TODO Auto-generated method stub
				getOwner().getAttack().addToAtk(-upAtkValue);
				upAtkValue = 0;
			}
		};
	}
	
	private Boolean once = true;
	
	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		super.before(args);
		
		this.begin = getOwner().getContainerPosition();
		this.end = ((IPlace)((Object[]) args[0])[0]).getPosition();
		
		if(once){
			getOwner().getPlayer().getContext().addIntercepter(done);
			once = false;
		}
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		super.after(args);
		
		affect();
	}
	
	private Integer upAtkValue = 0;
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		Integer step = getOwner().getPlayer().getGround().distance(begin, end);
		this.upAtkValue = step*Sprint.this.upAtkScale*Sprint.this.getOwner().getAtk()/100;
		getOwner().getAttack().addToAtk(upAtkValue);
		
		super.affect(objects);
	}
	
	@Override
	public void setOwner(LifeCard life) {
		// TODO Auto-generated method stub
		super.setOwner(life);
		
		life.getMove().addIntercepter(this);
	}
}
