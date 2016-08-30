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
package com.ccc.crest.core.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ccc.crest.core.CrestClientInfo;
import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.cache.account.AccountCallList;
import com.ccc.crest.core.cache.account.AccountInterfaces;
import com.ccc.crest.core.cache.account.AccountStatus;
import com.ccc.crest.core.cache.account.ApiKeyInfo;
import com.ccc.crest.core.cache.account.Characters;
import com.ccc.crest.core.cache.api.ApiCallList;
import com.ccc.crest.core.cache.api.ApiInterfaces;
import com.ccc.crest.core.cache.api.Time;
import com.ccc.crest.core.cache.character.AccountBalance;
import com.ccc.crest.core.cache.character.AssetList;
import com.ccc.crest.core.cache.character.Blueprints;
import com.ccc.crest.core.cache.character.Bookmarks;
import com.ccc.crest.core.cache.character.CalendarEventAttendees;
import com.ccc.crest.core.cache.character.CharacterInterfaces;
import com.ccc.crest.core.cache.character.CharacterSheet;
import com.ccc.crest.core.cache.character.ChatChannels;
import com.ccc.crest.core.cache.character.Clones;
import com.ccc.crest.core.cache.character.ContactList;
import com.ccc.crest.core.cache.character.ContactNotifications;
import com.ccc.crest.core.cache.character.ContractBids;
import com.ccc.crest.core.cache.character.ContractItems;
import com.ccc.crest.core.cache.character.Contracts;
import com.ccc.crest.core.cache.character.FacWarStats;
import com.ccc.crest.core.cache.character.IndustryJobs;
import com.ccc.crest.core.cache.character.IndustryJobsHistory;
import com.ccc.crest.core.cache.character.KillLog;
import com.ccc.crest.core.cache.character.KillMails;
import com.ccc.crest.core.cache.character.Locations;
import com.ccc.crest.core.cache.character.MailBodies;
import com.ccc.crest.core.cache.character.MailMessages;
import com.ccc.crest.core.cache.character.MailingLists;
import com.ccc.crest.core.cache.character.MarketOrders;
import com.ccc.crest.core.cache.character.Medals;
import com.ccc.crest.core.cache.character.NotificationTexts;
import com.ccc.crest.core.cache.character.Notifications;
import com.ccc.crest.core.cache.character.PlanetaryColonies;
import com.ccc.crest.core.cache.character.PlanetaryLinks;
import com.ccc.crest.core.cache.character.PlanetaryPins;
import com.ccc.crest.core.cache.character.PlanetaryRoutes;
import com.ccc.crest.core.cache.character.Research;
import com.ccc.crest.core.cache.character.SkillInTraining;
import com.ccc.crest.core.cache.character.SkillQueue;
import com.ccc.crest.core.cache.character.Skills;
import com.ccc.crest.core.cache.character.Standings;
import com.ccc.crest.core.cache.character.UpcomingCalendarEvents;
import com.ccc.crest.core.cache.character.WalletJournal;
import com.ccc.crest.core.cache.character.WalletTransactions;
import com.ccc.crest.core.cache.corporation.CorporationInterfaces;
import com.ccc.crest.core.cache.eve.AllianceList;
import com.ccc.crest.core.cache.eve.EveInterfaces;
import com.ccc.crest.core.cache.schema.CcpType;
import com.ccc.crest.core.cache.schema.CrestCallList;
import com.ccc.crest.core.cache.schema.CrestOptions;
import com.ccc.crest.core.cache.schema.Endpoint;
import com.ccc.crest.core.cache.schema.EndpointGroup;
import com.ccc.crest.core.cache.schema.Representation;
import com.ccc.crest.core.cache.schema.Representations;
import com.ccc.crest.core.cache.schema.SchemaInterfaces;
import com.ccc.crest.core.cache.server.ServerInterfaces;
import com.ccc.crest.core.cache.server.ServerStatus;
import com.ccc.crest.core.client.CrestResponseCallback;
import com.ccc.crest.core.events.CacheEventListener;
import com.ccc.crest.core.events.CommsEventListener;

@SuppressWarnings("javadoc")
public class DataCache implements AccountInterfaces, CharacterInterfaces, ApiInterfaces, CorporationInterfaces, EveInterfaces, ServerInterfaces, SchemaInterfaces
{
    public static final String CrestOverallVersion = "application/vnd.ccp.eve.Api-v5+json";
    
    private final HashMap<String, CacheData> cache;
    private final DataCacheCallback callback;
    private final CrestController controller;

