# Gawati XML Transformer

REST Service Wrapper on SAXON XSLT

to build run:

```
mvn package
```

which will generate: `xslt-transformer-1.0.x.war`, rename to xslt-transformer.war and deploy in Tomcat / Jetty.

APIs available: 

## Convert API 

Runs an XSLT transform on a XML file

Has the endpoint:

`/Gawati/xml/xslt/convert` - expects a url encoded form submission `Content-Type: application/x-www-form-urlencoded` .

Expects the parameters as HTTP POST:

`input_file` - required, XML file as string
`input_xslt` - required, XSLT file as string
`input_params` - required, Any parameters that the XSLT expects in a tilda `~` separated key=value string e.g. `param1=value1~param2=value2` is a key value pair param1 and param2. Can be left blank if there are no parameters.

Returns:

Transformed XML returned as XML

Example:

Send `input_xslt` as:

```
<?xml version="1.0" encoding="UTF-8"?>
<html xsl:version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
<body style="font-family:Arial;font-size:12pt;background-color:#EEEEEE">
<xsl:for-each select="breakfast_menu/food">
  <div style="background-color:teal;color:white;padding:4px">
    <span style="font-weight:bold"><xsl:value-of select="name"/> - </span>
    <xsl:value-of select="price"/>
    </div>
  <div style="margin-left:20px;margin-bottom:1em;font-size:10pt">
    <p>
    <xsl:value-of select="description"/>
    <span style="font-style:italic"> (<xsl:value-of select="calories"/> calories per serving)</span>
    </p>
  </div>
</xsl:for-each>
</body>
</html>
```

Send `input_file` as:

```
<?xml version="1.0" encoding="UTF-8"?>
<breakfast_menu>

<food>
<name>Belgian Waffles</name>
<price>$5.95</price>
<description>Two of our famous Belgian Waffles with plenty of real maple syrup</description>
<calories>650</calories>
</food>

<food>
<name>Strawberry Belgian Waffles</name>
<price>$7.95</price>
<description>Light Belgian waffles covered with strawberries and whipped cream</description>
<calories>900</calories>
</food>

<food>
<name>Berry-Berry Belgian Waffles</name>
<price>$8.95</price>
<description>Light Belgian waffles covered with an assortment of fresh berries and whipped cream</description>
<calories>900</calories>
</food>

<food>
<name>French Toast</name>
<price>$4.50</price>
<description>Thick slices made from our homemade sourdough bread</description>
<calories>600</calories>
</food>

<food>
<name>Homestyle Breakfast</name>
<price>$6.95</price>
<description>Two eggs, bacon or sausage, toast, and our ever-popular hash browns</description>
<calories>950</calories>
</food>

</breakfast_menu>
```
Send a blank `input_params`

You should get an output:

```
<html>
    <body style="font-family:Arial;font-size:12pt;background-color:#EEEEEE">
        <div style="background-color:teal;color:white;padding:4px">
            <span style="font-weight:bold">Belgian Waffles - </span>$5.95
        </div>
        <div style="margin-left:20px;margin-bottom:1em;font-size:10pt">
            <p>Two of our famous Belgian Waffles with plenty of real maple syrup
                <span style="font-style:italic"> (650 calories per serving)</span>
            </p>
        </div>
        <div style="background-color:teal;color:white;padding:4px">
            <span style="font-weight:bold">Strawberry Belgian Waffles - </span>$7.95
        </div>
        <div style="margin-left:20px;margin-bottom:1em;font-size:10pt">
            <p>Light Belgian waffles covered with strawberries and whipped cream
                <span style="font-style:italic"> (900 calories per serving)</span>
            </p>
        </div>
        <div style="background-color:teal;color:white;padding:4px">
            <span style="font-weight:bold">Berry-Berry Belgian Waffles - </span>$8.95
        </div>
        <div style="margin-left:20px;margin-bottom:1em;font-size:10pt">
            <p>Light Belgian waffles covered with an assortment of fresh berries and whipped cream
                <span style="font-style:italic"> (900 calories per serving)</span>
            </p>
        </div>
        <div style="background-color:teal;color:white;padding:4px">
            <span style="font-weight:bold">French Toast - </span>$4.50
        </div>
        <div style="margin-left:20px;margin-bottom:1em;font-size:10pt">
            <p>Thick slices made from our homemade sourdough bread
                <span style="font-style:italic"> (600 calories per serving)</span>
            </p>
        </div>
        <div style="background-color:teal;color:white;padding:4px">
            <span style="font-weight:bold">Homestyle Breakfast - </span>$6.95
        </div>
        <div style="margin-left:20px;margin-bottom:1em;font-size:10pt">
            <p>Two eggs, bacon or sausage, toast, and our ever-popular hash browns
                <span style="font-style:italic"> (950 calories per serving)</span>
            </p>
        </div>
    </body>
</html>
```

## Test API 

`/Gawati/xml/xslt/test` - http get. Returns version number.