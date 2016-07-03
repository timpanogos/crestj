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
package com.ccc.crestj.servlet.auth.eve;

import com.github.scribejava.core.builder.api.DefaultApi20;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.oauth.OAuth20Service;

@SuppressWarnings("javadoc")
public class EveAPI extends DefaultApi20
{
    private String loginUrl = null;
    private String tokenUrl = null;

    public EveAPI(String loginUrl, String tokenUrl)
    {
        this.loginUrl = loginUrl;
        this.tokenUrl = tokenUrl;
    }

    @Override
    public String getAccessTokenEndpoint()
    {
        if (tokenUrl == null || tokenUrl.length() == 0)
            throw new RuntimeException("serverBaseUrl is not properly initialized");
        return tokenUrl;
    }

    @Override
    public String getAuthorizationUrl(OAuthConfig config)
    {
        if (loginUrl == null || loginUrl.length() == 0)
            throw new RuntimeException("loginBaseUrl is not properly initialized");
        return loginUrl;
    }

//    @Override
//    public OAuth20Service createService(OAuthConfig config)
//    {
//        return new EveOAuth20Service(this, config);
//    }
}