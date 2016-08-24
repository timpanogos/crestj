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
package com.ccc.crest.core.cache.character;

import com.ccc.crest.core.CrestClientInfo;
import com.ccc.crest.core.cache.SourceFailureException;

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
	public ContactList getContactList(CrestClientInfo clientInfo) throws SourceFailureException;
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
