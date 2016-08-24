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
package com.ccc.crest.need.template;

import java.util.Properties;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.request.resource.ContextRelativeResource;

import com.ccc.crest.core.CrestController;
import com.ccc.crest.servlet.CrestServlet;
import com.ccc.oauth.CoreController;

@SuppressWarnings("javadoc")
public class FooterPanel extends Panel
{
    private static final long serialVersionUID = -4736200814044302198L;

    public FooterPanel(String id)
    {
        super(id);
        Properties properties = CrestController.getCrestController().getProperties();
        String copyrightYear = properties.getProperty(CrestServlet.CopyrightYearKey);
        String copyrightowner = properties.getProperty(CrestServlet.CopyrightOwnerKey);
        String crestImg = "/images/server_error.png";
        if (((CrestController) CoreController.getController()).isCrestUp())
            crestImg = "/images/server.png";
        String xmlApiImg = "/images/server_error.png";
        if (((CrestController) CoreController.getController()).isXmlApiUp())
            xmlApiImg = "/images/server.png";
        add(new Label("crestServerLabel", "crest server"));
        add(new Image("crestServerImage", new ContextRelativeResource(crestImg)));
        add(new Label("xmlApiServerLabel", "xmlapi server"));
        add(new Image("xmlApiServerImage", new ContextRelativeResource(xmlApiImg)));
        add(new Label("copyright", copyrightYear + " " + copyrightowner));

//        add(new NonCachingImage("crestServerImage", new AbstractReadOnlyModel<DynamicImageResource>()
//        {
//            @Override
//            public DynamicImageResource getObject()
//            {
//                DynamicImageResource dir = new DynamicImageResource()
//                {
//                    private static final long serialVersionUID = 7691769266370001440L;
//
//                    @Override
//                    protected byte[] getImageData(Attributes attributes)
//                    {
//                        String crestImg = "/images/server_error.png";
//                        if (((CrestController) CoreController.getController()).isCrestUp())
//                            crestImg = "/images/server.png";
//                        ResourceReference ir;
////                        new ContextRelativeResource(crestImg).
//                        return getCurrImage();
//                    }
//                };
//                dir.setFormat("image/png");
//                return dir;
//            }
//        }));
    }
}
