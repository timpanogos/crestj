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
package com.ccc.crest.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccc.crest.cache.DataCache;
import com.ccc.crest.cache.SourceFailureException;
import com.ccc.crest.cache.character.ContactList;
import com.ccc.crest.da.CapsuleerData;
import com.ccc.crest.da.CrestDataAccessor;
import com.ccc.crest.servlet.auth.CrestClientInfo;
import com.ccc.crest.servlet.events.ApiKeyEventListener;
import com.ccc.crest.servlet.events.CommsEventListener;
import com.ccc.tools.ElapsedTimer;
import com.ccc.tools.RequestThrottle;
import com.ccc.tools.RequestThrottle.IntervalType;
import com.ccc.tools.TabToLevel;
import com.ccc.tools.da.DataAccessor;
import com.ccc.tools.da.NotFoundException;
import com.ccc.tools.servlet.CoreController;
import com.ccc.tools.servlet.clientInfo.BaseClientInfo;
import com.ccc.tools.servlet.events.AuthEventListener;

@SuppressWarnings("javadoc")
public class CrestController extends CoreController implements AuthEventListener, CommsEventListener
{
    public final DataCache dataCache;
    private final List<CommsEventListener> commsEventListeners;
    private final List<ApiKeyEventListener> apiKeyEventListeners;
    public Logger log;

    public CrestController()
    {
        log = LoggerFactory.getLogger(getClass());
        dataCache = new DataCache(this);
        registerAuthenticatedEventListener(this);
        commsEventListeners = new ArrayList<>();
        apiKeyEventListeners = new ArrayList<>();
        registerCommunicationEventListener(this);
    }

    
    public boolean capsuleerHasApiKey(String name)
    {
        try
        {
            return ((CrestDataAccessor)dataAccessor).hasApiKeys(name);
        } catch (Exception e)
        {
            // TODO work in with some db down fire event scheme
            log.warn("DataAccessor failure", e);
            return false;
        }
    }
    
    public static CrestController getCrestController()
    {
        return (CrestController) getController();
    }

    public void registerCommunicationEventListener(CommsEventListener listener)
    {
        synchronized (commsEventListeners)
        {
            commsEventListeners.add(listener);
        }
    }

    public void deregisterCommunicationEventListener(CommsEventListener listener)
    {
        synchronized (commsEventListeners)
        {
            commsEventListeners.remove(listener);
        }
    }

    public void fireCommunicationEvent(CrestClientInfo clientInfo, CommsEventListener.Type type)
    {
        blockingExecutor.submit(new FireCommunicationsEventTask(clientInfo, type));
    }

    public void registerApiKeyEventListener(ApiKeyEventListener listener)
    {
        synchronized (apiKeyEventListeners)
        {
            apiKeyEventListeners.add(listener);
        }
    }

    public void deregisterApiKeyEventListener(ApiKeyEventListener listener)
    {
        synchronized (apiKeyEventListeners)
        {
            apiKeyEventListeners.remove(listener);
        }
    }

    public void fireApiKeyEvent(CrestClientInfo clientInfo, ApiKeyEventListener.Type type)
    {
        blockingExecutor.submit(new FireApiKeyEventTask(clientInfo, type));
    }

    @Override
    public void init(Properties properties, TabToLevel format) throws Exception
    {
        super.init(properties, format);
        if (dataAccessor == null)
            throw new Exception(DataAccessor.DaImplKey + " must be specified in the properties file");
    }

    @Override
    public void destroy()
    {
        if (dataCache != null)
            dataCache.clear();
        deregisterAuthenticatedEventListener(this);
        deregisterCommunicationEventListener(this);
        super.destroy();
    }

    private class FireCommunicationsEventTask implements Callable<Void>
    {
        private CrestClientInfo clientInfo;
        private final CommsEventListener.Type type;

        private FireCommunicationsEventTask(CrestClientInfo clientInfo, CommsEventListener.Type type)
        {
            this.clientInfo = clientInfo;
            this.type = type;
        }

        @Override
        public Void call() throws Exception
        {
            try
            {
                synchronized (commsEventListeners)
                {
                    for (CommsEventListener listener : commsEventListeners)
                    {
                        switch (type)
                        {
                            case CrestUp:
                                listener.crestUp(clientInfo);
                                break;
                            case CrestDown:
                                listener.crestDown(clientInfo);
                                break;
                            case XmlUp:
                                listener.xmlUp(clientInfo);
                                break;
                            case XmlDown:
                                listener.xmlDown(clientInfo);
                                break;
                            default:
                                break;
                        }
                    }
                }
            } catch (Exception e)
            {
                LoggerFactory.getLogger(getClass()).warn("An communicationsEventListener has thrown an exception", e);
            }
            return null;
        }
    }

