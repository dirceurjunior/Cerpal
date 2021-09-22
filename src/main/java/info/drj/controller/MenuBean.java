/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.drj.controller;

import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author Dirceu Junior
 */
@Named
@RequestScoped
public class MenuBean {

    public String getItemCssClass(String viewId) {
        FacesContext context = FacesContext.getCurrentInstance();
        String currentViewId = context.getViewRoot().getViewId();

        viewId = "/" + viewId + ".xhtml";

        return currentViewId.equals(viewId) ? "is-selected" : null;
    }

}
