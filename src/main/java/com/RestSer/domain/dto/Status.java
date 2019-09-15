package com.RestSer.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(of={"result"})
public class Status {
    @JsonView(Views.Balance.class)
    private int result;

    @JsonView(Views.Full.class)
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private Balance extras;

    public Status(int result) {
        this.result = result;
    }
}
