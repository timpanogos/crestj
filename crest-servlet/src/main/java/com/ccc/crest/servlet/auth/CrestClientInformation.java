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
package com.ccc.crest.servlet.auth;

import org.apache.wicket.request.cycle.RequestCycle;

import com.ccc.crest.json.OauthVerifyData;
import com.ccc.tools.servlet.BaseClientInformation;

@SuppressWarnings("javadoc")
public class CrestClientInformation extends BaseClientInformation
{
    private static final long serialVersionUID = -9115180428033729879L;
    private OauthVerifyData verifyData;
    
    public CrestClientInformation(RequestCycle requestCycle)
    {
        super(requestCycle);
    }

    public synchronized OauthVerifyData getVerifyData()
    {
        return verifyData;
    }

    public synchronized void setVerifyData(OauthVerifyData verifyData)
    {
        this.verifyData = verifyData;
    }
}
