package parsers.SAXParser;


import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

// DocumentBuilderFactory is secured from DoS through Entity Expansion by default
public class Tests {


    public static void main(String [] args) {
        // ignore error messages
        System.setErr(new PrintStream(new OutputStream() {
            public void write(int b) {
            }
        }));


        try {
            System.out.println("TESTING SAXParserFactory configurations");
            testDefaultConfig();
            testFactorySetFeatureSecureProcessing();
            testSetFeatureDissalowDoctypeDecl();
            testSetFeatureExternalGeneralEntities();
            testSetFeatureExternalParameterEntities();
            testSetValidating();
            testSetXIncludeAware();
            testSetFeatureXInclude();
            testSetFeatureLoadExternalDTD();

            //SAXParser itself can set properties too
            testSAXParserSetPropertyAccessExternalDTD();
            testSAXParserSetPropertyAccessExternalSchema();
            //testSAXParserSetPropertyAccessExternalStylesheet(); --> property not recognized
        } catch (Exception e) {
            System.out.println(e.getMessage());
            // do nothing
        }
    }

    public static void testDefaultConfig() throws ParserConfigurationException, SAXException {
        System.out.println("Default Config");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        parseAllThree(saxParser);
    }

    public static void testFactorySetFeatureSecureProcessing() throws ParserConfigurationException, SAXException {
        System.out.println("setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        SAXParser saxParser = factory.newSAXParser();
        parseAllThree(saxParser);
    }

    public static void testSetFeatureDissalowDoctypeDecl() throws ParserConfigurationException, SAXException {
        System.out.println("setFeature(\"http://apache.org/xml/features/disallow-doctype-decl\", true)");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        SAXParser saxParser = factory.newSAXParser();
        parseAllThree(saxParser);
    }

    public static void testSetFeatureExternalGeneralEntities() throws ParserConfigurationException, SAXException {
        System.out.println("setFeature(\"http://xml.org/sax/features/external-general-entities\", false)");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
        SAXParser saxParser = factory.newSAXParser();
        parseAllThree(saxParser);
    }

    public static void testSetFeatureExternalParameterEntities() throws ParserConfigurationException, SAXException {
        System.out.println("setFeature(\"http://xml.org/sax/features/external-parameter-entities\", false)");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
        SAXParser saxParser = factory.newSAXParser();
        parseAllThree(saxParser);
    }

    public static void testSetValidating() throws ParserConfigurationException, SAXException {
        System.out.println("setValidating(false)");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setValidating(false);
        SAXParser saxParser = factory.newSAXParser();
        parseAllThree(saxParser);
    }

    public static void testSetXIncludeAware() throws ParserConfigurationException, SAXException {
        System.out.println("setXIncludeAware(false)");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setXIncludeAware(false);
        SAXParser saxParser = factory.newSAXParser();
        parseAllThree(saxParser);
    }

    public static void testSetFeatureXInclude() throws ParserConfigurationException, SAXException {
        System.out.println("setFeature(\"http://apache.org/xml/features/xinclude\", false)");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/xinclude", false);
        SAXParser saxParser = factory.newSAXParser();
        parseAllThree(saxParser);
    }

    public static void testSetFeatureLoadExternalDTD() throws ParserConfigurationException, SAXException {
        System.out.println("setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false)");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        SAXParser saxParser = factory.newSAXParser();
        parseAllThree(saxParser);
    }

    public static void testSAXParserSetPropertyAccessExternalDTD() throws ParserConfigurationException, SAXException {
        System.out.println("SAXParser.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, \"\");");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
        parseAllThree(saxParser);
    }

    public static void testSAXParserSetPropertyAccessExternalSchema() throws ParserConfigurationException, SAXException {
        System.out.println("SAXParser.setProperty(ACCESS_EXTERNAL_SCHEMA, \"\");");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
        parseAllThree(saxParser);
    }

    public static void testSAXParserSetPropertyAccessExternalStylesheet() throws ParserConfigurationException, SAXException {
        System.out.println("SAXParser.setProperty(ACCESS_EXTERNAL_STYLESHEET, \"\");");
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.setProperty(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
        parseAllThree(saxParser);
    }

    /*
        Parse the test files
     */

    public static void parseAllThree(SAXParser saxParser){
        parseXmlBomb(saxParser);
        parseInputWithSchema(saxParser);
        parseInputWithStylesheet(saxParser);
        parseInputWithParameterEntity(saxParser);
    }

    public static void parseXmlBomb(SAXParser saxParser){
        try {
            File input = new File("payloads/input-dos/xml-bomb.xml");
            saxParser.parse(input, new DefaultHandler());
            System.out.println("    Parse XML Bomb: Secure");
        } catch (Exception e) {
            if (e.getMessage().equals("JAXP00010001: The parser has encountered more than \"64000\" entity expansions in this document; this is the limit imposed by the JDK.")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse XML Bomb: Insecure");
            } else if(e.getMessage().contains("DOCTYPE is disallowed")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse XML Bomb: Secure");
            }else{
                System.out.println(e.getMessage());
            }
        }

    }

    public static void parseInputWithSchema(SAXParser saxParser){
        try {
            File input = new File("payloads/input-with-schema/input.xml");
            saxParser.parse(input, new DefaultHandler());
            System.out.println("    Parse Input With Schema: Secure");
        } catch (Exception e) {
            if(e.getMessage().contains("Connection refused")){
                System.out.println("    Parse Input With Schema: Insecure");
            } else if(e.getMessage().contains("External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Input With Schema: Secure");
            } else if(e.getMessage().contains("DOCTYPE is disallowed")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Input With Schema: Secure");
            }else{
                System.out.println(e.getMessage());
            }
        }
    }

    public static void parseInputWithStylesheet(SAXParser saxParser){
        try {
            File input = new File("payloads/input-with-stylesheet/input.xml");
            saxParser.parse(input, new DefaultHandler());
            System.out.println("    Parse Input With Stylesheet: Secure");
        } catch (Exception e) {
            if(e.getMessage().contains("Connection refused")){
                System.out.println("    Parse Input With Stylesheet: Insecure");
            } else if(e.getMessage().contains("External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Input With Stylesheet: Secure");
            } else if(e.getMessage().contains("DOCTYPE is disallowed")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Input With Stylesheet: Secure");
            }else{
                System.out.println(e.getMessage());
            }
        }
    }

    public static void parseInputWithParameterEntity(SAXParser dBuilder){
        try {
            File input = new File("payloads/input-with-parameter-entity/input.xml");
            dBuilder.parse(input, new DefaultHandler());
            System.out.println("    Parse Xml Input With Parameter Entity: Secure");
        } catch (Exception e){
            if(e.getMessage().contains("Connection refused")){
                System.out.println("    Parse Xml Input With Parameter Entity: Insecure");
            } else if(e.getMessage().contains("External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Xml Input With Parameter Entity: Secure");
            } else if(e.getMessage().contains("DOCTYPE is disallowed")){
                System.out.println("        Exception: " + e.getMessage());
                System.out.println("    Parse Xml Input With Parameter Entity: Secure");
            }
            else {
                System.out.println(e.getMessage());
            }
        }
    }
}