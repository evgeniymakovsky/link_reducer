package com.evgeniymakovsky.utils;

import com.evgeniymakovsky.entity.Link;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mak on 8.6.17.
 */
public class LinkChecker {

    public static boolean checkShortedLink(List<Link> links, String shortedLink) {
        for (Link link : links) {
            if (link.getShorted().equals(shortedLink)) return true;
        }
        return false;
    }

    public static String checkOriginalLink(List<Link> links, String originalLink) {
        for (Link link : links) {
            if (link.getOriginal().equals(originalLink)) return link.getShorted();
        }
        return null;
    }

    public static boolean checkLinkProtocol(String originalLink) {
        Pattern pattern = Pattern.compile("^(http|https)://.*$");
        Matcher matcher = pattern.matcher(originalLink);
        return matcher.matches();
    }
}
