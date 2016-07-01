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
package org.opendof.tools.repository.interfaces.servlet.auth.jira.rest;

import java.util.Collections;
import java.util.Map;

import org.opendof.tools.repository.interfaces.servlet.auth.jira.JiraClientInfo;

import net.oauth.OAuth;
import net.oauth.OAuthAccessor;
import net.oauth.OAuthConsumer;
import net.oauth.OAuthMessage;
import net.oauth.OAuthServiceProvider;
import net.oauth.client.OAuthClient;
import net.oauth.client.httpclient4.HttpClient4;
import net.oauth.signature.RSA_SHA1;

@SuppressWarnings("javadoc")
public class JiraRestClient
{
    private static final String RestSessionApi = "/rest/auth/1/session";
    private static final String RestMyselfApi = "/rest/api/2/myself";
    
    public final static String EncodingDefault = "UTF-8";
    public final static String BodyEncodingDefault = "ISO-8859-1";
    public static final String Base64Encoding = "ISO-8859-1";

    private static final String ContentTypeKey = "Content-Type";
    private static final String AcceptKey = "Accept";

//    private static final String ContentLengthKey = "Content-Length";
    private static final String OauthTokenKey = "oauth_token";
//    private static final String OauthAccessSecretKey = "oauth_token_secret";
    private static final String OauthConsumerKeyKey = "oauth_consumer_key";
    private static final String OauthSignatureMethodKey = "oauth_signature_method";
    private static final String OauthVersionKey = "oauth_version";
    private static final String OauthTimestampKey = "oauth_timestamp";
    private static final String OauthNonce = "oauth_nonce";
    public static final String OauthSignatureKey = "oauth_signature";

    public static final String ContentTypeDefault = "application/x-www-form-urlencoded";
//    public static final String ContentTypeDefault = "application/json";
    public static final String AcceptDefault = "application/x-www-form-urlencoded";
//    private static final String AcceptDefault = "application/json";
    private static final String OauthSignatureMethodDefault = "RSA-SHA1";
    private static final String OauthVersionDefault = "1.0";
    
    private JiraRestClient()
    {
    }
    
    public static void getSessionInfo(JiraClientInfo clientInfo) throws Exception
    {
        String api = RestSessionApi;
        
        String baseUrl = clientInfo.getApplicationUrl();
        if (baseUrl.endsWith("/"))
            baseUrl = baseUrl.substring(0, baseUrl.length() - 2);
        api = baseUrl + api;
        OAuthAccessor accessor = getAccessor(clientInfo);
        OAuthClient client = new OAuthClient(new HttpClient4());
        accessor.accessToken = clientInfo.getAccessToken().getParameter(OauthTokenKey);
        OAuthMessage response = client.invoke(accessor, api, Collections.<Map.Entry<?, ?>> emptySet());
        String jsonInfo =  response.readBodyAsString();
        String nameTag = "\"name\":\"";
        int idx = jsonInfo.indexOf(nameTag);
        if(idx == -1)
        {
            clientInfo.setName("name not found");
            return;
        }
        String name = jsonInfo.substring(idx+nameTag.length());
        idx = name.indexOf('\"');
        name = name.substring(0, idx);
        clientInfo.setName(name);
        
        api = baseUrl + RestMyselfApi;
        response = client.invoke(accessor, api, Collections.<Map.Entry<?, ?>> emptySet());
        jsonInfo =  response.readBodyAsString();
        
        nameTag = "\"displayName\":";
        idx = jsonInfo.indexOf(nameTag);
        if(idx == -1)
            clientInfo.setEmail("name not found");
        else
        {
            name = jsonInfo.substring(idx+nameTag.length()+1);
            idx = name.indexOf('\"');
            name = name.substring(0, idx);
            clientInfo.setName(name);
        }
        
        String emailTag = "\"emailAddress\":";
        idx = jsonInfo.indexOf(emailTag);
        if(idx == -1)
        {
            clientInfo.setEmail("email not found");
            return;
        }
        String email = jsonInfo.substring(idx+emailTag.length()+1);
        idx = email.indexOf('\"');
        email = email.substring(0, idx);
        clientInfo.setEmail(email);
    }
    
