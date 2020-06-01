package com.apressbanger.quickpoll.controller;

import com.apressbanger.quickpoll.domian.Poll;
import com.apressbanger.quickpoll.exception.ResourceNotFoundException;
import com.apressbanger.quickpoll.repository.PollRepository;
import com.apressbanger.quickpoll.services.PollService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RestController
public class PollController {

    @Autowired
    //@Inject
    private PollRepository pollRepository;

    @Autowired
    private PollService pollService;

    protected void verifyPoll(Long pollId) throws ResourceNotFoundException{
        Optional<Poll> poll = pollRepository.findById(pollId);
        if (poll == null){
            throw new ResourceNotFoundException("Poll with id " + pollId + " not found");
        }
    }

    @RequestMapping(value="/polls", method=RequestMethod.GET)
    public ResponseEntity<Iterable<Poll>> getAllPolls() {
        Iterable<Poll> allPolls = pollService.getAllPolls();
//        Iterable<Poll> allPolls = pollRepository.findAll();
        return new ResponseEntity<>(allPolls, HttpStatus.OK);
    }

    @RequestMapping(value = "/polls", method = RequestMethod.POST)
    public ResponseEntity<?> createPoll(@RequestBody Poll poll){
        poll = pollRepository.save(poll);

        // Set the location header for the newly created resource
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newPollUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(poll.getId())
                .toUri();
        responseHeaders.setLocation(newPollUri);
        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }
    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.GET)
    public ResponseEntity<?> getPoll(@PathVariable Long pollId){
//        verifyPoll(pollId);
        Optional<Poll> poll = pollService.getPoll(pollId);
        return new ResponseEntity<>(poll,HttpStatus.OK);
    }
    @RequestMapping(value = "polls/{pollId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updatePoll(@RequestBody Poll poll, @PathVariable Long pollId){
//        verifyPoll(pollId);
       pollService.updatePoll(pollId, poll);
       return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @RequestMapping(value = "/polls/{pollId}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deletePoll(@PathVariable Long pollId){
        pollService.deletePoll(pollId);
//        pollRepository.deleteById(pollId);
        return new ResponseEntity<>(HttpStatus.OK);
    }





}
