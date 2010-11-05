/**
 * License Agreement.
 *
 * Rich Faces - Natural Ajax for Java Server Faces (JSF)
 *
 * Copyright (C) 2007 Exadel, Inc.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License version 2.1 as published by the Free Software Foundation.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

package org.richfaces.context;

import java.util.Collection;
import java.util.Collections;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.ajax4jsf.component.AjaxClientBehavior;
import org.richfaces.renderkit.util.CoreAjaxRendererUtils;

/**
 * @author akolonitsky
 * @since Oct 13, 2009
 */
class RenderComponentCallback extends ComponentCallback {

    private Collection<String> renderIds = null;
    
    private boolean limitRender = false;

    private String oncomplete;

    private String onbeforedomupdate;

    private Object data;

    RenderComponentCallback(FacesContext facesContext, String behaviorEvent) {
        super(facesContext, behaviorEvent);
    }

    public boolean isLimitRender() {
        return limitRender;
    }

    public String getOnbeforedomupdate() {
        return onbeforedomupdate;
    }

    public String getOncomplete() {
        return oncomplete;
    }

    public Object getData() {
        return data;
    }

    public Collection<String> getRenderIds() {
        return (renderIds != null) ? renderIds : Collections.<String>emptySet();
    }

    @Override
    protected void doVisit(UIComponent target, AjaxClientBehavior behavior) {
        Object renderValue;
        if (behavior != null) {
            renderValue = behavior.getRender();
            limitRender = behavior.isLimitRender();
            onbeforedomupdate = behavior.getOnbeforedomupdate();
            oncomplete = behavior.getOncomplete();
            data = behavior.getData();
        } else {
            renderValue = target.getAttributes().get("render");
            limitRender = CoreAjaxRendererUtils.isAjaxLimitRender(target);
            onbeforedomupdate = CoreAjaxRendererUtils.getAjaxOnBeforeDomUpdate(target);
            oncomplete = CoreAjaxRendererUtils.getAjaxOncomplete(target);
            data = CoreAjaxRendererUtils.getAjaxData(target);
        }
        
        renderIds = resolveComponents(renderValue, target, null);
    }

}
