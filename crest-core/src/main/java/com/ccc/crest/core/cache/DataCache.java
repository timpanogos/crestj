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
import com.ccc.crest.core.cache.crest.alliance.AllianceCollection;
import com.ccc.crest.core.cache.crest.character.BloodlineCollection;
import com.ccc.crest.core.cache.crest.character.ContactCollection;
import com.ccc.crest.core.cache.crest.character.RaceCollection;
import com.ccc.crest.core.cache.crest.character.TokenDecode;
import com.ccc.crest.core.cache.crest.corporation.NpcCorporationsCollection;
import com.ccc.crest.core.cache.crest.corporation.NpcCorporationsCollection;
import com.ccc.crest.core.cache.crest.dogma.DogmaAttributeCollection;
import com.ccc.crest.core.cache.crest.dogma.DogmaEffectCollection;
import com.ccc.crest.core.cache.crest.incursion.IncursionCollection;
import com.ccc.crest.core.cache.crest.industry.IndustryFacilityCollection;
import com.ccc.crest.core.cache.crest.industry.IndustrySystemCollection;
import com.ccc.crest.core.cache.crest.insurancePrice.InsurancePricesCollection;
import com.ccc.crest.core.cache.crest.inventory.ItemCategoryCollection;
import com.ccc.crest.core.cache.crest.inventory.ItemGroupCollection;
import com.ccc.crest.core.cache.crest.inventory.ItemTypeCollection;
import com.ccc.crest.core.cache.crest.map.ConstellationCollection;
import com.ccc.crest.core.cache.crest.map.RegionCollection;
import com.ccc.crest.core.cache.crest.map.SystemCollection;
import com.ccc.crest.core.cache.crest.market.MarketGroupCollection;
import com.ccc.crest.core.cache.crest.market.MarketTypeCollection;
import com.ccc.crest.core.cache.crest.market.MarketTypePriceCollection;
import com.ccc.crest.core.cache.crest.opportunity.OpportunityGroupsCollection;
import com.ccc.crest.core.cache.crest.opportunity.OpportunityTasksCollection;
import com.ccc.crest.core.cache.crest.schema.SchemaInterfaces;
import com.ccc.crest.core.cache.crest.schema.endpoint.EndpointCollection;
import com.ccc.crest.core.cache.crest.schema.option.CrestOptions;
import com.ccc.crest.core.cache.crest.sovereignty.SovCampaignsCollection;
import com.ccc.crest.core.cache.crest.sovereignty.SovStructureCollection;
import com.ccc.crest.core.cache.crest.time.CrestTime;
import com.ccc.crest.core.cache.crest.tournament.TournamentCollection;
import com.ccc.crest.core.cache.crest.tournament.TournamentMatchCollection;
import com.ccc.crest.core.cache.crest.tournament.TournamentSeriesCollection;
import com.ccc.crest.core.cache.crest.tournament.TournamentTournament;
import com.ccc.crest.core.cache.crest.virtualGoodStore.VirtualGoodStore;
import com.ccc.crest.core.cache.crest.war.WarsCollection;
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
public class DataCache implements CrestInterfaces, AccountInterfaces, CharacterInterfaces, ApiInterfaces, CorporationInterfaces, EveInterfaces, ServerInterfaces, SchemaInterfaces
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
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public AssetList getAssetList(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public Blueprints getBlueprints(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public Bookmarks getBookmarks(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public CalendarEventAttendees getCalendarEventAttendees(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public CharacterSheet getCharacterSheet(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public ChatChannels getChatChannels(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public Clones getClones(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    public ContactCollection getContactListXml(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(ContactCollection.getApiUrl(clientInfo));
        if (data != null)
        {
            data.data.accessed();
            return (ContactCollection) data.data;
        }
        try
        {
            ContactCollection list = (ContactCollection) ContactCollection.getFuture(clientInfo, callback).get();
            list.accessed();
            return list;
        } catch (Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + ContactCollection.getCrestUrl(clientInfo), e);
        }
    }

    @Override
    public ContactNotifications getContactNotifications(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public ContractBids getContractBids(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public ContractItems getContractItems(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public Contracts getContracts(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public FacWarStats getFacWarStats(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public IndustryJobs getIndustryJobs(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public IndustryJobsHistory getIndustryJobsHistory(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public KillLog getKillLog(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public KillMails getKillMails(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public Locations getLocations(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public MailBodies getMailBodies(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public MailingLists getMailingLists(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public MailMessages getMailMessages(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public MarketOrders getMarketOrders(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public Medals getMedals(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public Notifications getNotifications(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public NotificationTexts getNotificationTexts(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public PlanetaryColonies getPlanetaryColonies(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public PlanetaryLinks getPlanetaryLinks(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public PlanetaryPins getPlanetaryPins(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public PlanetaryRoutes getPlanetaryRoutes(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public Research getResearch(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public SkillInTraining getSkillInTraining(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public SkillQueue getSkillQueue(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public Skills getSkills(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public Standings getStandings(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public UpcomingCalendarEvents getUpcomingCalendarEvents(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public WalletJournal getWalletJournal(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public WalletTransactions getWalletTransactions(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public AccountStatus getAccountStatus(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public ApiKeyInfo getApiKeyInfo(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public Characters getCharacters(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
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
            throw new SourceFailureException("Failed to obtain Data from requested url: " + ApiCallList.getXmlUrl(), e);
        }
    }

    @Override
    public AccountCallList getAccountCallList() throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public AllianceList getAllianceList(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
    }

    @Override
    public com.ccc.crest.core.cache.xmlapi.corporation.AccountBalance getCorpAccountBalance(CrestClientInfo clientInfo) throws SourceFailureException
    {
        throw new SourceFailureException("not implemented yet");
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
            throw new SourceFailureException("Failed to obtain Data from requested url: " + ServerStatus.getXmlUrl(), e);
        }
    }

/* ****************************************************************************
 * Crest endpoints from here on down.
******************************************************************************/

    @Override
    public EndpointCollection getEndpointCollection() throws SourceFailureException
    {
        try
        {
            EndpointCollection callList = (EndpointCollection) EndpointCollection.getFuture(null).get();
            callList.accessed();
            return callList;
        } catch (Exception e)
        {
            CommsEventListener.Type type = CommsEventListener.Type.XmlDown;
            controller.fireCommunicationEvent(null, type);
            throw new SourceFailureException("Failed to obtain Data from requested url: " + EndpointCollection.getCrestUrl(), e);
        }
    }

    @Override
    public CrestOptions getOptions(String url) throws SourceFailureException
    {
        return getOptions(url, false);
    }

    public CrestOptions getOptions(String url, boolean doGet) throws SourceFailureException
    {
        try
        {
            CrestOptions options = (CrestOptions) CrestOptions.getFuture(url, doGet, null).get();
            options.accessed();
            return options;
        } catch (Exception e)
        {
            CommsEventListener.Type type = CommsEventListener.Type.XmlDown;
            controller.fireCommunicationEvent(null, type);
            throw new SourceFailureException("Failed to obtain Data from requested url: " + CrestOptions.getCrestUrl(), e);
        }
    }

    @Override
    public CrestTime getTime() throws SourceFailureException
    {
        CacheData data = cache.get(CrestTime.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (CrestTime) data.data;
        }
        try
        {
            CrestTime time = (CrestTime) CrestTime.getFuture(callback).get();
            time.accessed();
            return time;
        } catch (Exception e)
        {
            CommsEventListener.Type type = CommsEventListener.Type.CrestDown;
            controller.fireCommunicationEvent(null, type);
            throw new SourceFailureException("Failed to obtain Data from requested url: " + CrestTime.getUrl(), e);
        }
    }

    @Override
    public ContactCollection getContactList(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(ContactCollection.getCrestUrl(clientInfo));
        if (data != null)
        {
            data.data.accessed();
            return (ContactCollection) data.data;
        }
        try
        {
            ContactCollection list = (ContactCollection) ContactCollection.getFuture(clientInfo, callback).get();
            list.accessed();
            return list;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + ContactCollection.getCrestUrl(clientInfo), e);
        }
    }

    @Override
    public ConstellationCollection getConstellationCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(ConstellationCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (ConstellationCollection) data.data;
        }
        try
        {
            ConstellationCollection value = (ConstellationCollection) ConstellationCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + ConstellationCollection.getUrl(), e);
        }
    }

    @Override
    public ItemGroupCollection getItemGroupCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(ItemGroupCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (ItemGroupCollection) data.data;
        }
        try
        {
            ItemGroupCollection value = (ItemGroupCollection) ItemGroupCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + ItemGroupCollection.getUrl(), e);
        }
    }

    @Override
    public NpcCorporationsCollection getCorporationCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(NpcCorporationsCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (NpcCorporationsCollection) data.data;
        }
        try
        {
            NpcCorporationsCollection value = (NpcCorporationsCollection) NpcCorporationsCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + NpcCorporationsCollection.getUrl(), e);
        }
    }

    @Override
    public AllianceCollection getAllianceCollection() throws SourceFailureException
    {
        CacheData data = cache.get(AllianceCollection.getCrestUrl());
        if (data != null)
        {
            data.data.accessed();
            return (AllianceCollection) data.data;
        }
        try
        {
            AllianceCollection epdata = (AllianceCollection) AllianceCollection.getFuture(callback).get();
            epdata.accessed();
            return epdata;
        } catch (Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + AllianceCollection.getCrestUrl(), e);
        }
    }

    @Override
    public ItemTypeCollection getItemTypeCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(ItemTypeCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (ItemTypeCollection) data.data;
        }
        try
        {
            ItemTypeCollection value = (ItemTypeCollection) ItemTypeCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + ItemTypeCollection.getUrl(), e);
        }
    }

    @Override
    public TokenDecode getTokenDecode(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(TokenDecode.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (TokenDecode) data.data;
        }
        try
        {
            TokenDecode value = (TokenDecode) TokenDecode.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + TokenDecode.getUrl(), e);
        }
    }

    @Override
    public MarketTypePriceCollection getMarketTypePriceCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(MarketTypePriceCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (MarketTypePriceCollection) data.data;
        }
        try
        {
            MarketTypePriceCollection value = (MarketTypePriceCollection) MarketTypePriceCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + MarketTypePriceCollection.getUrl(), e);
        }
    }

    @Override
    public OpportunityTasksCollection getOpportunityTasksCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(OpportunityTasksCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (OpportunityTasksCollection) data.data;
        }
        try
        {
            OpportunityTasksCollection value = (OpportunityTasksCollection) OpportunityTasksCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + OpportunityTasksCollection.getUrl(), e);
        }
    }

    @Override
    public OpportunityGroupsCollection getOpportunityGroupsCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(OpportunityGroupsCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (OpportunityGroupsCollection) data.data;
        }
        try
        {
            OpportunityGroupsCollection value = (OpportunityGroupsCollection) OpportunityGroupsCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + OpportunityGroupsCollection.getUrl(), e);
        }
    }

    @Override
    public ItemCategoryCollection getItemCategoryCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(ItemCategoryCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (ItemCategoryCollection) data.data;
        }
        try
        {
            ItemCategoryCollection value = (ItemCategoryCollection) ItemCategoryCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + ItemCategoryCollection.getUrl(), e);
        }
    }

    @Override
    public RegionCollection getRegionCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(RegionCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (RegionCollection) data.data;
        }
        try
        {
            RegionCollection value = (RegionCollection) RegionCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + RegionCollection.getUrl(), e);
        }
    }

    @Override
    public BloodlineCollection getBloodlineCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(BloodlineCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (BloodlineCollection) data.data;
        }
        try
        {
            BloodlineCollection value = (BloodlineCollection) BloodlineCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + BloodlineCollection.getUrl(), e);
        }
    }

    @Override
    public MarketGroupCollection getMarketGroupCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(MarketGroupCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (MarketGroupCollection) data.data;
        }
        try
        {
            MarketGroupCollection value = (MarketGroupCollection) MarketGroupCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + MarketGroupCollection.getUrl(), e);
        }
    }

    @Override
    public SystemCollection getSystemCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(SystemCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (SystemCollection) data.data;
        }
        try
        {
            SystemCollection value = (SystemCollection) SystemCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + SystemCollection.getUrl(), e);
        }
    }

    @Override
    public SovCampaignsCollection getSovCampaignsCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(SovCampaignsCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (SovCampaignsCollection) data.data;
        }
        try
        {
            SovCampaignsCollection value = (SovCampaignsCollection) SovCampaignsCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + SovCampaignsCollection.getUrl(), e);
        }
    }

    @Override
    public SovStructureCollection getSovStructureCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(SovStructureCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (SovStructureCollection) data.data;
        }
        try
        {
            SovStructureCollection value = (SovStructureCollection) SovStructureCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + SovStructureCollection.getUrl(), e);
        }
    }

    @Override
    public TournamentCollection getTournamentCollection() throws SourceFailureException
    {
        CacheData data = cache.get(TournamentCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (TournamentCollection) data.data;
        }
        try
        {
            TournamentCollection value = (TournamentCollection) TournamentCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + TournamentCollection.getUrl(), e);
        }
    }

    public TournamentTournament getTournament(long tournamentId) throws SourceFailureException
    {
        String url = TournamentTournament.getUrl();
        url += tournamentId + "/";
        CacheData data = cache.get(url);
        if (data != null)
        {
            data.data.accessed();
            return (TournamentTournament) data.data;
        }
        try
        {
            TournamentTournament value = (TournamentTournament) TournamentTournament.getFuture(tournamentId, callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + TournamentTournament.getUrl(), e);
        }
    }

    public TournamentSeriesCollection getTournamentSeries(long tournamentId) throws SourceFailureException
    {
        String url = TournamentSeriesCollection.getCrestUrl();
        url += tournamentId + "/";
        CacheData data = cache.get(url);
        if (data != null)
        {
            data.data.accessed();
            return (TournamentSeriesCollection) data.data;
        }
        try
        {
            TournamentSeriesCollection value = (TournamentSeriesCollection) TournamentSeriesCollection.getFuture(tournamentId, callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + TournamentSeriesCollection.getCrestUrl(), e);
        }
    }

    public TournamentMatchCollection getTournamentMatches(long tournamentId) throws SourceFailureException
    {
        String url = TournamentMatchCollection.getCrestUrl();
        url += tournamentId + "/";
        CacheData data = cache.get(url);
        if (data != null)
        {
            data.data.accessed();
            return (TournamentMatchCollection) data.data;
        }
        try
        {
            TournamentMatchCollection value = (TournamentMatchCollection) TournamentMatchCollection.getFuture(tournamentId, callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + TournamentMatchCollection.getCrestUrl(), e);
        }
    }

    @Override
    public VirtualGoodStore getVirtualGoodStore(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(VirtualGoodStore.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (VirtualGoodStore) data.data;
        }
        try
        {
            VirtualGoodStore value = (VirtualGoodStore) VirtualGoodStore.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + VirtualGoodStore.getUrl(), e);
        }
    }

    @Override
    public WarsCollection getWarsCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(WarsCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (WarsCollection) data.data;
        }
        try
        {
            WarsCollection value = (WarsCollection) WarsCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + WarsCollection.getUrl(), e);
        }
    }

    @Override
    public IncursionCollection getIncursionCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(IncursionCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (IncursionCollection) data.data;
        }
        try
        {
            IncursionCollection value = (IncursionCollection) IncursionCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + IncursionCollection.getUrl(), e);
        }
    }

    @Override
    public DogmaAttributeCollection getDogmaAttributeCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(DogmaAttributeCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (DogmaAttributeCollection) data.data;
        }
        try
        {
            DogmaAttributeCollection value = (DogmaAttributeCollection) DogmaAttributeCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + DogmaAttributeCollection.getUrl(), e);
        }
    }

    @Override
    public DogmaEffectCollection getDogmaEffectCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(DogmaEffectCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (DogmaEffectCollection) data.data;
        }
        try
        {
            DogmaEffectCollection value = (DogmaEffectCollection) DogmaEffectCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + DogmaEffectCollection.getUrl(), e);
        }
    }

    @Override
    public RaceCollection getRaceCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(RaceCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (RaceCollection) data.data;
        }
        try
        {
            RaceCollection value = (RaceCollection) RaceCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + RaceCollection.getUrl(), e);
        }
    }

    @Override
    public InsurancePricesCollection getInsurancePricesCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(InsurancePricesCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (InsurancePricesCollection) data.data;
        }
        try
        {
            InsurancePricesCollection value = (InsurancePricesCollection) InsurancePricesCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + InsurancePricesCollection.getUrl(), e);
        }
    }

    @Override
    public IndustryFacilityCollection getIndustryFacilityCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(IndustryFacilityCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (IndustryFacilityCollection) data.data;
        }
        try
        {
            IndustryFacilityCollection value = (IndustryFacilityCollection) IndustryFacilityCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + IndustryFacilityCollection.getUrl(), e);
        }
    }

    @Override
    public IndustrySystemCollection getIndustrySystemCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(IndustrySystemCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (IndustrySystemCollection) data.data;
        }
        try
        {
            IndustrySystemCollection value = (IndustrySystemCollection) IndustrySystemCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + IndustrySystemCollection.getUrl(), e);
        }
    }

    @Override
    public NpcCorporationsCollection getNPCCorporationsCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(NpcCorporationsCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (NpcCorporationsCollection) data.data;
        }
        try
        {
            NpcCorporationsCollection value = (NpcCorporationsCollection) NpcCorporationsCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + NpcCorporationsCollection.getUrl(), e);
        }
    }

    @Override
    public MarketTypeCollection getMarketTypeCollection(CrestClientInfo clientInfo) throws SourceFailureException
    {
        CacheData data = cache.get(MarketTypeCollection.getUrl());
        if (data != null)
        {
            data.data.accessed();
            return (MarketTypeCollection) data.data;
        }
        try
        {
            MarketTypeCollection value = (MarketTypeCollection) MarketTypeCollection.getFuture(callback).get();
            value.accessed();
            return value;
        }catch(Exception e)
        {
            throw new SourceFailureException("Failed to obtain Data from requested url: " + MarketTypeCollection.getUrl(), e);
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
                if (previousData == null || previousData.data.equals(data))
                    controller.fireCacheEvent(requestData.clientInfo, requestData.url, CacheEventListener.Type.Changed);
                controller.fireCacheEvent(requestData.clientInfo, requestData.url, CacheEventListener.Type.Refreshed);
            }
        }
    }
}
