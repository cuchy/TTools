//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v@@BUILD_VERSION@@ 
// 	See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 	Any modifications to this file will be lost upon recompilation of the source schema. 
// 	Generated on: 2005.06.24 � 02:45:37 CEST 
//


package be.ac.ucl.ingi.totem.scenario.model.jaxb;


/**
 * Java content class for anonymous complex type.
 * 	<p>The following schema fragment specifies the expected 	content contained within this java content object. 	(defined at file:/shared_dir/belial/users/lepropre/eclipse/workspace/run-totem/src/resources/scenario/CBGP-Scenario-v1_0.xsd line 40)
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://jaxb.model.scenario.totem.run.montefiore.ulg.ac.be}eventType">
 *       &lt;attribute name="file" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="node" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface CBGPLoadRibType
    extends be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.EventType
{


    /**
     * Gets the value of the node property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getNode();

    /**
     * Sets the value of the node property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setNode(java.lang.String value);

    boolean isSetNode();

    void unsetNode();

    /**
     * Gets the value of the file property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getFile();

    /**
     * Sets the value of the file property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setFile(java.lang.String value);

    boolean isSetFile();

    void unsetFile();

}
