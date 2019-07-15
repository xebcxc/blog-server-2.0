package pro.meisen.boot.uc;

import pro.meisen.boot.domain.Tag;

import java.util.List;

/**
 * @author meisen
 * 2019-07-14
 */
public interface TagManage {

    List<Tag> listAll();

    void deleteTag(Long tagId);

    void updateTag(Tag tag);

}
