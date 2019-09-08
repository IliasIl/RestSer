package com.RestSer.domain.dto;

import com.fasterxml.jackson.annotation.JsonView;
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
    private Balance extras;

    public Status(int result) {
        this.result = result;
    }
}
