package com.thoughtworks.rslist.service;

import com.thoughtworks.rslist.repository.RsEventRepositpry;
import com.thoughtworks.rslist.repository.UserRepository;
import com.thoughtworks.rslist.repository.VoteRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    final RsEventRepositpry rsEventRepositpry;
    final UserRepository userRepository;
    final VoteRepository voteRepository;

    public UserService(RsEventRepositpry rsEventRepository, UserRepository userRepository, VoteRepository voteRepository) {
        this.rsEventRepositpry = rsEventRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
    }

}
