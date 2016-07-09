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

import java.util.Properties;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ccc.crest.cache.DataCache;
import com.ccc.crest.servlet.auth.CrestClientInfo;
import com.ccc.tools.ElapsedTimer;
import com.ccc.tools.RequestThrottle;
import com.ccc.tools.RequestThrottle.IntervalType;
import com.ccc.tools.TabToLevel;
import com.ccc.tools.servlet.CoreController;
import com.ccc.tools.servlet.clientInfo.BaseClientInfo;
import com.ccc.tools.servlet.events.AuthenticatedEventListener;

@SuppressWarnings("javadoc")
public class CrestController extends CoreController implements AuthenticatedEventListener
{
    public volatile DataCache dataCache;

    public Logger log;

    public CrestController()
    {
        log = LoggerFactory.getLogger(getClass());
    }

    @Override
    public void init(Properties properties, TabToLevel format) throws Exception
    {
        super.init(properties, format);
        dataCache = new DataCache();
        registerAuthenticatedListener(this);
        // blockingExecutor.submit(new TestTask());
    }

    @Override
    public void destroy()
    {
        if (dataCache != null)
            dataCache.clear();
        super.destroy();
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

    @Override
    public void authenticated(BaseClientInfo clientInfo)
    {
        CrestClientInfo ccinfo = (CrestClientInfo)clientInfo; 
        log.debug(ccinfo.getVerifyData().CharacterID + " " + ccinfo.getVerifyData().CharacterName + " authenticated");
    }

    @Override
    public void refreshed(BaseClientInfo clientInfo)
    {
        CrestClientInfo ccinfo = (CrestClientInfo)clientInfo; 
        log.debug(ccinfo.getVerifyData().CharacterID + " " + ccinfo.getVerifyData().CharacterName + " refreshed authentication");
    }

    @Override
    public void dropped(BaseClientInfo clientInfo)
    {
        CrestClientInfo ccinfo = (CrestClientInfo)clientInfo; 
        log.debug(ccinfo.getVerifyData().CharacterID + " " + ccinfo.getVerifyData().CharacterName + " dropped authentication");
    }
}