    private class FireApiKeyEventTask implements Callable<Void>
    {
        private CrestClientInfo clientInfo;
        private final ApiKeyEventListener.Type type;

        private FireApiKeyEventTask(CrestClientInfo clientInfo, ApiKeyEventListener.Type type)
        {
            this.clientInfo = clientInfo;
            this.type = type;
        }

        @Override
        public Void call() throws Exception
        {
            try
            {
                synchronized (apiKeyEventListeners)
                {
                    for (ApiKeyEventListener listener : apiKeyEventListeners)
                        listener.needsApiKey(clientInfo);
                }
            } catch (Exception e)
            {
                LoggerFactory.getLogger(getClass()).warn("An communicationsEventListener has thrown an exception", e);
            }
            return null;
        }
    }
    
    private class TestTask implements Callable<Void>
    {
        @Override
        public Void call() throws Exception
        {
            test4();
            return null;
        }

        private void test4() throws InterruptedException
        {
            RequestThrottle rt = IntervalType.getRequestThrottle(1, 300);
            log.info("start here");
            ElapsedTimer.startTimer(0);
            ElapsedTimer.startTimer(1);
            for (int i = 0; i < 3; i++)
            {
                if (i >= 1)
                    Thread.sleep(250);
                rt.waitAsNeeded();
                log.info(ElapsedTimer.getElapsedTime("waited: ", 1));
                log.info(ElapsedTimer.getElapsedTime("running: ", 0));
                ElapsedTimer.resetElapsedTimers(1, 1);
            }
            log.info(ElapsedTimer.getElapsedTime("total: ", 0));
            return;
        }

        private void test3() throws InterruptedException
        {
            RequestThrottle rt = new RequestThrottle(1, IntervalType.Minute);
            log.info("start here");
            ElapsedTimer.startTimer(0);
            ElapsedTimer.startTimer(1);
            for (int i = 0; i < 5; i++)
            {
                if (i >= 1)
                    Thread.sleep(250);
                rt.waitAsNeeded();
                log.info(ElapsedTimer.getElapsedTime("waited: ", 1));
                log.info(ElapsedTimer.getElapsedTime("running: ", 0));
                ElapsedTimer.resetElapsedTimers(1, 1);
            }
            log.info(ElapsedTimer.getElapsedTime("total: ", 0));
            return;
        }

        private void test2() throws InterruptedException
        {
            RequestThrottle rt = new RequestThrottle(5, IntervalType.Second);
            log.info("start here");
            ElapsedTimer.startTimer(0);
            ElapsedTimer.startTimer(1);
            for (int i = 0; i < 30; i++)
            {
                if (i == 3)
                    Thread.sleep(1000);
                if (i % 4 == 0)
                    Thread.sleep(250);
                rt.waitAsNeeded();
                log.info(ElapsedTimer.getElapsedTime("waited: ", 1));
                log.info(ElapsedTimer.getElapsedTime("running: ", 0));
                ElapsedTimer.resetElapsedTimers(1, 1);
            }
            log.info(ElapsedTimer.getElapsedTime("total: ", 0));
            return;
        }

        private void test1() throws InterruptedException
        {
            RequestThrottle rt = new RequestThrottle(1, IntervalType.Second);
            log.info("start here");
            ElapsedTimer.startTimer(0);
            ElapsedTimer.startTimer(1);
            for (int i = 0; i < 5; i++)
            {
                if (i >= 1)
                    Thread.sleep(250);
                rt.waitAsNeeded();
                log.info(ElapsedTimer.getElapsedTime("waited: ", 1));
                log.info(ElapsedTimer.getElapsedTime("running: ", 0));
                ElapsedTimer.resetElapsedTimers(1, 1);
            }
            log.info(ElapsedTimer.getElapsedTime("total: ", 0));
            return;
        }
    }

