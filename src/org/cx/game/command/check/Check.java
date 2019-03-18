package org.cx.game.command.check;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.exception.RuleValidatorException;
import org.cx.game.exception.ValidatorException;
import org.cx.game.tools.CommandUtil;
import org.cx.game.validator.Errors;
import org.cx.game.validator.IValidator;

public abstract class Check implements ICheck {

	private List<IValidator> validatorList = new ArrayList<IValidator>();
	private Errors errors = new Errors();
	
	@Override
	public Boolean isMatch(Event event) throws ValidatorException {
		// TODO Auto-generated method stub
		CheckEvent ce = (CheckEvent) event;
		/*
		 * 调用者是否匹配
		 */
		if(!isTypeMatch(ce.getSource()))
			return false;
		
		/*
		 * 方法是否匹配
		 */
		if(!getMethod().equals(ce.getMethod()))
			return false;
		
		/*
		 * 参数是否匹配
		 */
		if(!isArgumentMatch(ce.getArguments()))
			return false;
		
		return true;
	}
	
	public abstract Class [] getParameterTypes();
	
	public abstract String getMethod();
	
	public abstract Class getCallerType();
	
	private Boolean isArgumentMatch(Object [] args) {
		Class [] types = new Class[args.length];
		for(int i=0;i<args.length;i++){
			types[i] = args[i].getClass();
		}
		
		return CommandUtil.compareParameterClass(types, getParameterTypes());
	}
	
	private Boolean isTypeMatch(Object obj){
		return obj.getClass().equals(getCallerType()) || CommandUtil.isSuperClassOrInterface(getCallerType(), obj.getClass());
	}
	
	@Override
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
	public void doValidator(IValidator validator) throws ValidatorException {
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

}
