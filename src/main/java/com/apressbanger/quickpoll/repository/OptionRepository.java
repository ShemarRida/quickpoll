package com.apressbanger.quickpoll.repository;

import com.apressbanger.quickpoll.domian.Option;
import org.springframework.data.repository.CrudRepository;

public interface OptionRepository extends CrudRepository<Option, Long> {
}
