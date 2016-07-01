/*
**  Copyright (c) 2010-2015, Panasonic Corporation.
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

package org.opendof.tools.repository.interfaces.servlet.auth.jira;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import javax.xml.bind.DatatypeConverter;

import com.github.scribejava.core.builder.api.DefaultApi10a;
import com.github.scribejava.core.model.OAuth1RequestToken;
import com.github.scribejava.core.services.RSASha1SignatureService;
import com.github.scribejava.core.services.SignatureService;

public class JiraAPI extends DefaultApi10a {
  private static final String SERVLET_BASE_URL = "/plugins/servlet";
  private static final String AUTHORIZE_URL = "/oauth/authorize?oauth_token=%s";
  private static final String REQUEST_TOKEN_RESOURCE = "/oauth/request-token";
  private static final String ACCESS_TOKEN_RESOURCE = "/oauth/access-token";

  private String serverBaseUrl = null;

  private String privateKey = null;

  public JiraAPI(String serverBaseUrl, String privateKey) {
    this.serverBaseUrl = serverBaseUrl;
    this.privateKey = privateKey;
  }

  @Override
  public String getAccessTokenEndpoint() {
    if (null == serverBaseUrl || 0 == serverBaseUrl.length()) {
      throw new RuntimeException("serverBaseUrl is not properly initialized");
    }

    return serverBaseUrl + SERVLET_BASE_URL + ACCESS_TOKEN_RESOURCE;
  }

  @Override
  public String getRequestTokenEndpoint() {
    if (null == serverBaseUrl || 0 == serverBaseUrl.length()) {
      throw new RuntimeException("serverBaseUrl is not properly initialized");
    }

    return serverBaseUrl + SERVLET_BASE_URL + REQUEST_TOKEN_RESOURCE;
  }

  @Override
  public String getAuthorizationUrl(OAuth1RequestToken requestToken) {
    if (null == serverBaseUrl || 0 == serverBaseUrl.length()) {
      throw new RuntimeException("serverBaseUrl is not properly initialized");
    }

    return String.format(serverBaseUrl + SERVLET_BASE_URL + AUTHORIZE_URL, requestToken.getToken());
  }

  @Override
  public SignatureService getSignatureService() {
    if (null == privateKey || 0 == privateKey.length()) {
      throw new RuntimeException("privateKey is not properly initialized");
    }

    try {
      KeyFactory fac = KeyFactory.getInstance("RSA");
      PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(DatatypeConverter.parseBase64Binary(privateKey));
      PrivateKey privateKey = fac.generatePrivate(privKeySpec);
      return new RSASha1SignatureService(privateKey);
    }
    catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}