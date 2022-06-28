package parsers.SAXReader;

import org.dom4j.io.SAXReader;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
// import org.xml.sax.helpers.XMLReaderFactory;

public class SAXReaderWithXMLReaderInConstructor {
  public static void main(String [] args) {
    // ignore error messages
    System.setErr(new PrintStream(new OutputStream() {
        public void write(int b) {
        }
    }));

    try {
      System.out.println("TESTING new SAXReader(XMLReader) with XMLReader configurations");
      testDefaultConfig();
      testSetFeatureSecreProcessing();
      testAccessExternalDTD();
      testAccessExternalSchema();
      testAccessExternalStylesheet();
      testDisallowDoctypeDecl();
      testExternalGeneralEntities();
      testExternalParameterEntities();
      testSetValidating();
      testSetExpandEntities();
      testSetXIncludeAware();
      testXInclude();
      testLoadExternalDTD();
    } catch (Exception e) {
        System.out.println(e.getMessage());
        // do nothing
    }
  }

  public static void testDefaultConfig() throws SAXException, ParserConfigurationException {
    System.out.println("Default Config");
    // XMLReader reader = XMLReaderFactory.createXMLReader();
    XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
    parseAll(reader);
  }

  public static void testSetFeatureSecreProcessing() throws SAXException, ParserConfigurationException {
    System.out.println("setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)");
    // XMLReader reader = XMLReaderFactory.createXMLReader();
    XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
    reader.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
    parseAll(reader);
  }

  public static void testAccessExternalDTD() throws SAXException, ParserConfigurationException {
    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, \"\")");
    // XMLReader reader = XMLReaderFactory.createXMLReader();
    XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
    reader.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    parseAll(reader);
  }

  public static void testAccessExternalSchema() throws SAXException, ParserConfigurationException {
    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, \"\")");
    // XMLReader reader = XMLReaderFactory.createXMLReader();
    XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
    reader.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
    parseAll(reader);
  }

  public static void testAccessExternalStylesheet() throws SAXException, ParserConfigurationException {
    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, \"\") - n/a");
    // XMLReader reader = XMLReaderFactory.createXMLReader();
    /*XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
    reader.setProperty(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
    parseAll(reader);*/
  }

  public static void testDisallowDoctypeDecl() {
    System.out.println("setFeature(\"http://apache.org/xml/features/disallow-doctype-decl\", true)");
    try {
      // XMLReader reader = XMLReaderFactory.createXMLReader();
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      reader.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      parseAll(reader);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testExternalGeneralEntities() {
    System.out.println("setFeature(\"http://xml.org/sax/features/external-general-entities\", false)");
    try {
      // XMLReader reader = XMLReaderFactory.createXMLReader();
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      reader.setFeature("http://xml.org/sax/features/external-general-entities", false);
      parseAll(reader);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testExternalParameterEntities() {
    System.out.println("setFeature(\"http://xml.org/sax/features/external-parameter-entities\", false)");
    try {
      // XMLReader reader = XMLReaderFactory.createXMLReader();
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      reader.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
      parseAll(reader);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testSetValidating() {
    System.out.println("setValidating(false) - n/a");
  }

  public static void testSetExpandEntities() {
    System.out.println("setExpandEntityReferences(false) - n/a");
  }

  public static void testSetXIncludeAware() {
    System.out.println("setXIncludeAware(false) - n/a");
  }

  public static void testXInclude() {
    System.out.println("setFeature(\"http://apache.org/xml/features/xinclude\", false)");
    try {
      // XMLReader reader = XMLReaderFactory.createXMLReader();
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      reader.setFeature("http://apache.org/xml/features/xinclude", false);
      parseAll(reader);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testLoadExternalDTD() {
    System.out.println("setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false)");
    try {
      // XMLReader reader = XMLReaderFactory.createXMLReader();
      XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
      reader.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
      parseAll(reader);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /* Parsers */

  public static void parseAll(XMLReader reader) {
    parseXmlBomb(reader);
    parseInputWithSchema(reader);
    parseInputWithStylesheet(reader);
    parseInputWithParameterEntity(reader);
  }

  public static void parseXmlBomb(XMLReader reader) {
    try {
        System.out.print("    Parse XML Bomb: ");
        File xmlFile = new File("payloads/input-dos/xml-bomb.xml");
        InputStream is = new FileInputStream(xmlFile);

        SAXReader saxReader = new SAXReader(reader);
        saxReader.read(is);
        System.out.println("Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("entity expansions in this document; this is the limit imposed by the JDK.")){
          System.out.println("Insecure !");
      } else if(e.getMessage().contains("DOCTYPE is disallowed")){
          System.out.println("Secure !");
      }else{
          System.out.println(e.getMessage());
          System.out.println("Insecure !");
      }
    }
  }

  public static void parseInputWithSchema(XMLReader reader) {
    try {
        System.out.print("    Parse Xml Input With Schema: ");
        // File xmlFile = new File("payloads/input-with-schema/input-schema.xsd");
        File xmlFile = new File("payloads/input-with-schema/input.xml");
        InputStream is = new FileInputStream(xmlFile);
        SAXReader saxReader = new SAXReader(reader);
        saxReader.read(is);
        System.out.println("Secure");
    } catch (Exception e) {
        if(e.getMessage().contains("Connection refused")){
            System.out.println("Insecure !");
        } else if(e.getMessage().contains("'http' access is not allowed")){
            System.out.println("Secure !");
        } else if(e.getMessage().contains("DOCTYPE is disallowed")){
            System.out.println("Secure !");
        }else{
            System.out.println(e.getMessage());
        }
    }
  }

  public static void parseInputWithStylesheet(XMLReader reader) {
    try {
        System.out.print("    Parse Xml Input With Stylesheet: ");
        File xmlFile = new File("payloads/input-with-stylesheet/stylesheet.xsl");
        InputStream is = new FileInputStream(xmlFile);
        SAXReader saxReader = new SAXReader(reader);
        saxReader.read(is);
        System.out.println("Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("Connection refused")){
          System.out.println("Insecure !");
      } else if(e.getMessage().contains("'http' access is not allowed")){
          System.out.println("Secure !");
      } else if(e.getMessage().contains("DOCTYPE is disallowed")){
          System.out.println("Secure !");
      }else{
          System.out.println(e.getMessage());
      }
    }
  }

  public static void parseInputWithParameterEntity(XMLReader reader) {
    try {
        System.out.print("    Parse Xml Input With Parameter Entity: ");
        File xmlFile = new File("payloads/input-with-parameter-entity/input.xml");
        InputStream is = new FileInputStream(xmlFile);
        SAXReader saxReader = new SAXReader(reader);
        saxReader.read(is);
        System.out.println("Secure");
    } catch (Exception e){
        if(e.getMessage().contains("Connection refused")){
            System.out.println("Insecure !");
        } else if(e.getMessage().contains("'http' access is not allowed")){
            System.out.println("Secure !");
        } else if(e.getMessage().contains("DOCTYPE is disallowed")){
            System.out.println("Secure !");
        }
        else {
            System.out.println(e.getMessage());
        }
    }
  }
}
