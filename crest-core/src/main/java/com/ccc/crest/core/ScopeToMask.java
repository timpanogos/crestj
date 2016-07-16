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
package com.ccc.crest.core;

import java.util.HashMap;

@SuppressWarnings({ "javadoc" })
public class ScopeToMask
{
    //  https://community.eveonline.com/support/api-key/CreatePredefined?accessMask=133038347

    public final static HashMap<String, ScopeToMask> characterScopes;
    public final static HashMap<String, ScopeToMask> corporateScopes;

    static
    {
        characterScopes = new HashMap<>();
        characterScopes.put("characterWalletRead", new ScopeToMask("characterWalletRead", new long[] { 0x01, 0x200000, 0x400000 }, Type.Character)); // AccountBalance, WalletJournal, WalletTransactions
        characterScopes.put("characterAssetsRead", new ScopeToMask("characterAssetsRead", new long[] { 0x00000002, 0x08000000 }, Type.Character));
        characterScopes.put("characterCalendarRead", new ScopeToMask("characterCalendarRead", new long[] { 0x00000004, 0x00100000 }, Type.Character));
        characterScopes.put("characterContactsRead", new ScopeToMask("characterContactsRead", new long[] { 0x00000010, 0x00000020, 0x000080000}, Type.Character));
        characterScopes.put("characterContactsWrite", new ScopeToMask("characterContactsWrite", new long[] { 0x00000000}, Type.Character));
        characterScopes.put("characterFactionalWarfareRead", new ScopeToMask("characterFactionalWarfareRead", new long[] { 0x00000040 }, Type.Character));
        characterScopes.put("characterIndustryJobsRead", new ScopeToMask("characterIndustryJobsRead", new long[] { 0x00000080 }, Type.Character));
        characterScopes.put("characterKillsRead", new ScopeToMask("characterKillsRead", new long[] { 0x00000100 }, Type.Character));
        characterScopes.put("characterLocationRead", new ScopeToMask("characterLocationRead", new long[] { 0x00000000 }, Type.Character));
        characterScopes.put("characterMailRead", new ScopeToMask("characterMailRead", new long[] { 0x00000200, 0x00000400, 0x00000800 }, Type.Character));
        characterScopes.put("characterMarketOrdersRead", new ScopeToMask("characterMarketOrdersRead", new long[] { 0x00001000 }, Type.Character)); // MarketOrders
        characterScopes.put("characterMedalsRead", new ScopeToMask("characterMedalsRead", new long[] { 0x00002000 }, Type.Character));
        characterScopes.put("characterNavigationWrite", new ScopeToMask("characterNavigationWrite", new long[] { 0x00000000}, Type.Character));
        characterScopes.put("characterNotificationsRead", new ScopeToMask("characterNotificationsRead", new long[] { 0x00004000, 0x00008000 }, Type.Character));
        characterScopes.put("characterResearchRead", new ScopeToMask("characterResearchRead", new long[] { 0x00010000 }, Type.Character));
        characterScopes.put("characterSkillsRead", new ScopeToMask("characterSkillsRead", new long[] { 0x00020000, 0x00040000, 0x40000000 }, Type.Character));
        characterScopes.put("characterAccountRead", new ScopeToMask("characterAccountRead", new long[] { 0x02000000 }, Type.Account));
        characterScopes.put("characterContractsRead", new ScopeToMask("characterContractsRead", new long[] { 0x04000000 }, Type.Character));
        characterScopes.put("characterBookmarksRead", new ScopeToMask("characterBookmarksRead", new long[] { 0x10000000 }, Type.Character));
        characterScopes.put("characterChatChannelsRead", new ScopeToMask("characterChatChannelsRead", new long[] { 0x20000000 }, Type.Character));
        characterScopes.put("characterClonesRead", new ScopeToMask("characterClonesRead", new long[] { 0x80000000 }, Type.Character));

        corporateScopes = new HashMap<>();
        corporateScopes.put("corporationWalletRead", new ScopeToMask("corporationWalletRead", new long[] { 0x00000008, 0x00100000, 0x00200000}, Type.Corporate));
        corporateScopes.put("corporationAssetsRead", new ScopeToMask("corporationAssetsRead", new long[] { 0x00000020, 0x00040000, 0x01000000}, Type.Corporate));
        corporateScopes.put("corporationMedalsRead", new ScopeToMask("corporationMedalsRead", new long[] { 0x00002000 }, Type.Corporate));
        corporateScopes.put("corporationContactsRead", new ScopeToMask("corporationContactsRead", new long[] { 0x00000010, 0x00040000}, Type.Corporate));
        corporateScopes.put("corporationFactionalWarfareRead", new ScopeToMask("corporationFactionalWarfareRead", new long[] { 0x00000040}, Type.Corporate));
        corporateScopes.put("corporationIndustryJobsRead", new ScopeToMask("corporationIndustryJobsRead", new long[] { 0x00000080}, Type.Corporate));
        corporateScopes.put("corporationKillsRead", new ScopeToMask("corporationKillsRead", new long[] { 0x00000100}, Type.Corporate));
        corporateScopes.put("corporationMembersRead", new ScopeToMask("corporationMembersRead", new long[] { 0x00000200, 0x00000400, 0x00000800, 0x00400000, 0x02000000}, Type.Corporate));
        corporateScopes.put("corporationMarketOrdersRead", new ScopeToMask("corporationMarketOrdersRead", new long[] { 0x00001000}, Type.Corporate));
        corporateScopes.put("corporationStructuresRead", new ScopeToMask("corporationStructuresRead", new long[] { 0x00004000, 0x00008000, 0x00020000}, Type.Corporate));
        corporateScopes.put("corporationShareholdersRead", new ScopeToMask("corporationShareholdersRead", new long[] { 0x00010000}, Type.Corporate));
        corporateScopes.put("corporationContractsRead", new ScopeToMask("corporationContractsRead", new long[] { 0x00800000 }, Type.Corporate));
        corporateScopes.put("corporationBookmarksRead", new ScopeToMask("corporationBookmarksRead", new long[] { 0x04000000 }, Type.Corporate));
    }

    public final String name;
    public final long[] masks;
    public final Type type;

    public ScopeToMask(String name, long[] masks, Type type)
    {
        this.name = name;
        this.masks = masks;
        this.type = type;
    }

    // characterFittingsRead
    // characterFittingsWrite

    public enum Type{Public, Character, Corporate, Account, CrestOnlyCharacter, CrestOnlyCorporate, CrestOnlyPublic, XmlOnlyCharacter, XmlOnlyCorporate}
}
