# Gawati XML Transformer - REST Service Wrapper on SAXON XSLT

## to install a release:

 1. Go to (https://github.com/gawati/gawati-xsl-transformer/releases)[https://github.com/gawati/gawati-xsl-transformer/releases]

 2. Download the release. 

 3. rename `gawati-xsl-transformer-1.0.x.war` to `gawati-xsl-transformer.war` and put it in the Tomcat or Jetty webapps folder. 
 
 4. Test the installation by accessing the url: `http://localhost:<tomcat port>/gawati-xsl-transformer/xml/xslt/test`


## to build run:

```
mvn package

```

which will generate: `gawati-xsl-transformer-1.0.x.war`


## API

### convert `/xml/xslt/convert`

 * Expects HTTP Post
 * Form parameters submitted as `application/x-www-form-urlencoded`
 * The following form params are mandatory:
    * input_xslt - the XSLT file as a string, this file should be all inclusive, i.e. should not have any imports
    * input_file - the XML file to be transformed as a string
    * input_params - a string of input params for the XSL transformation, if required. Can be left blank if there are no params. The format is a tilda `~` separated `param=value` string. e.g. `param1=value1~param2=value2` is sending 2 params named `param1` and `param2`  with values `value1` and `value2`. 


