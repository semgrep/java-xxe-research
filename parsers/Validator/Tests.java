package parsers.Validator;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.xml.XMLConstants;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

public class Tests {
  public static void main(String [] args) {
    // ignore error messages
    System.setErr(new PrintStream(new OutputStream() {
        public void write(int b) {
        }
    }));

    try {
      System.out.println("TESTING Validator configurations");
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

  public static void testDefaultConfig() throws SAXException {
    System.out.println("Default Config");
    File xsdFile = new File("payloads/input-with-schema/ok-input-schema.xsd");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = sf.newSchema(xsdFile);
    Validator validator = schema.newValidator();
    parseAll(validator);
  }

  public static void testSetFeatureSecreProcessing() throws SAXException {
    System.out.println("setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)");
    File xsdFile = new File("payloads/input-with-schema/ok-input-schema.xsd");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = sf.newSchema(xsdFile);
    Validator validator = schema.newValidator();
    validator.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
    parseAll(validator);
  }

  public static void testAccessExternalDTD() throws SAXException {
    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, \"\")");
    File xsdFile = new File("payloads/input-with-schema/ok-input-schema.xsd");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = sf.newSchema(xsdFile);
    Validator validator = schema.newValidator();
    validator.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    parseAll(validator);
  }

  public static void testAccessExternalSchema() throws SAXException {
    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, \"\")");
    File xsdFile = new File("payloads/input-with-schema/ok-input-schema.xsd");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    Schema schema = sf.newSchema(xsdFile);
    Validator validator = schema.newValidator();
    validator.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
    parseAll(validator);
  }

  public static void testAccessExternalStylesheet() throws TransformerConfigurationException {
    System.out.println("ACCESS_EXTERNAL_STYLESHEET - n/a");
  }

  public static void testDisallowDoctypeDecl() {
    System.out.println("setFeature(\"http://apache.org/xml/features/disallow-doctype-decl\", true)");
    try {
      File xsdFile = new File("payloads/input-with-schema/ok-input-schema.xsd");
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      parseAll(validator);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testExternalGeneralEntities() {
    System.out.println("setFeature(\"http://xml.org/sax/features/external-general-entities\", false)");
    try {
      File xsdFile = new File("payloads/input-with-schema/ok-input-schema.xsd");
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setFeature("http://xml.org/sax/features/external-general-entities", false);
      parseAll(validator);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testExternalParameterEntities() {
    System.out.println("setFeature(\"http://xml.org/sax/features/external-parameter-entities\", false)");
    try {
      File xsdFile = new File("payloads/input-with-schema/ok-input-schema.xsd");
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
      parseAll(validator);
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
      File xsdFile = new File("payloads/input-with-schema/ok-input-schema.xsd");
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setFeature("http://apache.org/xml/features/xinclude", false);
      parseAll(validator);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testLoadExternalDTD() {
    System.out.println("setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false)");
    try {
      File xsdFile = new File("payloads/input-with-schema/ok-input-schema.xsd");
      SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
      parseAll(validator);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /* Parsers */

  public static void parseAll(Validator validator) {
    parseXmlBomb(validator);
    parseInputWithSchema(validator);
    parseInputWithStylesheet(validator);
    parseInputWithParameterEntity(validator);
  }

  public static void parseXmlBomb(Validator validator) {
    try {
        System.out.print("    Parse XML Bomb: ");
        File xmlFile = new File("payloads/input-dos/xml-bomb.xml");
        validator.validate(new StreamSource(xmlFile));
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

  public static void parseInputWithSchema(Validator validator) {
    try {
        System.out.print("    Parse Xml Input With Schema: ");
        File xmlFile = new File("payloads/input-with-schema/input.xml");
        validator.validate(new StreamSource(xmlFile));
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

  public static void parseInputWithStylesheet(Validator validator) {
    System.out.print("    Parse Xml Input With Stylesheet: ");
    System.out.println("n/a");
  }

  public static void parseInputWithParameterEntity(Validator validator){
    try {
      System.out.print("    Parse Xml Input With Parameter Entity: ");
      File xmlFile = new File("payloads/input-with-stylesheet/input-parameter-entity.xml");
      validator.validate(new StreamSource(xmlFile));
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
