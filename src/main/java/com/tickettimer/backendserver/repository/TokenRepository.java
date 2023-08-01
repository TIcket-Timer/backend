package com.tickettimer.backendserver.repository;

import com.tickettimer.backendserver.domain.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long> {
}
