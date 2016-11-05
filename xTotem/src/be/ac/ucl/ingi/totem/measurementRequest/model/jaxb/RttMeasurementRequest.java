//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.03.16 at 03:54:53 CET 
//


package be.ac.ucl.ingi.totem.measurementRequest.model.jaxb;


/**
 * Java content class for rttMeasurementRequest element declaration.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/biom/biom1/tdekens/workspace/rtt/xml/measurementRequest.xsd line 13)
 * <p>
 * <pre>
 * &lt;element name="rttMeasurementRequest">
 *   &lt;complexType>
 *     &lt;complexContent>
 *       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *         &lt;sequence>
 *           &lt;element name="requestList" maxOccurs="unbounded" minOccurs="0">
 *             &lt;complexType>
 *               &lt;complexContent>
 *                 &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                   &lt;sequence>
 *                     &lt;element name="subnetDNS" maxOccurs="unbounded">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="subnet" type="{}subnet"/>
 *                               &lt;element name="dnsServer" type="{}dnsServer" maxOccurs="unbounded"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                     &lt;element name="request" maxOccurs="unbounded">
 *                       &lt;complexType>
 *                         &lt;complexContent>
 *                           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                             &lt;sequence>
 *                               &lt;element name="startTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *                               &lt;element name="poissonParameters">
 *                                 &lt;complexType>
 *                                   &lt;complexContent>
 *                                     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                       &lt;sequence>
 *                                         &lt;element name="poissonDistribution" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                                         &lt;element name="numberOfObservations" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *                                         &lt;element name="lambda" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                                         &lt;element name="timingUnits" type="{}delayUnits"/>
 *                                         &lt;element name="takeFirstMeasureDirectly" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                                       &lt;/sequence>
 *                                     &lt;/restriction>
 *                                   &lt;/complexContent>
 *                                 &lt;/complexType>
 *                               &lt;/element>
 *                               &lt;element name="measurementMethod" type="{}rttMeasurementMethod"/>
 *                               &lt;element name="numberOfQueries" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *                               &lt;element name="randomizeDnsInList" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *                             &lt;/sequence>
 *                           &lt;/restriction>
 *                         &lt;/complexContent>
 *                       &lt;/complexType>
 *                     &lt;/element>
 *                   &lt;/sequence>
 *                 &lt;/restriction>
 *               &lt;/complexContent>
 *             &lt;/complexType>
 *           &lt;/element>
 *         &lt;/sequence>
 *       &lt;/restriction>
 *     &lt;/complexContent>
 *   &lt;/complexType>
 * &lt;/element>
 * </pre>
 * 
 */
public interface RttMeasurementRequest
    extends javax.xml.bind.Element, be.ac.ucl.ingi.totem.measurementRequest.model.jaxb.RttMeasurementRequestType
{


}
