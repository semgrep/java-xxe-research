package parsers.SchemaFactory;

import java.io.File;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.xml.XMLConstants;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public class Tests {
  public static void main(String [] args) {
    // ignore error messages
    System.setErr(new PrintStream(new OutputStream() {
        public void write(int b) {
        }
    }));

    try {
      System.out.println("TESTING SchemaFactory configurations");
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

  public static void testDefaultConfig() {
    System.out.println("Default Config");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    parseAll(sf);
  }

  public static void testSetFeatureSecreProcessing() throws TransformerConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
    System.out.println("setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true)");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    sf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
    parseAll(sf);
  }

  public static void testAccessExternalDTD() throws TransformerConfigurationException, SAXNotRecognizedException, SAXNotSupportedException {
    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, \"\")");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    sf.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, "");
    parseAll(sf);
  }

  public static void testAccessExternalSchema() throws SAXNotRecognizedException, SAXNotSupportedException {
    System.out.println("setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, \"\")");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    sf.setProperty(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
    parseAll(sf);
  }

  public static void testAccessExternalStylesheet() throws TransformerConfigurationException {
    System.out.println("ACCESS_EXTERNAL_STYLESHEET - n/a");
  }

  public static void testDisallowDoctypeDecl() {
    System.out.println("setFeature(\"http://apache.org/xml/features/disallow-doctype-decl\", true)");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    try {
      sf.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
      parseAll(sf);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testExternalGeneralEntities() {
    System.out.println("setFeature(\"http://xml.org/sax/features/external-general-entities\", false)");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    try {
      sf.setFeature("http://xml.org/sax/features/external-general-entities", true);
      parseAll(sf);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testExternalParameterEntities() {
    System.out.println("setFeature(\"http://xml.org/sax/features/external-parameter-entities\", false)");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    try {
      sf.setFeature("http://xml.org/sax/features/external-parameter-entities", true);
      parseAll(sf);
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
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    try {
      sf.setFeature("http://apache.org/xml/features/xinclude", false);
      parseAll(sf);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  public static void testLoadExternalDTD() {
    System.out.println("setFeature(\"http://apache.org/xml/features/nonvalidating/load-external-dtd\", false)");
    SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    try {
      sf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
      parseAll(sf);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  /* Parsers */

  public static void parseAll(SchemaFactory sf) {
    parseXmlBomb(sf);
    parseInputWithSchema(sf);
    parseInputWithStylesheet(sf);
    parseInputWithParameterEntity(sf);
  }

  public static void parseXmlBomb(SchemaFactory sf) {
    try {
        System.out.print("    Parse XML Bomb: ");
        File xmlFile = new File("payloads/input-dos/xml-bomb.xml");
        File xsdFile = new File("payloads/input-with-schema/ok-input-schema.xsd");

        Schema schema = sf.newSchema(xsdFile);
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(xmlFile));

        System.out.println("Secure");
    } catch (Exception e) {
      if (e.getMessage().contains("entity expansions in this document; this is the limit imposed by the JDK.")){
          System.out.println("Insecure");
      } else if(e.getMessage().contains("DOCTYPE is disallowed")){
          System.out.println("Secure");
      }else{
          System.out.println(e.getMessage());
          System.out.println("Insecure");
      }
    }
  }

  public static void parseInputWithSchema(SchemaFactory sf) {
    try {
        System.out.print("    Parse Xml Input With Schema: ");
        File xmlFile = new File("payloads/input-with-schema/input.xml");
        File xsdFile = new File("payloads/input-with-schema/input-schema.xsd");

        Schema schema = sf.newSchema(xsdFile);
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(xmlFile));

        System.out.println("Secure");
    } catch (Exception e) {
        if(e.getMessage().contains("Connection refused")){
            System.out.println("Insecure");
        } else if(e.getMessage().contains("'http' access is not allowed")){
            System.out.println("Secure");
        } else if(e.getMessage().contains("DOCTYPE is disallowed")){
            System.out.println("Secure");
        }else{
            System.out.println(e.getMessage());
        }
    }
  }

  public static void parseInputWithStylesheet(SchemaFactory sf) {
    System.out.print("    Parse Xml Input With Stylesheet: ");
    System.out.println("n/a");
  }

  public static void parseInputWithParameterEntity(SchemaFactory sf){
    try {
      System.out.print("    Parse Xml Input With Parameter Entity: ");
      File xmlFile = new File("payloads/input-with-stylesheet/input-parameter-entity.xml");
      File xsdFile = new File("payloads/input-with-schema/input-schema.xsd");

      Schema schema = sf.newSchema(xsdFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(xmlFile));
      System.out.println("Secure");
    } catch (Exception e){
        if(e.getMessage().contains("Connection refused")){
            System.out.println("Insecure");
        } else if(e.getMessage().contains("'http' access is not allowed")){
            System.out.println("Secure");
        } else if(e.getMessage().contains("DOCTYPE is disallowed")){
            System.out.println("Secure");
        }
        else {
            System.out.println(e.getMessage());
        }
    }
  }
}
