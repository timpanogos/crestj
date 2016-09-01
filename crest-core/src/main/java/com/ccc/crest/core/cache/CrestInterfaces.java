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
import com.ccc.crest.core.cache.crest.alliance.AllianceCollection;
import com.ccc.crest.core.cache.crest.bloodline.BloodlineCollection;
import com.ccc.crest.core.cache.crest.constellation.ConstellationCollection;
import com.ccc.crest.core.cache.crest.corporation.CorporationCollection;
import com.ccc.crest.core.cache.crest.decode.TokenDecode;
import com.ccc.crest.core.cache.crest.dogma.DogmaAttributeCollection;
import com.ccc.crest.core.cache.crest.dogma.DogmaEffectCollection;
import com.ccc.crest.core.cache.crest.incursion.IncursionCollection;
import com.ccc.crest.core.cache.crest.industry.IndustryFacilityCollection;
import com.ccc.crest.core.cache.crest.industry.IndustrySystemCollection;
import com.ccc.crest.core.cache.crest.insurancePrice.InsurancePricesCollection;
import com.ccc.crest.core.cache.crest.itemCategory.ItemCategoryCollection;
import com.ccc.crest.core.cache.crest.itemGroup.ItemGroupCollection;
import com.ccc.crest.core.cache.crest.itemType.ItemTypeCollection;
import com.ccc.crest.core.cache.crest.marketGroup.MarketGroupCollection;
import com.ccc.crest.core.cache.crest.marketPrice.MarketTypeCollection;
import com.ccc.crest.core.cache.crest.marketType.MarketTypePriceCollection;
import com.ccc.crest.core.cache.crest.npcCorporation.NPCCorporationsCollection;
import com.ccc.crest.core.cache.crest.opportunity.OpportunityGroupsCollection;
import com.ccc.crest.core.cache.crest.opportunity.OpportunityTasksCollection;
import com.ccc.crest.core.cache.crest.race.RaceCollection;
import com.ccc.crest.core.cache.crest.region.RegionCollection;
import com.ccc.crest.core.cache.crest.sovereignty.SovCampaignsCollection;
import com.ccc.crest.core.cache.crest.sovereignty.SovStructureCollection;
import com.ccc.crest.core.cache.crest.system.SystemCollection;
import com.ccc.crest.core.cache.crest.time.Time;
import com.ccc.crest.core.cache.crest.tournament.TournamentCollection;
import com.ccc.crest.core.cache.crest.virtualGoodStore.VirtualGoodStore;
import com.ccc.crest.core.cache.crest.war.WarsCollection;

@SuppressWarnings("javadoc")
public interface CrestInterfaces
{
	public ConstellationCollection getConstellationCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public ItemGroupCollection getItemGroupCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public CorporationCollection getCorporationCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public AllianceCollection getAllianceCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public ItemTypeCollection getItemTypeCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public TokenDecode getTokenDecode(CrestClientInfo clientInfo) throws SourceFailureException;
    public MarketTypePriceCollection getMarketTypePriceCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public OpportunityTasksCollection getOpportunityTasksCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public OpportunityGroupsCollection getOpportunityGroupsCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public ItemCategoryCollection getItemCategoryCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public RegionCollection getRegionCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public BloodlineCollection getBloodlineCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public MarketGroupCollection getMarketGroupCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public SystemCollection getSystemCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public SovCampaignsCollection getSovCampaignsCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public SovStructureCollection getSovStructureCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public TournamentCollection getTournamentCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public VirtualGoodStore getVirtualGoodStore(CrestClientInfo clientInfo) throws SourceFailureException;
    public WarsCollection getWarsCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public IncursionCollection getIncursionCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public DogmaAttributeCollection getDogmaAttributeCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public DogmaEffectCollection getDogmaEffectCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public RaceCollection getRaceCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public InsurancePricesCollection getInsurancePricesCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public IndustryFacilityCollection getIndustryFacilityCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public IndustrySystemCollection getIndustrySystemCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public NPCCorporationsCollection getNPCCorporationsCollection(CrestClientInfo clientInfo) throws SourceFailureException;
    public Time getTime(CrestClientInfo clientInfo) throws SourceFailureException;
    public MarketTypeCollection getMarketTypeCollection(CrestClientInfo clientInfo) throws SourceFailureException;
}
