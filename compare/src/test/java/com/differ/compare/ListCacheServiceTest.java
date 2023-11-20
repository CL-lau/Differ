package com.differ.compare;

/**
 * @description:
 * @author: lau
 * @time: 2023/11/7 23:30
 */

import com.differ.compare.cache.ChangeDtoCache;
import com.differ.compare.entity.ChangeDto;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class ListCacheServiceTest {


    @Resource
    private ChangeDtoCache changeDtoCacheService;

    @Test
    public void testChangeDtoCacheService() {
        // Create a ChangeDto instance
        ChangeDto changeDto = new ChangeDto();
        changeDto.setHost("localhost");
        changeDto.setPort(3306);
        changeDto.setTableName("example_table");
        // Set other fields as needed...

        // Save the ChangeDto to the cache
        changeDtoCacheService.saveChange("changeDtoKey", changeDto);

        // Retrieve the ChangeDto from the cache
        ChangeDto retrievedChangeDto = changeDtoCacheService.getChange("changeDtoKey");
        System.out.println("Retrieved ChangeDto: " + retrievedChangeDto);

        // Create a list of ChangeDto instances
        ChangeDto anotherChangeDto = new ChangeDto();
        anotherChangeDto.setHost("127.0.0.1");
        anotherChangeDto.setPort(5432);
        anotherChangeDto.setTableName("another_table");
        // Set other fields as needed...

        List<ChangeDto> changeDtoList = Arrays.asList(changeDto, anotherChangeDto);

        // Save the list of ChangeDto to the cache
        changeDtoCacheService.saveChanges("changeDtoListKey", changeDtoList);

        // Retrieve the list of ChangeDto from the cache
        List<ChangeDto> retrievedChangeDtoList = changeDtoCacheService.getChanges("changeDtoListKey");
        System.out.println("Retrieved ChangeDto list: " + retrievedChangeDtoList);

        // Append a ChangeDto to a list
        ChangeDto yetAnotherChangeDto = new ChangeDto();
        yetAnotherChangeDto.setHost("example.com");
        yetAnotherChangeDto.setPort(8080);
        yetAnotherChangeDto.setTableName("third_table");
        // Set other fields as needed...

        changeDtoCacheService.saveChangeToList("changeDtoListKey", yetAnotherChangeDto);

        // Retrieve the updated list of ChangeDto
        List<ChangeDto> updatedChangeDtoList = changeDtoCacheService.getChanges("changeDtoListKey");
        System.out.println("Updated ChangeDto list: " + updatedChangeDtoList);

        // Delete a ChangeDto from the list
        changeDtoCacheService.deleteChangesFromList("changeDtoListKey", 1, 2);
        List<ChangeDto> updatedChangeDtoListAfterDeletion = changeDtoCacheService.getChanges("changeDtoListKey");
        System.out.println("Updated ChangeDto list after deletion: " + updatedChangeDtoListAfterDeletion);
    }
}

