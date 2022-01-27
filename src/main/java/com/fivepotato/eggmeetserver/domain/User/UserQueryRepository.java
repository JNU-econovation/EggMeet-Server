package com.fivepotato.eggmeetserver.domain.user;

import com.fivepotato.eggmeetserver.domain.mentoring.Category;
import com.fivepotato.eggmeetserver.dto.mentoring.SortOrder;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class UserQueryRepository extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public UserQueryRepository(JPAQueryFactory jpaQueryFactory) {
        super(User.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<User> findMentorsByMultipleConditionsOnPageable(Pageable pageable,
                                                                Location location,
                                                                Sex sex,
                                                                List<Integer> ages,
                                                                Boolean onlineAvailable,
                                                                Boolean offlineAvailable,
                                                                Category mentorCategory,
                                                                SortOrder mentorRatingSortOrder,
                                                                SortOrder growthPointOrder) {
        List<OrderSpecifier> orders = new ArrayList<>();
        if (mentorRatingSortOrder != null) {
            orders.add(orderByMentorRating(mentorRatingSortOrder));
        }
        if (growthPointOrder != null) {
            orders.add(orderByGrowthPoint(growthPointOrder));
        }

        return jpaQueryFactory
                .selectFrom(QUser.user)
                .where(
                        existMentorArea(),
                        eqLocationAllOrCondition(location),
                        eqSex(sex),
                        eqAges(ages),
                        eqIsOnlineAvailable(onlineAvailable),
                        eqIsOfflineAvailable(offlineAvailable),
                        eqMentorCategory(mentorCategory)
                )
                .orderBy(orders.toArray(new OrderSpecifier[orders.size()]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    public List<User> findMenteesByMultipleConditionsOnPageable(Pageable pageable,
                                                                Location location,
                                                                Sex sex,
                                                                List<Integer> ages,
                                                                Boolean onlineAvailable,
                                                                Boolean offlineAvailable,
                                                                Category category,
                                                                SortOrder menteeRatingSortOrder) {
        List<OrderSpecifier> orders = new ArrayList<>();
        if (menteeRatingSortOrder != null) {
            orders.add(orderByMenteeRating(menteeRatingSortOrder));
        }

        return jpaQueryFactory
                .selectFrom(QUser.user)
                .where(
                        existMenteeArea(),
                        eqLocationAllOrCondition(location),
                        eqSex(sex),
                        eqAges(ages),
                        eqIsOnlineAvailable(onlineAvailable),
                        eqIsOfflineAvailable(offlineAvailable),
                        eqMenteeCategory(category)
                )
                .orderBy(orders.toArray(new OrderSpecifier[orders.size()]))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression existMentorArea() {
        return QUser.user.mentorArea.id.isNotNull();
    }

    private BooleanExpression existMenteeArea() {
        return QUser.user.menteeArea.id.isNotNull();
    }

    private BooleanBuilder eqLocationAllOrCondition(Location location) {
        if (location == null) {
            return null;
        }

        BooleanBuilder locationBuilder = new BooleanBuilder();
        locationBuilder.or(QUser.user.location.eq(Location.ALL));
        locationBuilder.or(QUser.user.location.eq(location));

        return locationBuilder;
    }

    private BooleanExpression eqSex(Sex sex) {
        if (sex == null) {
            return null;
        }

        return QUser.user.sex.eq(sex);
    }

    private BooleanBuilder eqAges(List<Integer> ages) {
        if (ages == null) {
            return null;
        }

        BooleanBuilder ageBuilder = new BooleanBuilder();
        for (Integer age : ages) {
            int minAge = age;
            int maxAge = age + 9;
            ageBuilder.or(QUser.user.age.between(minAge, maxAge));
        }

        return ageBuilder;
    }

    private BooleanExpression eqIsOnlineAvailable(Boolean onlineAvailable) {
        if (onlineAvailable == null) {
            return null;
        }

        return QUser.user.onlineAvailable.eq(onlineAvailable);
    }

    private BooleanExpression eqIsOfflineAvailable(Boolean offlineAvailable) {
        if (offlineAvailable == null) {
            return null;
        }

        return QUser.user.offlineAvailable.eq(offlineAvailable);
    }

    private BooleanExpression eqMentorCategory(Category mentorCategory) {
        if (mentorCategory == null) {
            return null;
        }

        return QUser.user.mentorArea.category.eq(mentorCategory);
    }

    private BooleanExpression eqMenteeCategory(Category menteeCategory) {
        if (menteeCategory == null) {
            return null;
        }

        return QUser.user.menteeArea.category.eq(menteeCategory);
    }

    private OrderSpecifier orderByMentorRating(SortOrder sortOrder) {
        if (sortOrder.equals(SortOrder.ASCENDING)) {
            return QUser.user.mentorRating.asc();

        } else if (sortOrder.equals(SortOrder.DESCENDING)) {
            return QUser.user.mentorRating.desc();
        }

        return null;
    }

    private OrderSpecifier orderByGrowthPoint(SortOrder sortOrder) {
        if (sortOrder.equals(SortOrder.ASCENDING)) {
            return QUser.user.mentorArea.growthCost.asc();

        } else if (sortOrder.equals(SortOrder.DESCENDING)) {
            return QUser.user.mentorArea.growthCost.desc();
        }

        return null;
    }

    private OrderSpecifier orderByMenteeRating(SortOrder sortOrder) {
        if (sortOrder.equals(SortOrder.ASCENDING)) {
            return QUser.user.menteeRating.asc();
        } else if (sortOrder.equals(SortOrder.DESCENDING)) {
            return QUser.user.menteeRating.desc();
        }

        return null;
    }
}
