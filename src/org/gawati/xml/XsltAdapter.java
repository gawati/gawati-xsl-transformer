package org.gawati.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;



@Path("/xslt")
public class XsltAdapter {
	private String thisClassName = XsltAdapter.class.getName();
	
	private static final Logger log = 
			 Logger.getLogger(XsltAdapter.class.getName());
	
	XmlConverter converter = null;
	Double randomNum ; 
	private String serviceVersion = "1.0.1";
	public XsltAdapter() {
	    converter = new XmlConverter();
	    randomNum = Math.random();
		log.info("VERSION: " + thisClassName+ " /xslt : constructing, version :" + serviceVersion);
	}
	
	public class XsltProcessRequest {
		final InputStream xsltFile ; 
		final InputStream xmlFile;
		final Map<String,String> inputParams;
		
		public XsltProcessRequest(InputStream xsltFile, InputStream xmlFile, Map<String, String> inputParams) {
			this.xsltFile = xsltFile;
			this.xmlFile = xmlFile;
			this.inputParams = inputParams;
		}
		
		public boolean isValid() {
			return (this.xsltFile != null ) && (this.xmlFile != null) && (this.inputParams != null);
		}
		
	}


	
	@POST
	@Path("/convert")
	@Produces(MediaType.TEXT_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response restApplyXslt(
			@FormParam("input_xslt") String input_xslt, 
			@FormParam("input_file") String input_file, 
			@FormParam("input_params") String input_params
			) throws IOException {
		
		
		final InputStream isXslt =   new ByteArrayInputStream(input_xslt.getBytes(StandardCharsets.UTF_8));
		final InputStream isFile =  new ByteArrayInputStream(input_file.getBytes(StandardCharsets.UTF_8));
		//final List<NameValuePair> nvParams = URLEncodedUtils.parse(input_params, StandardCharsets.UTF_8);
		final Map<String,String> nvParams = xsltInputParams(input_params);
		
		StreamingOutput stream =  new StreamingOutput(){

			@Override
			public void write(OutputStream out) throws IOException, WebApplicationException {
				try {
					converter.convertToXml(isXslt, isFile, nvParams, out);
				} catch(Exception e){
					throw new WebApplicationException(e);
				}
			}
			
		};
		return Response.ok(stream).build();
	}
	
	/**
	 * Accepts input params for the XSLT as tilda separate key value pairs: 
	 * param1=value1~param2=value2
	 * @param sParams
	 * @return
	 */
	private Map<String,String> xsltInputParams(String sParams) {
		Map<String, String> kvMaps = new HashMap<String, String>();
		if (sParams != null) {
			if (sParams.indexOf("~") != -1) {
				String[] kvPairs = sParams.trim().split("~");
				for (String kvPair: kvPairs) {
					String[] kv =  kvPair.split("=");
					if (kv.length == 2) {
	                    kvMaps.put(kv[0], kv[1]);			
	                }
	                if (kv.length == 1) {
	                    if (kv[0].length() > 0) {
	                        kvMaps.put(kv[0], "");
	                    }
	                }			
				}			
			}
		}
		return kvMaps;
	}
	
	
	@GET
	@Path("/test")
	public Response getTest() throws URISyntaxException{
		/**
		 * Check for thread safety ...every request generates a random number
		 */
		String s = "TEST (/xslt/test) !  = " +  this.randomNum + " VERSION = " + this.serviceVersion ;
		return Response.ok(s).build();
	}

}
