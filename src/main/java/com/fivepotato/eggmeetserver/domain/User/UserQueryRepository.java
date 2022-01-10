package com.fivepotato.eggmeetserver.domain.User;

import com.fivepotato.eggmeetserver.domain.Mentoring.Category;
import com.fivepotato.eggmeetserver.dto.Mentoring.SortOrder;
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

    public List<User> findMentorsByMultipleConditionsOnPageable(Pageable pageable, Location location, Category mentorCategory,
                                                                SortOrder mentorRatingSortOrder, SortOrder growthPointOrder) {
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
                        eqMentorCategory(mentorCategory)
                )
                .orderBy(
                        orders.toArray(new OrderSpecifier[orders.size()])
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private BooleanExpression existMentorArea() {
        return QUser.user.mentorArea.id.isNotNull();
    }

    private BooleanExpression eqLocation(Location location) {
        if (location == null) {
            return null;
        }

        return QUser.user.location.eq(location);
    }

    private BooleanExpression eqMentorCategory(Category mentorCategory) {
        if (mentorCategory == null) {
            return null;
        }

        return QUser.user.mentorArea.category.eq(mentorCategory);
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
}
