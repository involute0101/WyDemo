package com.imooc.sell.converter;

import com.imooc.sell.controller.form.LeaveMessageLikeForm;
import com.imooc.sell.dto.LeaveMessageLikeDTO;

public class LeaveMessageLikeFrom2LeaveMessageLikeDTOConverter {
    public static LeaveMessageLikeDTO convert(LeaveMessageLikeForm leaveMessageLikeForm){
        LeaveMessageLikeDTO leaveMessageLikeDTO = new LeaveMessageLikeDTO();
        leaveMessageLikeDTO.setUserOpenId(leaveMessageLikeForm.getUserOpenId());
        leaveMessageLikeDTO.setLeaveMessageId(leaveMessageLikeForm.getLeaveMessageId());
        return leaveMessageLikeDTO;
    }
}
