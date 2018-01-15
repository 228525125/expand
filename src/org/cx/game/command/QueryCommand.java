package org.cx.game.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cx.game.core.IPlayer;
import org.cx.game.corps.Corps;
import org.cx.game.magic.skill.IActiveSkill;
import org.cx.game.exception.CommandValidatorException;
import org.cx.game.exception.ValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.validator.QueryCommandValidator;
import org.cx.game.validator.SelectCorpsValidator;
import org.cx.game.validator.SelectOptionValidator;
import org.cx.game.validator.SelectSkillValidator;
import org.cx.game.widget.IGround;
import org.cx.game.widget.building.IOption;

public class QueryCommand extends InteriorCommand {

	private Map<String, String> map = new HashMap<String, String>();
	
	public QueryCommand(IPlayer player) {
		// TODO Auto-generated constructor stub
		super(player);
		map.put("attack", NotifyInfo.Command_Query_Attack);
		map.put("call", NotifyInfo.Command_Query_Call);
		map.put("move", NotifyInfo.Command_Query_Move);
		map.put("conjure", NotifyInfo.Command_Query_Conjure);
		map.put("swap", NotifyInfo.Command_Query_Swap);
		map.put("apply", NotifyInfo.Command_Query_Apply);
		map.put("execute", NotifyInfo.Command_Query_Execute);
		map.put("pick", NotifyInfo.Command_Query_Pick);
	}
	
	private List<Integer> positionList = new ArrayList<Integer>();
	
	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		IGround ground = player.getContext().getGround();
		
		if("conjure".equals(parameter)){
			doValidator(new SelectSkillValidator(buffer));
			if(hasError())
				throw new CommandValidatorException(getErrors().getMessage());
			
			positionList = ground.queryRange(buffer.getSkill(), map.get(parameter));
		}else if("attack".equals(parameter)){
			doValidator(new SelectCorpsValidator(buffer));
			if(hasError())
				throw new CommandValidatorException(getErrors().getMessage());
			
			Corps corps = (Corps) buffer.getCorps();           
			positionList = ground.queryRange(corps, map.get(parameter));   //这里需要计算
		}else if("move".equals(parameter)){
			doValidator(new SelectCorpsValidator(buffer));
			if(hasError())
				throw new CommandValidatorException(getErrors().getMessage());
			
			Corps corps = (Corps) buffer.getCorps();           
			positionList = ground.queryRange(corps, map.get(parameter));
		}else if("execute".equals(parameter)){
			doValidator(new SelectOptionValidator(buffer));
			if(hasError())
				throw new CommandValidatorException(getErrors().getMessage());
			
			IOption option = buffer.getOption();
			positionList = ground.queryRange(option, map.get(parameter));
		}else if("pick".equals(parameter)){
			doValidator(new SelectCorpsValidator(buffer));
			if(hasError())
				throw new CommandValidatorException(getErrors().getMessage());
			
			Corps corps = (Corps) buffer.getCorps();
			positionList = ground.areaForDistance(corps.getPosition(), 1, IGround.Contain);
		}
	
		Map<String,Object> bean = new HashMap<String,Object>();
		bean.put("positionList", positionList);
		NotifyInfo info = new NotifyInfo(map.get(parameter),bean);
		super.notifyObservers(info);    //通知观察者
	}
}
