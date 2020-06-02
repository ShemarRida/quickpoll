package com.apressbanger.quickpoll.controller;


import com.apressbanger.quickpoll.domian.Vote;
import com.apressbanger.quickpoll.repository.VoteRepository;
import com.apressbanger.quickpoll.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class VoteController {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private VoteService voteService;

    @RequestMapping(value="/polls/{pollId}/votes", method= RequestMethod.POST)
    public ResponseEntity<?> createVote(@PathVariable Long pollId, @RequestBody Vote vote) {
        voteService.createVote(vote);

//        vote = voteRepository.save(vote);
//         Set the headers for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(vote.getId())
                .toUri());
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
    @RequestMapping(value="/polls/{pollId}/votes", method=RequestMethod.GET)
    public ResponseEntity<Iterable<Vote>> getAllVotes(@PathVariable Long pollId){
        Iterable<Vote> votes = voteService.getAllVotes();
        return new ResponseEntity<>(votes, HttpStatus.OK);


//    public Iterable<Vote> getAllVotes(@PathVariable Long pollId) {
//        return voteRepository.findByPoll(pollId);


//        Iterable<Vote> votes = voteRepository.findByPoll(pollId);
//        return new ResponseEntity<>(votes, HttpStatus.OK);
    }
}
