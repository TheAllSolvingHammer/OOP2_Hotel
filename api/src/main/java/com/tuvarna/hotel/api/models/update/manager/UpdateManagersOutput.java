package com.tuvarna.hotel.api.models.update.manager;

import com.tuvarna.hotel.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor
@Builder
public class UpdateManagersOutput implements OperationOutput {
    private String message;
}
