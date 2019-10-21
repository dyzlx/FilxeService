package com.dyz.filxeservice.sal.bo;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

import org.apache.commons.lang3.time.DateUtils;

import com.dyz.filxeservice.common.constant.ServiceConstant;
import com.dyz.filxeservice.common.execption.IllegalParamException;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LogicFileQueryBo {

	private String logicFileName;

	private Boolean ishared;

	private Integer partitionId;

	private Date fromTime;

	private Date toTime;

	private Integer userId;

	public Date getFromTime() {
		if (Objects.isNull(fromTime)) {
			try {
				return DateUtils.parseDate(ServiceConstant.DEFAULT_FROM_DATE, ServiceConstant.DATE_FORMAT_SHORT);
			} catch (ParseException e) {
				throw new IllegalParamException("Illega param fromTime");
			}
		}
		return fromTime;
	}

	public Date getToTime() {
		if (Objects.isNull(toTime)) {
			try {
				return DateUtils.parseDate(ServiceConstant.DEFAULT_TO_DATE, ServiceConstant.DATE_FORMAT_SHORT);
			} catch (ParseException e) {
				throw new IllegalParamException("Illega param toTime");
			}
		}
		return toTime;
	}
}
