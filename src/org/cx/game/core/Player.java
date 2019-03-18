package org.cx.game.core;

import java.util.ArrayList;
import java.util.List;

import org.cx.game.command.CommandBuffer;
import org.cx.game.corps.Corps;
import org.cx.game.widget.treasure.Mineral;
import org.cx.game.widget.treasure.ResourceHelper;

public class Player implements IPlayer {
	
	public static final String Neutral = "neutral";
	
	private Integer troop = 0;
	private String name = null;
	private Boolean isComputer = false;
	
	private AbstractHost host = null;
	private Context context = null;
	private CommandBuffer commandBuffer = null;
	private Mineral mineral = null;
	
	private List<Corps> corpsList = new ArrayList<Corps>();
	
	public Player(Integer id, String name, AbstractHost host) {
		// TODO Auto-generated constructor stub
		
		this.troop = id;
		this.name = name;
		this.host = host;
		this.commandBuffer = new CommandBuffer(this);
		
		this.mineral = new Mineral();
		
		//this.groupPolicy = PolicyGroupFactory.getInstance(10450001);
		//this.groupPolicy.setOwner(this);
	}
	
	public AbstractHost getHost() {
		return host;
	}
	
	public Integer getTroop() {
		// TODO Auto-generated method stub
		return this.troop;
	}
	
	public void setTroop(Integer troop) {
		this.troop = troop;
	}
	
	public String getName() {
		// TODO Auto-generated method stub
		return this.name;
	}
	
	public Boolean isComputer() {
		// TODO Auto-generated method stub
		return this.isComputer;
	}
	
	public void setIsComputer(Boolean isComputer) {
		// TODO Auto-generated method stub
		this.isComputer = isComputer;
	}
	
	public CommandBuffer getCommandBuffer() {
		return commandBuffer;
	}

	public Context getContext() {
		// TODO Auto-generated method stub
		return context;
	}
	
	public void setContext(Context context) {
		// TODO Auto-generated method stub
		this.context = context;
	}
	
	/**
	 * player控制的live状态的corps
	 * @return
	 */
	public List<Corps> getCorpsList() {
		return corpsList;
	}
	
	/**
	 * 资源
	 * @return
	 */
	public Mineral getMineral() {
		return this.mineral;
	}
	
	/**
	 * 用于xml配置
	 * @param res
	 */
	public void setMineral(Mineral mineral) {
		this.mineral = mineral;
	}
	
	public void setMineral(Integer funType, Mineral mineral) {
		this.mineral = (Mineral) ResourceHelper.operating(funType, this.mineral, mineral);
	}

	/**
	 * 使用AI自动操作
	 */
	public void automation(){
		/*while (this.equals(getContext().getControlPlayer())) {
			IPolicy policy = this.groupPolicy.getPolicy();
			if(null!=policy){
				policy.execute();
				
				if(policy instanceof DonePolicy)
					break;
			}
			else
				break;
		}*/
		getContext().done();
	}
	
	@Override
	public boolean equals(Object arg0) {
		// TODO Auto-generated method stub
		if (arg0 instanceof IPlayer) {
			IPlayer player = (IPlayer) arg0;
			return player.getTroop().equals(getTroop());
		}
		return super.equals(arg0);
	}
}