    public DataCache(CrestController controller)
    {
        cache = new HashMap<>();
        callback = new DataCacheCallback();
        this.controller = controller;
    }

    public void remove(String url)
    {
        synchronized (cache)
        {
            cache.remove(url);
        }
    }

    public void clear()
    {
        synchronized (cache)
        {
            cache.clear();
        }
    }

    private class CacheData
    {
        private final EveData data;

        private CacheData(EveData data)
        {
            this.data = data;
        }
    }

    @Override
    public AccountBalance getAccountBalance(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public AssetList getAssetList(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public Blueprints getBlueprints(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public Bookmarks getBookmarks(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public CalendarEventAttendees getCalendarEventAttendees(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public CharacterSheet getCharacterSheet(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public ChatChannels getChatChannels(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public Clones getClones(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public ContactList getContactList(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(ContactList.getCrestUrl(clientInfo));
        if (data != null)
        {
            data.data.accessed();
            return (ContactList) data.data;
        }
        try
        {
            ContactList list = (ContactList) ContactList.getContacts(clientInfo, callback).get();
            list.accessed();
            return list;
        } catch (Exception e)
        {
            //TODO: can only determine neither worked here, is there a better isolation?
            CommsEventListener.Type type = CommsEventListener.Type.CrestDown;
            controller.fireCommunicationEvent(clientInfo, type);
            //            if(!data.data.isFromCrest())
            type = CommsEventListener.Type.XmlDown;
            controller.fireCommunicationEvent(clientInfo, type);
            throw new SourceFailureException("Failed to obtain requested url: " + ContactList.getCrestUrl(clientInfo));
        }
    }

    public ContactList getContactListXml(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(ContactList.getApiUrl(clientInfo));
        if (data != null)
        {
            data.data.accessed();
            return (ContactList) data.data;
        }
        try
        {
            ContactList list = (ContactList) ContactList.getContacts(clientInfo, callback).get();
            list.accessed();
            return list;
        } catch (Exception e)
        {
            //TODO: can only determine neither worked here, is there a better isolation?
            CommsEventListener.Type type = CommsEventListener.Type.CrestDown;
            controller.fireCommunicationEvent(clientInfo, type);
            //            if(!data.data.isFromCrest())
            type = CommsEventListener.Type.XmlDown;
            controller.fireCommunicationEvent(clientInfo, type);
            throw new SourceFailureException("Failed to obtain requested url: " + ContactList.getCrestUrl(clientInfo));
        }
    }

    @Override
    public ContactNotifications getContactNotifications(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public ContractBids getContractBids(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public ContractItems getContractItems(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public Contracts getContracts(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public FacWarStats getFacWarStats(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public IndustryJobs getIndustryJobs(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public IndustryJobsHistory getIndustryJobsHistory(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public KillLog getKillLog(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public KillMails getKillMails(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public Locations getLocations(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public MailBodies getMailBodies(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public MailingLists getMailingLists(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public MailMessages getMailMessages(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public MarketOrders getMarketOrders(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public Medals getMedals(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public Notifications getNotifications(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public NotificationTexts getNotificationTexts(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public PlanetaryColonies getPlanetaryColonies(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public PlanetaryLinks getPlanetaryLinks(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public PlanetaryPins getPlanetaryPins(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public PlanetaryRoutes getPlanetaryRoutes(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public Research getResearch(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public SkillInTraining getSkillInTraining(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public SkillQueue getSkillQueue(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public Skills getSkills(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public Standings getStandings(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public UpcomingCalendarEvents getUpcomingCalendarEvents(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public WalletJournal getWalletJournal(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public WalletTransactions getWalletTransactions(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public AccountStatus getAccountStatus(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public ApiKeyInfo getApiKeyInfo(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public Characters getCharacters(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public ApiCallList getApiCallList() throws SourceFailureException
    {
        CacheData data = cache.get(ApiCallList.getXmlUrl());
        if (data != null)
        {
            data.data.accessed();
            return (ApiCallList) data.data;
        }
        try
        {
            ApiCallList apiCallList = (ApiCallList) ApiCallList.getCallList(callback).get();
            apiCallList.accessed();
            return apiCallList;
        } catch (Exception e)
        {
            CommsEventListener.Type type = CommsEventListener.Type.CrestDown;
            controller.fireCommunicationEvent(null, type);
            //            if(!data.data.isFromCrest())
            //            type = CommsEventListener.Type.XmlDown;
            //            controller.fireCommunicationEvent(clientInfo, type);
            throw new SourceFailureException("Failed to obtain requested url: " + Time.getCrestUrl(), e);
        }
    }

    @Override
    public AccountCallList getAccountCallList() throws SourceFailureException
    {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public AllianceList getAllianceList(CrestClientInfo clientInfo) throws SourceFailureException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public com.ccc.crest.core.cache.corporation.AccountBalance getCorpAccountBalance(CrestClientInfo clientInfo) throws SourceFailureException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Time getTime() throws SourceFailureException
    {
        CacheData data = cache.get(Time.getCrestUrl());
        if (data != null)
        {
            data.data.accessed();
            return (Time) data.data;
        }
        try
        {
            Time time = (Time) Time.getTime(callback).get();
            time.accessed();
            return time;
        } catch (Exception e)
        {
            CommsEventListener.Type type = CommsEventListener.Type.CrestDown;
            controller.fireCommunicationEvent(null, type);
            //            if(!data.data.isFromCrest())
            //            type = CommsEventListener.Type.XmlDown;
            //            controller.fireCommunicationEvent(clientInfo, type);
            throw new SourceFailureException("Failed to obtain requested url: " + Time.getCrestUrl());
        }
    }

    @Override
    public ServerStatus getServerStatus() throws SourceFailureException
    {
        CacheData data = cache.get(ServerStatus.getXmlUrl());
        if (data != null)
        {
            data.data.accessed();
            return (ServerStatus) data.data;
        }
        try
        {
            ServerStatus status = (ServerStatus) ServerStatus.getServerStatus(callback).get();
            status.accessed();
            return status;
        } catch (Exception e)
        {
            CommsEventListener.Type type = CommsEventListener.Type.XmlDown;
            controller.fireCommunicationEvent(null, type);
            throw new SourceFailureException("Failed to obtain requested url: " + ServerStatus.getXmlUrl());
        }
    }

    @Override
    public CrestCallList getCrestCallList() throws SourceFailureException
    {
        try
        {
            CrestCallList callList = (CrestCallList) CrestCallList.getCallList(null).get();
            callList.accessed();
            return callList;
        } catch (Exception e)
        {
            CommsEventListener.Type type = CommsEventListener.Type.XmlDown;
            controller.fireCommunicationEvent(null, type);
            throw new SourceFailureException("Failed to obtain requested url: " + CrestCallList.getCrestUrl());
        }
    }
    
    @Override
    public CrestOptions getCrestOptions(String url) throws SourceFailureException
    {
        try
        {
            CrestOptions options = (CrestOptions) CrestOptions.getOptions(url, null).get();
            options.accessed();
            return options;
        } catch (Exception e)
        {
            CommsEventListener.Type type = CommsEventListener.Type.XmlDown;
            controller.fireCommunicationEvent(null, type);
            throw new SourceFailureException("Failed to obtain requested url: " + CrestOptions.getCrestUrl());
        }
    }
    
    public List<String> checkSchema() throws SourceFailureException
    {
        List<String> list = new ArrayList<>();
        Representations representations = getCrestOptions(null).getRepresentations();
        List<EndpointGroup> groups = getCrestCallList().getCallGroups();
        Representation schemaSchema = representations.representations.get(0);
        Representation endpointSchema = representations.representations.get(1);
        if(!CrestOptions.Version.equals(schemaSchema.acceptType.name))
            list.add(schemaSchema.acceptType.name);
        if(!CrestOverallVersion.equals(endpointSchema.acceptType.name))
            list.add(endpointSchema.acceptType.name);
        for(CcpType type : endpointSchema.acceptType.ccpType.children)
        {
            for(EndpointGroup group : groups)
            {
                for(Endpoint endpoint : group.getEndpoints())
                {
                    Representations reps = getCrestOptions(endpoint.uri).getRepresentations();
                    Representation rep0 = reps.representations.get(0);
                    Representation rep1 = reps.representations.get(1);
                    String rep0Version = rep0.acceptType.name;
                    String rep1Version = rep1.acceptType.name;
                    System.out.println("look here");
                    
                }
            }
        }
        System.out.println("look here");
        return list;
    }
    
    private class DataCacheCallback implements CrestResponseCallback
    {
        @Override
        public void received(CrestRequestData requestData, EveData data)
        {
            synchronized (cache)
            {
                CacheData previousData = cache.put(requestData.url, new CacheData(data));
                if(previousData == null || previousData.data.equals(data))
                    controller.fireCacheEvent(requestData.clientInfo, requestData.url, CacheEventListener.Type.Changed);
                controller.fireCacheEvent(requestData.clientInfo, requestData.url, CacheEventListener.Type.Refreshed);
            }
        }
    }
}
