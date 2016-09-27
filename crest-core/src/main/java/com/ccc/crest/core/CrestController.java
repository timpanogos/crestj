/*
**  Copyright (c) 2016, Chad Adams.
**
**  This program is free software: you can redistribute it and/or modify
**  it under the terms of the GNU Lesser General Public License as
**  published by the Free Software Foundation, either version 3 of the
**  License, or any later version.
**
**  This program is distributed in the hope that it will be useful,
**  but WITHOUT ANY WARRANTY; without even the implied warranty of
**  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
**  GNU General Public License for more details.

**  You should have received copies of the GNU GPLv3 and GNU LGPLv3
**  licenses along with this program.  If not, see http://www.gnu.org/licenses
*/
package com.ccc.crest.core;

import java.io.File;

// aws windows server Administrator/p6j.;uCGS9e

//You can compute the Euclidean distance:
//def distance((x1, y1, z1), (x2, y2, z2)):
//     return math.sqrt((x1-x2)**2 + (y1-y2)**2 + (z1-z2)**2)
//This function will give you the distance between two coordinates.

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccc.crest.core.cache.DataCache;
import com.ccc.crest.core.cache.SourceFailureException;
import com.ccc.crest.core.cache.crest.schema.RootEndpoint;
import com.ccc.crest.core.cache.crest.schema.SchemaMap;
import com.ccc.crest.core.client.CrestClient;
import com.ccc.crest.core.events.ApiKeyEventListener;
import com.ccc.crest.core.events.CacheEventListener;
import com.ccc.crest.core.events.CommsEventListener;
import com.ccc.crest.core.events.CommsLatch;
import com.ccc.crest.core.events.DeprecatedEventListener;
import com.ccc.crest.da.AccessGroup;
import com.ccc.crest.da.CapsuleerData;
import com.ccc.crest.da.CrestDataAccessor;
import com.ccc.crest.da.EntityData;
import com.ccc.db.AlreadyExistsException;
import com.ccc.db.DataAccessor;
import com.ccc.db.DataAccessorException;
import com.ccc.db.DbEventListener;
import com.ccc.db.NotFoundException;
import com.ccc.oauth.CoreController;
import com.ccc.oauth.clientInfo.BaseClientInfo;
import com.ccc.oauth.events.AuthEventListener;
import com.ccc.tools.ElapsedTimer;
import com.ccc.tools.PropertiesFile;
import com.ccc.tools.StrH;
import com.ccc.tools.TabToLevel;

@SuppressWarnings("javadoc")
public class CrestController extends CoreController implements AuthEventListener, CommsEventListener, DbEventListener
{
    public static final String DirectorGroupName = "directors";
    public static final String AnonymousGroupName = "anonymous";
    public static final String UserGroupName = "user";

    public static final String OauthLoginUrlKey = "ccc.crest.oauth.login-url";
    public static final String OauthTokenUrlKey = "ccc.crest.oauth.token-url";
    public static final String OauthVerifyUrlKey = "ccc.crest.oauth.verify-url";
    public static final String OauthCallbackUrlKey = "ccc.crest.oauth.callback-url";
    public static final String OauthClientIdKey = "ccc.crest.oauth.client-id";
    public static final String OauthClientSecretKey = "ccc.crest.oauth.client-secret";
    public static final String OauthScopeKey = "ccc.crest.scope";
    public static final String CrestUrlKey = "ccc.crest.url";
    public static final String XmlUrlKey = "ccc.crest.xml-url";
    public static final String UserAgentKey = "ccc.crest.user-agent";
    public static final String CreateApiKeyUrlKey = "ccc.crest.api.key-gen-url";
    public static final String GroupAdminBaseKey = "ccc.crest.group.director";

    public static final String CrestServletConfigDefault = "etc/opt/ccc/crest/crest.properties";
    public static final String OauthLoginUrlDefault = "https://login.eveonline.com/oauth/authorize";
    public static final String OauthTokenUrlDefault = "https://login.eveonline.com/oauth/token";
    public static final String OauthVerifyUrlDefault = "https://login.eveonline.com/oauth/verify";
    public static final String OauthScopeDefault = "publicData";
    public static final String CrestUrlDefault = "https://crest-tq.eveonline.com";
    public static final String XmlUrlDefault = "https://api.eveonline.com";
    public static final String UserAgentDefault = "Chad Adams (Salgare) cadams@xmission.com, https://github.com/timpanogos/crestj, @evecrestj";
    public static final String CreateApiKeyUrlDefault = "https://community.eveonline.com/support/api-key/CreatePredefined"; //?accessMask=133038347

