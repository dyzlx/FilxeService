package com.dyz.filxeservice.client;

import java.util.List;

import com.dyz.filxeservice.client.config.FeignClientConfiguration;
import com.dyz.filxeservice.client.model.LogicFileInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.dyz.filxeservice.client.model.Result;

@FeignClient(value = "filxeservice", contextId = "logicFileClient", configuration = {FeignClientConfiguration.class})
public interface LogicFileClient {

    /**
     * delete file by id and user id
     *
     * @param logicFileId
     */
    @RequestMapping(value = "/logicfiles/{logicFileId}",
            method = RequestMethod.DELETE,
            consumes = {"application/json", "application/xml"})
    void deleteLogicFile(@PathVariable(name = "logicFileId") Integer logicFileId);

    /**
     * delete files by ids and user id
     *
     * @param logicFileIds
     */
    @RequestMapping(value = "/logicfiles",
            method = RequestMethod.DELETE,
            consumes = {"application/json", "application/xml"})
    void deleteLogicFiles(@RequestBody List<Integer> logicFileIds);

    /**
     * upload files
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/logicfiles/upload",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    Result<List<Integer>> uploadFiles(@RequestPart(name = "file") MultipartFile[] file);


    /**
     * query logic file info by ids
     * @param logicFileId
     * @return
     */
    @RequestMapping(value = "/logicfiles",
            method = RequestMethod.GET,
            produces = {"application/json", "application/xml"})
    Result<List<LogicFileInfo>> queryLogicFileByLogicFileId(@RequestParam(value = "logicFileId") Integer logicFileId);
}
