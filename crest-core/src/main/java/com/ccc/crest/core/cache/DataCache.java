/*
 **  Copyright (c) 2016, Cascade Computer Consulting.
 **
 **  Permission to use, copy, modify, and/or distribute this software for any
 **  purpose with or without fee is hereby granted, provided that the above
 **  copyright notice and this permission notice appear in all copies.
 **
 **  THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 **  WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 **  MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 **  ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 **  WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 **  ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 **  OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */
package com.ccc.crest.core.cache;

import java.util.HashMap;

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
import com.ccc.crest.core.client.CrestResponseCallback;
import com.ccc.crest.core.events.CacheEventListener;
import com.ccc.crest.core.events.CommsEventListener;

@SuppressWarnings("javadoc")
public class DataCache implements AccountInterfaces, CharacterInterfaces, ApiInterfaces, CorporationInterfaces, EveInterfaces
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
        CacheData data = cache.get(ApiCallList.getCrestUrl());
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
}
