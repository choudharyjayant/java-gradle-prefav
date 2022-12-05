package com.poc.utility;

import org.springframework.beans.BeanUtils;

import com.poc.dto.ReplyDto;
import com.poc.models.Reply;

public class AppUtils {

	private AppUtils() {
	}
	
	public static ReplyDto entityToDto(Reply reply) {
		System.out.println(reply.toString());
		ReplyDto replyDto = new ReplyDto();
		BeanUtils.copyProperties(reply, replyDto);
		return replyDto;
	}

	public static Reply dtoToEntity(ReplyDto replyDto) {
		Reply reply = new Reply();
		BeanUtils.copyProperties(replyDto, reply);
		return reply;
	}
}
