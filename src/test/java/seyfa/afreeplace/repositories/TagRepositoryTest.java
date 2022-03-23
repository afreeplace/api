package seyfa.afreeplace.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import seyfa.afreeplace.controllers.TagController;
import seyfa.afreeplace.entities.business.Address;
import seyfa.afreeplace.entities.business.Tag;
import seyfa.afreeplace.utils.AddressBuilderTest;
import seyfa.afreeplace.utils.TagBuilderTest;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class TagRepositoryTest {

    @Autowired
    TagController tagController;

    @Autowired
    TagRepository tagRepository;

    int tagId1, tagId2, tagId3;

    @BeforeEach
    public void before() throws Exception {
        tagId1 = TagBuilderTest.create(tagRepository, "Tag1").getId();
        tagId2 = TagBuilderTest.create(tagRepository, "Tag2").getId();
        tagId3 = TagBuilderTest.create(tagRepository, "Tag3").getId();
    }

    @AfterEach
    public void after() {
        TagBuilderTest.delete(tagId1, tagRepository);
        TagBuilderTest.delete(tagId3, tagRepository);
        TagBuilderTest.delete(tagId2, tagRepository);
    }

    @Test
    public void testOk() {
        assertNotNull(tagRepository);
    }

    @Test
    public void findById() {

        ResponseEntity<Map<String, Object>> response = tagController.findTagById(tagId1);
        Map body = response.getBody();
        int tagId = ((Tag) body.get("tag")).getId();
        assertNotNull(tagRepository.findById(tagId).orElse(null));

    }

    @Test
    public void findAll() {
        ResponseEntity<Map<String, Object>> response = tagController.findAllTags();
        Map body = response.getBody();
        int tagSize = ((List<Tag>) body.get("tags")).size();
        assertEquals(3, tagSize);
    }



}
