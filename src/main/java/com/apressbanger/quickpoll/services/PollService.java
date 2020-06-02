package com.apressbanger.quickpoll.services;


import com.apressbanger.quickpoll.domian.Poll;
import com.apressbanger.quickpoll.domian.Vote;
import com.apressbanger.quickpoll.exception.ResourceNotFoundException;
import com.apressbanger.quickpoll.repository.PollRepository;
import com.apressbanger.quickpoll.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    @Autowired
    private PollRepository repository;

    @Autowired
    private VoteRepository voteRepository;

    protected void verifyPoll(Long pollId) throws ResourceNotFoundException {
        Optional<Poll> poll = repository.findById(pollId);
        if (poll == null){
            throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
        }
    }
    public void updatePoll(Long id, Poll poll){
        verifyPoll(id);
        repository.save(poll);

    }
    public Iterable<Poll> getAllPolls(){
        List<Poll> polls = new ArrayList<>();
        repository.findAll().forEach(polls::add);
        return polls;

    }
    public Optional<Poll> getPoll(Long pollId){
        verifyPoll(pollId);
        return repository.findById(pollId);

    }
    public void deletePoll(Long pollId){
        verifyPoll(pollId);
        Iterable<Vote> votes = voteRepository.findByPoll(pollId);
        repository.deleteById(pollId);
        voteRepository.deleteAll(votes);
    }
    public void createPoll(Poll poll){
        repository.save(poll);
    }
}
