/*
 * Copyright (c) 2018. Gavin Kenna
 */

package com.gkenna.tullamoreqa.core.impl.services;

import com.gkenna.tullamoreqa.core.api.exceptions.TagAlreadyExistsException;
import com.gkenna.tullamoreqa.core.api.exceptions.TagNotFoundException;
import com.gkenna.tullamoreqa.core.api.repositories.TagRepository;
import com.gkenna.tullamoreqa.domain.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TagServiceImplTest {

    private static final Logger LOGGER =
            LogManager.getLogger(TagServiceImplTest.class);

    @InjectMocks
    private final TagServiceImpl tagService;

    @Mock
    private TagRepository mockedTagRepository;

    public TagServiceImplTest() {
        MockitoAnnotations.initMocks(this);
        tagService = new TagServiceImpl(mockedTagRepository);
    }

    @Test
    public void shouldAddValidTagWithDescription() throws TagAlreadyExistsException {
        when(mockedTagRepository.existsById("Java")).thenReturn(false);

        final Tag tag = new Tag("Java");
        tag.setDescription("Description for Java Tag.");
        tagService.addTag(tag);

        verify(mockedTagRepository).saveAndFlush(tag);
    }

    @Test
    public void shouldAddValidTagWithoutDescription() throws TagAlreadyExistsException {
        final Tag tag = new Tag("Java");
        tagService.addTag(tag);

        verify(mockedTagRepository).saveAndFlush(tag);
    }

    @Test
    public void shouldAddMultipleValidTagsWithSameDescription() throws TagAlreadyExistsException {
        final Tag tagOne = new Tag("Java");
        final Tag tagTwo = new Tag("Numberwang");
        final Tag tagThree = new Tag("Wangernum");
        final Tag tagFour = new Tag("Emma");
        tagOne.setDescription("Description");
        tagTwo.setDescription("Description");
        tagThree.setDescription("Description");
        tagFour.setDescription("Description");
        tagService.addTag(tagOne);
        verify(mockedTagRepository).saveAndFlush(tagOne);
        tagService.addTag(tagTwo);
        verify(mockedTagRepository).saveAndFlush(tagTwo);
        tagService.addTag(tagThree);
        verify(mockedTagRepository).saveAndFlush(tagThree);
        tagService.addTag(tagFour);
        verify(mockedTagRepository).saveAndFlush(tagFour);
    }

    @Test
    public void shouldAddTagWithSpecialName() throws TagAlreadyExistsException {
        final Tag tag = new Tag("C++");
        tagService.addTag(tag);

        verify(mockedTagRepository).saveAndFlush(tag);
    }

    @Test(expected = TagAlreadyExistsException.class)
    public void shouldThrowTagExistsException() throws TagAlreadyExistsException {
        when(mockedTagRepository.existsById("Exists")).thenReturn(true);

        final Tag tag = new Tag("Exists");

        tagService.addTag(tag);
    }

    @Test
    public void shouldDeleteTagByIdSuccessfully() throws TagNotFoundException {
        when(mockedTagRepository.existsById("DeleteMe")).thenReturn(true);

        final Tag tag = new Tag("DeleteMe");

        tagService.deleteTag("DeleteMe");

        verify(mockedTagRepository).deleteById("DeleteMe");
    }

    @Test
    public void shouldDeleteTagSuccessfully() throws TagNotFoundException {
        when(mockedTagRepository.existsById("DeleteMe")).thenReturn(true);

        final Tag tag = new Tag("DeleteMe");

        tagService.deleteTag(tag);

        verify(mockedTagRepository).deleteById("DeleteMe");
    }

    @Test(expected = TagNotFoundException.class)
    public void shouldThrowExceptionWhenDeletingTagIdThatDoesNotExist() throws TagNotFoundException {
        when(mockedTagRepository.existsById("DeleteMe")).thenReturn(false);

        final Tag tag = new Tag("DeleteMe");

        tagService.deleteTag("DeleteMe");

        verify(mockedTagRepository).deleteById("DeleteMe");
    }

    @Test(expected = TagNotFoundException.class)
    public void shouldThrowExceptionWhenDeletingTagThatDoesNotExist() throws TagNotFoundException {
        when(mockedTagRepository.existsById("DeleteMe")).thenReturn(false);

        final Tag tag = new Tag("DeleteMe");

        tagService.deleteTag(tag);

        verify(mockedTagRepository).deleteById("DeleteMe");
    }

    @Test
    public void shouldUpdateValidTagSuccessfully() throws TagNotFoundException {
        when(mockedTagRepository.existsById("OriginalTag")).thenReturn(true);

        final Tag originalTag = new Tag("OriginalTag");
        originalTag.setDescription("Original Description");

        when(mockedTagRepository.findById("OriginalTag")).thenReturn(java.util.Optional.ofNullable(originalTag));

        final Tag input = new Tag("OriginalTag");
        input.setDescription("New Description");

        final Tag updated = tagService.updateTag("OriginalTag", input);

        verify(mockedTagRepository).existsById("OriginalTag");
        verify(mockedTagRepository).saveAndFlush(originalTag);

        assert (updated.equals(input));
        assert (updated.getDescription().equals(input.getDescription()));
    }

    @Test(expected = TagNotFoundException.class)
    public void shouldThrowExceptionWhenUpdating() throws TagNotFoundException {
        when(mockedTagRepository.existsById("OriginalTag")).thenReturn(false);

        final Tag originalTag = new Tag("OriginalTag");
        originalTag.setDescription("Original Description");

        final Tag input = new Tag("OriginalTag");
        input.setDescription("New Description");

        final Tag updated = tagService.updateTag("OriginalTag", input);

        verify(mockedTagRepository).existsById("OriginalTag");
        verify(mockedTagRepository).saveAndFlush(originalTag);
    }

    @Test
    public void shouldGetTagSuccessfully() throws TagNotFoundException {
        when(mockedTagRepository.existsById("GetMe")).thenReturn(true);

        final Tag tag = new Tag("GetMe");

        when(mockedTagRepository.findById("GetMe")).thenReturn(java.util.Optional.ofNullable(tag));

        assert (tagService.getTag("GetMe").equals(tag));

        verify(mockedTagRepository).findById("GetMe");
    }

    @Test(expected = TagNotFoundException.class)
    public void shouldThrowExceptionWhenGettingTag() throws TagNotFoundException {
        when(mockedTagRepository.existsById("GetMe")).thenReturn(false);

        tagService.getTag("GetMe");
    }

    @Test
    public void shouldGetAllTagsSuccessfully() {
        final Tag tag1 = new Tag("GetMe1");
        final Tag tag2 = new Tag("GetMe2");
        final Tag tag3 = new Tag("GetMe3");

        final List<Tag> tags = new ArrayList<Tag>();
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);

        when(mockedTagRepository.findAll()).thenReturn(tags);

        final List<Tag> allTags = tagService.getAllTags();

        assert allTags.get(0).equals(tag1);
        assert allTags.get(1).equals(tag2);
        assert allTags.get(2).equals(tag3);
    }

    @Test
    public void shouldGetAllTagsEmptySuccessfully() {
        final List<Tag> tags = new ArrayList<Tag>();

        when(mockedTagRepository.findAll()).thenReturn(tags);

        final List<Tag> allTags = tagService.getAllTags();

        assert allTags.size() == 0;
    }

    @Test
    public void shouldReturnTrueForTagExistsUsingId() {
        when(mockedTagRepository.existsById("Exists")).thenReturn(true);

        final boolean doesTagExist = tagService.doesTagExist("Exists");

        verify(mockedTagRepository).existsById("Exists");

        assert doesTagExist;
    }

    @Test
    public void shouldReturnTrueForTagExists() {
        when(mockedTagRepository.existsById("Exists")).thenReturn(true);

        final Tag tag = new Tag("Exists");

        final boolean doesTagExist = tagService.doesTagExist(tag);

        verify(mockedTagRepository).existsById("Exists");

        assert doesTagExist;
    }

    @Test
    public void shouldReturnFalseForTagExistsUsingId() {
        when(mockedTagRepository.existsById("NotExists")).thenReturn(false);

        final boolean doesTagExist = tagService.doesTagExist("NotExists");

        verify(mockedTagRepository).existsById("NotExists");

        assert !doesTagExist;
    }

    @Test
    public void shouldReturnFalseForTagExists() {
        when(mockedTagRepository.existsById("NotExists")).thenReturn(false);

        final Tag tag = new Tag("NotExists");

        final boolean doesTagExist = tagService.doesTagExist(tag);

        verify(mockedTagRepository).existsById("NotExists");

        assert !doesTagExist;
    }
}
