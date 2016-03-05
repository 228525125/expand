package org.cx.game.card.buff;

import java.util.List;

import org.cx.game.action.IMove;
import org.cx.game.card.LifeCard;
import org.cx.game.card.buff.Buff;
import org.cx.game.card.buff.DizzyBuff;
import org.cx.game.card.buff.IBuff;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;
import org.cx.game.widget.IGround;

/**
 * 协助.攻
 * @author chenxian
 *
 */
public class AssistAttackBuff extends Buff {

	private LifeCard aider;
	
	public AssistAttackBuff(Integer bout, Integer style, Integer type, Integer func,
			LifeCard aider, LifeCard life) {
		super(bout, style, type, func, life);
		// TODO Auto-generated constructor stub
		this.aider = aider;
	}

	@Override
	public void effect() {
		// TODO Auto-generated method stub
		super.effect();
		
		IIntercepter attackIn = new Intercepter(){

			@Override
			public void finish(Object[] args) {
				// TODO Auto-generated method stub
				LifeCard attacked = (LifeCard) ((Object[]) args[0])[0];
				IGround ground = getOwner().getPlayer().getGround();
				Integer distance = ground.distance(aider.getContainerPosition(), attacked.getContainerPosition());
				Integer moveRange = aider.getEnergy()/IMove.Consume;
				if(distance<=moveRange){     //判断攻击目标是否在援助者移动范围内
					List<Integer> list = ground.route(aider.getContainerPosition(), attacked.getContainerPosition());
					Integer movePosition = list.get(list.size()-1);
					aider.setActivate(true);            //激活
					try {
						aider.move(ground.getPlace(movePosition));
						aider.attack(attacked);
					} catch (RuleValidatorException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						aider.setActivate(false);           //结束
					}
				}
			}
		};
		recordIntercepter(getOwner().getAttack(), attackIn);
		
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
		
		/*
		 * 当自己与援助者超出移动范围时
		 */
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
		
		/*
		 * 当援助者死亡的时候
		 */
		IIntercepter deathIn =  new Intercepter(){

			@Override
			public void before(Object[] args) {
				// TODO Auto-generated method stub
				invalid();
			}
		};
		recordIntercepter(aider.getDeath(), deathIn);
		
		/*
		 * 当援助者被击晕的时候
		 */
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
	}
}
