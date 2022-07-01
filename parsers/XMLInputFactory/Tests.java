package parsers.XMLInputFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.xml.XMLConstants;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.xml.sax.SAXException;


public class Tests {
  public static void main(String[] args) {
    // ignore error messages
    System.setErr(new PrintStream(new OutputStream() {
      public void write(int b) {
      }
    }));

    try {
      System.out.println("TESTING XMLInputFactory configurations");
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
    XMLInputFactory factory = XMLInputFactory.newInstance();
    parseAll(factory);
  }

  public static void testSetFeatureSecreProcessing() throws SAXException, ParserConfigurationException {
    System.out.println("setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true) - n/a");
  }

  public static void testAccessExternalDTD() throws SAXException, ParserConfigurationException {
    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, \"\")");
    XMLInputFactory factory = XMLInputFactory.newInstance();
    factory.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    parseAll(factory);
  }

  public static void testAccessExternalSchema() throws SAXException, ParserConfigurationException {
    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, \"\")");
    XMLInputFactory factory = XMLInputFactory.newInstance();
    factory.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
    parseAll(factory);
  }

  public static void testAccessExternalStylesheet() throws SAXException, ParserConfigurationException {
    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, \"\") - n/a");
  }

  public static void testDisallowDoctypeDecl() {
    System.out.println("setFeature(\"http://apache.org/xml/features/disallow-doctype-decl\", true) - n/a");
  }

  public static void testExternalGeneralEntities() {
    System.out.println("setFeature(\"http://xml.org/sax/features/external-general-entities\", false) - n/a");
  }

  public static void testExternalParameterEntities() {
    System.out.println("setFeature(\"http://xml.org/sax/features/external-parameter-entities\", false) - n/a");
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
    System.out.println("setFeature(\"http://apache.org/xml/features/xinclude\", false) - n/a");
  }

  public static void testLoadExternalDTD() {
    System.out.println("setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false) - n/a");
  }

  /*
   * Parse the test files
   */

  public static void parseAll(XMLInputFactory factory) {
    parseXmlBomb(factory);
    parseXmlDtd(factory);
    parseXmlParameterEntity(factory);
    parseSchemaDtd(factory);
    parseSchemaImport(factory);
    parseSchemaInclude(factory);
    parseStylesheetDtd(factory);
    parseStylesheetImport(factory);
    parseStylesheetInclude(factory);
    parseStylesheetDocument(factory);
  }

  public static void parseXmlBomb(XMLInputFactory factory) {
    try {
      File xmlFile = new File("payloads-new/xml-attacks/xml-bomb.xml");
      InputStream is = new FileInputStream(xmlFile);
      XMLStreamReader xsr = factory.createXMLStreamReader(is);
      xsr.next();
      System.out.println("    Parse XML Bomb: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains(
          "The parser has encountered more than \"64000\" entity expansions in this document; this is the limit imposed by the JDK")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse XML Bomb: Insecure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse XML Bomb: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void parseXmlDtd(XMLInputFactory factory) {
    try {
      File xmlFile = new File("payloads-new/xml-attacks/input-dtd.xml");
      InputStream is = new FileInputStream(xmlFile);
      XMLStreamReader xsr = factory.createXMLStreamReader(is);
      xsr.next();
      System.out.println("    Parse XML Dtd: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("Connection refused")) {
        System.out.println("    Parse XML Dtd: Insecure");
      } else if (e.getMessage().contains(
          "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse XML Dtd: Secure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse XML Dtd: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void parseXmlParameterEntity(XMLInputFactory factory) {
    try {
      File xmlFile = new File("payloads-new/xml-attacks/input-with-parameter-entity.xml");
      InputStream is = new FileInputStream(xmlFile);
      XMLStreamReader xsr = factory.createXMLStreamReader(is);
      xsr.next();
      System.out.println("    Parse XML Parameter Entity: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("Connection refused")) {
        System.out.println("    Parse XML Parameter Entity: Insecure");
      } else if (e.getMessage().contains(
          "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse XML Parameter Entity: Secure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse XML Parameter Entity: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void parseSchemaDtd(XMLInputFactory factory) {
    try {
      File xsdFile = new File("payloads-new/xsd-schema-attacks/schema-dtd.xsd");
      InputStream is = new FileInputStream(xsdFile);
      XMLStreamReader xsr = factory.createXMLStreamReader(is);
      xsr.next();
      System.out.println("    Parse Schema Dtd: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("Connection refused")) {
        System.out.println("    Parse Schema Dtd: Insecure");
      } else if (e.getMessage().contains(
          "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Schema Dtd: Secure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Schema Dtd: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void parseSchemaImport(XMLInputFactory factory) {
    try {
      File xsdFile = new File("payloads-new/xsd-schema-attacks/schema-import.xsd");
      InputStream is = new FileInputStream(xsdFile);
      XMLStreamReader xsr = factory.createXMLStreamReader(is);
      xsr.next();
      System.out.println("    Parse Schema Import: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("Connection refused")) {
        System.out.println("    Parse Schema Import: Insecure");
      } else if (e.getMessage().contains(
          "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Schema Import: Secure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Schema Import: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void parseSchemaInclude(XMLInputFactory factory) {
    try {
      File xsdFile = new File("payloads-new/xsd-schema-attacks/schema-include.xsd");
      InputStream is = new FileInputStream(xsdFile);
      XMLStreamReader xsr = factory.createXMLStreamReader(is);
      xsr.next();
      System.out.println("    Parse Schema Include: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("Connection refused")) {
        System.out.println("    Parse Schema Include: Insecure");
      } else if (e.getMessage().contains(
          "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Schema Include: Secure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Schema Include: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void parseStylesheetDtd(XMLInputFactory factory) {
    try {
      File style = new File("payloads-new/xsl-stylesheet-attacks/stylesheet-dtd.xsl");
      InputStream is = new FileInputStream(style);
      XMLStreamReader xsr = factory.createXMLStreamReader(is);
      xsr.next();
      System.out.println("    Parse Stylesheet Dtd: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("Connection refused")) {
        System.out.println("    Parse Stylesheet Dtd: Insecure");
      } else if (e.getMessage().contains(
          "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Stylesheet Dtd: Secure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Stylesheet Dtd: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void parseStylesheetDocument(XMLInputFactory factory) {
    try {
      File style = new File("payloads-new/xsl-stylesheet-attacks/stylesheet-document.xsl");
      InputStream is = new FileInputStream(style);
      XMLStreamReader xsr = factory.createXMLStreamReader(is);
      xsr.next();
      System.out.println("    Parse Stylesheet Document: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("Connection refused")) {
        System.out.println("    Parse Stylesheet Document: Insecure");
      } else if (e.getMessage().contains(
          "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Stylesheet Document: Secure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Stylesheet Document: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void parseStylesheetImport(XMLInputFactory factory) {
    try {
      File style = new File("payloads-new/xsl-stylesheet-attacks/stylesheet-import.xsl");
      InputStream is = new FileInputStream(style);
      XMLStreamReader xsr = factory.createXMLStreamReader(is);
      xsr.next();
      System.out.println("    Parse Stylesheet Import: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("Connection refused")) {
        System.out.println("    Parse Stylesheet Import: Insecure");
      } else if (e.getMessage().contains(
          "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Stylesheet Import: Secure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Stylesheet Import: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

  public static void parseStylesheetInclude(XMLInputFactory factory) {
    try {
      File style = new File("payloads-new/xsl-stylesheet-attacks/stylesheet-include.xsl");
      InputStream is = new FileInputStream(style);
      XMLStreamReader xsr = factory.createXMLStreamReader(is);
      xsr.next();
      System.out.println("    Parse Stylesheet Include: Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("Connection refused")) {
        System.out.println("    Parse Stylesheet Include: Insecure");
      } else if (e.getMessage().contains(
          "External Entity: Failed to read external document 'localhost:8090', because 'http' access is not allowed due to restriction set by the accessExternalDTD property.")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Stylesheet Include: Secure");
      } else if (e.getMessage().contains("DOCTYPE is disallowed")) {
        System.out.println("        Exception: " + e.getMessage());
        System.out.println("    Parse Stylesheet Include: Secure");
      } else {
        System.out.println(e.getMessage());
      }
    }
  }

}

