package com.evgeniymakovsky.service;

import com.evgeniymakovsky.entity.Link;
import com.evgeniymakovsky.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class LinkServiceImpl implements LinkService {

    @Autowired
    LinkRepository repository;

    public void saveLink(Link link) {
        repository.saveAndFlush(link);
    }

    public void deleteLink(Link link) {
        repository.delete(link);
    }

    public void alterStatistics(Integer linkId, Integer invocations) {
        repository.alterStatistics(linkId, invocations);
    }
}
