package com.dyz.filxeservice.api.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartitionCreateVo {

    @Size(min = 1, max = 50)
    @NotBlank
	private String partitionName;

    @Size(max = 3)
	private String description;
}
