//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.03.28 at 12:33:07 CET 
//


package be.ac.ulg.montefiore.run.totem.scenario.model.jaxb;


/**
 * Java content class for anonymous complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/home/hakan/svn-totem/totem/trunk/run-totem/src/resources/scenario/Scenario-v1_3.xsd line 382)
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;extension base="{http://jaxb.model.scenario.totem.run.montefiore.ulg.ac.be}BaseLSPCreationType">
 *       &lt;choice>
 *         &lt;element name="Detour">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="methodType" type="{http://jaxb.model.scenario.totem.run.montefiore.ulg.ac.be}methodType" default="GLOBAL" />
 *                 &lt;attribute name="protectedLSP" use="required" type="{http://jaxb.model.scenario.totem.run.montefiore.ulg.ac.be}lspIdType" />
 *                 &lt;attribute name="protectionType" type="{http://jaxb.model.scenario.totem.run.montefiore.ulg.ac.be}protectionType" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Bypass">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="protectedLink" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;attribute name="linkId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="dst" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface LSPBackupCreationType
    extends be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.BaseLSPCreationType
{


    /**
     * Gets the value of the bypass property.
     * 
     * @return
     *     possible object is
     *     {@link be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.LSPBackupCreationType.BypassType}
     */
    be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.LSPBackupCreationType.BypassType getBypass();

    /**
     * Sets the value of the bypass property.
     * 
     * @param value
     *     allowed object is
     *     {@link be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.LSPBackupCreationType.BypassType}
     */
    void setBypass(be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.LSPBackupCreationType.BypassType value);

    boolean isSetBypass();

    void unsetBypass();

    /**
     * Gets the value of the detour property.
     * 
     * @return
     *     possible object is
     *     {@link be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.LSPBackupCreationType.DetourType}
     */
    be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.LSPBackupCreationType.DetourType getDetour();

    /**
     * Sets the value of the detour property.
     * 
     * @param value
     *     allowed object is
     *     {@link be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.LSPBackupCreationType.DetourType}
     */
    void setDetour(be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.LSPBackupCreationType.DetourType value);

    boolean isSetDetour();

    void unsetDetour();


    /**
     * Java content class for anonymous complex type.
     * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/home/hakan/svn-totem/totem/trunk/run-totem/src/resources/scenario/Scenario-v1_3.xsd line 394)
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="protectedLink" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;attribute name="linkId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="dst" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     */
    public interface BypassType {


        /**
         * Gets the value of the dst property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getDst();

        /**
         * Sets the value of the dst property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setDst(java.lang.String value);

        boolean isSetDst();

        void unsetDst();

        /**
         * Gets the value of the ProtectedLink property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ProtectedLink property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProtectedLink().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.LSPBackupCreationType.BypassType.ProtectedLinkType}
         * 
         */
        java.util.List getProtectedLink();

        boolean isSetProtectedLink();

        void unsetProtectedLink();


        /**
         * Java content class for anonymous complex type.
         * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/home/hakan/svn-totem/totem/trunk/run-totem/src/resources/scenario/Scenario-v1_3.xsd line 397)
         * <p>
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;attribute name="linkId" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         */
        public interface ProtectedLinkType {


            /**
             * Gets the value of the linkId property.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String}
             */
            java.lang.String getLinkId();

            /**
             * Sets the value of the linkId property.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String}
             */
            void setLinkId(java.lang.String value);

            boolean isSetLinkId();

            void unsetLinkId();

        }

    }


    /**
     * Java content class for anonymous complex type.
     * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/home/hakan/svn-totem/totem/trunk/run-totem/src/resources/scenario/Scenario-v1_3.xsd line 387)
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="methodType" type="{http://jaxb.model.scenario.totem.run.montefiore.ulg.ac.be}methodType" default="GLOBAL" />
     *       &lt;attribute name="protectedLSP" use="required" type="{http://jaxb.model.scenario.totem.run.montefiore.ulg.ac.be}lspIdType" />
     *       &lt;attribute name="protectionType" type="{http://jaxb.model.scenario.totem.run.montefiore.ulg.ac.be}protectionType" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     */
    public interface DetourType {


        /**
         * Gets the value of the methodType property.
         * 
         * @return
         *     possible object is
         *     {@link be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.MethodType}
         */
        be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.MethodType getMethodType();

        /**
         * Sets the value of the methodType property.
         * 
         * @param value
         *     allowed object is
         *     {@link be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.MethodType}
         */
        void setMethodType(be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.MethodType value);

        boolean isSetMethodType();

        void unsetMethodType();

        /**
         * Gets the value of the protectedLSP property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getProtectedLSP();

        /**
         * Sets the value of the protectedLSP property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setProtectedLSP(java.lang.String value);

        boolean isSetProtectedLSP();

        void unsetProtectedLSP();

        /**
         * Gets the value of the protectionType property.
         * 
         * @return
         *     possible object is
         *     {@link be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.ProtectionType}
         */
        be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.ProtectionType getProtectionType();

        /**
         * Sets the value of the protectionType property.
         * 
         * @param value
         *     allowed object is
         *     {@link be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.ProtectionType}
         */
        void setProtectionType(be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.ProtectionType value);

        boolean isSetProtectionType();

        void unsetProtectionType();

    }

}
