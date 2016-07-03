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
import com.github.scribejava.core.model.AbstractRequest;
import com.github.scribejava.core.model.OAuthConfig;
import com.github.scribejava.core.model.OAuthConstants;
import com.github.scribejava.core.oauth.OAuth20Service;

@SuppressWarnings("javadoc")
public class EveOAuth20Service extends OAuth20Service
{
    DefaultApi20 api;

    public EveOAuth20Service(DefaultApi20 api, OAuthConfig config)
    {
        super(api, config);
        this.api = api;
    }

    @Override
    protected <T extends AbstractRequest> T createAccessTokenRequest(String code, T request)
    {
        final OAuthConfig config = getConfig();
        request.addParameter(OAuthConstants.CLIENT_ID, config.getApiKey());
        request.addParameter(OAuthConstants.CLIENT_SECRET, config.getApiSecret());
        request.addParameter(OAuthConstants.CODE, code);
        request.addParameter(OAuthConstants.REDIRECT_URI, config.getCallback());
        if (config.hasScope())
        {
            request.addParameter(OAuthConstants.SCOPE, config.getScope());
        }
        if (config.hasGrantType())
        {
            request.addParameter(OAuthConstants.GRANT_TYPE, config.getGrantType());
        }
        return request;
    }

    // public final OAuth2AccessToken getAccessToken(String code)
    // {
    // OAuthRequest request = new OAuthRequest(api.getAccessTokenVerb(), api.getAccessTokenEndpoint(), this);
    // request = createAccessTokenRequest(code, request);
    //
    // return sendAccessTokenRequestSync(request);
    // }
}