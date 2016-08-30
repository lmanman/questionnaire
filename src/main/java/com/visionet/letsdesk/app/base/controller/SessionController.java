package com.visionet.letsdesk.app.base.controller;


import com.visionet.letsdesk.app.foundation.service.SessionService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;


@RequiresRoles(value = { "admin", "subadmin"}, logical = Logical.OR)
@Controller
@RequestMapping("/console/sessions")
public class SessionController {
	
    @Autowired
    private SessionService sessionService;
    
    
    @RequestMapping()
    public String list(Model model) {
    	List<Map<String,String>> result = sessionService.getActiveSessionMap();
        model.addAttribute("sessions", result);
        model.addAttribute("sessionCount", result.size());
        return "console/monitor/sessionList";
    }

    @RequestMapping("/{sessionId}/forceLogout")
    public String forceLogout(
            @PathVariable("sessionId") String sessionId, RedirectAttributes redirectAttributes) {
    	sessionService.logouSession(sessionId);
        redirectAttributes.addFlashAttribute("msg", "Force Logout Succ！");
        return "redirect:/console/sessions";
    }

}