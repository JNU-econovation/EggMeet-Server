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
                                                                Integer age,
                                                                Boolean isOnlineAvailable,
                                                                Boolean isOfflineAvailable,
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
                        eqLocation(location),
                        eqSex(sex),
                        eqAge(age),
                        eqIsOnlineAvailable(isOnlineAvailable),
                        eqIsOfflineAvailable(isOfflineAvailable),
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
                                                                Integer age,
                                                                Boolean isOnlineAvailable,
                                                                Boolean isOfflineAvailable,
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
                        eqLocation(location),
                        eqSex(sex),
                        eqAge(age),
                        eqIsOnlineAvailable(isOnlineAvailable),
                        eqIsOfflineAvailable(isOfflineAvailable),
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

    private BooleanExpression eqLocation(Location location) {
        if (location == null) {
            return null;
        }

        return QUser.user.location.eq(location);
    }

    private BooleanExpression eqSex(Sex sex) {
        if (sex == null) {
            return null;
        }

        return QUser.user.sex.eq(sex);
    }

    private BooleanBuilder eqAge(Integer age) {
        if (age == null) {
            return null;
        }

        int minAge = age;
        int maxAge = age + 9;
        BooleanBuilder ageBuilder = new BooleanBuilder();
        ageBuilder.and(QUser.user.age.goe(minAge));
        ageBuilder.and(QUser.user.age.loe(maxAge));
        return ageBuilder;
    }

    private BooleanExpression eqIsOnlineAvailable(Boolean isOnlineAvailable) {
        if (isOnlineAvailable == null) {
            return null;
        }

        return QUser.user.isOnlineAvailable.eq(isOnlineAvailable);
    }

    private BooleanExpression eqIsOfflineAvailable(Boolean isOfflineAvailable) {
        if (isOfflineAvailable == null) {
            return null;
        }

        return QUser.user.isOfflineAvailable.eq(isOfflineAvailable);
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
            return QUser.user.mentorArea.growthPoint.asc();

        } else if (sortOrder.equals(SortOrder.DESCENDING)) {
            return QUser.user.mentorArea.growthPoint.desc();
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
