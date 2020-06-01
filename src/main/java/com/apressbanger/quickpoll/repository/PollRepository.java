package com.apressbanger.quickpoll.repository;

import com.apressbanger.quickpoll.domian.Poll;

import org.springframework.data.repository.CrudRepository;

public interface PollRepository extends CrudRepository<Poll, Long> {

}
