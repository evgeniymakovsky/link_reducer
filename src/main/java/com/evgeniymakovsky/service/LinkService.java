package com.evgeniymakovsky.service;

import com.evgeniymakovsky.entity.Link;

/**
 * Created by mak on 1.6.17.
 */
public interface LinkService {

    public void saveLink(Link link);

    public void deleteLink(Link link);

    public void alterStatistics(Integer linkId, Integer invocations);
}
