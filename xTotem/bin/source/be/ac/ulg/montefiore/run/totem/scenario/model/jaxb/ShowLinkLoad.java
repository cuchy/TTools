//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.03.28 at 12:33:07 CET 
//


package be.ac.ulg.montefiore.run.totem.scenario.model.jaxb;


/**
 * Java content class for showLinkLoad element declaration.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/home/hakan/svn-totem/totem/trunk/run-totem/src/resources/scenario/Scenario-v1_3.xsd line 480)
 * <p>
 * <pre>
 * &lt;element name="showLinkLoad">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;extension base="{http://jaxb.model.scenario.totem.run.montefiore.ulg.ac.be}ASEventType">
 *         &lt;attribute name="classOfService" type="{http://jaxb.model.scenario.totem.run.montefiore.ulg.ac.be}cosType" />
 *         &lt;attribute name="linkId" type="{http://jaxb.model.scenario.totem.run.montefiore.ulg.ac.be}linkIdType" />
 *         &lt;attribute name="llcId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *         &lt;attribute name="perCos" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *         &lt;attribute name="perLink" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;/extension>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 */
public interface ShowLinkLoad
    extends javax.xml.bind.Element, be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.ShowLinkLoadType
{


}