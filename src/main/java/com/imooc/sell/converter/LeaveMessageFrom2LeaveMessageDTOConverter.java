package com.imooc.sell.converter;

import com.imooc.sell.controller.form.LeaveMessageForm;
import com.imooc.sell.dto.LeaveMessageDTO;

import java.util.Date;

public class LeaveMessageFrom2LeaveMessageDTOConverter {
    public static LeaveMessageDTO convert(LeaveMessageForm leaveMessageForm){
        LeaveMessageDTO leaveMessageDTO = new LeaveMessageDTO();
        leaveMessageDTO.setProjectId(leaveMessageForm.getProjectId());
        leaveMessageDTO.setUserOpenid(leaveMessageForm.getUserOpenId());
        leaveMessageDTO.setContent(leaveMessageForm.getContent());
        leaveMessageDTO.setLikeNumber(0);
        leaveMessageDTO.setPublishTime(new Date());
        return  leaveMessageDTO;
    }
}
