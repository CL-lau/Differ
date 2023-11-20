package com.differ.compare.cache;

import com.differ.compare.entity.ChangeDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ChangeDtoCache {
    void saveChange(String key, ChangeDto changeDto);

    ChangeDto getChange(String key);

    void deleteChange(String key);

    void saveChanges(String key, List<ChangeDto> changeDtoList);

    List<ChangeDto> getChanges(String key);

    void saveChangeToList(String key, ChangeDto changeDto);

    void saveChangesToList(String key, List<ChangeDto> changeDtoList);

    List<ChangeDto> getChangesFromList(String key, Integer begin, Integer end);

    void deleteChanges(String key);

    void deleteChangesFromList(String key, Integer begin, Integer end);

    void deleteChangesFromList(String key, Integer size);
}
