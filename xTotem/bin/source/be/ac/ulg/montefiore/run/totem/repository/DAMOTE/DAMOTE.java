/* TOTEM-v3.2 June 18 2008*/

/*
 * ===========================================================
 * TOTEM : A TOolbox for Traffic Engineering Methods
 * ===========================================================
 *
 * (C) Copyright 2004-2006, by Research Unit in Networking RUN, University of Liege. All Rights Reserved.
 *
 * Project Info:  http://totem.run.montefiore.ulg.ac.be
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU General Public License version 2.0 as published by the Free Software Foundation;
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc.
 * in the United States and other countries.]
*/
package be.ac.ulg.montefiore.run.totem.repository.DAMOTE;

import be.ac.ulg.montefiore.run.totem.repository.model.exception.*;
import be.ac.ulg.montefiore.run.totem.repository.model.*;
import be.ac.ulg.montefiore.run.totem.util.IdGenerator;
import be.ac.ulg.montefiore.run.totem.util.ParameterDescriptor;
import be.ac.ulg.montefiore.run.totem.domain.model.*;
import be.ac.ulg.montefiore.run.totem.domain.model.jaxb.LspBackupType;
import be.ac.ulg.montefiore.run.totem.domain.model.impl.LspImpl;
import be.ac.ulg.montefiore.run.totem.domain.model.impl.PathImpl;
import be.ac.ulg.montefiore.run.totem.domain.exception.*;
import be.ac.ulg.montefiore.run.totem.domain.facade.InterDomainManager;


import java.util.*;

import org.apache.log4j.Logger;

/*
 * Changes:
 * --------
 * - 14-Feb-2005: now DAMOTE also returns AddLSPActions even for backups
 * - 23-Mar-2005: DAMOTE now extends DomainChangeAdapter (JL).
 * - 24-Mar-2005: Add impl for nodeStatusChangeEvent & LinkStatusChangeEvent (JL).
 * - 02-May-2005: move the listener code to a new class (JL).
 * - 28-Oct-2005: fix use of addLSP and preempt params (GMO).
 * - 8-Nov-2005: bugfix: delete listener from observer when stopped (GMO).
 * - 8-Nov-2005: bugfix in exception handling in routeLSP (GMO).
 * - 29-Nov-2005 : Added the possibility to obtain the algorithm parameters (getStartAlgoParameters()). (GMO)
 * - 29-Nov-2005 : Added the possibility to obtain the additional routing parameters (getPrimaryRoutingParameters()). (GMO)
 * - 08-Dec-2005 : Implement new getRunningParameters() from the TotemAlgorithm interface. (GMO)
 * - 19-May-2006 : better exception handling and more explicit messages in exceptions. (GMO)
 * - 10-Aug-2006 : better exception handling and more explicit messages in exceptions. (GMO)
 * - 10-Aug-2006 : id generation for detour LSP rewritten. (GMO)
 * - 10-Aug-2006 : addLsp now defaults to false in routeDetour(). (GMO)
 * - 18-Aug-2006 : fix cleanup bug in local backup computation : correctly remove lspIds from convertor on exception (GMO)
 * - 22-Aug-2006 : fix bug from previous change (GMO)
 * - 22-Aug-2006 : add info message if protectionType is given and if methodType is not given (GMO).
 * - 23-Aug-2006 : Add all lsps as primary when computing backups without bw sharing enabled and warn on logger (GMO).
 * - 23-Oct-2006 : bugfix : reset preemption list (GMO)
 * - 23-Oct-2006 : add default value for setup preemption level, holding preemption level and class type. Issue a warning
 *                if the preemption level is specified and the domain do not use preemptions. (GMO)
 * - 22-Nov-2006 : now backup lsp ids are generated by a domain method (GMO)
 * - 05-Mar-2007 : Move library loading in start(.) and throw exception (GMO)
 * - 13-Mar-2007: add reroute event to the Totem action list after preemption (GMO)
 * - 17-Sep-2007: set DB valid when starting/stopping the algorithm (GMO)
 * - 29-Nov-2007: DAMOTE does not implement LSPBackupRouting but LSPDetourRouting (GMO)
 * - 05-Dec-2007: throw exception when lsp cannot be added in start() (GMO)
 * - 05-Dec-2007: fix bug with local detour lsp ids (GMO)
 * - 18-Dec-2007: only one preemption level can be used at once when routing lsp (among setup and holding) (GMO)
 * - 26-Feb-2008: make use of LspImpl setInitParameters(.) method, detour lsps are checked to have the same bandwidth as its primary (GMO)
 */

