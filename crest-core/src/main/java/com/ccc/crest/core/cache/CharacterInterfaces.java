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


import com.ccc.crest.core.CrestClientInfo;
import com.ccc.crest.core.cache.crest.character.ContactCollection;
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

@SuppressWarnings("javadoc")
public interface CharacterInterfaces
{
	public AccountBalance getAccountBalance(CrestClientInfo clientInfo) throws SourceFailureException;
	public AssetList getAssetList(CrestClientInfo clientInfo) throws SourceFailureException;
	public Blueprints getBlueprints(CrestClientInfo clientInfo) throws SourceFailureException;
	public Bookmarks getBookmarks(CrestClientInfo clientInfo) throws SourceFailureException;
	public CalendarEventAttendees getCalendarEventAttendees(CrestClientInfo clientInfo) throws SourceFailureException;
	public CharacterSheet getCharacterSheet(CrestClientInfo clientInfo) throws SourceFailureException;
	public ChatChannels getChatChannels(CrestClientInfo clientInfo) throws SourceFailureException;
	public Clones getClones(CrestClientInfo clientInfo) throws SourceFailureException;
	public ContactCollection getContactList(CrestClientInfo clientInfo) throws SourceFailureException;
	public ContactNotifications getContactNotifications(CrestClientInfo clientInfo) throws SourceFailureException;
	public ContractBids getContractBids(CrestClientInfo clientInfo) throws SourceFailureException;
	public ContractItems getContractItems(CrestClientInfo clientInfo) throws SourceFailureException;
	public Contracts getContracts(CrestClientInfo clientInfo) throws SourceFailureException;
	public FacWarStats getFacWarStats(CrestClientInfo clientInfo) throws SourceFailureException;
	public IndustryJobs getIndustryJobs(CrestClientInfo clientInfo) throws SourceFailureException;
	public IndustryJobsHistory getIndustryJobsHistory(CrestClientInfo clientInfo) throws SourceFailureException;
	public KillLog getKillLog(CrestClientInfo clientInfo) throws SourceFailureException;
	public KillMails getKillMails(CrestClientInfo clientInfo) throws SourceFailureException;
	public Locations getLocations(CrestClientInfo clientInfo) throws SourceFailureException;
	public MailBodies getMailBodies(CrestClientInfo clientInfo) throws SourceFailureException;
	public MailingLists getMailingLists(CrestClientInfo clientInfo) throws SourceFailureException;
	public MailMessages getMailMessages(CrestClientInfo clientInfo) throws SourceFailureException;
	public MarketOrders getMarketOrders(CrestClientInfo clientInfo) throws SourceFailureException;
	public Medals getMedals(CrestClientInfo clientInfo) throws SourceFailureException;
	public Notifications getNotifications(CrestClientInfo clientInfo) throws SourceFailureException;
	public NotificationTexts getNotificationTexts(CrestClientInfo clientInfo) throws SourceFailureException;
	public PlanetaryColonies getPlanetaryColonies(CrestClientInfo clientInfo) throws SourceFailureException;
	public PlanetaryLinks getPlanetaryLinks(CrestClientInfo clientInfo) throws SourceFailureException;
	public PlanetaryPins getPlanetaryPins(CrestClientInfo clientInfo) throws SourceFailureException;
	public PlanetaryRoutes getPlanetaryRoutes(CrestClientInfo clientInfo) throws SourceFailureException;
	public Research getResearch(CrestClientInfo clientInfo) throws SourceFailureException;
	public SkillInTraining getSkillInTraining(CrestClientInfo clientInfo) throws SourceFailureException;
	public SkillQueue getSkillQueue(CrestClientInfo clientInfo) throws SourceFailureException;
	public Skills getSkills(CrestClientInfo clientInfo) throws SourceFailureException;
	public Standings getStandings(CrestClientInfo clientInfo) throws SourceFailureException;
	public UpcomingCalendarEvents getUpcomingCalendarEvents(CrestClientInfo clientInfo) throws SourceFailureException;
	public WalletJournal getWalletJournal(CrestClientInfo clientInfo) throws SourceFailureException;
	public WalletTransactions getWalletTransactions(CrestClientInfo clientInfo) throws SourceFailureException;
}
