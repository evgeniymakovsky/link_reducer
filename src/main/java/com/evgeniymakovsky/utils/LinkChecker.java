package com.evgeniymakovsky.utils;

import com.evgeniymakovsky.entity.Link;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mak on 8.6.17.
 */
public class LinkChecker {

    final static Logger logger = Logger.getLogger(LinkChecker.class);

    public static boolean checkIfShortedLinkExists(List<Link> links, String shortedLink) {
        logger.info("Start checkIfShortedLinkExists(List<Link> links, String shortedLink)");
        for (Link link : links) {
            if (link.getShorted().equals(shortedLink)) return true;
        }
        return false;
    }

    public static String checkIfOriginalLinkExists(List<Link> links, String originalLink) {
        logger.info("Start checkIfOriginalLinkExists(List<Link> links, String originalLink)");
        for (Link link : links) {
            if (link.getOriginal().equals(originalLink)) return link.getShorted();
        }
        return null;
    }

    public static boolean checkIfLinkProtocolExists(String originalLink) {
        logger.info("Start checkIfLinkProtocolExists(String originalLink)");
        Pattern pattern = Pattern.compile("^(http|https)://.*$");
        Matcher matcher = pattern.matcher(originalLink);
        return matcher.matches();
    }
}
