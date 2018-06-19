package org.gawati.xml;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.transform.stream.StreamSource;

import net.sf.saxon.s9api.Processor;
import net.sf.saxon.s9api.QName;
import net.sf.saxon.s9api.Serializer;
import net.sf.saxon.s9api.XdmAtomicValue;
import net.sf.saxon.s9api.XdmNode;
import net.sf.saxon.s9api.XsltCompiler;
import net.sf.saxon.s9api.XsltExecutable;
import net.sf.saxon.s9api.XsltTransformer;

public class XmlConverter {
	
	private String thisClassName ;
	
	private static final Logger log = 
			 Logger.getLogger(XmlConverter.class.getName());
	
	Processor m_proc = null;
	XsltCompiler m_comp = null;
	
	public XmlConverter() {
	    m_proc = new Processor(false);
	    m_comp = m_proc.newXsltCompiler();
	    thisClassName = this.getClass().getName();
	    log.info(thisClassName+": setting up XmlConverter ");
	}
	
	public void convertToXml(InputStream isXslt, InputStream isFile, Map<String, String> nvParams, OutputStream out)  {
		try {
			log.info(thisClassName + ": entering convertToXml " );
			XsltExecutable exp = m_comp.compile(
		    		new StreamSource(isXslt)
		    		);
		    XdmNode source = m_proc.newDocumentBuilder().build(
		    		new StreamSource(isFile)
		    		);
		    log.info(thisClassName + ": setup XsltExecutable and source XdmNode " );
		    Serializer serOut =  m_proc.newSerializer(out);
		    XsltTransformer trans = exp.load();
		    if (nvParams.size() > 0 ) {
		    	for (String key : nvParams.keySet()) {
		    		QName pName = new QName(key);
		    		XdmAtomicValue xVal =  new XdmAtomicValue(nvParams.get(key));
		    		trans.setParameter(pName, xVal);
		    	}
		    }
		    trans.setInitialContextNode(source);
		    //trans.setInitialTemplate(new QName("main"));
		    trans.setDestination(serOut);
		    log.info(thisClassName + ": about to call transform " );
		    trans.transform();
		} catch (Exception e) {
			log.log(Level.SEVERE, "error during convertToXml ", e);
		}
	}

	public void convertToXml2(String systemId, InputStream isXslt, InputStream isFile, Map<String, String> nvParams, OutputStream out)  {
		try {
			log.info(thisClassName + ": entering convertToXml " );
			StreamSource sourceXslt = new StreamSource(isXslt);
			sourceXslt.setSystemId(systemId);
			XsltExecutable exp = m_comp.compile(
					sourceXslt
		    		);
		    XdmNode source = m_proc.newDocumentBuilder().build(
		    		new StreamSource(isFile)
		    		);
		    log.info(thisClassName + ": setup XsltExecutable and source XdmNode " );
		    Serializer serOut =  m_proc.newSerializer(out);
		    XsltTransformer trans = exp.load();
		    if (nvParams.size() > 0 ) {
		    	for (String key : nvParams.keySet()) {
		    		QName pName = new QName(key);
		    		XdmAtomicValue xVal =  new XdmAtomicValue(nvParams.get(key));
		    		trans.setParameter(pName, xVal);
		    	}
		    }
		    trans.setInitialContextNode(source);
		    //trans.setInitialTemplate(new QName("main"));
		    trans.setDestination(serOut);
		    log.info(thisClassName + ": about to call transform " );
		    trans.transform();
		} catch (Exception e) {
			log.log(Level.SEVERE, "error during convertToXml ", e);
		}
	}

}
 