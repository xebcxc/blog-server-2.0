package pro.meisen.boot.dao.service;

import pro.meisen.boot.dao.service.basic.BasicService;
import pro.meisen.boot.domain.Tag;

import java.util.List;

/**
 * @author meisen
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
