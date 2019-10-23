package com.lhj.activiti.design.dean;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * @author lhj
 * @version 1.0
 * @description: TODO
 * @date 2019-9-811:20
 */
@Data
public class TaskBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String localizedName;

    private String description;

    private Integer priority;

    private String owner;

    private String assignee;

    private Date dueDate;

    private String category;

    private String delegate;

    private String formKey;

    private Date createTime;

    private String processInstanceId;

    private String executionId;

    private String taskDefinitionKey;


    private Map<String, Object> paramMap;
}
