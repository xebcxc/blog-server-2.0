package pro.meisen.boot.dao;

import pro.meisen.boot.web.req.PageInfo;

import java.util.List;

public class TagSearchParam extends PageInfo {
    private List<Long> idList;

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }
}
