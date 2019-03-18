package org.cx.game.command;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.core.HostManager;
import org.cx.game.exception.ValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;

public class ConnectCommand extends Command {

	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		
		String account = parameter.toString();
		String playNo = HostManager.getPlayNoByAccount(account);
		Integer troop = null;
		Boolean isExistedHost = null;
		if(null!=playNo){
			troop = HostManager.getHost(playNo).getTroopForAccount(account);
			isExistedHost = true;
		}else{
			isExistedHost = false;
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("isExistedHost", isExistedHost);
		map.put("playNo", playNo);
		map.put("troop", troop);
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Command_Connect,map);
		super.notifyObservers(info);
	}
}
