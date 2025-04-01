package com.seanhuvaya.quickpoll.coreapi.polls;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PollRepository extends JpaRepository<Poll, UUID> {
}
