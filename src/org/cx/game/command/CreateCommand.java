package org.cx.game.command;

import java.util.HashMap;
import java.util.Map;

import org.cx.game.core.HostManager;
import org.cx.game.core.SceneHost;
import org.cx.game.exception.ValidatorException;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.tools.CommonIdentifierE;

public class CreateCommand extends Command {

	@Override
	public void execute() throws ValidatorException {
		// TODO Auto-generated method stub
		super.execute();
		String SPACE = " ";
		String account = parameter.toString().split(SPACE)[0];
		String hostName = parameter.toString().split(SPACE)[1];
		Integer mapId  = new Integer(parameter.toString().split(SPACE)[2]);
		SceneHost host = (SceneHost) HostManager.createHost(hostName, account, mapId);
		
		host.setCorpsDataOfTroop(1,"[10100006,1,1];[10100005,1,1]");      //[10190001,1,1];[10100002,1,1];107003
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("playNo", host.getPlayNo());
		map.put("troop", host.getTroopForAccount(account));
		NotifyInfo info = new NotifyInfo(CommonIdentifierE.Command_CreateHost,map);
		super.notifyObservers(info);
	}
}
