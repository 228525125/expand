package org.cx.game.widget;

/**
 * 传送站，用于链接不同地图空间的节点，它对应的建筑物是
 * 另外区别于时空门
 * @author chenxian
 *
 */
public class SpatialNode {

	private Integer id = null;
	private Integer position = null;
	private Integer mapId = null;
	private Boolean usable = false;
	
	private Area area = null;
	private IGroundE ground = null;
	
	public SpatialNode(Integer id, Integer mapId, Integer position) {
		// TODO Auto-generated constructor stub
		this.id = id;
		this.mapId = mapId;
		this.position = position;
	}
	
	public void setOwner(Area area) {
		// TODO Auto-generated method stub
		this.area = area;
	}
	
	public Area getOwner() {
		// TODO Auto-generated method stub
		return this.area;
	}
	
	public IGroundE getGround() {
		return this.ground;
	}
	
	public Boolean getUsable() {
		return this.usable;
	}
	
	public void setUsable(Boolean usable) {
		this.usable = usable;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	/**
	 * 传送到目标节点
	 * @param destNode 节点
	 */
	public void transmit(SpatialNode destNode) {
		
	}
	
}
