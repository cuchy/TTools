//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.02.29 at 02:59:16 CET 
//


package be.ac.ulg.montefiore.run.totem.domain.model.jaxb;


/**
 * Java content class for linkType.
 *  <p>The following schema fragment specifies the expected content contained within this java content object.
 * <p>
 * <pre>
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *   &lt;enumeration value="INTRA"/>
 *   &lt;enumeration value="INTER"/>
 *   &lt;enumeration value="ACCESS"/>
 *   &lt;enumeration value="PEERING"/>
 *   &lt;enumeration value="VIRTUAL"/>
 * &lt;/restriction>
 * </pre>
 * 
 */
public class LinkType {

    private final static java.util.Map valueMap = new java.util.HashMap();
    public final static java.lang.String _INTRA = com.sun.xml.bind.DatatypeConverterImpl.installHook("INTRA");
    public final static be.ac.ulg.montefiore.run.totem.domain.model.jaxb.LinkType INTRA = new be.ac.ulg.montefiore.run.totem.domain.model.jaxb.LinkType(_INTRA);
    public final static java.lang.String _INTER = com.sun.xml.bind.DatatypeConverterImpl.installHook("INTER");
    public final static be.ac.ulg.montefiore.run.totem.domain.model.jaxb.LinkType INTER = new be.ac.ulg.montefiore.run.totem.domain.model.jaxb.LinkType(_INTER);
    public final static java.lang.String _ACCESS = com.sun.xml.bind.DatatypeConverterImpl.installHook("ACCESS");
    public final static be.ac.ulg.montefiore.run.totem.domain.model.jaxb.LinkType ACCESS = new be.ac.ulg.montefiore.run.totem.domain.model.jaxb.LinkType(_ACCESS);
    public final static java.lang.String _PEERING = com.sun.xml.bind.DatatypeConverterImpl.installHook("PEERING");
    public final static be.ac.ulg.montefiore.run.totem.domain.model.jaxb.LinkType PEERING = new be.ac.ulg.montefiore.run.totem.domain.model.jaxb.LinkType(_PEERING);
    public final static java.lang.String _VIRTUAL = com.sun.xml.bind.DatatypeConverterImpl.installHook("VIRTUAL");
    public final static be.ac.ulg.montefiore.run.totem.domain.model.jaxb.LinkType VIRTUAL = new be.ac.ulg.montefiore.run.totem.domain.model.jaxb.LinkType(_VIRTUAL);
    private final java.lang.String lexicalValue;
    private final java.lang.String value;

    protected LinkType(java.lang.String v) {
        value = v;
        lexicalValue = v;
        valueMap.put(v, this);
    }

    public java.lang.String toString() {
        return lexicalValue;
    }

    public java.lang.String getValue() {
        return value;
    }

    public final int hashCode() {
        return super.hashCode();
    }

    public final boolean equals(java.lang.Object o) {
        return super.equals(o);
    }

    public static be.ac.ulg.montefiore.run.totem.domain.model.jaxb.LinkType fromValue(java.lang.String value) {
        be.ac.ulg.montefiore.run.totem.domain.model.jaxb.LinkType t = ((be.ac.ulg.montefiore.run.totem.domain.model.jaxb.LinkType) valueMap.get(value));
        if (t == null) {
            throw new java.lang.IllegalArgumentException();
        } else {
            return t;
        }
    }

    public static be.ac.ulg.montefiore.run.totem.domain.model.jaxb.LinkType fromString(java.lang.String str) {
        return fromValue(str);
    }

}