/**
 * This class implements the integration of DAMOTE (ULg).
 * DAMOTE is a Decentralized Agent for Mpls Online Traffic Engineering.
 * 
 * <p>The specific parameter required by this algorithm is:
 * <ul>
 *  <li><code>preempt</code> which can be <code>"true"</code> or <code>"false"</code>. This indicates
 *      whether DAMOTE can preempt existing LSPs which have a lower priority or not.
 *  </li>
 * </ul>
 *
 * <p>Creation date: 24-mar.-2004
 *
 * @author  Olivier Delcourt (delcourt@run.montefiore.ulg.ac.be)
 * @author Gaël Monfort (monfort@run.montefiore.ulg.ac.be)
 */
public class DAMOTE implements LSPPrimaryRouting, LSPDetourRouting, DomainSyncAlgorithm {
    private static Logger logger = Logger.getLogger(DAMOTE.class.getName());

    private DomainConvertor convertor = null;
    private Domain domain = null;
    private DAMOTEChangeListener changeListener = null;
    private JNIDAMOTE jniDamote = null;
    private boolean DBValid = true;

    public JNIDAMOTE getJniInstance() {
        return jniDamote;
    }

    private HashMap runningParams = null;

    private static final ArrayList<ParameterDescriptor> params = new ArrayList<ParameterDescriptor>();
    private static final ArrayList<ParameterDescriptor> routingParams = new ArrayList<ParameterDescriptor>();

    static {
        try {
        	// TODO : document those parameters 
        	params.add(new ParameterDescriptor("ASID", "Domain ASID (leave blank for default).", Integer.class, null));
            params.add(new ParameterDescriptor("colorClause", "", Boolean.class, new Boolean(false)));
            params.add(new ParameterDescriptor("allowReroute", "", Boolean.class, new Boolean(false)));
            params.add(new ParameterDescriptor("capacityClause", "", Boolean.class, new Boolean(true)));
            params.add(new ParameterDescriptor("loadBal", "", Float.class, new Float(2)));
            params.add(new ParameterDescriptor("tMin", "", Float.class, new Float(1f)));
            params.add(new ParameterDescriptor("delay", "", Float.class, new Float(0)));
            params.add(new ParameterDescriptor("load", "", Float.class, new Float(0)));
        } catch (AlgorithmParameterException e) {
            e.printStackTrace();
        }

        try {
            routingParams.add(new ParameterDescriptor("preempt", "Tell whether preemptions should be used or not.", Boolean.class, new Boolean(false)));
            routingParams.add(new ParameterDescriptor("addLSP", "Tell whether computed LSP should be directly added to local algorithm-specific DB.", Boolean.class, new Boolean(false)));
        } catch (AlgorithmParameterException e) {
            e.printStackTrace();
        }

    }

    public boolean isDBValid() {
        return DBValid;
    }

    public void invalidateDB() {
        DBValid = false;
    }

