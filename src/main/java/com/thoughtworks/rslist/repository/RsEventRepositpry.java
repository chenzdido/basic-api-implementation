package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.RsEventDto;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface RsEventRepositpry extends CrudRepository<RsEventDto,Integer> {

    @Override
    List<RsEventDto> findAll();
}
