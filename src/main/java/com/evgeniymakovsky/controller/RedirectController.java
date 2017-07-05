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

/**
 * Class RedirectController is the main controller for redirection by shorted links.
 */
@Controller
public class RedirectController {

    final static Logger LOGGER = Logger.getLogger(RedirectController.class);

    @Autowired
    private LinkService linkService;

    /**
     * Method redirect user from shorted link to original link that exists in database.
     *
     * @param id - shorted link
     * @return to original link
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String redirection(@PathVariable String id) {
        LOGGER.info("Request: " + id);
        String redirectToURL = "";
        List<Link> links = linkService.findAll();
        for (Link link : links) {
            if (link.getShorted().equals(id)) {
                redirectToURL = link.getOriginal();
                linkService.alterStatistics(link.getLink_id(), link.getInvocations() + 1);
                break;
            }
        }
        LOGGER.info("Redirect to " + redirectToURL);
        return "redirect:" + redirectToURL;
    }

    public LinkService getLinkService() {
        return linkService;
    }

    public void setLinkService(LinkService linkService) {
        this.linkService = linkService;
    }
}