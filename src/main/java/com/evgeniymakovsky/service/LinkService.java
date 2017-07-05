package com.evgeniymakovsky.service;

import com.evgeniymakovsky.entity.Link;

import java.util.List;

public interface LinkService {

    public List<Link> findAll();

    public void saveLink(Link link);

    public void deleteLink(Link link);

    public void alterStatistics(Integer linkId, Integer invocations);
}
