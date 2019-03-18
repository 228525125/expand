package org.cx.game.magic.buff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Map.Entry;

import org.cx.game.corps.Corps;
import org.cx.game.magic.IMagic;
import org.cx.game.intercepter.IInterceptable;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.IRecover;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.out.ResponseFactory;
import org.cx.game.tag.TagHelper;
import org.cx.game.tools.I18n;

/**
 * buff持续回合数计算方式：从添加buff的回合开始计算，例如持续回合为1，下一回buff就将失效；
 * 添加Buff使用Corps addBuff，删除Buff使用buff invalid
 * 生命周期：effect -> affect -> invalid  = 生效 -> 产生影响 -> 失效
 * @author chenxian
 *
 */
public abstract class AbstractBuff extends Observable implements IBuff {

	protected static final Integer Max_Bout = 999;
	protected final static String Affect = "_Affect";
	
	private Integer type = 0;                         //buff的id 对应magic的id
	private String name = null;
	private String depiction = null;
	private String action = null;
	private Integer bout = 0;
	private Integer beginBout = 0;
	private Boolean duplication = false;  //是否可以叠加
	private Integer atk = 0;
	private Integer def = 0;
	
	private Corps owner = null;

	private List<Map<IInterceptable, IIntercepter>> resetList = new ArrayList<Map<IInterceptable, IIntercepter>>();

	public AbstractBuff(Integer type, Integer bout, Corps corps) {
		// TODO Auto-generated constructor stub
		this.type = type;
		this.owner = corps;
		this.bout = bout;
		recordIntercepter(corps.getGround().getQueue().getAddBoutAction(), this);
		
		addObserver(ResponseFactory.getResponse());
	}
	
	public String getName() {
		// TODO Auto-generated method stub
		if(null==name)
			name = I18n.getMessage(this, "name");
		return name;
	}
	
	public String getDepiction() {
		// TODO Auto-generated method stub
		if(null==depiction)
			depiction = I18n.getMessage(this, "depiction");
		return depiction;
	}
	
	@Override
	public Integer getType() {
		// TODO Auto-generated method stub
		return this.type;
	}
	
	/**
	 * 持续回合
	 * @return
	 */
	public Integer getBout() {
		// TODO Auto-generated method stub
		return bout;
	}
	
	public void setBout(Integer bout) {
		// TODO Auto-generated method stub
		this.bout = bout;
	}
	
	/**
	 * 是否可以叠加
	 * @return
	 */
	public Boolean isDuplication() {
		return duplication;
	}

	public void setDuplication(Boolean duplication) {
		this.duplication = duplication;
	}

	/**
	 * 通知类型
	 * @return
	 */
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	/**
	 * 受影响者
	 */
	public Corps getOwner() {
		return owner;
	}
	
	public void setOwner(Corps owner){
		this.owner = owner;
	}
	
	/**
	 * buff增加的攻击力，在extraAtk中体现
	 * @return
	 */
	public Integer getAtk() {
		// TODO Auto-generated method stub
		return this.atk;
	}
	
	public void setAtk(Integer atk) {
		this.atk = atk;
	}
	
	/**
	 * buff增加的防御力，在extraAtk中体现
	 * @return
	 */
	public Integer getDef() {
		// TODO Auto-generated method stub
		return this.def;
	}
	
	public void setDef(Integer def) {
		this.def = def;
	}
	
	@Override
	public Boolean isTrigger(Object[] args) {
		// TODO Auto-generated method stub
		return true;
	}
	
	/**
	 * 生效，表示buff显示存在
	 */
	public void effect() {
		// TODO Auto-generated method stub
		owner.addBuff(this);
		
		beginBout = getOwner().getGround().getQueue().getBout();
	}
	
	/**
	 * 失效，与effect对应
	 */
	public void invalid() {
		// TODO Auto-generated method stub
		resetIntercepter();           //清除buff的拦截器
		owner.removeBuff(this);
	}
	
	@Override
	public void affect(Object...objects) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("player", getOwner().getPlayer());
		map.put("container", getOwner().getGround());
		map.put("card", getOwner());
		map.put("buff", this);
		map.put("position", getOwner().getPosition());
		NotifyInfo info = new NotifyInfo(getAction()+Affect,map);
		notifyObservers(info);
	}
	
	//---------------- IRecover ------------------
	
	public void resetIntercepter() {
		// TODO Auto-generated method stub
		resetIntercepter(IIntercepter.Level_Current);
	}
	
	@Override
	public void resetIntercepter(Integer level) {
		// TODO Auto-generated method stub
		for(Map<IInterceptable, IIntercepter> map : resetList){
			for(Entry<IInterceptable, IIntercepter> entry : map.entrySet()){
				IInterceptable interceptable = entry.getKey();
				IIntercepter intercepter = entry.getValue();	
				if(level.equals(intercepter.getLevel()))
					interceptable.deleteIntercepter(intercepter);
			}
		}
	}
	
	public void recordIntercepter(IInterceptable interceptable, IIntercepter intercepter){
		interceptable.addIntercepter(intercepter);
		Map entry = new HashMap<IInterceptable, IIntercepter>();
		entry.put(interceptable, intercepter);
		resetList.add(entry);
	}
	
	//---------------- IRecover End -------------------

	//---------------- IIntercepter -------------------
	
	@Override
	public String getIntercepterMethod() {
		// TODO Auto-generated method stub
		return "action";
	}

	@Override
	public Boolean isInvoke() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		Integer curBout = getOwner().getGround().getQueue().getBout();
		if((curBout-beginBout)>=bout){
			invalid();
		}
	}
	
	@Override
	public Integer getLevel() {
		// TODO Auto-generated method stub
		return IIntercepter.Level_Current;
	}
	
	@Override
	public Integer getOrder() {
		// TODO Auto-generated method stub
		return IIntercepter.Order_Default;
	}
	
	//---------------- IIntercepter End -------------------
	
	@Override
	public void notifyObservers(Object arg) {
		// TODO Auto-generated method stub
		super.setChanged();
		super.notifyObservers(arg);
	}
	
	//------------------ ITag --------------------
	
	@Override
	public Boolean contains(Integer tag) {
		// TODO Auto-generated method stub
		List<Integer> objectList = TagHelper.queryForTag(tag);
		return objectList.contains(getType());
	}
	
	@Override
	public List<Integer> queryTagForCategory(Integer category) {
		// TODO Auto-generated method stub
		List<Integer> list1 =  TagHelper.queryForCategory(category);
		List<Integer> list2 = TagHelper.queryForObject(getType());
		list2.retainAll(list1);
		return list2;
	}

	@Override
	public List<Integer> queryTagForObject() {
		// TODO Auto-generated method stub
		return TagHelper.queryForObject(getType());
	}
	
	//------------------ ITag End --------------------
}