    public final DataCache dataCache;
    public volatile CrestClient crestClient;
    private final List<CommsEventListener> commsEventListeners;
    private final List<ApiKeyEventListener> apiKeyEventListeners;
    private final List<CacheEventListener> cacheEventListeners;
    private final List<DeprecatedEventListener> deprecatedEventListeners;
    private final CommsLatch commsLatch;
    public final Scope scopes;

    public Logger log;

    public CrestController()
    {
        log = LoggerFactory.getLogger(getClass());
        dataCache = new DataCache(this);
        registerAuthenticatedEventListener(this);
        commsEventListeners = new ArrayList<>();
        cacheEventListeners = new ArrayList<>();
        apiKeyEventListeners = new ArrayList<>();
        deprecatedEventListeners = new ArrayList<>();
        registerCommunicationEventListener(this);
        scopes = new Scope();
        commsLatch = new CommsLatch();
    }

    public CrestDataAccessor getDataAccessor()
    {
        return (CrestDataAccessor) dataAccessor;
    }

    public boolean isCrestUp()
    {
        return commsLatch.isCrestUp();
    }

    public boolean isXmlApiUp()
    {
        return commsLatch.isXmlUp();
    }

    public String getAuthenticationScopesString() throws Exception
    {
        String predefinedUrl = StrH.getParameter(properties, CreateApiKeyUrlDefault, CreateApiKeyUrlDefault, null);
        scopes.setCreatePredefinedUrl(predefinedUrl);
        List<Entry<String, String>> list = PropertiesFile.getPropertiesForBaseKey(OauthScopeKey, properties);
        if (list.size() == 0)
            return OauthScopeDefault;

        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Entry<String, String> entry : list)
        {
            if (!first)
                sb.append(" ");
            first = false;
            String scopeStr = entry.getValue();
            sb.append(scopeStr);
            scopes.addScope(scopeStr);
        }
        return sb.toString();
    }

    public boolean capsuleerHasApiKey(String name)
    {
        try
        {
            return ((CrestDataAccessor) dataAccessor).hasApiKeys(name);
        } catch (Exception e)
        {
            // TODO work in with some db down fire event scheme
            log.warn("DataAccessor failure", e);
            return false;
        }
    }

    public void setCapsuleerApiKey(String name, String keyId, String code) throws InvalidApiKeysException, DataAccessorException
    {
        try
        {
            CapsuleerData cdata = ((CrestDataAccessor) dataAccessor).getCapsuleer(name);
            CapsuleerData updatedata = new CapsuleerData(name, cdata.capsuleerId, Long.parseLong(keyId), code, cdata.refreshToken);
            ((CrestDataAccessor) dataAccessor).updateCapsuleer(name, updatedata);
        } catch (NumberFormatException e)
        {
            throw new InvalidApiKeysException("Invalid KeyID: " + keyId);
        } catch (Exception e1)
        {
            // TODO work in with some db down fire event scheme
            log.warn("DataAccessor failure", e1);
            throw new DataAccessorException("Failed to update " + name + "'s CapsuleerData with xml-api keys");
        }
    }

    public static CrestController getCrestController()
    {
        return (CrestController) getController();
    }

    public void registerCommunicationEventListener(CommsEventListener listener)
    {
        synchronized (commsEventListeners)
        {
            commsEventListeners.add(listener);
        }
    }

    public void deregisterCommunicationEventListener(CommsEventListener listener)
    {
        synchronized (commsEventListeners)
        {
            commsEventListeners.remove(listener);
        }
    }

    public void fireEndpointDeprecatedEvent(String endpointInfo)
    {
        executor.submit(new FireDeprecatedEventTask(endpointInfo));
    }

    public void fireCacheEvent(CrestClientInfo clientInfo, String url, CacheEventListener.Type type)
    {
        executor.submit(new FireCacheEventTask(clientInfo, url, type));
    }

    public void fireCommunicationEvent(CrestClientInfo clientInfo, CommsEventListener.Type type)
    {
        if (commsLatch.shouldFire(type))
            executor.submit(new FireCommunicationsEventTask(clientInfo, type));
    }

    public void registerApiKeyEventListener(ApiKeyEventListener listener)
    {
        synchronized (apiKeyEventListeners)
        {
            apiKeyEventListeners.add(listener);
        }
    }

    public void deregisterApiKeyEventListener(ApiKeyEventListener listener)
    {
        synchronized (apiKeyEventListeners)
        {
            apiKeyEventListeners.remove(listener);
        }
    }

    public void fireApiKeyEvent(CrestClientInfo clientInfo, ApiKeyEventListener.Type type)
    {
        executor.submit(new FireApiKeyEventTask(clientInfo, type));
    }

    @Override
    public void init(Properties properties, TabToLevel format) throws Exception
    {
        //TODO: don't let a down db be fatal to the webpage coming up.
        super.init(properties, format);
        if (dataAccessor == null)
            throw new Exception(DataAccessor.DaImplKey + " must be specified in the properties file");
        dataAccessor.setExecutor(executor);
        initializeGroups();

        String crestUrl = properties.getProperty(CrestUrlKey, CrestUrlDefault);
        String xmlUrl = properties.getProperty(XmlUrlKey, XmlUrlDefault);
        String userAgent = properties.getProperty(UserAgentKey, UserAgentDefault);
        crestClient = new CrestClient(this, crestUrl, xmlUrl, userAgent, executor);
        crestClient.init();
        SchemaMap.init();
        executor.submit(new CheckHealthTask());
        //TODO: add a scheduled task that will hit CheckHealthTask so dbIsUp is checked.
    }

    @Override
    public void destroy()
    {
        if (dataCache != null)
            dataCache.clear();
        deregisterAuthenticatedEventListener(this);
        deregisterCommunicationEventListener(this);
        super.destroy();
    }

    /* ****************************************************************************
     * AuthenticatedEventListener impl
     ******************************************************************************/
    @Override
    public void authenticated(BaseClientInfo clientInfo)
    {
        CrestClientInfo ccinfo = (CrestClientInfo) clientInfo;
        if (ccinfo.getVerifyData() == null)
        {
            log.warn("got a successfully authenticated, but no verifed user.");
            return;
        }

        String name = ccinfo.getVerifyData().CharacterName;
        long id = Long.parseLong(ccinfo.getVerifyData().CharacterID);

        log.info(id + " " + name + " authenticated");
        if (dataAccessor.isUp())
        {
            CrestDataAccessor da = (CrestDataAccessor) dataAccessor;
            try
            {
                CapsuleerData userData = da.getCapsuleer(name);
                CapsuleerData updateData = new CapsuleerData(userData.capsuleer, userData.capsuleerId, userData.apiKeyId, userData.apiCode, ccinfo.getRefreshToken());
                da.updateCapsuleer(name, updateData);
                if (userData.apiKeyId == -1)
                    fireApiKeyEvent(ccinfo, ApiKeyEventListener.Type.NeedsApi);
            } catch (NotFoundException e1)
            {
                CapsuleerData userData = new CapsuleerData(name, id, -1, null, ccinfo.getRefreshToken());
                try
                {
                    da.addCapsuleer(userData);
                    fireApiKeyEvent(ccinfo, ApiKeyEventListener.Type.NeedsApi);
                } catch (Exception e)
                {
                    //TODO: add/fire database error events
                    log.warn("Failed to add user: " + userData.toString());
                }
            } catch (Exception e1)
            {
                //TODO: add/fire database error events
                log.warn("Database failure:", e1);
            }

            getGroups(da, ccinfo);
        }
    }

    public void getAuthenticatedUserInfo(String capsuleer)
    {

    }

    private void getGroups(CrestDataAccessor da, CrestClientInfo clientInfo)
    {
        List<AccessGroup> groups;
        try
        {
            groups = da.listGroups();
            for (AccessGroup group : groups)
            {
                if (group.group.equals(AnonymousGroupName))
                {
                    clientInfo.addGroup(group);
                    continue;
                }
                if (group.group.equals(UserGroupName))
                {
                    clientInfo.addGroup(group);
                    continue;
                }

                if (da.isMember(clientInfo.getVerifyData().CharacterID, group.group))
                    clientInfo.addGroup(group);
            }
//FIXME: clean me up
authenticatedTest(clientInfo);

        } catch (Exception e)
        {
            log.warn("Database failure getting groups:", e);
        }
    }

    @Override
    public void refreshed(BaseClientInfo clientInfo)
    {
        CrestClientInfo ccinfo = (CrestClientInfo) clientInfo;
        log.debug(ccinfo.getVerifyData().CharacterID + " " + ccinfo.getVerifyData().CharacterName + " refreshed authentication");
    }

    @Override
    public void dropped(BaseClientInfo clientInfo)
    {
        CrestClientInfo ccinfo = (CrestClientInfo) clientInfo;
        log.debug(ccinfo.getVerifyData().CharacterID + " " + ccinfo.getVerifyData().CharacterName + " dropped authentication");
    }

    /* ***************************************************************************
     * CommunicationsEventListener impl
     ******************************************************************************/
    @Override
    public void crestUp(CrestClientInfo clientInfo)
    {
        if (clientInfo == null)
        {
            log.debug("public call, crestup");
            return;
        } else
            log.debug(clientInfo.getVerifyData().CharacterID + " " + clientInfo.getVerifyData().CharacterName + " crestup");
        ElapsedTimer.resetAllElapsedTimers();
        ElapsedTimer.resetTimer(0);
        ElapsedTimer.resetTimer(1);
        ElapsedTimer.startTimer(0);
        ElapsedTimer.startTimer(1);
        ElapsedTimer.startTimer(2);
        //        try
        //        {
        //            Time t = dataCache.getTime();
        //        } catch (SourceFailureException e)
        //        {
        //            e.printStackTrace();
        //        }
        System.err.println(ElapsedTimer.getElapsedTime("Time to obtain first ContactList from cache", 1));
        System.out.println("look here");
    }

    @Override
    public void crestDown(CrestClientInfo clientInfo)
    {
        if (clientInfo != null)
            log.debug(clientInfo.getVerifyData().CharacterID + " " + clientInfo.getVerifyData().CharacterName + " crestdown");
        else
            log.debug("public endpoint,  crestdown");
        try
        {
            Thread.sleep(5000);
        } catch (InterruptedException e1)
        {
            log.warn("Unexpected interrupt from Thread.sleep", e1);
        }

        ElapsedTimer.resetAllElapsedTimers();
        ElapsedTimer.resetTimer(0);
        ElapsedTimer.resetTimer(1);
        ElapsedTimer.startTimer(0);
        ElapsedTimer.startTimer(1);
        ElapsedTimer.startTimer(2);
        try
        {
            dataCache.getTime();
        } catch (SourceFailureException e)
        {
            log.warn("crestDown retry failed", e);
        }
        System.err.println(ElapsedTimer.getElapsedTime("Time to obtain first ContactList from cache", 1));
        System.out.println("look here");
    }

    @Override
    public void xmlUp(CrestClientInfo clientInfo)
    {
        if (clientInfo == null)
            log.debug("public call, xmlApiUp");
        else
            log.debug(clientInfo.getVerifyData().CharacterID + " " + clientInfo.getVerifyData().CharacterName + " xmlApiUp");
    }

    @Override
    public void xmlDown(CrestClientInfo clientInfo)
    {
        if (clientInfo != null)
            log.debug(clientInfo.getVerifyData().CharacterID + " " + clientInfo.getVerifyData().CharacterName + " xmldown");
        else
            log.debug("public endpoint,  xmldown");
        try
        {
            Thread.sleep(5000);
        } catch (InterruptedException e1)
        {
            log.warn("Unexpected interrupt from Thread.sleep", e1);
        }
        try
        {
            dataCache.getServerStatus();
        } catch (SourceFailureException e)
        {
            log.warn("xmlApiDown retry failed", e);
        }
    }

    /* ****************************************************************************
     * DbEventListener impl
     ******************************************************************************/
    @Override
    public void dbUp()
    {
        log.info("The database has is up");
    }

    @Override
    public void dbDown()
    {
        log.warn("The database is down");
    }

    private void initializeGroups() throws AlreadyExistsException, Exception
    {
        List<Entry<String, String>> admins = PropertiesFile.getPropertiesForBaseKey(GroupAdminBaseKey, properties);
        if (admins.size() == 0)
            throw new Exception("You need to configure at least one admin. i.e. " + GroupAdminBaseKey + "0=Capsuleer Name");

        AccessGroup directors = null;
        try
        {
            directors = ((CrestDataAccessor) dataAccessor).getAccessGroup(DirectorGroupName);
            return; // if director group is setup, assumed all is setup.
        } catch (NotFoundException e)
        {
            EntityData anon = new EntityData(AnonymousGroupName, true);
            EntityData user = new EntityData(UserGroupName, true);
            EntityData director = new EntityData(DirectorGroupName, true);
            EntityData admin = new EntityData(admins.get(0).getValue(), false);
            CrestDataAccessor da = (CrestDataAccessor) dataAccessor;
            try
            {
                da.addEntity(admin);
            } catch (AlreadyExistsException e1)
            {
                //do nothing this is ok
            }
            da.addGroup(admin.name, anon);
            da.addGroup(admin.name, user);
            da.addGroup(admin.name, director);
        }
    }

    private class FireCommunicationsEventTask implements Callable<Void>
    {
        private final CrestClientInfo clientInfo;
        private final CommsEventListener.Type type;

        private FireCommunicationsEventTask(CrestClientInfo clientInfo, CommsEventListener.Type type)
        {
            this.clientInfo = clientInfo;
            this.type = type;
        }

        @Override
        public Void call() throws Exception
        {
            try
            {
                synchronized (commsEventListeners)
                {
                    for (CommsEventListener listener : commsEventListeners)
                        switch (type)
                        {
                            case CrestUp:
                                listener.crestUp(clientInfo);
                                break;
                            case CrestDown:
                                listener.crestDown(clientInfo);
                                break;
                            case XmlUp:
                                listener.xmlUp(clientInfo);
                                break;
                            case XmlDown:
                                listener.xmlDown(clientInfo);
                                break;
                            default:
                                break;
                        }
                }
            } catch (Exception e)
            {
                LoggerFactory.getLogger(getClass()).warn("An communicationsEventListener has thrown an exception", e);
            }
            return null;
        }
    }

    private class FireApiKeyEventTask implements Callable<Void>
    {
        private final CrestClientInfo clientInfo;
        private final ApiKeyEventListener.Type type;

        private FireApiKeyEventTask(CrestClientInfo clientInfo, ApiKeyEventListener.Type type)
        {
            this.clientInfo = clientInfo;
            this.type = type;
        }

        @Override
        public Void call() throws Exception
        {
            try
            {
                synchronized (apiKeyEventListeners)
                {
                    for (ApiKeyEventListener listener : apiKeyEventListeners)
                        listener.needsApiKey(clientInfo);
                }
            } catch (Exception e)
            {
                LoggerFactory.getLogger(getClass()).warn("An communicationsEventListener has thrown an exception", e);
            }
            return null;
        }
    }

    private class FireCacheEventTask implements Callable<Void>
    {
        private final CrestClientInfo clientInfo;
        private final String url;
        private final CacheEventListener.Type type;

        private FireCacheEventTask(CrestClientInfo clientInfo, String url, CacheEventListener.Type type)
        {
            this.clientInfo = clientInfo;
            this.url = url;
            this.type = type;
        }

        @Override
        public Void call() throws Exception
        {
            try
            {
                synchronized (apiKeyEventListeners)
                {
                    for (ApiKeyEventListener listener : apiKeyEventListeners)
                        listener.needsApiKey(clientInfo);
                }
            } catch (Exception e)
            {
                LoggerFactory.getLogger(getClass()).warn("An communicationsEventListener has thrown an exception", e);
            }
            return null;
        }
    }

    private class FireDeprecatedEventTask implements Callable<Void>
    {
        private final String endpointInfo;

        private FireDeprecatedEventTask(String endpointInfo)
        {
            this.endpointInfo = endpointInfo;
        }

        @Override
        public Void call() throws Exception
        {
            try
            {
                synchronized (apiKeyEventListeners)
                {
                    for (DeprecatedEventListener listener : deprecatedEventListeners)
                        listener.deprecated(endpointInfo);
                }
            } catch (Exception e)
            {
                LoggerFactory.getLogger(getClass()).warn("An communicationsEventListener has thrown an exception", e);
            }
            return null;
        }
    }

    private class CheckHealthTask implements Callable<Void>
    {
        @Override
        public Void call() throws Exception
        {
            try
            {
                //                "https://api-sisi.testeveonline.com/tournaments/teams/1/"
                //                "https://api-sisi.testeveonline.com/tournaments/1/teams/1/"

                //                CrestOptions copts = dataCache.getOptions("https://api-sisi.testeveonline.com/tournaments/teams/1/");
                //                log.info("\nteam1 options:\n" + copts.getRepresentations().toString());


                if(true)
                    return null;
//                dataCache.getAllianceCollection(1);
//                dataCache.getAlliancesElement(99000006);
                dataCache.getNpcCorporationCollection(1);
                dataCache.getDogmaAttributeCollection(1);


                dumpSchema();
//                try{dataCache.getOptions("https://crest-tq.eveonline.com/stations/60000004/");}catch(Exception e){log.error("failed:", e);}
//                try{dataCache.getOptions("https://crest-tq.eveonline.com/stations/0/");}catch(Exception e){log.error("failed:", e);}
//                try{
//                    log.info(dataCache.getOptions(null).representations.toString());
//                    }catch(Exception e){log.error("failed:", e);}

//                log.info("Host: " + CrestClient.getCrestBaseUri());
//                RootEndpoint root = new RootEndpoint();
//                root.dumpTree(new File("/tmp/crestj"));

                dataCache.getTime();
                dataAccessor.isUp();
                dataCache.getServerStatus();
            } catch (Throwable e)
            {
                log.warn("GetTime failed: ", e);
            }
            return null;
        }
    }
    private void authenticatedTest(CrestClientInfo clientInfo) throws SourceFailureException
    {
        dataCache.getContactList(clientInfo);
        dataCache.getCorporationStructureCollection(clientInfo, 98465079, 1);
    }

    //TODO: clean this up
    private void dumpSchema() throws Exception
    {
        String urlBase = CrestClient.getCrestBaseUri();

        try{dataCache.getOptions("https://api-sisi.testeveonline.com/corporations/665335352/structures/");}catch(Exception e){log.error("failed:", e);}
        try{dataCache.getOptions(null);}catch(Exception e){log.error("failed:", e);}
        try{dataCache.getOptions("https://api-sisi.testeveonline.com/alliances/");}catch(Exception e){log.error("failed:", e);}
        try{dataCache.getOptions("https://api-sisi.testeveonline.com/alliances/0/");}catch(Exception e){log.error("failed:", e);}
        try{dataCache.getOptions("https://api-sisi.testeveonline.com/alliances/99000006/");}catch(Exception e){log.error("failed:", e);}

        dataCache.getAllianceCollection(0);
        for(int i=1; i < 13; i++)
            dataCache.getAllianceCollection(i).getAlliances().toString();

        log.info("Host: " + CrestClient.getCrestBaseUri());
        RootEndpoint root = new RootEndpoint();
        root.dumpTree(new File("/tmp/crestj"));

//        https://crest-tq.eveonline.com/characters/1364371482/contacts/
//        try{dataCache.getOptions(urlBase + "/characters/");}catch(Exception e){log.error("failed:", e);}

        try{dataCache.getOptions("https://crest-tq.eveonline.com//characters/1364371482/contacts/");}catch(Exception e){log.error("failed:", e);}
        try{dataCache.getOptions("https://crest-tq.eveonline.com//characters/1364371482/contacts/0/");}catch(Exception e){log.error("failed:", e);}

//        try{dataCache.getOptions("https://crest-tq.eveonline.com/characters/0/contacts/");}catch(Exception e){log.error("failed:", e);}
//        application/vnd.ccp.eve.ContactCollection-v2+json

        try{dataCache.getOptions(urlBase + "/characters/0/contacts/");}catch(Exception e){log.error("failed:", e);}
        try{dataCache.getOptions(urlBase + "/characters/0/fittings/");}catch(Exception e){log.error("failed:", e);}
        try{dataCache.getOptions(urlBase + "/characters/0/fittings/0/");}catch(Exception e){log.error("failed:", e);}

        try{dataCache.getOptions(urlBase + "/characters/0/characterOpportunitiesRead/");}catch(Exception e){log.error("failed:", e);}
        try{dataCache.getOptions(urlBase + "/characters/0/ui/autopilot/waypoints/");}catch(Exception e){log.error("failed:", e);}
        try{dataCache.getOptions(urlBase + "/characters/0/location/");}catch(Exception e){log.error("failed:", e);}
        try{dataCache.getOptions(urlBase + "/characters/0/ui/openwindow/marketdetails/");}catch(Exception e){log.error("failed:", e);}

        dataCache.getOptions(urlBase + "/planets/");
        dataCache.getOptions(urlBase + "/planets/", true);

        dataCache.getOptions(urlBase + "/planets/0/");
        dataCache.getOptions(urlBase + "/planets/2016/", true);

        dataCache.getOptions(urlBase + "/solarsystems/");
        dataCache.getOptions(urlBase + "/solarsystems/", true);

        dataCache.getOptions(urlBase + "/solarsystems/0/");
        dataCache.getOptions(urlBase + "/solarsystems/30000001/", true);

        dataCache.getOptions(urlBase + "/tournaments/");
        dataCache.getOptions(urlBase + "/tournaments/", true);

        dataCache.getOptions(urlBase + "/tournaments/0/");
        dataCache.getOptions(urlBase + "/tournaments/9/", true);

        dataCache.getOptions(urlBase + "/tournaments/0/series/");
        dataCache.getOptions(urlBase + "/tournaments/9/series/", true);

        dataCache.getOptions(urlBase + "/tournaments/0/series/0/");
        dataCache.getOptions(urlBase + "/tournaments/9/series/1/", true);

        dataCache.getOptions(urlBase + "/tournaments/0/series/0/matches/");
        dataCache.getOptions(urlBase + "/tournaments/9/series/1/matches/", true);

        dataCache.getOptions(urlBase + "/tournaments/0/series/0/matches/0/");
        dataCache.getOptions(urlBase + "/tournaments/9/series/1/matches/0/", true);

        dataCache.getOptions(urlBase + "/tournaments/0/series/0/matches/0/bans/");
        dataCache.getOptions(urlBase + "/tournaments/9/series/1/matches/0/bans/", true);

        dataCache.getOptions(urlBase + "/tournaments/0/series/0/matches/0/static/");
        dataCache.getOptions(urlBase + "/tournaments/9/series/1/matches/0/static/", true);

        dataCache.getOptions(urlBase + "/tournaments/0/series/0/matches/0/pilotstats/");
        dataCache.getOptions(urlBase + "/tournaments/9/series/1/matches/0/pilotstats/", true);

        dataCache.getOptions(urlBase + "/tournaments/0/series/0/matches/0/realtime/0/");
        dataCache.getOptions(urlBase + "/tournaments/9/series/1/matches/0/realtime/0/", true);

        //                    dataCache.getOptions(urlBase+"/tournaments/0/teams/");
        //                    dataCache.getOptions(urlBase+"/tournaments/9/teams/", true);

        dataCache.getOptions(urlBase + "/tournaments/0/teams/0/");
        dataCache.getOptions(urlBase + "/tournaments/9/teams/208/", true);

        dataCache.getOptions(urlBase + "/tournaments/teams/");
        dataCache.getOptions(urlBase + "/tournaments/teams/", true);

        dataCache.getOptions(urlBase + "/tournaments/teams/0/");
        dataCache.getOptions(urlBase + "/tournaments/teams/1/", true);

        dataCache.getOptions(urlBase + "/tournaments/teams/0/members/");
        dataCache.getOptions(urlBase + "/tournaments/teams/1/members/", true);
    }
}
