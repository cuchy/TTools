//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2005.08.31 at 12:16:28 CEST 
//


package be.ac.ulg.montefiore.run.totem.repository.SAMTE.scenario.jaxb;


/**
 * Java content class for anonymous complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/home/skivee/sync/run/cvs-home/run-totem/src/resources/scenario/SAMTE-Scenario-v1_0.xsd line 71)
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://jaxb.model.scenario.totem.run.montefiore.ulg.ac.be}param" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="name" use="required" type="{http://jaxb.scenario.SAMTE.repository.totem.run.montefiore.ulg.ac.be}objectiveType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ObjectiveFunctionType {


    /**
     * Gets the value of the Param property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Param property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParam().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.ParamType}
     * {@link be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.Param}
     * 
     */
    java.util.List getParam();

    boolean isSetParam();

    void unsetParam();

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link be.ac.ulg.montefiore.run.totem.repository.SAMTE.scenario.jaxb.ObjectiveType}
     */
    be.ac.ulg.montefiore.run.totem.repository.SAMTE.scenario.jaxb.ObjectiveType getName();

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link be.ac.ulg.montefiore.run.totem.repository.SAMTE.scenario.jaxb.ObjectiveType}
     */
    void setName(be.ac.ulg.montefiore.run.totem.repository.SAMTE.scenario.jaxb.ObjectiveType value);

    boolean isSetName();

    void unsetName();

}
