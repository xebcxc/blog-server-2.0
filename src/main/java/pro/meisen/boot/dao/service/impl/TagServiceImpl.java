package pro.meisen.boot.dao.service.impl;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import pro.meisen.boot.dao.mapper.BasicMapper;
import pro.meisen.boot.dao.mapper.TagMapper;
import pro.meisen.boot.dao.service.TagService;
import pro.meisen.boot.dao.service.basic.BasicServiceImpl;
import pro.meisen.boot.domain.Tag;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author meisen
 * 2019-07-13
 */
@Service
public class TagServiceImpl extends BasicServiceImpl<Tag> implements TagService {

    @Autowired
    private TagMapper mapper;

    @Override
    public BasicMapper<Long, Tag> getMapper() {
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
        return notEmptyList(mapper.listAll());
    }

    @Override
    public Tag getByTagName(String tagName) {
        if (Strings.isEmpty(tagName)) {
            return null;
        }
        return mapper.getByTagName(tagName);
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
