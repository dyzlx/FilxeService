package com.dyz.filxeservice.sal.bo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PartitionCreateBO {

	private String partitionName;

	private String description;
}
