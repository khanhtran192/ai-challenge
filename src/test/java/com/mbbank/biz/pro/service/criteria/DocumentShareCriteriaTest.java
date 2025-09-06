package com.mbbank.biz.pro.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class DocumentShareCriteriaTest {

    @Test
    void newDocumentShareCriteriaHasAllFiltersNullTest() {
        var documentShareCriteria = new DocumentShareCriteria();
        assertThat(documentShareCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void documentShareCriteriaFluentMethodsCreatesFiltersTest() {
        var documentShareCriteria = new DocumentShareCriteria();

        setAllFilters(documentShareCriteria);

        assertThat(documentShareCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void documentShareCriteriaCopyCreatesNullFilterTest() {
        var documentShareCriteria = new DocumentShareCriteria();
        var copy = documentShareCriteria.copy();

        assertThat(documentShareCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(documentShareCriteria)
        );
    }

    @Test
    void documentShareCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var documentShareCriteria = new DocumentShareCriteria();
        setAllFilters(documentShareCriteria);

        var copy = documentShareCriteria.copy();

        assertThat(documentShareCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(documentShareCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var documentShareCriteria = new DocumentShareCriteria();

        assertThat(documentShareCriteria).hasToString("DocumentShareCriteria{}");
    }

    private static void setAllFilters(DocumentShareCriteria documentShareCriteria) {
        documentShareCriteria.id();
        documentShareCriteria.permission();
        documentShareCriteria.grantedAt();
        documentShareCriteria.documentId();
        documentShareCriteria.userId();
        documentShareCriteria.distinct();
    }

    private static Condition<DocumentShareCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getPermission()) &&
                condition.apply(criteria.getGrantedAt()) &&
                condition.apply(criteria.getDocumentId()) &&
                condition.apply(criteria.getUserId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<DocumentShareCriteria> copyFiltersAre(
        DocumentShareCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getPermission(), copy.getPermission()) &&
                condition.apply(criteria.getGrantedAt(), copy.getGrantedAt()) &&
                condition.apply(criteria.getDocumentId(), copy.getDocumentId()) &&
                condition.apply(criteria.getUserId(), copy.getUserId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