    /*
     * *************************************************************************
     * *** AuthenticatedEventListener impl
     ******************************************************************************/
    @Override
    public void authenticated(BaseClientInfo clientInfo)
    {
        CrestClientInfo ccinfo = (CrestClientInfo) clientInfo;
        if (ccinfo.getVerifyData() == null)
        {
            log.warn("got a successfully authenticated, but no verifed user.");
            return;
        }

        String name = ccinfo.getVerifyData().CharacterName;
        long id = Long.parseLong(ccinfo.getVerifyData().CharacterID);

        log.info(id + " " + name + " authenticated");
        if (dataAccessor.isUp())
        {
            CrestDataAccessor da = (CrestDataAccessor) dataAccessor;
            try
            {
                CapsuleerData userData = da.getCapsuleer(ccinfo.getVerifyData().CharacterName);
                CapsuleerData updateData = new CapsuleerData(userData.capsuleer, userData.capsuleerId, userData.apiKeyId, userData.apiCode, ccinfo.getRefreshToken());
                da.updateCapsuleer(name, updateData);
                if(userData.apiKeyId == -1)
                    fireApiKeyEvent(ccinfo, ApiKeyEventListener.Type.NeedsApi);
            } catch (NotFoundException e1)
            {
                CapsuleerData userData = new CapsuleerData(name, id, -1, null, ccinfo.getRefreshToken());
                try
                {
                    da.addCapsuleer(userData);
                    fireApiKeyEvent(ccinfo, ApiKeyEventListener.Type.NeedsApi);
                } catch (Exception e)
                {
                    //TODO: add/fire database error events
                    log.warn("Failed to add user: " + userData.toString());
                }
            } catch (Exception e1)
            {
                //TODO: add/fire database error events
                log.warn("Database failure:", e1);
            }
        }
        ElapsedTimer.resetAllElapsedTimers();
        ElapsedTimer.resetTimer(0);
        ElapsedTimer.resetTimer(1);
        ElapsedTimer.startTimer(0);
        ElapsedTimer.startTimer(1);
        ElapsedTimer.startTimer(2);
        try
        {
            ContactList c = dataCache.getContactList((CrestClientInfo) clientInfo);
        } catch (SourceFailureException e)
        {
            e.printStackTrace();
        }
        System.err.println(ElapsedTimer.getElapsedTime("Time to obtain first ContactList from cache", 1));
        System.out.println("look here");
    }

    @Override
    public void refreshed(BaseClientInfo clientInfo)
    {
        CrestClientInfo ccinfo = (CrestClientInfo) clientInfo;
        log.debug(ccinfo.getVerifyData().CharacterID + " " + ccinfo.getVerifyData().CharacterName + " refreshed authentication");
    }

    @Override
    public void dropped(BaseClientInfo clientInfo)
    {
        CrestClientInfo ccinfo = (CrestClientInfo) clientInfo;
        log.debug(ccinfo.getVerifyData().CharacterID + " " + ccinfo.getVerifyData().CharacterName + " dropped authentication");
    }

    /*
     * *************************************************************************
     * *** CommunicationsEventListener impl
     ******************************************************************************/
    @Override
    public void crestUp(CrestClientInfo clientInfo)
    {
        log.debug(clientInfo.getVerifyData().CharacterID + " " + clientInfo.getVerifyData().CharacterName + " crestup");
        ElapsedTimer.resetAllElapsedTimers();
        ElapsedTimer.resetTimer(0);
        ElapsedTimer.resetTimer(1);
        ElapsedTimer.startTimer(0);
        ElapsedTimer.startTimer(1);
        ElapsedTimer.startTimer(2);
        try
        {
            ContactList c = dataCache.getContactList(clientInfo);
        } catch (SourceFailureException e)
        {
            e.printStackTrace();
        }
        System.err.println(ElapsedTimer.getElapsedTime("Time to obtain first ContactList from cache", 1));
        System.out.println("look here");

    }

    @Override
    public void crestDown(CrestClientInfo clientInfo)
    {
        try
        {
            Thread.sleep(2000);
        } catch (InterruptedException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        log.debug(clientInfo.getVerifyData().CharacterID + " " + clientInfo.getVerifyData().CharacterName + " crestdown");
        ElapsedTimer.resetAllElapsedTimers();
        ElapsedTimer.resetTimer(0);
        ElapsedTimer.resetTimer(1);
        ElapsedTimer.startTimer(0);
        ElapsedTimer.startTimer(1);
        ElapsedTimer.startTimer(2);
        try
        {
            ContactList c = dataCache.getContactList(clientInfo);
        } catch (SourceFailureException e)
        {
            e.printStackTrace();
        }
        System.err.println(ElapsedTimer.getElapsedTime("Time to obtain first ContactList from cache", 1));
        System.out.println("look here");
    }

    @Override
    public void xmlUp(CrestClientInfo clientInfo)
    {
        log.debug(clientInfo.getVerifyData().CharacterID + " " + clientInfo.getVerifyData().CharacterName + " xmlUp");
    }

    @Override
    public void xmlDown(CrestClientInfo clientInfo)
    {
        log.debug(clientInfo.getVerifyData().CharacterID + " " + clientInfo.getVerifyData().CharacterName + " xmlDown");
    }
}
