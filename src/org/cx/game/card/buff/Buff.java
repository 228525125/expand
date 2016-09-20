package org.cx.game.card.buff;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Map.Entry;

import org.cx.game.card.LifeCard;
import org.cx.game.card.magic.IMagic;
import org.cx.game.core.Context;
import org.cx.game.intercepter.IInterceptable;
import org.cx.game.intercepter.IIntercepter;
import org.cx.game.intercepter.IntercepterAscComparator;
import org.cx.game.observer.NotifyInfo;
import org.cx.game.out.JsonOut;
import org.cx.game.tools.I18n;

/**
 * buff持续回合数计算方式：1、buff第一次影响单位的回合开始计算；
 * 2、从添加buff的回合开始计算
 * 目前采用第2种
 * 添加Buff使用lifecard addBuff，删除Buff使用buff invalid
 * 生命周期：effect -> affect -> invalid  = 生效 -> 产生影响 -> 失效
 * @author chenxian
 *
 */
public abstract class Buff extends Observable implements IBuff {

	private String cType = null;                    //类名
	private String name = null;
	private LifeCard owner;
	private Map<String,List<IIntercepter>> intercepterList = new HashMap<String,List<IIntercepter>>();
	private List<Map<IInterceptable, IIntercepter>> resetList = new ArrayList<Map<IInterceptable, IIntercepter>>();
	private Boolean isDelete = false;
	private String action = null;
	private Integer bout = 0;
	private Integer style = IMagic.Style_physical;       //风格，法术、物理   
	private Integer hostility = IBuff.Type_Neutral;        //类型，受益、受损、中性    
	private Integer beginBout = 0;
	private Boolean duplication = false;  //是否可以叠加
	private Integer func = IMagic.Func_Damage;      //功能类型
	
	protected final static String Affect = "_Affect";

	public Buff(Integer bout, LifeCard life) {
		// TODO Auto-generated constructor stub
		this.owner = life;
		this.bout = bout;
		recordIntercepter(life.getPlayer().getContext(), this);
		
		addObserver(new JsonOut());
		
		String allName = this.getClass().getName();
		String packageName = this.getClass().getPackage().getName();
		this.cType = allName.substring(packageName.length()+1);
		setAction("Buff");
		
		this.func = Context.getMagicFunction(allName);
		this.style = Context.getMagicStyle(allName);
		this.hostility = Context.getMagicHostility(allName);
	}
	
	@Override
	public Integer getBout() {
		// TODO Auto-generated method stub
		return bout;
	}
	
	@Override
	public void setBout(Integer bout) {
		// TODO Auto-generated method stub
		this.bout = bout;
	}

	public LifeCard getOwner() {
		return owner;
	}
	
	public void setOwner(LifeCard owner){
		this.owner = owner;
	}
	
	@Override
	public String getCType() {
		// TODO Auto-generated method stub
		return cType;
	}
	
	public String getName() {
		// TODO Auto-generated method stub
		/* 在没有分包之前，buff的名字自动选用skill的名字代替，分包后逻辑发生变化了
		 * if(null==name){
			String clazz = this.getClass().getName();
			if(-1!=clazz.indexOf("Buff"))
				name = I18n.getMessage(clazz.substring(0, clazz.indexOf("Buff"))+".name");
		}*/
		if(null==name)
			name = I18n.getMessage(this, "name");
		return name;
	}
	
	@Override
	public void before(Object[] args) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void after(Object[] args) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void affect(Object...objects) {
		// TODO Auto-generated method stub
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("player", getOwner().getPlayer());
		map.put("container", getOwner().getContainer());
		map.put("card", getOwner());
		map.put("buff", this);
		map.put("position", getOwner().getContainerPosition());
		NotifyInfo info = new NotifyInfo(getAction()+Affect,map);
		notifyObservers(info);
		
		if(0==beginBout)
			beginBout = getOwner().getPlayer().getContext().getBout();
	}
	
	@Override
	public void effect() {
		// TODO Auto-generated method stub
		owner.addBuff(this);
	}
	
	@Override
	public void invalid() {
		// TODO Auto-generated method stub
		resetIntercepter();           //清除buff的拦截器
		owner.removeBuff(this);
	}
	
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
	
	public Integer getStyle() {
		return style;
	}

	public Integer getHostility() {
		return hostility;
	}
	
	public Boolean isDuplication() {
		return duplication;
	}

	public void setDuplication(Boolean duplication) {
		this.duplication = duplication;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Integer getFunc() {
		return func;
	}

	@Override
	public String getIntercepterMethod() {
		// TODO Auto-generated method stub
		return "addBout";
	}

	@Override
	public Boolean isInvoke() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public void finish(Object[] args) {
		// TODO Auto-generated method stub
		if(0==beginBout)
			beginBout = getOwner().getPlayer().getContext().getBout();
		
		Integer curBout = getOwner().getPlayer().getContext().getBout();
		if((curBout-beginBout)==bout){
			invalid();
		}
	}
	
	@Override
	public void addIntercepter(IIntercepter intercepter) {
		// TODO Auto-generated method stub
		List<IIntercepter> intercepters = intercepterList.get(intercepter.getIntercepterMethod());
		if(null!=intercepters){
			intercepters.add(intercepter);
		}else{
			intercepters = new ArrayList<IIntercepter>();
			intercepters.add(intercepter);
			intercepterList.put(intercepter.getIntercepterMethod(), intercepters);
		}
	}

	@Override
	public void deleteIntercepter(IIntercepter intercepter) {
		// TODO Auto-generated method stub
		/*List<IIntercepter> list = intercepterList.get(intercepter.getIntercepterMethod());
		if(null!=list){
			list.remove(intercepter);
		}*/
		
		intercepter.delete();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		intercepterList.clear();
	}

	@Override
	public Map<String,List<IIntercepter>> getIntercepterList() {
		// TODO Auto-generated method stub
		return intercepterList;
	}
	
	@Override
	public Integer getOrder() {
		// TODO Auto-generated method stub
		return IIntercepter.Order_Default;
	}
	
	@Override
	public Integer getLevel() {
		// TODO Auto-generated method stub
		return IIntercepter.Level_Current;
	}
	
	@Override
	public void notifyObservers(Object arg) {
		// TODO Auto-generated method stub
		super.setChanged();
		super.notifyObservers(arg);
	}
	
	@Override
	public void delete() {
		// TODO Auto-generated method stub
		this.isDelete = true;
	}
	
	@Override
	public Boolean isDelete() {
		// TODO Auto-generated method stub
		return this.isDelete;
	}

}
