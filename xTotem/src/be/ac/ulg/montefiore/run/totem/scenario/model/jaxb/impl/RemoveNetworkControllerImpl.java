//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.4-b18-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.03.28 at 12:33:07 CET 
//


package be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.impl;

public class RemoveNetworkControllerImpl
    extends be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.impl.RemoveNetworkControllerTypeImpl
    implements be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.RemoveNetworkController, com.sun.xml.bind.RIElement, com.sun.xml.bind.JAXBObject, be.ac.ulg.montefiore.run.totem.util.jaxb.runtime.UnmarshallableObject, be.ac.ulg.montefiore.run.totem.util.jaxb.runtime.XMLSerializable, be.ac.ulg.montefiore.run.totem.util.jaxb.runtime.ValidatableObject
{

    public final static java.lang.Class version = (be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.impl.JAXBVersion.class);
    private static com.sun.msv.grammar.Grammar schemaFragment;

    private final static java.lang.Class PRIMARY_INTERFACE_CLASS() {
        return (be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.RemoveNetworkController.class);
    }

    public java.lang.String ____jaxb_ri____getNamespaceURI() {
        return "http://jaxb.model.scenario.totem.run.montefiore.ulg.ac.be";
    }

    public java.lang.String ____jaxb_ri____getLocalName() {
        return "removeNetworkController";
    }

    public be.ac.ulg.montefiore.run.totem.util.jaxb.runtime.UnmarshallingEventHandler createUnmarshaller(be.ac.ulg.montefiore.run.totem.util.jaxb.runtime.UnmarshallingContext context) {
        return new be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.impl.RemoveNetworkControllerImpl.Unmarshaller(context);
    }

    public void serializeBody(be.ac.ulg.montefiore.run.totem.util.jaxb.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
        context.startElement("http://jaxb.model.scenario.totem.run.montefiore.ulg.ac.be", "removeNetworkController");
        super.serializeURIs(context);
        context.endNamespaceDecls();
        super.serializeAttributes(context);
        context.endAttributes();
        super.serializeBody(context);
        context.endElement();
    }

    public void serializeAttributes(be.ac.ulg.montefiore.run.totem.util.jaxb.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public void serializeURIs(be.ac.ulg.montefiore.run.totem.util.jaxb.runtime.XMLSerializer context)
        throws org.xml.sax.SAXException
    {
    }

    public java.lang.Class getPrimaryInterface() {
        return (be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.RemoveNetworkController.class);
    }

    public com.sun.msv.verifier.DocumentDeclaration createRawValidator() {
        if (schemaFragment == null) {
            schemaFragment = com.sun.xml.bind.validator.SchemaDeserializer.deserialize((
 "\u00ac\u00ed\u0000\u0005sr\u0000\'com.sun.msv.grammar.trex.ElementPattern\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000"
+"\tnameClasst\u0000\u001fLcom/sun/msv/grammar/NameClass;xr\u0000\u001ecom.sun.msv."
+"grammar.ElementExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002Z\u0000\u001aignoreUndeclaredAttributesL\u0000"
+"\fcontentModelt\u0000 Lcom/sun/msv/grammar/Expression;xr\u0000\u001ecom.sun."
+"msv.grammar.Expression\u00f8\u0018\u0082\u00e8N5~O\u0002\u0000\u0002L\u0000\u0013epsilonReducibilityt\u0000\u0013Lj"
+"ava/lang/Boolean;L\u0000\u000bexpandedExpq\u0000~\u0000\u0003xppp\u0000sr\u0000\u001fcom.sun.msv.gra"
+"mmar.SequenceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\u001dcom.sun.msv.grammar.BinaryExp"
+"\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0004exp1q\u0000~\u0000\u0003L\u0000\u0004exp2q\u0000~\u0000\u0003xq\u0000~\u0000\u0004ppsq\u0000~\u0000\u0007ppsr\u0000\u001dcom.s"
+"un.msv.grammar.ChoiceExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\bppsr\u0000 com.sun.msv.g"
+"rammar.AttributeExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\u0003expq\u0000~\u0000\u0003L\u0000\tnameClassq\u0000~\u0000\u0001xq"
+"\u0000~\u0000\u0004sr\u0000\u0011java.lang.Boolean\u00cd r\u0080\u00d5\u009c\u00fa\u00ee\u0002\u0000\u0001Z\u0000\u0005valuexp\u0000psr\u0000\u001bcom.sun."
+"msv.grammar.DataExp\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\u0002dtt\u0000\u001fLorg/relaxng/datatype/"
+"Datatype;L\u0000\u0006exceptq\u0000~\u0000\u0003L\u0000\u0004namet\u0000\u001dLcom/sun/msv/util/StringPai"
+"r;xq\u0000~\u0000\u0004ppsr\u0000!com.sun.msv.datatype.xsd.TimeType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr"
+"\u0000)com.sun.msv.datatype.xsd.DateTimeBaseType\u0014W\u001a@3\u00a5\u00b4\u00e5\u0002\u0000\u0000xr\u0000*co"
+"m.sun.msv.datatype.xsd.BuiltinAtomicType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000%com.s"
+"un.msv.datatype.xsd.ConcreteType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000\'com.sun.msv.d"
+"atatype.xsd.XSDatatypeImpl\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0003L\u0000\fnamespaceUrit\u0000\u0012Ljava"
+"/lang/String;L\u0000\btypeNameq\u0000~\u0000\u001aL\u0000\nwhiteSpacet\u0000.Lcom/sun/msv/da"
+"tatype/xsd/WhiteSpaceProcessor;xpt\u0000 http://www.w3.org/2001/X"
+"MLSchemat\u0000\u0004timesr\u00005com.sun.msv.datatype.xsd.WhiteSpaceProces"
+"sor$Collapse\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xr\u0000,com.sun.msv.datatype.xsd.WhiteSpa"
+"ceProcessor\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpsr\u00000com.sun.msv.grammar.Expression$N"
+"ullSetExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004ppsr\u0000\u001bcom.sun.msv.util.Stri"
+"ngPair\u00d0t\u001ejB\u008f\u008d\u00a0\u0002\u0000\u0002L\u0000\tlocalNameq\u0000~\u0000\u001aL\u0000\fnamespaceURIq\u0000~\u0000\u001axpq\u0000~\u0000"
+"\u001eq\u0000~\u0000\u001dsr\u0000#com.sun.msv.grammar.SimpleNameClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0002L\u0000\tl"
+"ocalNameq\u0000~\u0000\u001aL\u0000\fnamespaceURIq\u0000~\u0000\u001axr\u0000\u001dcom.sun.msv.grammar.Nam"
+"eClass\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xpt\u0000\u0004timet\u0000\u0000sr\u00000com.sun.msv.grammar.Express"
+"ion$EpsilonExpression\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0004sq\u0000~\u0000\u000f\u0001q\u0000~\u0000,sq\u0000~\u0000\rppsq"
+"\u0000~\u0000\u0011ppsr\u0000#com.sun.msv.datatype.xsd.StringType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001Z\u0000\ri"
+"sAlwaysValidxq\u0000~\u0000\u0017q\u0000~\u0000\u001dt\u0000\u0006stringsr\u00005com.sun.msv.datatype.xsd"
+".WhiteSpaceProcessor$Preserve\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000 \u0001q\u0000~\u0000#sq\u0000~\u0000$q\u0000"
+"~\u00002q\u0000~\u0000\u001dsq\u0000~\u0000&t\u0000\u0004nameq\u0000~\u0000*sq\u0000~\u0000\u000bppsq\u0000~\u0000\rq\u0000~\u0000\u0010psq\u0000~\u0000\u0011ppsr\u0000\"co"
+"m.sun.msv.datatype.xsd.QnameType\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0000xq\u0000~\u0000\u0017q\u0000~\u0000\u001dt\u0000\u0005QNa"
+"meq\u0000~\u0000!q\u0000~\u0000#sq\u0000~\u0000$q\u0000~\u0000=q\u0000~\u0000\u001dsq\u0000~\u0000&t\u0000\u0004typet\u0000)http://www.w3.or"
+"g/2001/XMLSchema-instanceq\u0000~\u0000,sq\u0000~\u0000&t\u0000\u0017removeNetworkControll"
+"ert\u00009http://jaxb.model.scenario.totem.run.montefiore.ulg.ac."
+"besr\u0000\"com.sun.msv.grammar.ExpressionPool\u0000\u0000\u0000\u0000\u0000\u0000\u0000\u0001\u0002\u0000\u0001L\u0000\bexpTab"
+"let\u0000/Lcom/sun/msv/grammar/ExpressionPool$ClosedHash;xpsr\u0000-co"
+"m.sun.msv.grammar.ExpressionPool$ClosedHash\u00d7j\u00d0N\u00ef\u00e8\u00ed\u001c\u0003\u0000\u0003I\u0000\u0005cou"
+"ntB\u0000\rstreamVersionL\u0000\u0006parentt\u0000$Lcom/sun/msv/grammar/Expressio"
+"nPool;xp\u0000\u0000\u0000\u0004\u0001pq\u0000~\u00008q\u0000~\u0000\nq\u0000~\u0000\tq\u0000~\u0000\fx"));
        }
        return new com.sun.msv.verifier.regexp.REDocumentDeclaration(schemaFragment);
    }

    public class Unmarshaller
        extends be.ac.ulg.montefiore.run.totem.util.jaxb.runtime.AbstractUnmarshallingEventHandlerImpl
    {


        public Unmarshaller(be.ac.ulg.montefiore.run.totem.util.jaxb.runtime.UnmarshallingContext context) {
            super(context, "----");
        }

        protected Unmarshaller(be.ac.ulg.montefiore.run.totem.util.jaxb.runtime.UnmarshallingContext context, int startState) {
            this(context);
            state = startState;
        }

        public java.lang.Object owner() {
            return be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.impl.RemoveNetworkControllerImpl.this;
        }

        public void enterElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname, org.xml.sax.Attributes __atts)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  0 :
                        if (("removeNetworkController" == ___local)&&("http://jaxb.model.scenario.totem.run.montefiore.ulg.ac.be" == ___uri)) {
                            context.pushAttributes(__atts, false);
                            state = 1;
                            return ;
                        }
                        break;
                    case  1 :
                        attIdx = context.getAttribute("", "name");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().enterElement(___uri, ___local, ___qname, __atts);
                            return ;
                        }
                        break;
                    case  3 :
                        revertToParentFromEnterElement(___uri, ___local, ___qname, __atts);
                        return ;
                }
                super.enterElement(___uri, ___local, ___qname, __atts);
                break;
            }
        }

        public void leaveElement(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  1 :
                        attIdx = context.getAttribute("", "name");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveElement(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  3 :
                        revertToParentFromLeaveElement(___uri, ___local, ___qname);
                        return ;
                    case  2 :
                        if (("removeNetworkController" == ___local)&&("http://jaxb.model.scenario.totem.run.montefiore.ulg.ac.be" == ___uri)) {
                            context.popAttributes();
                            state = 3;
                            return ;
                        }
                        break;
                }
                super.leaveElement(___uri, ___local, ___qname);
                break;
            }
        }

        public void enterAttribute(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  1 :
                        if (("name" == ___local)&&("" == ___uri)) {
                            spawnHandlerFromEnterAttribute((((be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.impl.RemoveNetworkControllerTypeImpl)be.ac.ulg.montefiore.run.totem.scenario.model.jaxb.impl.RemoveNetworkControllerImpl.this).new Unmarshaller(context)), 2, ___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  3 :
                        revertToParentFromEnterAttribute(___uri, ___local, ___qname);
                        return ;
                }
                super.enterAttribute(___uri, ___local, ___qname);
                break;
            }
        }

        public void leaveAttribute(java.lang.String ___uri, java.lang.String ___local, java.lang.String ___qname)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                switch (state) {
                    case  1 :
                        attIdx = context.getAttribute("", "name");
                        if (attIdx >= 0) {
                            context.consumeAttribute(attIdx);
                            context.getCurrentHandler().leaveAttribute(___uri, ___local, ___qname);
                            return ;
                        }
                        break;
                    case  3 :
                        revertToParentFromLeaveAttribute(___uri, ___local, ___qname);
                        return ;
                }
                super.leaveAttribute(___uri, ___local, ___qname);
                break;
            }
        }

        public void handleText(final java.lang.String value)
            throws org.xml.sax.SAXException
        {
            int attIdx;
            outer:
            while (true) {
                try {
                    switch (state) {
                        case  1 :
                            attIdx = context.getAttribute("", "name");
                            if (attIdx >= 0) {
                                context.consumeAttribute(attIdx);
                                context.getCurrentHandler().text(value);
                                return ;
                            }
                            break;
                        case  3 :
                            revertToParentFromText(value);
                            return ;
                    }
                } catch (java.lang.RuntimeException e) {
                    handleUnexpectedTextException(value, e);
                }
                break;
            }
        }

    }

}
