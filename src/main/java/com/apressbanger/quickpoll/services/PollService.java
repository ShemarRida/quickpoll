package com.apressbanger.quickpoll.services;


import com.apressbanger.quickpoll.domian.Poll;
import com.apressbanger.quickpoll.exception.ResourceNotFoundException;
import com.apressbanger.quickpoll.repository.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PollService {

    @Autowired
    private PollRepository repository;

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
        return repository.findAll();

    }
    public Optional<Poll> getPoll(Long pollId){
        verifyPoll(pollId);
        return repository.findById(pollId);

    }
    public void deletePoll(Long pollId){
        verifyPoll(pollId);
        repository.deleteById(pollId);
    }
}