    public void restart() {
        HashMap params = getRunningParameters();
        stop();
        try {
            start(params);
        } catch (AlgorithmInitialisationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Print DAMOTE database content
     */
    public void printDamoteDB() {
        jniDamote.jniprintDamoteDB();
    }

    /**
     * Initializes DAMOTE
     * @param params  general configuration parameters, the score function
     * specific parameters,...
     */
    public void start(HashMap params) throws AlgorithmInitialisationException {

        try {
            System.loadLibrary("damote");
        } catch (UnsatisfiedLinkError e) {
            throw new LibraryInitialisationException("Cannot load library damote.");
        }

        jniDamote = new JNIDAMOTE();
        runningParams = params;
        int ASID=0;

        // if DAMOTE is not used with scenarios, the ASID might not be passed adequately
        if (params.get("ASID")==null){
            domain = InterDomainManager.getInstance().getDefaultDomain();
            ASID = domain.getASID();
            logger.warn("You've not specified a domain when starting DAMOTE, default domain with ASID " + ASID + " was used");

        }else{
            ASID = Integer.parseInt((String)params.get("ASID"));
            try{
                domain = InterDomainManager.getInstance().getDomain(ASID);
            }catch(InvalidDomainException e){
                logger.error("Invalid domain ASID. Revert to default domain with ASID " + ASID);
                domain = InterDomainManager.getInstance().getDefaultDomain();
            }
        }

        changeListener = new DAMOTEChangeListener(domain, this);
        domain.getObserver().addListener(changeListener);
        int truenbOA = domain.getMaxCTvalue() + 1;
        int truenbPL = domain.getMaxPLvalue() + 1;

        int nbOA = 1;   // DAMOTE is configured to use 8 preemption levels for each 8 categories of services
        int nbPL = 1;

        if (truenbOA>1 || truenbPL>1){
            //System.out.println("**************************************************************");
            //System.out.println("* Please make sure you linked to appropriate DAMOTE library! *");
            //System.out.println("* DAMOTE by default works with 1 PL and 1 CT                 *");
            //System.out.println("* Update link lib/libdamote.so to point to lib/libdamote88.so*");
            //System.out.println("* You can then use up to 8 PL and 8 CT                       *");
            //System.out.println("**************************************************************");
            nbOA = 8;
            nbPL = 8;
        }

        boolean colorClause = false;
        if (params.get("colorClause")!=null) colorClause = Boolean.parseBoolean((String)params.get("colorClause"));

        boolean allowReroute = false;
        if (params.get("allowReroute")!=null) allowReroute = Boolean.parseBoolean((String)params.get("allowReroute"));

        boolean capacityClause = true;
        if (params.get("capacityClause")!= null) capacityClause = Boolean.parseBoolean((String)params.get("capacityClause"));

        float loadBal = 2;
        if (params.get("loadBal")!=null) loadBal = Float.parseFloat((String)params.get("loadBal"));

        float tMin = 1f;   // square relative load
        if (params.get("tMin")!=null) tMin = Float.parseFloat((String)params.get("tMin"));

        float delay = 0;
        if (params.get("delay")!=null) delay = Float.parseFloat((String)params.get("delay"));

        float load = 0;
        if (params.get("load")!=null) load = Float.parseFloat((String)params.get("load"));

        //System.out.println("DAMOTE parameters : loadBal = " + loadBal + "; tmin = " + tMin + "; load = " + load + "; delay = " + delay);

        /** these values are not configurable from scenarios */
        float fortz = 0;
        float sqLoad = 0;
        float relativeLoad = 0;


        // score function configuration
        float[][] scoreCoeff = new float[nbOA][nbPL];
        for (int i=0;i<nbOA;i++){
            for (int j=0;j<nbPL;j++){
                scoreCoeff[i][j]=1;
            }
        }

        try{
            jniDamote.jniinitDamote(nbOA,nbPL,colorClause, allowReroute, capacityClause, loadBal, load, sqLoad, relativeLoad, tMin, delay, fortz, scoreCoeff);
        }
        catch(Exception e){
            e.printStackTrace();
            throw new LibraryInitialisationException(e.getMessage());
        }

        convertor = domain.getConvertor();

        //logger.info("Adding nodes");

        // adding nodes
        List<Node> nodes = domain.getUpNodes();

        for(Iterator<Node> it1 = nodes.iterator(); it1.hasNext();) {
            String nodeId = it1.next().getId();

            try {
                int intnodeId = convertor.getNodeId(nodeId);
                jniDamote.jniaddNode(intnodeId);
            } catch (AddDBException e) {
                e.printStackTrace();
            } catch (NodeNotFoundException e) {
                e.printStackTrace();
            }
        }

        //logger.info("Adding links");
        // adding links
        List<Link> links = domain.getUpLinks();

        for(Iterator<Link> it1 = links.iterator(); it1.hasNext();){
            Link link = it1.next();


            float[] bcArray = new float[nbOA];
            for (int i=0; i<nbOA; i++){
                bcArray[i] = link.getBCs()[i];
            }


            // DAMOTE needs this information, we first fill this array with 0 and then update for
            // existing priority values the value
            float[][] reservedbwArray = new float[nbOA][nbPL];

            for (int classType=0;classType<nbOA;classType++){
                for (int preemptionLevel=0;preemptionLevel<nbPL;preemptionLevel++){

                    if (domain.isExistingPriority(preemptionLevel,classType)){
                        int priority = domain.getPriority(preemptionLevel,classType);
                        reservedbwArray[classType][preemptionLevel] = link.getReservedBandwidth(priority);
                        }
                    else reservedbwArray[classType][preemptionLevel] = 0;

                }
            }


            try {

                int srcnodeId = convertor.getNodeId(link.getSrcNode().getId());
                int dstnodeId = convertor.getNodeId(link.getDstNode().getId());
                int intlinkId = convertor.getLinkId(link.getId());

                //setting some unuseful paramaters;
                int color = 0;
                float[][] pbw = null;
                float[][] bbw = null;
                float[][] fbw = null;
                jniDamote.jniaddLink(intlinkId, color, srcnodeId, dstnodeId, bcArray, reservedbwArray, pbw, bbw, fbw);

            } catch (AddDBException e) {
                e.printStackTrace();
                throw new AlgorithmInitialisationException(e.getMessage());
            } catch (LinkNotFoundException e) {
                e.printStackTrace();
            } catch (NodeNotFoundException e) {
                e.printStackTrace();
            }

        }

        //logger.info("Adding lsps");
        // adding lsps if they exist
        if (domain.getAllLsps().size() != 0){

            List<Lsp> lspsList = domain.getAllLsps();

            for (Iterator<Lsp> it = lspsList.iterator(); it.hasNext();){

                try{
                    //logger.info("Add lsp is called");
                    addLSP(it.next());

                }
                catch(RoutingException e)
                {
                    throw new AlgorithmInitialisationException(e.getMessage());
                }
            }
        }

        DBValid = true;
    }



    /**
     * Cleans all damote data structures
     */
    public void stop() {
        runningParams = null;
        domain.getObserver().removeListener(changeListener);
        changeListener = null;
        DBValid = false;
        try{
            if (jniDamote != null) jniDamote.jnikillDamote();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        jniDamote = null;
    }



    /**
     * Computes paths with DAMOTE for a list of demands.
     * This method just calls the routeLSP method for each demand.
     *
     * @param param the list of the demand specified as a list of LSPPrimaryRoutingParameter
     * @return a list of AddLspAction
     * @throws RoutingException
     * @throws NoRouteToHostException
     */
    public TotemActionList routeNLSP(Domain dom, List<LSPPrimaryRoutingParameter> param) throws RoutingException, NoRouteToHostException, LocalDatabaseException {
        if (domain.getASID() != dom.getASID()){
            throw new LocalDatabaseException("ERROR: ASID from route computation differs from the one loaded into DAMOTE DB");
        }
        TotemActionList fullActionList = new TotemActionList();
        TotemActionList currentActionList;
        for (int i = 0; i < param.size(); i++) {
            currentActionList = routeLSP(domain,param.get(i));
            fullActionList.add(currentActionList.get(0));
        }

        return fullActionList;
    }


    /**
     * Computes a path with DAMOTE for one demand from a source node
     * to a destination node with a bandwidth requirement
     * @param param contains the source, destination, bandwidth, PL, OA, ...
     * @return a list of actions
     * @throws RoutingException
     * @throws NoRouteToHostException
     */
    public TotemActionList routeLSP(Domain dom, LSPPrimaryRoutingParameter param) throws RoutingException, NoRouteToHostException, LocalDatabaseException {
        if (!isDBValid()) {
            throw new LocalDatabaseException("Database is invalid. Please restart the algorithm.");
        }

        if (domain.getASID() != dom.getASID()){
            throw new LocalDatabaseException("ERROR: ASID from route computation differs from the one loaded into DAMOTE DB");
        }

        boolean preemptLSPs = false;
        if (param.getRoutingAlgorithmParameter("preempt")!=null){
            preemptLSPs = Boolean.parseBoolean((String)param.getRoutingAlgorithmParameter("preempt"));
        }

        boolean addLSP = false;
        if (param.getRoutingAlgorithmParameter("addLSP")!=null){
            addLSP = Boolean.parseBoolean((String)param.getRoutingAlgorithmParameter("addLSP"));
        }

        String srcNode = param.getSrcNode();
        String dstNode = param.getDstNode();
        float bw = param.getBandwidth();

        int classType = param.isSetClassType() ? param.getClassType() : domain.getMinCTValue();
        if (param.isSetSetup() && !domain.usePreemption())
            logger.warn("The setup preemption level was specified but preemptions are not used on the domain. Ignoring.");
        if (param.isSetHolding() && !domain.usePreemption())
            logger.warn("The holding preemption level was specified but preemptions are not used on the domain. Ignoring.");

        int setupPreemptionLevel = param.isSetSetup() ? param.getSetup() : domain.getMaxPreemptionLevel(classType);
        int holdingPreemptionLevel = param.isSetHolding() ? param.getHolding() : domain.getMaxPreemptionLevel(classType);

        if (addLSP && setupPreemptionLevel != holdingPreemptionLevel) {
            logger.warn("Setup and holding preemption differs. DAMOTE will use only setup preemption level.");
            holdingPreemptionLevel = setupPreemptionLevel;
        }

        int[] path = null;

        String lspId = param.getLspId();
        if (lspId == null){
            lspId = IdGenerator.getInstance().generateStringId("LSP-");
        }

        try {
            if (!domain.isExistingPriority(holdingPreemptionLevel,classType)){
                logger.warn("Non existing holding priority level!");
                throw new RoutingException("Non existing priority level");

            }
            if (!domain.isExistingPriority(setupPreemptionLevel,classType)){
                logger.warn("Non existing setup priority level!");
                throw new RoutingException("Non existing priority level");
             }


            convertor.addLspId(lspId);
            int intlspId = convertor.getLspId(lspId);

            int srcnodeId = convertor.getNodeId(srcNode);
            int dstnodeId = convertor.getNodeId(dstNode);


            int rrid = -1;
            int rrsrc = -1;
            int rrdst = -1;
            int[] colorArray = null;

            // reset the preemption list
            jniDamote.lsps = null;

            path = jniDamote.jnicomputePath(intlspId,srcnodeId,dstnodeId,rrid,rrsrc,rrdst,classType,setupPreemptionLevel,bw,colorArray,addLSP,preemptLSPs);
        }
        catch (AddDBException e){
            try {
                convertor.removeLspId(lspId);
            } catch (LspNotFoundException e1) {
                e1.printStackTrace();
            }
            logger.warn("This primary path failed to add to DAMOTE database, or preempted LSPs can't be removed!");
            if (logger.isDebugEnabled()){
                e.printStackTrace();
            }
            throw new RoutingException("This primary path failed to add to DAMOTE database, or preempted LSPs can't be removed!");
        }
        catch (LspAlreadyExistException e){
            logger.warn("Lsp already exists");
            if (logger.isDebugEnabled()){
                e.printStackTrace();
            }
            throw new RoutingException("Lsp already exists");
        }
        catch (LspNotFoundException e){
            logger.warn("Error with lsp ids string to int conversion");
            if (logger.isDebugEnabled()){
                e.printStackTrace();
            }
            throw new RoutingException("Error with lsp ids string to int conversion");
        }
        catch (NoRouteToHostException e){
            try {
                convertor.removeLspId(lspId);
            } catch (LspNotFoundException e1) {
                e1.printStackTrace();
            }
            logger.warn("Impossible to compute a path for this LSP!");
            if (logger.isDebugEnabled()){
                e.printStackTrace();
            }
            throw new NoRouteToHostException();
        }
        catch (RoutingException e){
            try {
                convertor.removeLspId(lspId);
            } catch (LspNotFoundException e1) {
                e1.printStackTrace();
            }
            logger.warn("Problem with the preemptList!");
            if (logger.isDebugEnabled()){
                e.printStackTrace();
            }
            throw new RoutingException("Problem with the preemptList!");
        }
        catch (NodeNotFoundException e){
            try {
                convertor.removeLspId(lspId);
            } catch (LspNotFoundException e1) {
                e1.printStackTrace();
            }
            logger.warn("Node not found!");
            if (logger.isDebugEnabled()){
                e.printStackTrace();
            }
            throw new RoutingException("Node not found!");
        }


        Path returnPath = null;
        List<String> lspsList = null;
        try {
            List<Node> nodeList = new ArrayList<Node>(path.length);

            for (int i = 0; i < path.length; i++) {
                nodeList.add(domain.getNode(convertor.getNodeId(path[i])));
            }


            returnPath = new PathImpl(domain);
            returnPath.createPathFromNode(nodeList);

            if (jniDamote.lsps != null) {
                lspsList = new ArrayList<String>(jniDamote.lsps.length);

                for (int i = 0; i < jniDamote.lsps.length; i++) {
                    lspsList.add(convertor.getLspId(jniDamote.lsps[i]));
                }
            }

        } catch (LspNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException("Damote returned unknown lsp.");
        } catch (NodeNotFoundException e) {
            e.printStackTrace();
            throw new IllegalStateException("Damote returned invalid path : node not found.");
        } catch (InvalidPathException e) {
            e.printStackTrace();
            throw new IllegalStateException("Damote returned invalid path.");
        }


        LspImpl lsp = null;
        try {
            lsp = new LspImpl(domain,lspId,bw,returnPath,classType,holdingPreemptionLevel,setupPreemptionLevel);
            lsp.setInitParameters(param);
        } catch (DiffServConfigurationException e) {
            throw new RoutingException("Non existing priority level"); 
        }

        TotemAction addLsp = new AddLspAction(domain,lsp);

        TotemActionList actionList = new TotemActionList();
        
        // TODO: manage preemptions adequately
        if (lspsList != null) {
        	TotemAction preemptLsps = new PreemptLspsAction(domain,lspsList);
            actionList.add(preemptLsps);

            ArrayList<Lsp> preemptList = new ArrayList<Lsp>(lspsList.size());
            for (String ss : lspsList) {
                try {
                    preemptList.add(domain.getLsp(ss));
                } catch (LspNotFoundException e) {
                    logger.error("Lsp not found in Totem DB: " + ss);
                    e.printStackTrace();
                }
            }
            actionList.add(addLsp);
            actionList.add(new TriggerRerouteLspsAction(domain, preemptList));
        } else {
            actionList.add(addLsp);
        }

        return actionList;
    }

    /**
     * Computes a global detour or local detours for a primary LSP
     * @param dom
     * @param param the primary LSP id, backup type, preemption support, add lsp support
     * @return a list of actions (addDetourLspAction)
     * @throws RoutingException
     * @throws NoRouteToHostException
     */
    public TotemActionList routeDetour(Domain dom, LSPDetourRoutingParameter param) throws RoutingException, NoRouteToHostException, LocalDatabaseException {
        if (domain.getASID() != dom.getASID()){
            throw new RoutingException("ERROR: ASID from route computation differs from the one loaded into DAMOTE DB");
        }

        if (!domain.useBandwidthSharing()) {
            logger.warn("Trying to compute backups with DAMOTE without BW Sharing.");
            throw new RoutingException("Bandwidth sharing must be enabled to compute backup path with DAMOTE");
        }

        if (!isDBValid()) {
            throw new LocalDatabaseException("Database is invalid. Please restart the algorithm.");
        }

        boolean preemptLSPs = false;
        if (param.getRoutingAlgorithmParameter("preempt")!=null){
            preemptLSPs = Boolean.parseBoolean((String)param.getRoutingAlgorithmParameter("preempt"));
        }

        boolean addLSP = false;
        if (param.getRoutingAlgorithmParameter("addLSP")!=null){
            addLSP = Boolean.parseBoolean((String)param.getRoutingAlgorithmParameter("addLSP"));
        }

        if (param.getProtectionType() != LSPDetourRoutingParameter.ALGORITHM_DEFAULT) {
            logger.warn("A protection type (NODE or LINK disjoint) was specified but DAMOTE doesn't use this parameter." +
                    " (see documentation for more  details).");
        }

        int backupType = param.getMethodType();

        if (backupType == LSPDetourRoutingParameter.ALGORITHM_DEFAULT) {
            logger.info("You did not specify the detour LSP type (LOCAL or GLOBAL). Using default : GLOBAL");
            param.setMethodType(LSPDetourRoutingParameter.GLOBAL);
            backupType = LSPDetourRoutingParameter.GLOBAL;
        }

        String lspId = null;
        int[] intlspIds = null;
        int top = 0;

        boolean exceptionOccurred = false;

        try{
            lspId = param.getProtectedLSP();
            Lsp primaryLsp = domain.getLsp(lspId);
            if (param.isSetBandwidth() && param.getBandwidth() != primaryLsp.getReservation()) {
                throw new RoutingException("DAMOTE can only compute detour LSP of same bandwidth demand as primary");
            }
            param.setBandwidth(primaryLsp.getReservation());

            List<Link> primaryPath = primaryLsp.getLspPath().getLinkPath();

            if (backupType == LSPDetourRoutingParameter.GLOBAL) {
                intlspIds = new int[1];

                String backuplspId = param.getLspId();
                if (backuplspId == null) {
                    backuplspId = domain.generateDetourLspId(lspId, LSPDetourRoutingParameter.GLOBAL, LSPDetourRoutingParameter.ALGORITHM_DEFAULT);
                } else {
                    convertor.addLspId(backuplspId);
                }

                intlspIds[0] = convertor.getLspId(backuplspId);

            }
            else {
                int nbLocalBackupLsps = primaryPath.size();

                intlspIds = new int[nbLocalBackupLsps];


                for (int i = 0; i < nbLocalBackupLsps; i++) {
                    String backuplspId;
                    if (param.getLspId() == null) {
                        backuplspId = domain.generateDetourLspId(lspId, LSPDetourRoutingParameter.LOCAL, LSPDetourRoutingParameter.ALGORITHM_DEFAULT);
                        intlspIds[i] = convertor.getLspId(backuplspId);
                        top++;
                    } else {
                        backuplspId = param.getLspId().concat("-").concat(new Integer(i).toString());
                        try {
                            convertor.addLspId(backuplspId);
                            intlspIds[i] = convertor.getLspId(backuplspId);
                            top++;
                        } catch (LspAlreadyExistException e) {
                            //remove all previously added lspIds
                            for (int j = 0; j < i; j++) {
                                convertor.removeLspId(convertor.getLspId(intlspIds[j]));
                            }
                            throw e;
                        }
                    }
                }
            }

            Object[] pathsArray = null;

            // pathsArray should get the same size as intlspsIds. pathsArray[i] should represent the path for the lsp
            // with id intlspsIds[i]
            pathsArray = jniDamote.jnicomputeBackupPath(convertor.getLspId(lspId),intlspIds,backupType,addLSP,preemptLSPs);


            TotemActionList actionList = new TotemActionList();

            /* This shouldn't happen, otherwise we cannot know which path belongs to which lsp */
            if (backupType == LSPDetourRoutingParameter.LOCAL) {
                if (pathsArray.length != primaryPath.size()) {
                    logger.fatal("There is not one protection LSP per link of the primary LSP !");
                    logger.fatal("Number of backup LSPs: " + pathsArray.length + " Length of the primary LSP: " + primaryPath.size());
                    throw new IllegalStateException();
                }
            }
            else {
                if (pathsArray.length != 1) {
                    logger.fatal("There is more than one LSP for Global backup !");
                    throw new IllegalStateException();
                }
            }

            for (int u = 0; u < pathsArray.length; u++){
                int[] pathArray = ((int[]) pathsArray[u]);
                List<Node> nodeList = new ArrayList<Node>(pathArray.length);


                for (int v = 0; v < pathArray.length; v++){
                    nodeList.add(domain.getNode(convertor.getNodeId(pathArray[v])));
                }

                Path returnPath = new PathImpl(domain);

                returnPath.createPathFromNode(nodeList);

                String backuplspId = null;

                backuplspId = convertor.getLspId(intlspIds[u]);

                List<Link> protectedLinks = new ArrayList<Link>();

                if (backupType == LSPDetourRoutingParameter.LOCAL) {
                    protectedLinks.add(primaryPath.get(u));
                }
                else {
                    for(int j=0; j<primaryPath.size(); j++) {
                        protectedLinks.add(primaryPath.get(j));
                    }
                }

                LspImpl lsp = null;
                if (backupType == LSPDetourRoutingParameter.LOCAL)
                    lsp = new LspImpl(domain,lspId,backuplspId,returnPath,LspBackupType.DETOUR_LOCAL,protectedLinks);
                else lsp = new LspImpl(domain,lspId,backuplspId,returnPath,LspBackupType.DETOUR_E_2_E,protectedLinks);
                lsp.setInitParameters(param);

                TotemAction addLsp = new AddLspAction(domain, lsp);

                actionList.add(addLsp);

            }

            return actionList;
        }
        catch (NoRouteToHostException e){
            exceptionOccurred = true;
            if (backupType==LSPDetourRoutingParameter.LOCAL){
                logger.warn("One/All Local Backup LSP(s) for primary " +  lspId + " failed to compute");
            }
            else{
                logger.warn("The Global Backup LSP for primary " +  lspId + " failed to compute");
            }
            if (logger.isDebugEnabled()){
                e.printStackTrace();
            }
            throw new NoRouteToHostException();
        }
        catch (AddDBException e){
            exceptionOccurred = true;
            if (backupType==LSPDetourRoutingParameter.LOCAL){
                logger.warn("One/All Local Backup LSP(s) for primary " + lspId + " failed to add to DAMOTE Database");
            }
            else{
                logger.warn("The Global Backup LSP for primary " + lspId + " failed to add to DAMOTE Database");
            }

            if (logger.isDebugEnabled()){
                e.printStackTrace();
            }
            throw new RoutingException("Failed to add LSP to DAMOTE Database");
        } catch (LspAlreadyExistException e) {
            exceptionOccurred = true;
            logger.warn("Lsp already exists");
            if (logger.isDebugEnabled()){
                e.printStackTrace();
            }
            throw new RoutingException("Lsp already exists");
        } catch (LspNotFoundException e) {
            exceptionOccurred = true;
            logger.warn("Error with lsp ids string to int conversion");
            if (logger.isDebugEnabled()){
                e.printStackTrace();
            }
            throw new RoutingException("Error with lsp ids string to int conversion");
        } catch (NodeNotFoundException e) {
            exceptionOccurred = true;
            logger.warn("Node not found!");
            if (logger.isDebugEnabled()){
                e.printStackTrace();
            }
            throw new RoutingException("Node not found!");
        } catch (InvalidPathException e) {
            exceptionOccurred = true;
            logger.warn("Invalid Path!");
            if (logger.isDebugEnabled()){
                e.printStackTrace();
            }
            throw new RoutingException("Invalid Path!");
        } finally {
            if (exceptionOccurred && intlspIds != null) {
                try {
                    for (int i = 0; i < top; i++) {
                        convertor.removeLspId(convertor.getLspId(intlspIds[i]));
                    }
                } catch (LspNotFoundException ex) {
                }
            }
        }
    }

    /**
     * Adds an LSP to DAMOTE database
     * @param lsp the LSP to be added
     * @throws RoutingException
     */
    public void addLSP(Lsp lsp) throws RoutingException{
	
        try{
            List<Node> pathnodeList = lsp.getLspPath().getNodePath();

            int[] pathArray = new int[pathnodeList.size()];

            int i=0;
            //logger.info("Path: ");
            for (Iterator<Node> it = pathnodeList.iterator(); it.hasNext(); ){

                pathArray[i++]=convertor.getNodeId(it.next().getId());
                //logger.info(" " + pathArray[i-1]);

            }

            //logger.info("Valeur de preemption " + lsp.getHoldingPreemption());
            //logger.info("Valeur de CT " + lsp.getCT());
            //logger.info("Reservation" + lsp.getReservation());
            //logger.info("LSPID " + convertor.getLspId(lsp.getId()));
            if (!domain.isExistingPriority(lsp.getHoldingPreemption(),lsp.getCT())){
                logger.warn("Non existing holding priority level!");
                throw new RoutingException("Non existing priority level");
            }

            if (lsp.isBackupLsp() && domain.useBandwidthSharing()) {
                boolean type;
                switch (lsp.getBackupType()) {
                    case Lsp.DETOUR_E2E_BACKUP_TYPE:
                        type = false;
                        break;
                    case Lsp.DETOUR_LOCAL_BACKUP_TYPE:
                        type = true;
                        break;
                    default:
                        logger.warn("Trying to add bypass lsp to DAMOTE database.");
                        throw new RoutingException("Bypass Lsps cannot be added to DAMOTE database when using bw sharing.");
                }
                jniDamote.jniaddBackupLSP(convertor.getLspId(lsp.getId()), convertor.getLspId(lsp.getProtectedLsp().getId()), pathArray, lsp.getHoldingPreemption(),lsp.getCT(),lsp.getReservation(), type);
            } else {
                // primary or detour without bw sharing or bypass without bw sharing
                logger.debug("Adding lsp " + lsp.getId() + " to the damote database.");
                jniDamote.jniaddLSP(convertor.getLspId(lsp.getId()),pathArray,lsp.getHoldingPreemption(),lsp.getCT(),lsp.getReservation());
                //printDamoteDB();
            }
        }
        catch(AddDBException e){
            if (logger.isDebugEnabled()) {
	            e.printStackTrace();
            }
            throw new RoutingException("The lsp " + lsp.getId() + " could not be added to DAMOTE database.");
        } catch (LspNotFoundException e) {
            if (logger.isDebugEnabled()) {
	            e.printStackTrace();
            }
            throw new RoutingException("Lsp unknown!");
        } catch (NodeNotFoundException e) {
            if (logger.isDebugEnabled()) {
	            e.printStackTrace();
            }
            throw new RoutingException("Node not found!");
        }
    }

    /**
     * Remove an LSP from DAMOTE database
     * @param lspid
     * @throws RoutingException
     */
    public void removeLSP(String lspid) throws RoutingException{
        try{
            jniDamote.jniremoveLSP(convertor.getLspId(lspid));
        }catch(AddDBException e){
            if (logger.isDebugEnabled()) {
                e.printStackTrace();
            }
            logger.warn("Failed to remove lsp " + lspid + " from DAMOTE database");
            throw new RoutingException();

        } catch (LspNotFoundException e) {
            if (logger.isDebugEnabled()) {
	            e.printStackTrace();
            }
            throw new RoutingException("Lsp unknown!");
        }
    }

    public DAMOTEChangeListener getListener() {
        return changeListener;
    }

	public List<ParameterDescriptor> getStartAlgoParameters() {
		return (List<ParameterDescriptor>) params.clone();
	}

    public HashMap getRunningParameters() {
        return (runningParams == null) ? null : (HashMap)runningParams.clone();
    }

    public List<ParameterDescriptor> getPrimaryRoutingParameters() {
        return (List<ParameterDescriptor>) routingParams.clone();
    }

    public List<ParameterDescriptor> getDetourRoutingParameters() {
        /* detour routing parameters are the same as primary */
        return (List<ParameterDescriptor>) routingParams.clone();
    }
}
