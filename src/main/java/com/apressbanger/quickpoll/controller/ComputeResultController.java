package com.apressbanger.quickpoll.controller;


import com.apressbanger.quickpoll.domian.Vote;
import com.apressbanger.quickpoll.dto.OptionCount;
import com.apressbanger.quickpoll.dto.VoteResult;
import com.apressbanger.quickpoll.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class ComputeResultController<allVotes> {

    @Autowired
    private VoteRepository voteRepository;

    @RequestMapping(value = "/computeresult", method = RequestMethod.GET)
    public ResponseEntity<?> computeResult(@RequestParam Long pollId) {
        VoteResult voteResult = new VoteResult();
        Iterable<Vote> allVotes = voteRepository.findByPoll(pollId);
        // Algorithm to count votes
        int totalVotes = 0;
        Map<Long, OptionCount> tempMap = new HashMap<Long, OptionCount>();
        for (Vote vote : allVotes) {
            totalVotes++;
            // Get the OptionCount corresponding to this Option
            OptionCount optionCount = tempMap.get(vote.getOption().getId());
            if (optionCount == null) {
                optionCount = new OptionCount();
                optionCount.setOptionId(vote.getOption().getId());
                tempMap.put(vote.getOption().getId(), optionCount);
            }
            optionCount.setCount(optionCount.getCount() + 1);
        }
        voteResult.setTotalVotes(totalVotes);
        voteResult.setResults(tempMap.values());
        return new ResponseEntity<VoteResult>(voteResult, HttpStatus.OK);

    }
}
