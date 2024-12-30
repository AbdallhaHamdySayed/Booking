package com.AlTaraf.Booking.database.Specifications;


import com.AlTaraf.Booking.database.entity.Feature;
import com.AlTaraf.Booking.database.entity.FeatureForHalls;
import com.AlTaraf.Booking.database.entity.SubFeature;
import com.AlTaraf.Booking.database.entity.Unit;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import java.util.Set;

public class UnitSpecifications {

    public static Specification<Unit> byUnitTypeId(Long unitTypeId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("unitType").get("id"), unitTypeId);
    }

    public static Specification<Unit> byHallTypeId(Long hallTypeId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("typesOfEventHalls").get("id"), hallTypeId);
    }

    public static Specification<Unit> byAccommodationTypeIds(Set<Long> accommodationTypeId) {
        return (root, query, criteriaBuilder) ->
                root.get("accommodationType").get("id").in(accommodationTypeId);
    }

    public static Specification<Unit> byCity(Long cityId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("city").get("id"), cityId);
    }


    public static Specification<Unit> byRegion(Long regionId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("region").get("id"), regionId);
    }


    public static Specification<Unit> byHotelClassificationIds(Set<Long> hotelClassificationId) {
        return (root, query, criteriaBuilder) ->
                root.get("hotelClassification").get("id").in(hotelClassificationId);
    }

    public static Specification<Unit> byEvaluationIds(Set<Long> evaluationId) {
        return (root, query, criteriaBuilder) ->
                root.get("evaluation").get("id").in(evaluationId);
    }

    public static Specification<Unit> byBasicFeaturesIds(Set<Long> basicFeaturesIds) {
        return (root, query, criteriaBuilder) -> {
            Join<Unit, Feature> featuresJoin = root.join("basicFeaturesSet", JoinType.INNER);
            return featuresJoin.get("id").in(basicFeaturesIds);
        };
    }

    public static Specification<Unit> byFeaturesHallsIds(Set<Long> featuresHallsIds) {
        return (root, query, criteriaBuilder) -> {
            Join<Unit, FeatureForHalls> featuresJoin = root.join("featuresHallsSet", JoinType.INNER);
            return featuresJoin.get("id").in(featuresHallsIds);
        };
    }


    public static Specification<Unit> bySubFeaturesIds(Set<Long> subFeaturesIds) {
        return (root, query, criteriaBuilder) -> {
            Join<Unit, SubFeature> subFeaturesJoin = root.join("subFeaturesSet", JoinType.INNER);
            return subFeaturesJoin.get("id").in(subFeaturesIds);
        };
    }

    public static Specification<Unit> byAvailablePeriod(Long availablePeriodId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isMember(availablePeriodId, root.get("availablePeriodsHallsSet").get("id"));
    }


    public static Specification<Unit> byCapacityHalls(int capacityHalls) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("capacityHalls"), capacityHalls);
    }

    public static Specification<Unit> byAdultsAllowed(int adultsAllowed) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("roomDetails").get("adultsAllowed"), adultsAllowed);
    }

    public static Specification<Unit> byChildrenAllowed(int childrenAllowed) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.join("roomDetails").get("childrenAllowed"), childrenAllowed);
    }


}