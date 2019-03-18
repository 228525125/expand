package org.cx.game.rule;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.cx.game.builder.ObjectTypeBuilder;
import org.cx.game.builder.ObjectTypeParse;
import org.cx.game.exception.BuilderException;
import org.cx.game.exception.ParseException;
import org.cx.game.intercepter.IInterceptable;
import org.cx.game.tools.PropertiesUtil;
import org.cx.game.tools.XmlUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class RuleGroupFactory {
	
	private static Element getRoot() {
		SAXReader saxReader = new SAXReader();
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(PropertiesUtil.getConfigure("rule.path"))); 
			Document document = saxReader.read(is);
			return document.getRootElement();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static RuleGroup ruleGroup = null;
	
	public static void bindingRule(IInterceptable interceptable, Integer groupId){
		ruleGroup = getInstance(groupId);
		ruleGroup.bindingRule(interceptable);
	}
	
	public static void bindingRule(IInterceptable interceptable){
		ruleGroup = getInstance(RuleGroup.RuleGroup_System);
		ruleGroup.bindingRule(interceptable);
	}
	
	public static RuleGroup getInstance(Integer groupId){
		Element groupEl = null;
		for(Iterator it = getRoot().elementIterator();it.hasNext();){
			Element el = (Element) it.next();
			if(groupId.equals(Integer.valueOf(el.attribute(XmlUtil.Rule_RuleGroup_Id).getText())))
				groupEl = el;
		}
		
		if(null!=groupEl){
			ObjectTypeBuilder otb = new ObjectTypeBuilder();
			try {
				new ObjectTypeParse(otb).parse(groupEl);
				RuleGroup rg = (RuleGroup) otb.builder();
				return rg;
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BuilderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
}
