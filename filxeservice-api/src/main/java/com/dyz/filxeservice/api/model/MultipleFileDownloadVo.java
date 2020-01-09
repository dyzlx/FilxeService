package com.dyz.filxeservice.api.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MultipleFileDownloadVo {

	@Size(min = 1, max = 100)
	@NotBlank
	private String compressionFileName;

	@NotEmpty
	private List<Integer> logicFileIds;
}
