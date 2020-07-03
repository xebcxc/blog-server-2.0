package fun.baozi.boot.uc;

import fun.baozi.boot.domain.Tag;

import java.util.List;

/**
 * @author baozi
 * 2019-07-14
 */
public interface TagManage {

    List<Tag> listAll();

    void deleteTag(Long tagId);

    void updateTag(Tag tag);

}
