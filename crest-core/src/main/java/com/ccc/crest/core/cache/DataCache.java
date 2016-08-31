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

import java.util.HashMap;

import com.ccc.crest.core.CrestClientInfo;
import com.ccc.crest.core.CrestController;
import com.ccc.crest.core.cache.crest.character.ContactList;
import com.ccc.crest.core.cache.crest.schema.SchemaInterfaces;
import com.ccc.crest.core.cache.crest.schema.endpoint.CrestCallList;
import com.ccc.crest.core.cache.crest.schema.option.CrestOptions;
import com.ccc.crest.core.cache.crest.time.Time;
import com.ccc.crest.core.cache.xmlapi.account.AccountCallList;
import com.ccc.crest.core.cache.xmlapi.account.AccountStatus;
import com.ccc.crest.core.cache.xmlapi.account.ApiKeyInfo;
import com.ccc.crest.core.cache.xmlapi.account.Characters;
import com.ccc.crest.core.cache.xmlapi.api.ApiCallList;
import com.ccc.crest.core.cache.xmlapi.character.AccountBalance;
import com.ccc.crest.core.cache.xmlapi.character.AssetList;
import com.ccc.crest.core.cache.xmlapi.character.Blueprints;
import com.ccc.crest.core.cache.xmlapi.character.Bookmarks;
import com.ccc.crest.core.cache.xmlapi.character.CalendarEventAttendees;
import com.ccc.crest.core.cache.xmlapi.character.CharacterSheet;
import com.ccc.crest.core.cache.xmlapi.character.ChatChannels;
import com.ccc.crest.core.cache.xmlapi.character.Clones;
import com.ccc.crest.core.cache.xmlapi.character.ContactNotifications;
import com.ccc.crest.core.cache.xmlapi.character.ContractBids;
import com.ccc.crest.core.cache.xmlapi.character.ContractItems;
import com.ccc.crest.core.cache.xmlapi.character.Contracts;
import com.ccc.crest.core.cache.xmlapi.character.FacWarStats;
import com.ccc.crest.core.cache.xmlapi.character.IndustryJobs;
import com.ccc.crest.core.cache.xmlapi.character.IndustryJobsHistory;
import com.ccc.crest.core.cache.xmlapi.character.KillLog;
import com.ccc.crest.core.cache.xmlapi.character.KillMails;
import com.ccc.crest.core.cache.xmlapi.character.Locations;
import com.ccc.crest.core.cache.xmlapi.character.MailBodies;
import com.ccc.crest.core.cache.xmlapi.character.MailMessages;
import com.ccc.crest.core.cache.xmlapi.character.MailingLists;
import com.ccc.crest.core.cache.xmlapi.character.MarketOrders;
import com.ccc.crest.core.cache.xmlapi.character.Medals;
import com.ccc.crest.core.cache.xmlapi.character.NotificationTexts;
import com.ccc.crest.core.cache.xmlapi.character.Notifications;
import com.ccc.crest.core.cache.xmlapi.character.PlanetaryColonies;
import com.ccc.crest.core.cache.xmlapi.character.PlanetaryLinks;
import com.ccc.crest.core.cache.xmlapi.character.PlanetaryPins;
import com.ccc.crest.core.cache.xmlapi.character.PlanetaryRoutes;
import com.ccc.crest.core.cache.xmlapi.character.Research;
import com.ccc.crest.core.cache.xmlapi.character.SkillInTraining;
import com.ccc.crest.core.cache.xmlapi.character.SkillQueue;
import com.ccc.crest.core.cache.xmlapi.character.Skills;
import com.ccc.crest.core.cache.xmlapi.character.Standings;
import com.ccc.crest.core.cache.xmlapi.character.UpcomingCalendarEvents;
import com.ccc.crest.core.cache.xmlapi.character.WalletJournal;
import com.ccc.crest.core.cache.xmlapi.character.WalletTransactions;
import com.ccc.crest.core.cache.xmlapi.eve.AllianceList;
import com.ccc.crest.core.cache.xmlapi.server.ServerStatus;
import com.ccc.crest.core.client.CrestResponseCallback;
import com.ccc.crest.core.events.CacheEventListener;
import com.ccc.crest.core.events.CommsEventListener;

@SuppressWarnings("javadoc")
public class DataCache implements AccountInterfaces, CharacterInterfaces, ApiInterfaces, CorporationInterfaces, EveInterfaces, ServerInterfaces, SchemaInterfaces
{
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
            throw new SourceFailureException("Failed to obtain requested url: " + Time.getUrl(), e);
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
    public com.ccc.crest.core.cache.xmlapi.corporation.AccountBalance getCorpAccountBalance(CrestClientInfo clientInfo) throws SourceFailureException
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Time getTime() throws SourceFailureException
    {
        CacheData data = cache.get(Time.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (Time) data.data;
        }
        try
        {
            Time time = (Time) Time.getFuture(callback).get();
            time.accessed();
            return time;
        } catch (Exception e)
        {
            CommsEventListener.Type type = CommsEventListener.Type.CrestDown;
            controller.fireCommunicationEvent(null, type);
            //            if(!data.data.isFromCrest())
            //            type = CommsEventListener.Type.XmlDown;
            //            controller.fireCommunicationEvent(clientInfo, type);
            throw new SourceFailureException("Failed to obtain requested url: " + Time.getUrl());
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
