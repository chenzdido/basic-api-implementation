package com.thoughtworks.rslist.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="vote")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteDto {
    @Id
    @GeneratedValue
    private int id;

    private LocalDateTime localDateTime;

    private int num;
    @ManyToOne
    private UserDto userDto;
    @ManyToOne
    private RsEventDto rsEventDto;
}
