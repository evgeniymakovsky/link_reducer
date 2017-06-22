package com.evgeniymakovsky.controller;

import com.evgeniymakovsky.entity.Link;
import com.evgeniymakovsky.service.LinkService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
public class RedirectController {

    final static Logger logger = Logger.getLogger(RedirectController.class);

    @Autowired
    private LinkService linkService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String redirection(@PathVariable String id) {
        logger.info("Request: " + id);
        String redirectToURL = "";
        List<Link> links = linkService.findAll();
        for (Link link : links) {
            if (link.getShorted().equals(id)) {
                redirectToURL = link.getOriginal();
                linkService.alterStatistics(link.getLink_id(), link.getInvocations() + 1);
                break;
            }
        }
        logger.info("Redirect to " + redirectToURL);
        return "redirect:" + redirectToURL;
    }

//    @RequestMapping(value = "/j_spring_security_check", method = RequestMethod.POST)
//    public String security() {
//        return "redirect:userpage.xhtml";
//    }

    public LinkService getLinkService() {
        return linkService;
    }

    public void setLinkService(LinkService linkService) {
        this.linkService = linkService;
    }
}