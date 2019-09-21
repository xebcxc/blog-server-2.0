package pro.meisen.boot.dao;

import pro.meisen.boot.web.req.PageModel;

import java.util.List;

public class TagSearchParam extends PageModel {
    private List<Long> idList;

    public List<Long> getIdList() {
        return idList;
    }

    public void setIdList(List<Long> idList) {
        this.idList = idList;
    }
}
