package com.mbbank.biz.pro.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SensitiveInfoCriteriaTest {

    @Test
    void newSensitiveInfoCriteriaHasAllFiltersNullTest() {
        var sensitiveInfoCriteria = new SensitiveInfoCriteria();
        assertThat(sensitiveInfoCriteria).is(criteriaFiltersAre(Objects::isNull));
    }

    @Test
    void sensitiveInfoCriteriaFluentMethodsCreatesFiltersTest() {
        var sensitiveInfoCriteria = new SensitiveInfoCriteria();

        setAllFilters(sensitiveInfoCriteria);

        assertThat(sensitiveInfoCriteria).is(criteriaFiltersAre(Objects::nonNull));
    }

    @Test
    void sensitiveInfoCriteriaCopyCreatesNullFilterTest() {
        var sensitiveInfoCriteria = new SensitiveInfoCriteria();
        var copy = sensitiveInfoCriteria.copy();

        assertThat(sensitiveInfoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::isNull)),
            criteria -> assertThat(criteria).isEqualTo(sensitiveInfoCriteria)
        );
    }

    @Test
    void sensitiveInfoCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var sensitiveInfoCriteria = new SensitiveInfoCriteria();
        setAllFilters(sensitiveInfoCriteria);

        var copy = sensitiveInfoCriteria.copy();

        assertThat(sensitiveInfoCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(Objects::nonNull)),
            criteria -> assertThat(criteria).isEqualTo(sensitiveInfoCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var sensitiveInfoCriteria = new SensitiveInfoCriteria();

        assertThat(sensitiveInfoCriteria).hasToString("SensitiveInfoCriteria{}");
    }

    private static void setAllFilters(SensitiveInfoCriteria sensitiveInfoCriteria) {
        sensitiveInfoCriteria.id();
        sensitiveInfoCriteria.infoType();
        sensitiveInfoCriteria.pageNumber();
        sensitiveInfoCriteria.position();
        sensitiveInfoCriteria.detectedAt();
        sensitiveInfoCriteria.documentId();
        sensitiveInfoCriteria.distinct();
    }

    private static Condition<SensitiveInfoCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getInfoType()) &&
                condition.apply(criteria.getPageNumber()) &&
                condition.apply(criteria.getPosition()) &&
                condition.apply(criteria.getDetectedAt()) &&
                condition.apply(criteria.getDocumentId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SensitiveInfoCriteria> copyFiltersAre(
        SensitiveInfoCriteria copy,
        BiFunction<Object, Object, Boolean> condition
    ) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getInfoType(), copy.getInfoType()) &&
                condition.apply(criteria.getPageNumber(), copy.getPageNumber()) &&
                condition.apply(criteria.getPosition(), copy.getPosition()) &&
                condition.apply(criteria.getDetectedAt(), copy.getDetectedAt()) &&
                condition.apply(criteria.getDocumentId(), copy.getDocumentId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
