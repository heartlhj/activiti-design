package com.lhj.activiti.design.dean;

import com.lhj.activiti.design.model.PageBean;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: 李海军
 * @Date: 2018/9/2 19:30
 * @Description:
 */
@Data
public class ActivitiModelDto extends PageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String nameCopy;

    private String key;

    private String category;

    private  Date createTime;

    private  Date lastUpdateTime;

    private Integer version;

    private String metaInfo;

    private String deploymentId;

    private String tenantId;

    private boolean hasEditorSource;

    private boolean hasEditorSourceExtra;

    private String description;

    private String descriptionCopy;

    private String[] modelIds;

    private String createDateStr;
    private String updateDateStr;

    public String getCreateDateStr() {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(createTime != null){
            long ts = createTime.getTime();
            res = String.valueOf(ts);
            long lt = new Long(res);
            Date date = new Date(lt);
            return simpleDateFormat.format(date);
        }
        return null;
    }

    public String getUpdateDateStr() {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(lastUpdateTime != null){
            long ts = lastUpdateTime.getTime();
            res = String.valueOf(ts);
            long lt = new Long(res);
            Date date = new Date(lt);
            return simpleDateFormat.format(date);
        }
        return null;
    }
}