    private static OAuthAccessor getAccessor(JiraClientInfo clientInfo)
    {
        OAuthServiceProvider serviceProvider = new OAuthServiceProvider(null, null, null);//getRequestTokenUrl(), getAuthorizeUrl(), getAccessTokenUrl());
        OAuthConsumer consumer = new OAuthConsumer(null, clientInfo.getConsumerKey(), null, serviceProvider);//callback, consumerKey, null, serviceProvider);
        consumer.setProperty(RSA_SHA1.PRIVATE_KEY, clientInfo.getPrivateHash());
        consumer.setProperty(OAuth.OAUTH_SIGNATURE_METHOD, OAuth.RSA_SHA1);
        return new OAuthAccessor(consumer);
    }
    
//TODO: finish figuring this out, or cleanup    
/* ****************************************************************************
 * The methods from here are down are close to working without the google libraries    
******************************************************************************/    
    
//    private static String getBaseSignatureString(List<NameValuePair> nvps, String url) throws UnsupportedEncodingException
//    {
//        String methodString = "GET ";
//        //@formatter:off
//        StringBuilder parameters = new StringBuilder()
//                        .append(OauthConsumerKeyKey).append("=").append(nvps.get(0)).append("&")
//                        .append(OauthNonce).append("=").append(nvps.get(1)).append("&")
//                        .append(OauthSignatureMethodKey).append("=").append(nvps.get(2)).append("&")
//                        .append(OauthTimestampKey).append("=").append(nvps.get(3)).append("&")
//                        .append(OauthTokenKey).append("=").append(nvps.get(4)).append("&")
//                        .append(OauthVersionKey).append("=").append(nvps.get(5));
//       //@formatter:on
//
//        String encodedUrl = URLEncoder.encode(url, EncodingDefault);
//        String encodedParams = URLEncoder.encode(parameters.toString(), EncodingDefault);
//        return methodString + encodedUrl + "?" + encodedParams;
//    }
//
//    private static final Encoder encoder = Base64.getUrlEncoder();
//    private static String getSignature(String baseString, String privateKey) throws Exception
//    {
//        byte[] baseBytes = baseString.getBytes(EncodingDefault);
//        PrivateKey pkey = RsaPrivateKeyFactory.getPrivateKey(privateKey);
//        Signature signer = Signature.getInstance("SHA1withRSA");
//        signer.initSign(pkey);
//        signer.update(baseBytes);
//        byte[] signature = signer.sign(); 
//        byte[] encoded = encoder.encode(signature);
//        return new String(encoded, Base64Encoding);
//    }
//    
//    public static void get(JiraClientInfo clientInfo, String restUrl) throws Exception
//    {
//        String baseUrl = clientInfo.getApplicationUrl();
//        if (baseUrl.endsWith("/"))
//            baseUrl = baseUrl.substring(0, baseUrl.length() - 2);
//        restUrl = baseUrl + restUrl;
//        //@formatter:off
//        RequestBuilder sessionBuilder = RequestBuilder.get()
//                        .setUri(restUrl)
//                        .addHeader(new BasicHeader(ContentTypeKey, ContentTypeDefault))
//                        .addHeader(new BasicHeader(AcceptKey, AcceptDefault))
//                        .addParameter(OauthConsumerKeyKey, clientInfo.getConsumerKey())
//                        .addParameter(OauthNonce, "" + System.nanoTime())
//                        .addParameter(OauthSignatureMethodKey, OauthSignatureMethodDefault)
//                        .addParameter(OauthTimestampKey, "" + System.currentTimeMillis() / 1000)
//                        .addParameter(OauthTokenKey, clientInfo.getAccessToken().getParameter(OauthTokenKey))
//                        .addParameter(OauthVersionKey, OauthVersionDefault);
//        
//        //@formatter:on
//        String baseString = getBaseSignatureString(sessionBuilder.getParameters(), restUrl);
//        String signature = getSignature(baseString, null);
//        HttpUriRequest sessionRequest = sessionBuilder.addParameter(OauthSignatureKey, signature).build();                        
//        
//        CloseableHttpClient client = null;
//        CloseableHttpResponse response = null;
//        try
//        {
//            client = HttpClients.createDefault();
//            response = client.execute(sessionRequest);
//        } catch (Exception e)
//        {
//            e.printStackTrace();
//            e.printStackTrace();
//        }
//
//        finally
//        {
//            if (response != null)
//            {
//                try
//                {
//                    response.close();
//                } catch (IOException e)
//                {
//                    // best try, ok to just log
//                    LoggerFactory.getLogger(JiraRestClient.class).warn("HttpResponse.close failed", e);
//                }
//            }
//            if (client != null)
//                client.close();
//        }
//    }
}