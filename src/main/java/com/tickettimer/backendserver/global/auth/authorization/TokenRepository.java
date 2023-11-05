package com.tickettimer.backendserver.global.auth.authorization;

import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Long> {
}
