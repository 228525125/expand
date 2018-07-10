package org.cx.game.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.core.AbstractPlayer;
import org.cx.game.corps.AbstractCorps;
import org.cx.game.exception.CommandValidatorException;
import org.cx.game.exception.ValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;
import org.cx.game.validator.SelectCorpsValidator;
import org.cx.game.validator.SelectOptionValidator;
import org.cx.game.validator.SelectSkillValidator;
import org.cx.game.widget.AbstractGround;
import org.cx.game.widget.building.AbstractOption;

public class QueryCommand extends InteriorCommand {

	private Map<String, String> map = new HashMap<String, String>();
	
	private AbstractPlayer player = null;
	
	public QueryCommand(AbstractPlayer player) {
		// TODO Auto-generated constructor stub
		super(player);
		map.put("attack", CommonIdentifierE.Command_Query_Attack);
		map.put("call", CommonIdentifierE.Command_Query_Call);
		map.put("move", CommonIdentifierE.Command_Query_Move);
		map.put("conjure", CommonIdentifierE.Command_Query_Conjure);
		map.put("swap", CommonIdentifierE.Command_Query_Swap);
		map.put("merge", CommonIdentifierE.Command_Query_Merge);
		map.put("leave", CommonIdentifierE.Command_Query_Leave);
		map.put("execute", CommonIdentifierE.Command_Query_Execute);
		map.put("pick", CommonIdentifierE.Command_Query_Pick);
		
		this.player = player;
	}
	
	private List<Integer> positionList = new ArrayList<Integer>();
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		AbstractGround ground = this.player.getContext().getGround();
		
		if("conjure".equals(parameter)){
			doValidator(new SelectSkillValidator(buffer));
			if(hasError())
				throw new CommandValidatorException(getErrors().getMessage());
			
			positionList = ground.queryRange(buffer.getSkill(), map.get(parameter));
		}else if("attack".equals(parameter)){
			doValidator(new SelectCorpsValidator(buffer));
			if(hasError())
				throw new CommandValidatorException(getErrors().getMessage());
			
			AbstractCorps corps = buffer.getCorps();           
			positionList = ground.queryRange(corps, map.get(parameter));   //这里需要计算
		}else if("move".equals(parameter) || "merge".equals(parameter) || "leave".equals(parameter)){
			doValidator(new SelectCorpsValidator(buffer));
			if(hasError())
				throw new CommandValidatorException(getErrors().getMessage());
			
			AbstractCorps corps = buffer.getCorps();           
			positionList = ground.queryRange(corps, map.get(parameter));
		}else if("execute".equals(parameter)){
			doValidator(new SelectOptionValidator(buffer));
			if(hasError())
				throw new CommandValidatorException(getErrors().getMessage());
			
			AbstractOption option = buffer.getOption();
			positionList = ground.queryRange(option, map.get(parameter));
		}else if("pick".equals(parameter)){
			doValidator(new SelectCorpsValidator(buffer));
			if(hasError())
				throw new CommandValidatorException(getErrors().getMessage());
			
			AbstractCorps corps = buffer.getCorps();
			positionList = ground.areaForDistance(corps.getPosition(), 1, AbstractGround.Contain);
		}
	
		Map<String,Object> bean = new HashMap<String,Object>();
		bean.put("positionList", positionList);
		NotifyInfo info = new NotifyInfo(map.get(parameter),bean);
		super.notifyObservers(info);    //通知观察者
	}
}
