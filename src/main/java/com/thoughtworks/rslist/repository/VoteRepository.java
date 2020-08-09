package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.VoteDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface VoteRepository extends CrudRepository<VoteDto,Integer> {
    List<VoteDto> findAll();

    @Override
    List<VoteDto> findAllById(Iterable<Integer> integers);

    List<VoteDto> findByLocalDateTimeBetween(LocalDateTime start, LocalDateTime end);

    List<VoteDto> findAllByUserIdAndRsEventId(int userId, int rsEventId, Pageable pageable);
}
