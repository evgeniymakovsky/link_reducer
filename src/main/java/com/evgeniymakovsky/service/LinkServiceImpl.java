package com.evgeniymakovsky.service;

import com.evgeniymakovsky.entity.Link;
import com.evgeniymakovsky.repository.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("LinkService")
@Transactional
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkRepository repository;

    public List<Link> findAll() {
        return repository.findAll();
    }

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
