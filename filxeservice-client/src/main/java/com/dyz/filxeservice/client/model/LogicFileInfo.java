package com.dyz.filxeservice.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogicFileInfo {

    private int logicFileId;

    private String logicFileName;

    private String createTime;

    private boolean ishared;

    private int partitionId;

    private int userId;
}
