package com.base.myFramework.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @author jinqixiang
 */
@Getter
@Setter
@ToString
public class MessageAttr {
    private List<String> recEmailAddr;
    private String mailTitle;
    private String mailContent;
}
