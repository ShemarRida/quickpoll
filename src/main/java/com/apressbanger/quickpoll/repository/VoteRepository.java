package com.apressbanger.quickpoll.repository;

import com.apressbanger.quickpoll.domian.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface VoteRepository extends CrudRepository<Vote, Long> {
    @Query(value = "select v.* from Option o, Vote v where o.Poll_ID = ?1 and v.Option_ID = o.Option_ID", nativeQuery = true)
    public Iterable<Vote> findByPoll(Long pollID);
}
