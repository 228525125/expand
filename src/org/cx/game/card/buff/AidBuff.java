package org.cx.game.card.buff;

import org.cx.game.action.IMove;
import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.Buff;
import org.cx.game.card.buff.DizzyBuff;
import org.cx.game.card.buff.IBuff;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;
import org.cx.game.widget.IGround;

public class AidBuff extends Buff {

	private LifeCard aider = null;
	
	public AidBuff(Integer bout, Integer style, Integer type, Integer func, LifeCard aider, LifeCard life) {
		super(bout, style, type, func, life);
		// TODO Auto-generated constructor stub
		this.aider = aider;
	}
	
	@Override
	public void affect(Object... objects) {
		// TODO Auto-generated method stub
		LifeCard attack = (LifeCard) ((Object[]) objects[0])[0];
		
		try {
			Integer attackBackChance = aider.getAttacked().getAttackBackChance();
			aider.getAttacked().setAttackBackChance(0);      //援助者不应该反击，而应该是被援助者反击
			aider.attacked(attack);
			aider.getAttacked().setAttackBackChance(attackBackChance);
		} catch (RuleValidatorException e) {
			// TODO Auto-generated catch block
			//不会发生异常
			e.printStackTrace();
		}
		
		super.affect(objects);
	}

	@Override
	public void effect() {
		// TODO Auto-generated method stub
		IIntercepter attackedIn = new Intercepter(){

			@Override
			public void finish(Object[] args) {
				// TODO Auto-generated method stub
				invalid();
			}

			@Override
			public void before(Object[] args) {
				affect(args);
			}

			@Override
			public Boolean isInvoke() {
				// TODO Auto-generated method stub
				return false;
			}
		};
		recordIntercepter(getOwner().getAttacked(), attackedIn);
		
		/*
		 * 当离目标超出行动范围时，效果取消
		 */
		IIntercepter moveIn1 =  new Intercepter(){

			@Override
			public void finish(Object[] args) {
				// TODO Auto-generated method stub
				IGround ground = getOwner().getPlayer().getGround();
				Integer distance = ground.distance(aider.getContainerPosition(), getOwner().getContainerPosition());
				Integer moveRange = aider.getEnergy()/IMove.Consume;
				if(distance>moveRange){
					invalid();
				}
			}
		};
		recordIntercepter(this.aider.getMove(), moveIn1);		
		
		IIntercepter moveIn2 =  new Intercepter(){

			@Override
			public void finish(Object[] args) {
				// TODO Auto-generated method stub
				IGround ground = getOwner().getPlayer().getGround();
				Integer distance = ground.distance(aider.getContainerPosition(), getOwner().getContainerPosition());
				Integer moveRange = aider.getEnergy()/IMove.Consume;
				if(distance>moveRange){
					invalid();
				}
			}
		};
		recordIntercepter(getOwner().getMove(), moveIn2);
		
		IIntercepter deathIn =  new Intercepter(){

			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				invalid();
			}
		};
		recordIntercepter(aider.getDeath(), deathIn);
		
		IIntercepter dizzyIn = new Intercepter("addBuff"){

			@Override
			public void finish(Object[] args) {
				// TODO Auto-generated method stub
				IBuff buff = (IBuff) args[0];
				if (buff instanceof DizzyBuff) {
					invalid();
				}
			}
		};
		recordIntercepter(aider, dizzyIn);
		
		super.effect();
	}
}
