package org.cx.game.widget;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.action.AbstractAction;
import org.cx.game.action.ActionProxyHelper;
import org.cx.game.action.IAction;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.tools.I18n;
import org.cx.game.validator.Errors;
import org.cx.game.validator.IValidatable;
import org.cx.game.validator.IValidator;
import org.cx.game.widget.building.OptionExecuteProcess;
import org.cx.game.widget.building.OptionSpacingProcess;

public abstract class AbstractOption implements IOption {

	/**
	 * 可执行状态
	 */
	public final static Integer Status_Executable = 1;
	
	/**
	 * 不可执行状态
	 */
	public final static Integer Status_Unenforceable = 2;
	
	/**
	 * 等待执行状态
	 */
	public final static Integer Status_WaitExecute = 3;
	
	private Integer number = 1;                        //参数
	private Integer spacingWait = 0;                   //间隔等待
	private Integer executeWait = 0;                   //执行等待
	
	private String name = null;
	
	private Integer status = Status_Executable;        //选项状态
	
	private OptionSpacingProcess spacingProcess = null;
	private OptionExecuteProcess executeProcess = null;
	private Object owner = null;
	
	public String getName() {
		// TODO Auto-generated method stub
		if(null==name)
			name = I18n.getMessage(this, "name");
		return name;
	}
	
	public Object getOwner() {
		// TODO Auto-generated method stub
		return this.owner;
	}

	public void setOwner(Object owner) {
		// TODO Auto-generated method stub
		this.owner = owner;
	}
	
	/**
	 * 选项参数，一个表示数字的参数
	 * @return 执行数量
	 */
	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	public void setSpacingWait(Integer spacingWait) {
		// TODO Auto-generated method stub
		this.spacingWait = spacingWait;
	}
	
	/**
	 * 选项被执行后，下一次可执行需间隔的回合数
	 * 回合数从本回合开始计算，例如间隔回合数为1，则下回合结束间隔周期
	 * @param spacingWait 间隔回合数
	 */
	public Integer getSpacingWait() {
		// TODO Auto-generated method stub
		return this.spacingWait;
	}
	
	/**
	 * 间隔剩余回合数
	 * @return
	 */
	public Integer getSpacingRemainBout() {
		// TODO Auto-generated method stub
		return null!=this.spacingProcess ? this.spacingProcess.getRemainBout() : 0;
	}
	
	public void setExecuteWait(Integer executeWait) {
		this.executeWait = executeWait;
	}
	
	/**
	 * 选项需要等待一定回合后才被执行
	 * 回合数从本回合开始计算，例如等待回合数为1，则下回合执行
	 * @param executeWait 等待回合数
	 */
	public Integer getExecuteWait() {
		// TODO Auto-generated method stub
		return this.executeWait;
	}
	
	/**
	 * 查询执行范围
	 * @return
	 */
	public abstract List<Integer> getExecuteRange();
	
	/**
	 * 距离执行完成还有多少回合
	 * @return
	 */
	public Integer getExecuteRemainBout() {
		// TODO Auto-generated method stub
		return null!=this.executeProcess ? this.executeProcess.getRemainBout() : 0;
	}
	
	/**
	 * 选项状态，选项根据执行情况可分为3种状态，分别是：可执行、不可执行、等待执行
	 * @return
	 */
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public void cancelExecuteWait() {
		if(Status_WaitExecute.equals(getStatus()))
			this.executeProcess.stop();
	}
	
	/**
	 * 开始Execute方法进度
	 */
	public void firing() {
		
		/*
		 * 不管是否执行等待，这个方法都会被执行
		
		beforeExecute();*/
		
		if(!Integer.valueOf(0).equals(getExecuteWait())){
			setStatus(Status_WaitExecute);
			if(null==this.executeProcess)
				this.executeProcess = new OptionExecuteProcess(getControlQueue(), this);
			this.executeProcess.begin();
		}else
			setStatus(Status_Executable);
	}
	
	/**
	 * 开始执行间隔周期
	 */
	public void cooling() {
		// TODO Auto-generated method stub		
		if(!Integer.valueOf(0).equals(this.spacingWait)){
			setStatus(Status_Unenforceable);
			if(null==this.spacingProcess)
				this.spacingProcess = new OptionSpacingProcess(getControlQueue(), this);
			this.spacingProcess.begin();
		}else
			setStatus(Status_Executable);
	}
	
	/**
	 * 在选项被执行前必须做的事，例如建造之前要扣减费用
	 
	protected void beforeExecute() {
		
	}*/
	
	private BeforeExecute beforeExecute = null;
	private Execute execute = null;
	
	/**
	 * 在子类中，定义不同的Execute
	 * @return
	 */
	public BeforeExecute getBeforeExecute() {
		if(null==this.beforeExecute){
			this.beforeExecute = new BeforeExecute();
			this.beforeExecute.setOwner(this);
		}
		return this.beforeExecute;
	}
	
	/**
	 * 在子类中，定义不同的Execute；
	 * @return
	 */
	public Execute getExecute() {
		if(null==this.execute){
			this.execute = new Execute();
			this.execute.setOwner(this);
		}
		return this.execute;
	}
	
	/**
	 * 执行选项
	 * @param objects
	 */
	public void execute(Object...objects) throws RuleValidatorException {
		// TODO Auto-generated method stub
		
		doValidator();
		
		if(hasError())
			throw new RuleValidatorException(getErrors().getMessage());
			
		IAction action = new ActionProxyHelper(getBeforeExecute());
		action.action(objects);
		
		action = new ActionProxyHelper(getExecute());
		action.action(objects);
	}
	
	private Errors errors = new Errors();	
	private List<IValidator> validatorList = new ArrayList<IValidator>();
	
	public void addValidator(IValidator validator) {
		// TODO Auto-generated method stub
		validatorList.add(validator);
	}

	@Override
	public void deleteValidator(IValidator validator) {
		// TODO Auto-generated method stub
		validatorList.remove(validator);
	}

	@Override
	public List<IValidator> getValidators() {
		// TODO Auto-generated method stub
		return validatorList;
	}
	
	@Override
	public void doValidator() {
		// TODO Auto-generated method stub
		for(IValidator v : validatorList)
			if(!v.validate())
				errors.addError(v);
	}
	
	@Override
	public void doValidator(IValidator validator) throws RuleValidatorException {
		// TODO Auto-generated method stub
		if(!validator.validate())
			throw new RuleValidatorException(validator.getErrorMessage());
	}
	
	@Override
	public Errors getErrors() {
		// TODO Auto-generated method stub
		return errors;
	}
	
	@Override
	public Boolean hasError() {
		// TODO Auto-generated method stub
		return errors.hasError();
	}
	
	protected abstract AbstractControlQueue getControlQueue();

	public class BeforeExecute extends AbstractAction implements IAction {
		
		public AbstractOption getOwner() {
			// TODO Auto-generated method stub
			return (AbstractOption) super.getOwner();
		}
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			super.action(objects);
			
			firing();
		}
	}
	
	public class Execute extends AbstractAction implements IAction {
		
		public AbstractOption getOwner() {
			// TODO Auto-generated method stub
			return (AbstractOption) super.getOwner();
		}
		
		@Override
		public void action(Object... objects) {
			// TODO Auto-generated method stub
			super.action(objects);
			
			cooling();
		}
	}
}
