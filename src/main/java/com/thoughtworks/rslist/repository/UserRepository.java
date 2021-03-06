package com.thoughtworks.rslist.repository;

import com.thoughtworks.rslist.dto.UserDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<UserDto,Integer> {
    @Override
    List<UserDto> findAll();

    @Override
    void deleteById(Integer integer);

    @Override
    Optional<UserDto> findById(Integer integer);
}
