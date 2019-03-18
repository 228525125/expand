package org.cx.game.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.cx.game.validator.HostIsExistedValidator;

public class HostManager {

	private static Map<String, AbstractHost> hostMap = new HashMap<String, AbstractHost>();    //playNo - host
	private static Map<String, String> hostNamePlayNoMap = new HashMap<String, String>();      //hostName - playNo浏览器环境下使用
	private static Map<String, String> accountPlayNoMap = new HashMap<String, String>();       //account - playNo
	
	/**
	 * 创建游戏
	 * @param hostName 主机名，不是唯一ID
	 * @param creatorAccount 创建主机的帐号
	 * @param mapId 地图
	 * @return
	 */
	public static AbstractHost createHost(String hostName, String creatorAccount, Integer mapId) {
		String playNo = UUID.randomUUID().toString();
		SceneHost host = new SceneHost(hostName, playNo, creatorAccount, mapId);
		
		registerHost(playNo, host);
		hostNamePlayNoMap.put(hostName, playNo);
		accountPlayNoMap.put(creatorAccount, playNo);
		
		return host;
	}
	
	/**
	 * 加入游戏
	 * @param playNo 游戏唯一编号
	 * @param account 加入的帐号
	 */
	public static AbstractHost joinGame(String playNo, String account) {
		AbstractHost host = getHost(playNo);
		Integer troop = host.getUsableTroop();
		host.playerJoinGame(account, troop);
		accountPlayNoMap.put(account, host.getPlayNo());
		return host;
	}
	
	public static AbstractHost getHost(String playNo) {
		return hostMap.get(playNo);
	}
	
	/**
	 * 通过帐号查找游戏编号
	 * @param account
	 * @return 如果该帐号正处于游戏中的话，则返回PlayNo，否则返回null
	 */
	public static String getPlayNoByAccount(String account) {
		String playNo = accountPlayNoMap.get(account);
		
		if(hostIsExist(playNo))
			return playNo;
		else
			return null;
	}
	
	/**
	 * 判断主机是否存在，这里只是简单的判断，真实环境下要复杂得多
	 * @param playNo 
	 * @return
	 */
	private static Boolean hostIsExist(String playNo) {
		Boolean ret = null!=getHost(playNo) ? true : false;
		return ret;
	}
	
	/**
	 * 浏览器调试环境下使用;
	 * 因为真实环境应该是玩家直接去选择主机，那么玩家已经获取到了主机的PlayNo；
	 * @param hostName
	 * @return
	 */
	public static String getPlayNoByHostName(String hostName) {
		return hostNamePlayNoMap.get(hostName);
	}
	
	public static List<AbstractHost> queryForList(String hostName) {
		return null;
	}

	private static void registerHost(String playNo, AbstractHost host) {
		hostMap.put(playNo, host);
	}
	
	
}
