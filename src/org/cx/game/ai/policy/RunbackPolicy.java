package org.cx.game.ai.policy;

import org.cx.game.ai.CorpsAgent;
import org.cx.game.ai.policy.formula.MoveableFormula;
import org.cx.game.ai.policy.formula.NotLockFormula;
import org.cx.game.ai.policy.formula.NotStagnantFormula;
import org.cx.game.command.Command;
import org.cx.game.command.CommandFactory;
import org.cx.game.corps.Corps;
import org.cx.game.exception.ValidatorException;
import org.cx.game.widget.IGround;

/**
 * 回到起始位置的策略
 * @author chenxian
 *
 */
public class RunbackPolicy extends AbstractPolicy<CorpsAgent> {

	private String cmdStr = "";
	
	private static RunbackPolicy policy = null;
	
	private RunbackPolicy() {
		// TODO Auto-generated constructor stub
	}
	
	public static RunbackPolicy getInstance(){
		if(null==policy)
			policy = new RunbackPolicy();
		
		return policy;
	}
	
	@Override
	public void calculate() {
		// TODO Auto-generated method stub
		super.calculate();
		
		Integer guardPosition = getAgent().getGuardPosition();
		Corps corps = getAgent().getOwner();
		
		IGround ground = corps.getGround();
		Integer position = ground.getPointByWay(corps.getPosition(), guardPosition, corps.getMove().getEnergy(), corps.getMove().getType(), corps.getPlayer());
		
		this.cmdStr = "move ground place"+position+";";
		
		setPri(AbstractPolicy.PRI_Min);
		
		/*
		 * 判断是否被锁定
		 */
		addValidator(new NotLockFormula(corps));
		
		/*
		 * 是否在原点
		 */
		addValidator(new NotStagnantFormula(corps, guardPosition));
		
		/*
		 * 是否能移动
		 */
		addValidator(new MoveableFormula(corps));
		
		doValidator();

		if(hasError()){
			System.out.println(getErrors().getMessage());
		}else{
			//Invoker invoker = new Invoker();
			String cmd = "select ground place"+corps.getPosition()+" corps;";
			try {
				//invoker.receiveCommand(owner.getPlayer(), cmd);
				Command command= CommandFactory.getInstance(corps.getPlayer(),cmd);
				command.execute();
				
				super.command = CommandFactory.getInstance(corps.getPlayer(),this.cmdStr);
				super.command.doValidator();
				if(!super.command.hasError()){
					setPri(AbstractPolicy.PRI_High);
				}
			} catch (ValidatorException e) {
				// TODO Auto-generated catch block
				return;
			}
		}
	}
}
