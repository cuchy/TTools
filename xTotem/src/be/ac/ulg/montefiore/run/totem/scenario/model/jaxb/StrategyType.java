//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.03.28 at 12:33:07 CET 
//


package be.ac.ulg.montefiore.run.totem.scenario.model.jaxb;


/**
 * Java content class for strategyType.
 *  <p>The following schema fragment specifies the expected content contained within this java content object.
 * <p>
 * <pre>
 * &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *   &lt;enumeration value="IP"/>
 *   &lt;enumeration value="BIS"/>
 *   &lt;enumeration value="IS"/>
 *   &lt;enumeration value="OVERLAY"/>
 * &lt;/restriction>
 * </pre>
 * 
 */
public class StrategyType {

    private final static java.util.Map valueMap = new java.util.HashMap();
    public final static java.lang.String _IP = com.sun.xml.bind.DatatypeConverterImpl.installHook("IP");
    public final static be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.StrategyType IP = new be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.StrategyType(_IP);
    public final static java.lang.String _BIS = com.sun.xml.bind.DatatypeConverterImpl.installHook("BIS");
    public final static be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.StrategyType BIS = new be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.StrategyType(_BIS);
    public final static java.lang.String _IS = com.sun.xml.bind.DatatypeConverterImpl.installHook("IS");
    public final static be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.StrategyType IS = new be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.StrategyType(_IS);
    public final static java.lang.String _OVERLAY = com.sun.xml.bind.DatatypeConverterImpl.installHook("OVERLAY");
    public final static be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.StrategyType OVERLAY = new be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.StrategyType(_OVERLAY);
    private final java.lang.String lexicalValue;
    private final java.lang.String value;

    protected StrategyType(java.lang.String v) {
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

    public static be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.StrategyType fromValue(java.lang.String value) {
        be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.StrategyType t = ((be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.StrategyType) valueMap.get(value));
        if (t == null) {
            throw new java.lang.IllegalArgumentException();
        } else {
            return t;
        }
    }

    public static be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.StrategyType fromString(java.lang.String str) {
        return fromValue(str);
    }

}
