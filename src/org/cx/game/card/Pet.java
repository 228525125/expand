package org.cx.game.card;

import java.util.List;

import org.cx.game.action.IMove;
import org.cx.game.action.PetMove;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.Intercepter;
import org.cx.game.widget.IGround;

/**
 * 宠物
 * @author chenxian
 *
 */
public class Pet extends LifeCard {

	private LifeCard owner;
	
	public Pet(Integer id, final LifeCard owner) {
		super(id);
		// TODO Auto-generated constructor stub
		this.owner = owner;
		
		final IIntercepter moveIn = new Intercepter(){
			@Override
			public void finish(Object[] args) {
				// TODO Auto-generated method stub
				IGround ground = getOwner().getPlayer().getGround(); 
				Integer distance = ground.easyDistance(owner.getContainerPosition(), getContainerPosition());
				if(PetMove.Move_Range<distance){
					homing();
				}
			}
		};
		this.owner.getMove().addIntercepter(moveIn);
		getMove().addIntercepter(moveIn);
		
		final IIntercepter deathIn = new Intercepter(){
			
			@Override
			public void finish(Object[] args) {
				// TODO Auto-generated method stub
				try {
					death();
				} catch (RuleValidatorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public Integer getLevel() {
				// TODO Auto-generated method stub
				return IIntercepter.Level_Rule;
			}
		};
		owner.addIntercepter(deathIn);
		
		getDeath().addIntercepter(new Intercepter(){
			
			@Override
			public void finish(Object[] args) {
				// TODO Auto-generated method stub
				owner.deleteIntercepter(deathIn);
				owner.getMove().deleteIntercepter(moveIn);
				getMove().deleteIntercepter(moveIn);
			}
		});
	}
	
	public LifeCard getOwner() {
		return owner;
	}

	public void setOwner(LifeCard owner) {
		this.owner = owner;
	}

	public void homing(){
		PetMove move = (PetMove) getMove();
		move.homing(owner);
	}
}
