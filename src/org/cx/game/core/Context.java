package org.cx.game.core;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.corps.Corps;
import org.cx.game.exception.RuleValidatorException;
import org.cx.game.widget.Ground;

public class Context implements IContext {

	private String playNo = null;
	private Integer status = Status_Start;
	
	private AbstractPlayState playState = null;
	
	private Player controlPlayer = null;
	private Corps controlCorps = null;
	private Ground ground = null;
	
	private List<Player> playerList = new ArrayList<Player>();
	
	public Context(String playNo, Ground ground) {
		// TODO Auto-generated constructor stub
		this.playNo = playNo;           //比赛唯一编号
		this.ground = ground;
	}
	
	/**
	 * 编号，用于保存比赛进度
	 * @return
	 */
	public String getPlayNo() {
		return playNo;
	}
	
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public void setPlayState(AbstractPlayState playState) {
		this.playState = playState;
		this.playState.setContext(this);
	}
	
	//---------------- Player -----------------
	
	/**
	 * 初始化game时，调用
	 * @param player 
	 */
	public void addPlayer(Player player) {
		// TODO Auto-generated method stub
		this.playerList.add(player);
		player.setContext(this);
	}
	
	/**
	 * 阵营玩家和非阵营玩家结束后，都会被移除；
	 * @param player
	 */
	public void removePlayer(IPlayer player) {
		// TODO Auto-generated method stub
		this.playerList.remove(player);
	}
	
	/**
	 * 所有player，包含中立player
	 * @return
	 */
	public List<Player> getPlayerList() {
		// TODO Auto-generated method stub
		return this.playerList;
	}
	
	/**
	 * 当前操作比赛的玩家对象
	 */
	public Player getControlPlayer() {
		return controlPlayer;
	}

	public void setControlPlayer(Player controlPlayer) {
		this.controlPlayer = controlPlayer;
	}
	
	/**
	 * 根据troop查找player
	 */
	public Player getPlayer(Integer troop) {
		// TODO Auto-generated method stub
		for(Player player : getPlayerList()){
			if(troop.equals(player.getTroop()))
				return player;
		}
		return null;
	}
	
	//------------------ Corps ---------------
	
	public Corps getControlCorps() {
		return controlCorps;
	}
	
	public void setControlCorps(Corps controlCorps) {
		controlCorps.activate(true);
		this.controlCorps = controlCorps;
		
		setControlPlayer(controlCorps.getPlayer());
	}
	
	//------------------ Ground --------------
	
	public Ground getGround() {
		// TODO Auto-generated method stub
		return this.ground;
	}
	
	public Ground getGround(Integer id) {
		// TODO Auto-generated method stub
		if(null==getGround().getArea())
			return getGround();
		
		return (Ground) getGround().getArea().getGround(id);
	}
	
	/**
	 * 切换当前ground，因为有area的存在
	 * @param ground
	 */
	public void setGround(Ground ground) {
		// TODO Auto-generated method stub
		this.ground = ground;
	}
	
	
	public void start() {
		// TODO Auto-generated method stub
		setStatus(Status_Start);
		setPlayState(new StartState());
		this.playState.start();
	}
	
	/**
	 * 部署
	 */
	public void deploy(){
		this.playState.deploy();
	}
	
	/**
	 * 操作完毕
	 */
	public void done(){
		this.playState.done();
	}
	
	/**
	 * 游戏结束
	 */
	public void finish(){
		this.playState.finish();
	}
	
	/**
	 * 交换比赛控制权
	 * @throws RuleValidatorException 
	 */
	public void switchControl() {
		
		Object object = this.ground.getQueue().out();
		
		if (object instanceof Player) {
			Player player = (Player) object;
			setControlPlayer(player);
		}else{
			Corps corps = (Corps) object;
			setControlCorps(corps);
		}
	}
}
