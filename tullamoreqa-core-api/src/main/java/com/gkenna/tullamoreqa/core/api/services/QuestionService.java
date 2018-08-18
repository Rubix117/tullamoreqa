/*
 * Copyright (c) 2018. Gavin Kenna
 */

package com.gkenna.tullamoreqa.core.api.services;

import com.gkenna.tullamoreqa.core.api.exceptions.QuestionAlreadyExistsException;
import com.gkenna.tullamoreqa.core.api.exceptions.QuestionNotFoundException;
import com.gkenna.tullamoreqa.domain.Question;
import com.gkenna.tullamoreqa.domain.Tag;
import com.gkenna.tullamoreqa.domain.User;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

/**
 * API for interacting with {@link com.gkenna.tullamoreqa.domain.Question}.
 * This API communicates directly with the
 * {@link com.gkenna.tullamoreqa.core.api.repositories.QuestionRepository}.
 *
 * @author Gavin Kenna
 * @since 0.0.0
 */
@Service
public interface QuestionService {
    /**
     * Insert a new {@link Question} to the
     * {@link com.gkenna.tullamoreqa.core.api.repositories.QuestionRepository}.
     *
     * @param question {@link Question} to Add.
     * @throws QuestionAlreadyExistsException Thrown when a Question with the
     *                                        supplied ID already exists.
     */
    void addQuestion(final Question question)
            throws QuestionAlreadyExistsException;

    /**
     * Delete a {@link Question} from the
     * {@link com.gkenna.tullamoreqa.core.api.repositories.QuestionRepository}.
     *
     * @param question {@link Question} to Delete.
     * @throws QuestionNotFoundException Thrown when the {@link Question}
     *                                   cannot be found.
     */
    void deleteQuestion(final Question question)
            throws QuestionNotFoundException;

    /**
     * Delete a {@link Question} from the
     * {@link com.gkenna.tullamoreqa.core.api.repositories.QuestionRepository}.
     *
     * @param questionId ID of the {@link Question} to delete.
     * @throws QuestionNotFoundException Thrown the {@link Question} cannot
     *                                   be found.
     */
    void deleteQuestion(final BigInteger questionId)
            throws QuestionNotFoundException;

    /**
     * Update a {@link Question} on the Database.
     *
     * @param questionId The ID of the {@link Question} to update.
     * @param input      An {@link Question} container that holds
     *                   new values for questionId to update to.
     * @return The {@link Question} that was updated.
     * @throws QuestionNotFoundException Thrown when the {@link Question}
     *                                   cannot be found.
     */
    Question updateQuestion(final BigInteger questionId, final Question input)
            throws QuestionNotFoundException;

    /**
     * Return if a {@link Question} exists in the DB or not.
     *
     * @param question Does this {@link Question} exist?
     * @return True if the {@link Question} exists,
     * false if otherwise.
     */
    boolean doesQuestionExist(final Question question);

    /**
     * Return if an {@link  Question} exists in
     * the DB or not.
     *
     * @param questionId Does the ID for this
     *                   {@link Question} exist?
     * @return True if the {@link Question} exists,
     * false if otherwise.
     */
    boolean doesQuestionExist(final BigInteger questionId);

    /**
     * Return an {@link Question} based on its ID.
     *
     * @param questionId The ID of the {@link Question} to return.
     * @return A {@link Question} with the
     * ID passed.
     * @throws QuestionNotFoundException Thrown if the
     *                                   {@link Question} cannot be found.
     */
    Question getQuestion(final BigInteger questionId)
            throws QuestionNotFoundException;

    /**
     * Return all {@link Question}s in the DB.
     *
     * @return All {@link Question}s in the DB.
     */
    Iterable<Question> getAllQuestions();

    /**
     * Retrieve list of {@link Question}s whose Title matches the
     * supplied Title.
     *
     * @param title Title of the {@link Question}(s) to return.
     * @return Array of {@link Question}s.
     */
    Question[] findQuestionsByTitle(final String title);

    /**
     * Return a list of all {@link Question}s asked by a particular
     * {@link com.gkenna.tullamoreqa.domain.User}.
     *
     * @param user Filter all  {@link Question}s based on this
     *             {@link com.gkenna.tullamoreqa.domain.User}.
     * @return Array of {@link Question}s.
     */
    Question[] findQuestionsAskedByUser(final User user);

    /**
     * Return a list of all {@link Question}s answered by a particular
     * {@link com.gkenna.tullamoreqa.domain.User}.
     *
     * @param user Filter all  {@link Question}s based on this
     *             {@link com.gkenna.tullamoreqa.domain.User}.
     * @return Array of {@link Question}s.
     */
    Question[] findQuestionsAnsweredByUser(final User user);

    /**
     * Return a list of all {@link Question}s that are tagged by a particular
     * {@link Tag}.
     *
     * @param tag Filter all  {@link Question}s based on this
     *            {@link com.gkenna.tullamoreqa.domain.Tag}.
     * @return Array of {@link Question}s.
     */
    Question[] findQuestionsByTag(final Tag tag);

}
