package com.apressbanger.quickpoll.services;

import com.apressbanger.quickpoll.domian.Vote;
import com.apressbanger.quickpoll.repository.PollRepository;
import com.apressbanger.quickpoll.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private PollRepository pollRepository;

    public Iterable<Vote> getAllVotes(){
        List<Vote> votes = new ArrayList<>();
        voteRepository.findAll().forEach(votes::add);
        return votes;
    }
    public void createVote(Vote vote){
        voteRepository.save(vote);
    }


}
