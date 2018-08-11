/*
 * Copyright (c) 2018. Gavin Kenna
 */

package com.gkenna.tullamoreqa.domain;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Answer Entity, that is in response to a Question. Contains information about
 * the User who wrote it , and the Question it is Answering.
 *
 * @author Gavin Kenna
 * @since 0.0.1
 */
@Entity
@Table(name = "answers")
public class Answer extends Entry {

    /**
     * The Question that this Answer is in response to.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "question_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.REFRESH)
    private Question question;

    /**
     * A boolean to signify if this Answer is the chosen Answer for
     * the Question at hand. Only one Answer can be the Chosen Answer
     * for a Question.
     */
    private boolean chosenAnswer;

    /**
     * Default Constructor. Shouldn't be called.
     * See Effective Java (2nd+3rd Edition).
     */
    private Answer() {
        super();
    }

    /**
     * Constructor for Answer.
     *
     * @param question The Question this "Answer" is answering.
     * @param user     The User who wrote this Answer
     * @param body     The body of this Answer.
     */
    public Answer(final Question question, final User user, final String body) {
        super(user, body);
        this.question = question;
    }

    /**
     * The Question that this Answer is in reply to.
     *
     * @return The question at hand.
     */
    public final Question getQuestion() {
        return question;
    }

    /**
     * Set the Question we are answering.
     *
     * @param question The Question object.
     */
    public final void setQuestion(final Question question) {
        this.question = question;
    }

    /**
     * The body of the Answer, which contains the whole of the Answer.
     *
     * @return Entire Answer body.
     */
    public final String getBody() {
        return body;
    }

    /**
     * Set the body of the Answer.
     *
     * @param body Body of the Answer.
     */
    public final void setBody(final String body) {
        this.body = body;
    }

    /**
     * Is the Answer the Chosen Answer of the Question?
     *
     * @return True if chosen answer.
     */
    public final boolean isChosenAnswer() {
        return chosenAnswer;
    }

    /**
     * Set if this Answer is the chosen answer of the Question.
     *
     * @param chosenAnswer Boolean if this Answer is the chosen Answer.
     */
    public final void setChosenAnswer(final boolean chosenAnswer) {
        this.chosenAnswer = chosenAnswer;
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Answer)) {
            return false;
        }
        final Answer answer = (Answer) o;
        return isChosenAnswer() == answer.isChosenAnswer()
                && Objects.equals(getQuestion(), answer.getQuestion())
                && Objects.equals(getBody(), answer.getBody())
                && Objects.equals(getId(), answer.getId())
                && Objects.equals(getUpvotes(), answer.getUpvotes())
                && Objects.equals(getDownvotes(), answer.getDownvotes())
                && Objects.equals(getUser(), answer.getUser());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(getQuestion(), isChosenAnswer(), getBody(),
                getId(), getUpvotes(), getDownvotes(),
                getUser());
    }

    @Override
    public final String toString() {
        final StringBuilder sb = new StringBuilder("Answer{");
        sb.append("question=").append(question);
        sb.append(", chosenAnswer=").append(chosenAnswer);
        sb.append(", user=").append(user);
        sb.append(", body='").append(body).append('\'');
        sb.append(", upvotes=").append(upvotes);
        sb.append(", downvotes=").append(downvotes);
        sb.append('}');
        return sb.toString();
    }
}
