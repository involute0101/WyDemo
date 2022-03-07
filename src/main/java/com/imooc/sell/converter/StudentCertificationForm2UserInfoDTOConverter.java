package com.imooc.sell.converter;

import com.imooc.sell.controller.form.StudentCertificationForm;
import com.imooc.sell.dto.UserInfoDTO;

public class StudentCertificationForm2UserInfoDTOConverter {
    public static UserInfoDTO convert(StudentCertificationForm studentCertificationForm){
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUserOpenid(studentCertificationForm.getUserOpenId());
        userInfoDTO.setUserSex(studentCertificationForm.getUserSex());
        userInfoDTO.setUserUniversity(studentCertificationForm.getUserUniversity());
        userInfoDTO.setUserMajor(studentCertificationForm.getUserMajor());
        userInfoDTO.setUserRealName(studentCertificationForm.getUserRealName());
        userInfoDTO.setUserCollege(studentCertificationForm.getUserCollege());
        userInfoDTO.setUserDegree(studentCertificationForm.getUserDegree());
        String studentCardPhotos = "";
        for(String photo : studentCertificationForm.getStudentsCard()){
            studentCardPhotos = studentCardPhotos + photo + ",";
        }
        userInfoDTO.setStudentCardPhotos(studentCardPhotos.substring(0,studentCardPhotos.length()-1));
        return userInfoDTO;
    }
}
