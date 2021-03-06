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
public class PartitionQueryBo {

    private Integer partitionId;
	
	private String partitionName;
	
	private Integer userId;
	
	private Date fromDate;
	
	private Date toDate;
	
	public Date getFromDate() {
		if (Objects.isNull(fromDate)) {
			try {
				return DateUtils.parseDate(ServiceConstant.DEFAULT_FROM_DATE, ServiceConstant.DATE_FORMAT_SHORT);
			} catch (ParseException e) {
				throw new IllegalParamException(0, "Illega param fromTime");
			}
		}
		return fromDate;
	}

	public Date getToDate() {
		if (Objects.isNull(toDate)) {
			try {
				return DateUtils.parseDate(ServiceConstant.DEFAULT_TO_DATE, ServiceConstant.DATE_FORMAT_SHORT);
			} catch (ParseException e) {
				throw new IllegalParamException(0, "Illega param toTime");
			}
		}
		return toDate;
	}
}
