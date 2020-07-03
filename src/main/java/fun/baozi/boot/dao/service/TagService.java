package fun.baozi.boot.dao.service;

import fun.baozi.boot.domain.Tag;
import fun.baozi.boot.dao.service.basic.BasicService;

import java.util.List;

/**
 * @author baozi
 * 2019-07-13
 */
public interface TagService extends BasicService<Tag> {
    List<Tag> listByIds(List<Long> idList);

    List<Tag> listAll();

    Tag getByTagName(String tagName);

    List<Tag> listByTagNameList(List<String> tagNameList);

    int batchInsert(List<Tag> tagList);

    int batchUpdate(List<Tag> tagList);


}
