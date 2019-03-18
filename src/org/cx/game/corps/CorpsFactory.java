package org.cx.game.corps;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

public class CorpsFactory {

	private static Element getRoot() {
		SAXReader saxReader = new SAXReader();
		try {
			InputStream is=new BufferedInputStream(new FileInputStream(PropertiesUtil.getConfigure("corps.path"))); 
		
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

	public static Corps getInstance(Integer cid){
		Element corpsEl = getElement(cid);
		Corps corps = getInstance(corpsEl);
		//corps.setPlayer(player);
		return corps;
	}
	
	private static Corps getInstance(Element corpsEl){
		ObjectTypeBuilder otb = new ObjectTypeBuilder();
		try {
			new ObjectTypeParse(otb).parse(corpsEl);
			Corps corps = (Corps) otb.builder();
			return corps;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BuilderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private static Element getElement(Integer cid){
		
		Element el = null;
		
		Element cs = getRoot().element(XmlUtil.Corps_Corps);
		
		for(Iterator it = cs.elementIterator(XmlUtil.Corps_Card);it.hasNext();){
			Element corps = (Element) it.next();
			if(cid.equals(Integer.valueOf(corps.attribute(XmlUtil.Corps_Card_Id).getText())))
				el = corps;
		}
		
		return el;
	}
	
	public static void main(String[] args) {
		Element root = getRoot();
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1130001);
		ids.add(1120002);
		ids.add(1110004);
		ids.add(1140005);
		ids.add(1120006);
		ids.add(1140007);
		ids.add(1230008);
		ids.add(1220009);
		ids.add(1230010);
		ids.add(1210011);
		ids.add(1330012);
		ids.add(1320013);
		ids.add(1420016);
		ids.add(1430017);
		ids.add(1410018);
		ids.add(1440019);
		ids.add(1440020);
	}
}
