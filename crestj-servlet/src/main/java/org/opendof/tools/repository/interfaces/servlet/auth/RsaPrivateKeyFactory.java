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
package org.opendof.tools.repository.interfaces.servlet.auth;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@SuppressWarnings({ "javadoc" })
public class RsaPrivateKeyFactory
{
    public static X509Certificate loadPublicX509(String fileName) throws GeneralSecurityException
    {
        InputStream is = null;
        X509Certificate crt = null;
        try
        {
            is = fileName.getClass().getResourceAsStream("/" + fileName);
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            crt = (X509Certificate) cf.generateCertificate(is);
        } finally
        {
            closeSilent(is);
        }
        return crt;
    }

    public static String getStringFromFile(String fileName) throws Exception
    {
        PrivateKey key = null;
        InputStream is = null; 
        try
        {
            is = new FileInputStream(fileName); 
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();
            for (String line = br.readLine(); line != null; line = br.readLine())
                builder.append(line);
            return builder.toString();
        }catch(Exception e)
        {
            throw e;
        }
        finally
        {
            closeSilent(is);
        }
    }
    
    public static String getCommentStripedString(String fileName) throws Exception
    {
        PrivateKey key = null;
        InputStream is = null; 
        try
        {
            is = new FileInputStream(fileName); 
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            StringBuilder builder = new StringBuilder();
            boolean inKey = false;
            for (String line = br.readLine(); line != null; line = br.readLine())
            {
                if (!inKey)
                {
                    if (line.startsWith("-----BEGIN ") && line.endsWith(" PRIVATE KEY-----"))
                    {
                        inKey = true;
                    }
                    continue;
                }
                if (line.startsWith("-----END ") && line.endsWith(" PRIVATE KEY-----"))
                {
                    inKey = false;
                    break;
                }
                builder.append(line);
            }
            return builder.toString();
        }catch(Exception e)
        {
            throw e;
        }
        finally
        {
            closeSilent(is);
        }
    }
    
    public static PrivateKey loadPrivateKey(String fileName) throws Exception
    {
        String keyString = getCommentStripedString(fileName);
        return getPrivateKey(keyString);
    }

    public static PrivateKey getPrivateKey(String value) throws Exception
    {
        PrivateKey key = null;
        byte[] encoded = Base64.getDecoder().decode(value);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        key = kf.generatePrivate(keySpec);
        return key;
    }

    public static void closeSilent(final InputStream is)
    {
        if (is == null)
            return;
        try
        {
            is.close();
        } catch (Exception ign)
        {
        }
    }
}
