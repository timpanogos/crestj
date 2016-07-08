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
package com.ccc.crest.cache;

import java.util.HashMap;

import com.ccc.crest.cache.account.AccountInterfaces;
import com.ccc.crest.cache.account.AccountStatus;
import com.ccc.crest.cache.account.ApiKeyInfo;
import com.ccc.crest.cache.account.CallList;
import com.ccc.crest.cache.account.Characters;
import com.ccc.crest.cache.character.AccountBalance;
import com.ccc.crest.cache.character.AssetList;
import com.ccc.crest.cache.character.Blueprints;
import com.ccc.crest.cache.character.Bookmarks;
import com.ccc.crest.cache.character.CalendarEventAttendees;
import com.ccc.crest.cache.character.CharacterInterfaces;
import com.ccc.crest.cache.character.CharacterSheet;
import com.ccc.crest.cache.character.ChatChannels;
import com.ccc.crest.cache.character.Clones;
import com.ccc.crest.cache.character.ContactList;
import com.ccc.crest.cache.character.ContactNotifications;
import com.ccc.crest.cache.character.ContractBids;
import com.ccc.crest.cache.character.ContractItems;
import com.ccc.crest.cache.character.Contracts;
import com.ccc.crest.cache.character.FacWarStats;
import com.ccc.crest.cache.character.IndustryJobs;
import com.ccc.crest.cache.character.IndustryJobsHistory;
import com.ccc.crest.cache.character.KillLog;
import com.ccc.crest.cache.character.KillMails;
import com.ccc.crest.cache.character.Locations;
import com.ccc.crest.cache.character.MailBodies;
import com.ccc.crest.cache.character.MailMessages;
import com.ccc.crest.cache.character.MailingLists;
import com.ccc.crest.cache.character.MarketOrders;
import com.ccc.crest.cache.character.Medals;
import com.ccc.crest.cache.character.NotificationTexts;
import com.ccc.crest.cache.character.Notifications;
import com.ccc.crest.cache.character.PlanetaryColonies;
import com.ccc.crest.cache.character.PlanetaryLinks;
import com.ccc.crest.cache.character.PlanetaryPins;
import com.ccc.crest.cache.character.PlanetaryRoutes;
import com.ccc.crest.cache.character.Research;
import com.ccc.crest.cache.character.SkillInTraining;
import com.ccc.crest.cache.character.SkillQueue;
import com.ccc.crest.cache.character.Skills;
import com.ccc.crest.cache.character.Standings;
import com.ccc.crest.cache.character.UpcomingCalendarEvents;
import com.ccc.crest.cache.character.WalletJournal;
import com.ccc.crest.cache.character.WalletTransactions;
import com.ccc.crest.client.CrestClient;
import com.ccc.crest.client.CrestResponseCallback;
import com.ccc.crest.servlet.auth.CrestClientInfo;
import com.ccc.tools.ElapsedTimer;

@SuppressWarnings("javadoc")
public class DataCache implements AccountInterfaces, CharacterInterfaces
{
	private final HashMap<String, CacheData> cache;
	private final DataCacheCallback callback;

	public DataCache()
	{
		cache = new HashMap<String, CacheData>();
		callback = new DataCacheCallback();
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
        if(data != null)
        {
            data.data.accessed();
            return (ContactList)data.data;
        }
        try
        {
            ContactList list = (ContactList)ContactList.getContacts(clientInfo, callback).get();
            list.accessed();
            return list;
        } catch (Exception e)
        {
            throw new SourceFailureException("Failed to obtain requested ");
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
    public CallList getCallList(CrestClientInfo clientInfo) throws SourceFailureException
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
                cache.put(requestData.url, new CacheData(data));
            }
String msg = ElapsedTimer.getElapsedTime("cache contactlist full circle", 2);
ElapsedTimer.resetElapsedTimers(2, 1);
System.err.println(msg);
            if(data.isContinueRefresh())
            {
                CrestClient.getClient().getCrest(requestData);
            }
        }
    }
}
