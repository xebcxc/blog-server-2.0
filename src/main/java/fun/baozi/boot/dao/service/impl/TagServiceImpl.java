package fun.baozi.boot.dao.service.impl;

import fun.baozi.boot.dao.mapper.TagMapper;
import fun.baozi.boot.domain.Tag;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import fun.baozi.boot.dao.service.TagService;
import fun.baozi.boot.dao.service.basic.BasicServiceImpl;
import tk.mybatis.mapper.common.Mapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author baozi
 * 2019-07-13
 */
@Service
public class TagServiceImpl extends BasicServiceImpl<Tag> implements TagService {

    @Autowired
    private TagMapper mapper;

    @Override
    public Mapper<Tag> getArticleMapper() {
        return mapper;
    }

    @Override
    public List<Tag> listByIds(List<Long> idList) {
        if (CollectionUtils.isEmpty(idList)) {
            return new ArrayList<>();
        }
        return notEmptyList(mapper.listByIds(idList));
    }

    @Override
    public List<Tag> listAll() {
        return notEmptyList(mapper.selectAll());
    }

    @Override
    public Tag getByTagName(String tagName) {
        if (Strings.isEmpty(tagName)) {
            return null;
        }
        Tag record = new Tag();
        record.setTagName(tagName);
        return mapper.selectOne(record);
    }

    @Override
    public List<Tag> listByTagNameList(List<String> tagNameList) {
        if (CollectionUtils.isEmpty(tagNameList)) {
            return new ArrayList<>();
        }
        return notEmptyList(mapper.listByTagNameList(tagNameList));
    }

    @Override
    public int batchInsert(List<Tag> tagList) {
        if (CollectionUtils.isEmpty(tagList)) {
            return 0;
        }
        Date now = new Date();
        for (Tag tag : tagList) {
            tag.setCreateTime(now);
            tag.setModifyTime(now);
        }
        return mapper.batchInsert(tagList);
    }

    @Override
    public int batchUpdate(List<Tag> tagList) {
        if (CollectionUtils.isEmpty(tagList)) {
            return 0;
        }
        Date now = new Date();
        for (Tag tag : tagList) {
            if (null == tag.getId()) {
                return 0;
            }
            tag.setModifyTime(now);
        }
        return mapper.batchUpdate(tagList);
    }
}
