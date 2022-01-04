package com.imooc.sell.dataobject.map;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class Favorites_Map implements Serializable {
    private String projectId;
    private Integer userId;

}
