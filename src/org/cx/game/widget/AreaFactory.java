package org.cx.game.widget;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.cx.game.builder.ObjectTypeBuilder;
import org.cx.game.builder.ObjectTypeParse;
import org.cx.game.exception.BuilderException;
import org.cx.game.exception.ParseException;
import org.cx.game.tools.PropertiesUtil;
import org.cx.game.tools.XmlUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class AreaFactory {

	private static Element getRoot() {
		SAXReader saxReader = new SAXReader();
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(PropertiesUtil.getConfigure("area.path")));
			Document document = saxReader.read(is);
			return document.getRootElement();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Area getInstance(Integer areaId){
		Element areaEl = null;
		for(Iterator it = getRoot().elementIterator();it.hasNext();){
			Element el = (Element) it.next();
			if(areaId.equals(Integer.valueOf(el.attribute(XmlUtil.Area_Area_Id).getText())))
				areaEl = el;
		}
		
		if(null!=areaEl){
			ObjectTypeBuilder otb = new ObjectTypeBuilder();
			try {
				new ObjectTypeParse(otb).parse(areaEl);
				Area area = (Area) otb.builder();
				area.afterConstruct();
				return area;
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
