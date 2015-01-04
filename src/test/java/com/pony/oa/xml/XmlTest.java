package com.pony.oa.xml;

import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;

public class XmlTest {
	
	public static String XML = "<Form name=\"form\">测试</Form>";

	@Test
	public void testEscapeXml(){
		System.out.println(StringEscapeUtils.escapeXml11(new String(XML)));
	}
}
