package org.cx.game.policy;

import org.cx.game.command.Command;
import org.cx.game.command.CommandFactory;
import org.cx.game.command.Invoker;
import org.cx.game.corps.Corps;
import org.cx.game.exception.ValidatorException;
import org.cx.game.policy.IPolicy;
import org.cx.game.policy.formula.MoveableFormula;
import org.cx.game.policy.formula.NotLockFormula;
import org.cx.game.policy.formula.NotStagnantFormula;
import org.cx.game.widget.GroundFactory;
import org.cx.game.widget.IGround;

/**
 * 回防策略
 * @author chenxian
 *
 */
public class RunbackPolicy extends AbstractPolicy {

	private Integer guardPosition = null;
	private String cmdStr = "";
	
	public void setGuardPoistion(Integer guardPosition){
		this.guardPosition = guardPosition;
	}
	
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		super.calculate();
		
		Corps owner = (Corps) getOwner().getOwner();
		
		IGround ground = GroundFactory.getGround();
		Integer position = ground.getPointByWay(owner.getPosition(), this.guardPosition, owner.getMove().getEnergy(), owner.getMove().getType(), owner.getPlayer());
		
		this.cmdStr = "move ground place"+position+";";
		
		setPri(IPolicy.PRI_Min);
		
		/*
		 * 判断是否被锁定
		 */
		addValidator(new NotLockFormula(owner));
		
		/*
		 * 是否在原点
		 */
		addValidator(new NotStagnantFormula(owner, this.guardPosition));
		
		/*
		 * 是否能移动
		 */
		addValidator(new MoveableFormula(owner));
		
		doValidator();		

		if(hasError()){
			System.out.println(getErrors().getMessage());
		}else{
			//Invoker invoker = new Invoker();
			String cmd = "select ground place"+owner.getPosition()+" corps;";
			try {
				//invoker.receiveCommand(owner.getPlayer(), cmd);
				Command command= CommandFactory.getInstance(owner.getPlayer(),cmd);
				command.execute();
				
				super.command = CommandFactory.getInstance(owner.getPlayer(),this.cmdStr);
				super.command.doValidator();
				if(!super.command.hasError()){
					setPri(IPolicy.PRI_High);
				}
			} catch (ValidatorException e) {
				// TODO Auto-generated catch block
				return;
			}
		}
	}
}
